package server.database;
/** InsufficientColumnNumberException
 * 
 * <p>
 * Classe che gestisce l'eccezione del numero di colonne nella tabella del DB.
 * </p>
 */

public class InsufficientColumnNumberException extends Exception{
    
    private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe.
	 * @param msg
	 */
    public InsufficientColumnNumberException(String msg){
        super(msg);
    }
}
