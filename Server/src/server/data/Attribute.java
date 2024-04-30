package server.data;

import java.io.Serializable;

/**
 * Attribute
 * 
 * <p>
 * Classe che modella un attributo discreto o continuo.
 *</p>
 */
public abstract class Attribute implements Serializable{

	private static final long serialVersionUID = 10L;

	/**
	 * variabile di tipo stringa contenente il nome dell'attributo.
	 */
	protected String name;

	/**
	 * variabile di tipo intero contenente il nome dell'attributo.
	 */
	protected int index;

	  /**
     * costruttore classe Attribute.
     * @param name 
     * @param index
     */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

    /**
     * metodo che restituisce il nome dell'attributo.
     * @return name 
     */
	protected abstract String getName();
    /**
     * metodo che restituisce il nome dell'attributo.
     * @return index 
     */
	protected abstract int getIndex();

}
