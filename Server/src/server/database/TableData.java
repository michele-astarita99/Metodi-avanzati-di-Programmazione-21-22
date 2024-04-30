package server.database;

//import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;

import server.example.Example;
/** TableData
 * <p>
 * Classe che modella gli spostamenti all'interno della tabella.
 * </p>
 */
public class TableData {
	/**
	 * Attributo appartente alla classe DbAccess.
	 */
	private DbAccess db;
	/**
	 * Attributo che descrive il nome della tabella.
	 */
	private String table;
	/**
	 * Attributo che descrive lo schema della tabella.
	 */
	private TableSchema tSchema;
	@SuppressWarnings("rawtypes")
	/**
	 * Lista di target.
	 */
	private List target;
	/**
	 * Lista di Esempi.
	 */
	private List<Example> transSet;
	/**
	 * Costruttore della classe.
	 * @param db
	 * @param tSchema
	 * @throws SQLException
	 * @throws InsufficientColumnNumberException
	 */
	public TableData(DbAccess db, TableSchema tSchema) throws SQLException,InsufficientColumnNumberException{
		this.db=db;
		this.tSchema=tSchema;
		this.table=tSchema.getTableName();
		transSet = new ArrayList<Example>();
		target= new ArrayList<>();	
		init();
	}
	
	/**
	 * Metodo che inizializza i valori.
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void init() throws SQLException{		
		String query="select ";
		
		//Iterator<Column> it=tSchema.iterator();
		for(Column c:tSchema){			
			query += c.getColumnName();
			query+=",";
		}
		query +=tSchema.target().getColumnName();
		query += (" FROM "+table);
		
		Statement statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			Example currentTuple=new Example(tSchema.getNumberOfAttributes());
			int i=0;
			for(Column c:tSchema) {
				if(c.isNumber())
					currentTuple.set(rs.getDouble(i+1),i);
				else
					currentTuple.set(rs.getString(i+1),i);
				i++;
			}
			transSet.add(currentTuple);
			
			if(tSchema.target().isNumber())
				target.add(rs.getDouble(tSchema.target().getColumnName()));
			else
				target.add(rs.getString(tSchema.target().getColumnName()));
		}
		rs.close();
		statement.close();	
	}
	
	
	/**
	 * metodo che restituisce gli esempi.
	 * @return transSet
	 */
	public List<Example> getExamples(){
		return transSet; 
	}
	/**
	 * metodo che restituisce i target.
	 * @return target
	 */
	@SuppressWarnings("rawtypes")
	public List getTargetValues(){
		return target; 
	}
	/**
	 * Metodo che formula una query per stabilire i valori minimo e massimo della colonna.
	 * @param column
	 * @param aggregate
	 * @return x 
	 * @throws NoValueException
	 */
	public Object getAggregateColumnValue(Column column,QUERY_TYPE aggregate) throws NoValueException {
		Object x = new Object();
		try{
			Statement s = db.getConnection().createStatement();

			// codice SQL: può generare l’eccezione SQLException
			String q = "SELECT " + aggregate + "(" + column.getColumnName() + ")" +
			" FROM provac; ";
			//System.out.println(q);

			ResultSet r = s.executeQuery(q);
			if (r == null){
				throw new NoValueException("Nessun valore trovato");
			}
			if(r.next()){
				x = r.getString(1);
			}
			
			r.close();
			s.close(); // Also closes ResultSet

		} catch (SQLException ex) {
						// handle any errors
						System.out.println("SQLException: " + ex.getMessage());
						System.out.println("SQLState: " + ex.getSQLState());
						System.out.println("VendorError: " + ex.getErrorCode());
		}

	return x;
	}

}
