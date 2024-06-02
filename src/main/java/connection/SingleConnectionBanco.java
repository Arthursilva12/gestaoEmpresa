package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5433/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "admin";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {// Quando tiver uma instancia, vai conectar.
		conectar();
	}
	
	public static void conectar() {
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");// Carrega o driver de conexão do banco.
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);// Para não efetuar alterações do banco sem nosso comando
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
