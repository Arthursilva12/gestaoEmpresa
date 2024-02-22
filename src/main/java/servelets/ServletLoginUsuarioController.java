package servelets;

import java.io.IOException;
import java.util.List;

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

@MultipartConfig()// prepara a servlet para o upload da foto do usuï¿½rio
@WebServlet(urlPatterns =  {"/ServletLoginUsuarioController"})
 public class ServletLoginUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletLoginUsuarioController() {
    }

    // doGet geralmente é usado para deletar e consultar.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUSer = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUSer);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuario excluido!");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
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
				 
				 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
				 
				 ObjectMapper mapper = new ObjectMapper();
				 String json = mapper.writeValueAsString(dadosJsonUser);
				 
				 response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				 response.getWriter().write(json);
			} 
			else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				
				 String nomeBusca = request.getParameter("nomeBusca");
				 String pagina = request.getParameter("pagina");
				 
				 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioListOffset(nomeBusca, super.getUserLogado(request), pagina);
				 
				 ObjectMapper mapper = new ObjectMapper();
				 String json = mapper.writeValueAsString(dadosJsonUser);
				 
				 response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				 response.getWriter().write(json);
			} 
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String id = request.getParameter("id");
				 
			    ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
			 
			    List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			    request.setAttribute("modelLogins", modelLogins);
			     
			    request.setAttribute("msg", "Usuário em edição!");
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				 
				 request.setAttribute("msg", "Usuários carregados");
			     request.setAttribute("modelLogins", modelLogins);
			     request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				 
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				 
				ModelLogin modelLogin =  daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request));//Para o navegador fazer o dawnload.
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					//setHeader é um cabessalho. 
					//As informações dentro do parenese  são comando para o navegador idenficar um dawnload.
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new org.apache.commons.codec.binary.Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				 
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				 
				request.setAttribute("modelLogins", modelLogins);
			    request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 }else {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	// doPost geralmente são usado para salvar(gravar) e atualizar
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String msg = "Operação realizada com sucesso!";
			 
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String localidade = request.getParameter("localidade");
			String bairro = request.getParameter("bairro");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			
			ModelLogin modelLogin = new ModelLogin(); 
			
			// faz uma condiÃ§Ã£o se contem valor, se sim depois fazer a conversÃ£o de texto para Long
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setBairro(bairro);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			
			
			if(request.getPart("fileFoto") == null) {
				
				Part part = request.getPart("fileFoto");
				// Verificação se conte foto
				if(part.getSize() > 0) {
					byte[] foto = org.apache.commons.compress.utils.IOUtils.toByteArray(part.getInputStream());// Converte a imagem para byte.
					String imgBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + org.apache.commons.codec.binary.Base64.encodeBase64String(foto);
					
	 				modelLogin.setFotouser(imgBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);// Extensão da imagem.
				}
			}
			
			// Se usuario existe(true) e o Id for dirente de nulo(true), ï¿½ jogado uma mensagem.
			// mas se o usuario nï¿½o existe(false) e o ID for diferente de nulo(true), usuario Ã© cadastrado.
			// Se o ID for nulo, ira gravar o usuario, caso contrario manda a mensagem.
			// Se o ID for diferente de nulo e diferente de vazio e porque jï¿½ existe.
			if(daoUsuarioRepository.validarLogin(modelLogin) && modelLogin.getId() == null) {
				msg = "Jï¿½ existe usuario com o mesmo login, informe outro login!";
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
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}