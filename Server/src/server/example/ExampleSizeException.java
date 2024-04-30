package server.example;
/** ExampleSizeException
 * <p>
 * Classe che gestisce una possibile eccezione nella classe Example.
 * </p>
 */
public class ExampleSizeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore classe.
	 */
	ExampleSizeException(){
        super();
    }
	/**
	 * Costruttore classe.
	 * @param s
	 */
    ExampleSizeException(String s){
        super(s);
    }
    
}
