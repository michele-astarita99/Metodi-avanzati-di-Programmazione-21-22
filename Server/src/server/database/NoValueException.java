package server.database;
/** NoValueException
 * 
 * <p>
 * Classe che gestisce l'eccezione riguardante un eventuale valore nullo.
 * </p>
 */
public class NoValueException extends Exception {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe.
	 * @param msg
	 */
	public NoValueException(String msg) {
		
		super(msg);
	}

}
