package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@WebFilter(urlPatterns = { "/principal/*" }) // Intercepta todas as requisiss�es que vierem do projeto ou mapeamento.
public class FilterAutenticacao extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private static Connection connection;

	public FilterAutenticacao() {
	}

	// Encerra os processos quando o servidor é parado.
	// Ele mataria os processos de cone��o com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Ele intercepta todas as requisiss�es e as respostas do meu sistema.
	// Tudo o que fizer no sistema passara por ele.
	// Valida��o de autenti��o.
	// Dar commit e rolback de transações.
	// validar e fazer redirecionamento de paginas.
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath();// Verifica a Url que está sendo acessada

			// Validar se está logado, senão, ele vai redirecionara para tela de login
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {// N�o est� logado

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;// Para a execução e redireciona para o login

			} else {
				// O chain do filter deixa o processo do software continuar.
				chain.doFilter(request, response);
			}
			
			connection.commit();// Deu tudo certo, então comita as alterações no banco de dados.
			
		} catch (Exception e) {
			e.printStackTrace();
			
			// Ira redirecionar a mensagem de erro na tela do ussuario.
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	/* Ele � executado quando inicia o sistema, e iniciar todos os processos ou
	 * recursos quando o servidor sobe o projeto
	 * 
	 * Inicia a conex�o ao banco.*/
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
