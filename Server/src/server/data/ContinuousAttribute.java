package server.data;
/** Continuos Attribute
 * 
 * <p>
 * Estensione della classe Attribute che identifica un attributo continuo.
 * </p>
 *
 */
class ContinuousAttribute extends Attribute {

	private static final long serialVersionUID = 1L;
	/**
	 * variabile che identifica il valore più piccolo che può essere assunto.
	 */
	private double min = Double.POSITIVE_INFINITY;
	/**
	 * variabile che identifica il valore più grande che può essere assunto.
	 */
	private double max = Double.NEGATIVE_INFINITY;

	/**
	 *metodo che richiama il costruttore della superclasse Attribute.
	 *
	 * @param name  Nome dell'attributo.
	 * @param index Numero indetificativo dell'attributo.
	 */
	public ContinuousAttribute(String name, int index) {
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

	/**
	 * metodo che aggiorna il valore minimo in base a v.
	 *
	 * @param v Parametro passato in input.
	 */
	public void setMin(Double v) {
		if (v < min) {
			min = v;
		}
	}

	/**
	 * metodo che aggiorna il massimo in base a v.
	 *
	 * @param v Parametro passato in input.
	 */
	public void setMax(Double v) {
		if (v > max) {
			max = v;
		}
	}

	/**
	 * metodo che restituisce il valore scalato.
	 *
	 * @param value parametro passato in input
	 * @return double
	 */
	double scale(Double value) {
		return (value - min) / (max - min);
	}
	@Override
	public String toString(){
        return "name: " + name + " index: " + index + " min: " + min + " max: " + max;
    }

}