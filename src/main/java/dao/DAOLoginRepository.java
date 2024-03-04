package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {
  
	private Connection connection;
	
	// Sempre que chamar essa class, já vai ter o conexão. 
	public  DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	// Metodo de validação de login
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sqlConsulta = "Select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		PreparedStatement statement = connection.prepareStatement(sqlConsulta);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		// Seta os valores na variavel
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return true;
		}else {
			return false;
		}
	}
}