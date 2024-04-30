package server.database;
/** Column
 * 
 *<p>
 * Classe che gestisce le colonne del Db.
 * </p>
 *
 */
public class Column {
	/**
	 * variabile che descrive il nome della colonna.
	 */
    private String name;
    /**
     * variabile che descrive il tipo della colonna.
     */
	private String type;
	/**
	 * Costruttore della classe
	 * @param name
	 * @param type
	 */
	Column(String name,String type){
		this.name=name;
		this.type=type;
	}
	/**
	 * metodo che restituisce il nome della colonna.
	 * @return name
	 */
	public String getColumnName(){
		return name;
	}
	/**
	 * metodo di verifica se il valore è effettivamente un numero.
	 * @return bool
	 */
	public boolean isNumber(){
		return type.equals("number");
	}
	@Override
	public String toString(){
		return name+":"+type;
	}
}
