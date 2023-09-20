package servelets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet(urlPatterns = {"/ServletLoginUsuarioControler", "/principal/usuario.jsp"})
public class ServletLoginUsuarioControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletLoginUsuarioControler() {
    }

    // doGet geralmente é usado para deletar e consultar.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String idUSer = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUSer);

				request.setAttribute("msg", "Usuario excluido!");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				String idUSer = request.getParameter("id");

				daoUsuarioRepository.deletarUser(idUSer);

				response.getWriter().write("Excluido com sucesso");
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				String nomeBusca = request.getParameter("nomeBusca");
				System.out.println(nomeBusca);

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultarUserAjax(nomeBusca);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dadosJsonUser);

				response.getWriter().write(json);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id = request.getParameter("id");

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(id);

				request.setAttribute("msg", "Usuario em edição");
				request.setAttribute("modelLogin", modelLogin);// Retorna os valores para tela
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				/*
				 * Tem que terminar o metodo de listar usuarios
				 */
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioList();
				
				List<ModelLogin> UserList = new ArrayList<>();
				UserList.add(modelLogin);
				
				request.setAttribute("msg", "Usuarios carregados");
				request.setAttribute("modelLogins", UserList);// Retorna os valores para tela
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}else {

				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

	
	// doPost geralmente é usado para salvar(gravar) e atualizar
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String msg = "Operação realizada com sucesso!";
			 
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			ModelLogin modelLogin = new ModelLogin(); 
			
			// faz uma condição se contem valor, se sim depois fazer a conversão de texto para Long
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			// Se usuario existe(true) e o Id for dirente de nulo(true), é jogado uma mensagem.
			// mas se o usuario não existe(false) e o ID for diferente de nulo(true), usuario é cadastrado.
			// Se o ID for nulo, ira gravar o usuario, caso contrario manda a mensagem.
			// Se o ID for diferente de nulo e diferente de vazio e porque le já existe.
			if(daoUsuarioRepository.validarLogin(modelLogin) && modelLogin.getId() == null) {
				msg = "Já existe usuario com o mesmo login, informe outro login!";
			}else {
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);
			}
			
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
