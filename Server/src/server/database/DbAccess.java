package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/** DbAccess
 * 
 * <p>
 * Gestisce l'accesso al DB per la lettura dei dati di training set.
 * </p>
 *
 */
public class DbAccess {

	//private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final int PORT = 3306;
	private final String DATABASE = "map";
	private final String USER_ID = "root";
	private final String PASSWORD = "dario00";

	private Connection conn;

	/**
	 * Inizializza una connessione al DB
	 */
	public DbAccess() throws DatabaseConnectionException{
		String connectionString =  DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
	
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}
	/**
	 * metodo ausiliario per la gestione della connessione.
	 * @return conn
	 */
	public  Connection getConnection(){
		return conn;
	}
	/**
	 * metodo che chiude la connessione.
	 */
	public  void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}


}
