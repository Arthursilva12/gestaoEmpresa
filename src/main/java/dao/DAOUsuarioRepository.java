package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {

		if(objeto.isNovo()) {// Gravar um novo
			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?,?,?,?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.execute();
	
			connection.commit();// Commita no banco.
		}else {// Update 
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.executeUpdate();
			
			connection.commit();
		}
		
		return this.consultaUsuario(objeto.getLogin());
	}

	
	public List<ModelLogin> consultarUserAjax(String nome) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {// Percorrer a linha de resultado do SQL.
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
//			modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}
	
	
	public ModelLogin consultaUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		// seta os valores dentro da variavel que foi pego no banco.
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // Percorre o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			
		}
		
		return modelLogin;
	}
	
	
	public ModelLogin consultarUsuarioID(String id) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) { 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}
		
		return modelLogin;
	}
	
	
	public ModelLogin consultarUsuarioList() throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // Percorre o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}
		
		return modelLogin;
	}
	

	public boolean validarLogin(ModelLogin login) throws Exception {
		
		/*O count ele retorna os registro dentro de uma determinada coluna
		 * Aqui ele está fazendo uma verificação vendo se contem um usuario dentro da coluna
		 * O maior que zero e para retorna um TRUE ou FALSE inves do ID*/
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login.getLogin()+"');";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		resultado.next();// Pra ele entrar nos resultados do SQL.
		return resultado.getBoolean("existe");
	}
	
	
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "DELETE FROM model_login WHERE id = ?;";
		
		PreparedStatement deleteUser =  connection.prepareStatement(sql);
		deleteUser.setLong(1, Long.parseLong(idUser ));
		deleteUser.executeUpdate();
		
		connection.commit();
	}
	
	

}
