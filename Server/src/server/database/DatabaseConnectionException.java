package server.database;
/** DatabaseConnectionException
 * 
 *  <p>
 *  Classe che gestisce le possibili eccezioni del Db.
 * </p>
 */
public class DatabaseConnectionException extends Exception{
    private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe.
	 * @param msg
	 */
    public DatabaseConnectionException(String msg){
        
        super(msg);
    }
}
