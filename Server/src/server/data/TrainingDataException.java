package server.data;

/** TrainingDataException
 * 
 *
 * <p>
 * classe eccezione che gestisce un eventuale errore
 * nell'acquisizione del training set.
 * </p>
 */
public class TrainingDataException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * costruttore classe.
	 */
    public TrainingDataException(){

        super();
		
    }
	/**
	 * costruttore classe.
	 * @param s
	 */
	public TrainingDataException(String s) {

		super(s);
	}

}
