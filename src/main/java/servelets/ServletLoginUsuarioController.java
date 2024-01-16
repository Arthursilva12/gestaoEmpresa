package servelets;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

@MultipartConfig()// prepara a servlet para o upload da foto do usu�rio
@WebServlet(urlPatterns =  {"/ServletLoginUsuarioController"})
 public class ServletLoginUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	
//	private Object ServletFileUpload;

    public ServletLoginUsuarioController() {
    }

    // doGet geralmente � usado para deletar e consultar.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUSer = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUSer);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuario excluido!");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} 
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				String idUSer = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUSer);
				 
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				 
				response.getWriter().write("Excluido com sucesso");
			}
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
					
				ObjectMapper mapper = new ObjectMapper();
			    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dadosJsonUser);
			    
				response.getWriter().write(json);
			} 
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
				 
			    ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
			 
			    List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			    request.setAttribute("modelLogins", modelLogins);
			     
			    request.setAttribute("msg", "Usu�rio em edi��o!");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 
				 request.setAttribute("msg", "Usu�rios carregados");
			     request.setAttribute("modelLogins", modelLogins);
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				 
				ModelLogin modelLogin =  daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request));//Para o navegador fazer o dawnload.
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					//setHeader é um cabessalho. 
					//As informações dentro do parenese  são comando para o navegador idenficar um dawnload.
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new org.apache.tomcat.util.codec.binary.Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}
				
			}else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	
	// doPost geralmente s�o usado para salvar(gravar) e atualizar
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String msg = "Opera��o realizada com sucesso!";
			 
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			
			ModelLogin modelLogin = new ModelLogin(); 
			
			// faz uma condição se contem valor, se sim depois fazer a conversão de texto para Long
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			
			
			if(org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload.isMultipartContent(request)) {
				
				Part part = request.getPart("fileFoto");
				// Verifica��o se conte foto
				if(part.getSize() > 0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream());// Converte a imagem para byte.
					String imgBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + Base64.encodeBase64String(foto);
					
	 				modelLogin.setFotouser(imgBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);// Extens�o da imagem.
				}
			}
			
			// Se usuario existe(true) e o Id for dirente de nulo(true), � jogado uma mensagem.
			// mas se o usuario n�o existe(false) e o ID for diferente de nulo(true), usuario é cadastrado.
			// Se o ID for nulo, ira gravar o usuario, caso contrario manda a mensagem.
			// Se o ID for diferente de nulo e diferente de vazio e porque j� existe.
			if(daoUsuarioRepository.validarLogin(modelLogin) && modelLogin.getId() == null) {
				msg = "J� existe usuario com o mesmo login, informe outro login!";
			}else {
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);// Retorna os valores para tela
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}