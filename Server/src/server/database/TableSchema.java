package server.database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/** TableSchema
 * 
 * <p> 
 * Classe che modella lo schema della tabella.
 * </p>
 *
 */

public class TableSchema implements Iterable<Column>{
	/**
	 * Lista di colonne
	 */
	private List<Column> tableSchema=new ArrayList<Column>();
	/**
	 * valore target
	 */
	private Column target;
	/**
	 * stringa per memorizzare il nome della tabella.
	 */
	private String tableName;
	
	/**
	 * costruttore della classe.
	 * @param tableName
	 * @param db
	 * @throws SQLException
	 * @throws InsufficientColumnNumberException
	 * @throws DatabaseConnectionException
	 */
	public TableSchema(String tableName, DbAccess db) throws SQLException,InsufficientColumnNumberException, DatabaseConnectionException{
		this.tableName=tableName;
		
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		 DatabaseMetaData meta = db.getConnection().getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
	     
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        	if(res.isLast()) 
	        		target=new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 ;
	        	else
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	
	         
	         
	      }
	     
	      res.close();
	      if(target==null)
		  	throw new DatabaseConnectionException("Tabella non presente nel Database");
		  else if (tableSchema.size()==0) 
		  	throw new InsufficientColumnNumberException("La tabella selezionata contiene meno di due colonne");
		
		}

		/**
		 * Metodo che restituisce la colonna.
		 * @return target
		 */
		public Column target(){
			return target;
		}
		/**
		 * metodo che restituisce il numero di Attributi.
		 * @return tableSchema.size
		 */		
		public int getNumberOfAttributes() {
			return tableSchema.size();
		}
		/**
		 * metodo che restituisce il nome della tabella.
		 * @return tableName
		 */
		String getTableName() {
			return tableName;
		}

		@Override
		public Iterator<Column> iterator() {
			return tableSchema.iterator();
		}

		
	}

		     


