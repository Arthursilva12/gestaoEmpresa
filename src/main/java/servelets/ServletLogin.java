package servelets;

import java.io.IOException;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/*
 * O chamado Controller são as servlets, ou servletLoginController
 */
@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) // Mapeamento de URL que vem da tela
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public ServletLogin() {
	}

	// Receber os dados pela URL em parametros(pela tela) em parametros
	// O doGet, ele intercepta quando é acionado a url
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate(); // invalida || finaliza a sesão, ou seja, apaga os atributos.
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
			doPost(request, response);
		}
	}

	// Recebe os dados enviados por um formulario.
	// O doPost ele é acionado quando é acionado o formulario.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			/*
			 * isEmpty() quer dizer vazio, ou seja, aqui faz o teste se (!login.isEmpty())
			 * login/senha e diferente de vazio
			 */
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				// Esse if mantem a pessoa logada caso acerte o login e senha correto.
				if (daoLoginRepository.validarAutenticacao(modelLogin)) {

					modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
					
					// Aqui redireciona o usuario para outra tela.
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					
					request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());	
					
					// Se a URL for nula, vai fazer a verifica��o e setar o valor. Assim vai
					// continuar o fluco
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					request.getRequestDispatcher(url).forward(request, response);

				} else {
					// RequestDispatcher redireciona a pessoa para a tela que foi passa por
					// parametro.
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente!");
					redirecionar.forward(request, response);
				}

			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();

			// Ira redirecionar a mensagem de erro na tela do ussuario.
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
}
