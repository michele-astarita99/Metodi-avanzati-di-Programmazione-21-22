package server.data;

/** Discrete Attribute
 * 
 *<p>
 *Estensione della classe Attribute che identifica un attributo discreto .
 * </p>
 */
public class DiscreteAttribute extends Attribute {
	
	private static final long serialVersionUID = 2L;
	/**
	 * metodo che invoca il costruttore della classe Attribute.
	 * @param name
	 * @param index
	 */
	DiscreteAttribute(String name, int index) {
		super(name, index);
	}
	/**
	 * metodo che restituisce il nome dell'attributo.
	 */
	protected String getName(){
        return name;
    }
    /**
     * metodo che restituisce il numero dell'attributo.
     */
    protected int getIndex(){
        return index;
    }

}