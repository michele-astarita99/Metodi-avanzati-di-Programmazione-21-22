package server.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import server.database.Column;
import server.database.DatabaseConnectionException;
import server.database.DbAccess;
import server.database.InsufficientColumnNumberException;
import server.database.TableData;
import server.database.TableSchema;
import server.example.Example;
import server.example.ExampleSizeException;

/** Data
 * 
 * <p>
 * Classe che modella il training set.
 *</p>
 */
public class Data implements Serializable{

	private static final long serialVersionUID = 3L;

	/**
	 * array di oggetti istanza della classe Example.
	 */
	private ArrayList<Example> data;
	/**
	 * array di oggetti contenenti variabili target.
	 */
	private ArrayList<Double> target;
	/**
	 * array di attributi che definiscono lo schema degli oggetti.
	 */
	private ArrayList<Attribute> explanatorySet;
	/**
	 * numero di esempi.
	 */
	int numberOfExamples;
	/**
	 * Attributo target.
	 */
	ContinuousAttribute classAttribute;

	/**
	 * costruttore classe Data.
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws TrainingDataException
	 */
	@SuppressWarnings({"resource"})
	public Data(String fileName)throws FileNotFoundException, TrainingDataException{
		  
		File inFile = new File (fileName);
		if (inFile.exists() == false)
		  throw new TrainingDataException("@ERROR File inesistente");
		if (inFile.length() == 0)
		  throw new TrainingDataException("@ERROR File vuoto");

		Scanner sc = new Scanner (inFile);
		String line = sc.nextLine();

		if(!line.contains("@schema"))
			throw new RuntimeException("@ERROR Errore nello schema");
		String s[]=line.split(" ");
	  
		

		//popolare explanatory Set 
		explanatorySet = new ArrayList<Attribute>();
		
		short iAttribute=0;
		line = sc.nextLine();
		while(!line.contains("@data")){
			s=line.split(" ");
			if(s[0].equals("@desc"))
			{ // aggiungo l'attributo allo spazio descrittivo
				  //@desc motor discrete 
				  if (s[2].equals("discrete"))
					//explanatorySet[iAttribute] = new DiscreteAttribute(s[1],iAttribute);
					  explanatorySet.add(new DiscreteAttribute(s[1],iAttribute));
				  else if (s[2].equals("continuous")){
					  explanatorySet.add(new ContinuousAttribute(s[1],iAttribute));
				  }
			}
			else if(s[0].equals("@target"))
					classAttribute=new ContinuousAttribute(s[1], iAttribute);
					
			iAttribute++;
			line = sc.nextLine();
			
		}
			
		//avvalorare numero di esempi
		 numberOfExamples= Integer.valueOf(line.split(" ")[1]);
		 
		//popolare data e target
		data = new ArrayList<>(numberOfExamples);;
		target = new ArrayList<>(numberOfExamples);
		
		while (sc.hasNextLine())
		{
			Example e=new Example(getNumberOfExplanatoryAttributes());
			line = sc.nextLine();
			// assumo che attributi siano tutti discreti
			s=line.split(","); //E,E,5,4, 0.28125095
		  
		  for(short jColumn=0;jColumn<s.length-1;jColumn++){
			  int i=0;	
			  for(Attribute a:explanatorySet)
			  {
				  if(a instanceof DiscreteAttribute) {
					  e.set(String.valueOf(s[i]),i);	
				  }
				  else if(a instanceof ContinuousAttribute) {
					  e.set(Double.valueOf(s[i]), i);
					  ((ContinuousAttribute) a).setMin(Double.valueOf(s[i]));
					  ((ContinuousAttribute) a).setMax(Double.valueOf(s[i]));
			  }
			  i++;
			  }
		  }

		  data.add(e);
		  target.add(Double.valueOf(s[s.length-1]));
			
		}
	  
		  sc.close();

  }

	/**
	 * costruttore classe Data che preleva i valori dal DB.
	 * @param db
	 * @param tableName
	 * @throws TrainingDataException
	 * @throws InsufficientColumnNumberException
	 * @throws DatabaseConnectionException
	 * @throws SQLException
	 * @throws ExampleSizeException
	 */
	@SuppressWarnings("unchecked")
	public Data(DbAccess db, String tableName)throws TrainingDataException, InsufficientColumnNumberException, DatabaseConnectionException, SQLException{
		
		data = new ArrayList<>();
		target = new ArrayList<>();
		explanatorySet = new ArrayList<Attribute>();
		short iAttribute = 0;
		
		try {
			TableSchema ts = new TableSchema(tableName, db);
			if (!ts.target().isNumber()) {
				throw new TrainingDataException("Attributo target non numerico");
			}
			for (Column c: ts){
				if (c.isNumber()) {
					explanatorySet.add(new ContinuousAttribute(c.getColumnName(), iAttribute));
				}
				else {
					explanatorySet.add(new DiscreteAttribute(c.getColumnName(), iAttribute));
				}
					classAttribute = new ContinuousAttribute(ts.target().getColumnName(), iAttribute);
					iAttribute++;
			}
				
			
			TableData t = new TableData(db, ts);
			data = (ArrayList<Example>) t.getExamples();
			target = (ArrayList<Double>) t.getTargetValues();
			numberOfExamples = data.size();
			Statement s = db.getConnection().createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM " + tableName + ";");
			while(r.next()) {
				Example e=new Example(numberOfExamples);
				int i=0;	
				for(Attribute a:explanatorySet)
				{
					if(a instanceof DiscreteAttribute) {
						e.set(String.valueOf(r.getString(a.getName())),i);
					}
					else if(a instanceof ContinuousAttribute) {
						e.set(Double.valueOf(r.getString(a.getName())), i);	
						((ContinuousAttribute) a).setMin(Double.valueOf(r.getString(a.getName())));
						((ContinuousAttribute) a).setMax(Double.valueOf(r.getString(a.getName())));
					}
				i++;
				r.next();
			}
			
		}
			r.close();
			s.close(); // Also closes Result/* */

		} catch (SQLException ex) {
					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
		}
		

	}

	/**
	 * metodo che restitusce il numero di esempi del'explanatory set.
	 */
	private int getNumberOfExplanatoryAttributes() {
		return explanatorySet.size();
	}

	/**
	 * Partiziona data rispetto all'elemento x di key e restiutisce il punto di separazione.
	 * @param key
	 * @param inf
	 * @param sup
	 * @return j
	 */
	private int partition(ArrayList<Double> key, int inf, int sup){
		int i,j;
	
		i=inf; 
		j=sup; 
		int	med=(inf+sup)/2;

		Double x=key.get(med);
		data.get(inf).swap(data.get(med));

		double temp = target.get(inf);
		target.set(inf, target.get(med));
		target.set(med, temp);
		
		temp=key.get(inf);
		key.set(inf, key.get(med));
		key.set(med, temp);
		
		while (true) 
		{
			while(i<=sup && key.get(i) <=x){ 
				i++; 
			}
		
			while(key.get(j)>x) {
				j--;
			}
			
			if(i<j) { 
				
				data.get(i).swap(data.get(j));
			   
				temp = target.get(i);
				target.set(i, target.get(j));
				target.set(j, temp);
				
				temp=key.get(i);
				key.set(i, key.get(j));
				key.set(j, temp);
					
			}
			else break;
		}
		
		data.get(inf).swap(data.get(j));
	    
		temp = target.get(inf);
		target.set(inf, target.get(j));
		target.set(j, temp);
		
		temp = key.get(inf);
		key.set(inf, key.get(j));
		key.set(j, temp);
		
		return j;

	}

	/**
	 * Algoritmo quicksort per l'ordinamento di data 
	 * usando come relazione d'ordine totale "<=" definita su key.
	 * @param key
	 * @param inf
	 * @param sup
	 */
	private void quicksort(ArrayList<Double> key, int inf, int sup) throws ExampleSizeException {

		if (sup >= inf) {

			int pos;

			pos = partition(key, inf, sup);

			if ((pos - inf) < (sup - pos + 1)) {
				quicksort(key, inf, pos - 1);
				quicksort(key, pos + 1, sup);
			} else {
				quicksort(key, pos + 1, sup);
				quicksort(key, inf, pos - 1);
			}

		}

	}

	/**
	 * metodo che setta i valori di key con le distanze calcolate attraverso
	 * il metodo distance tra tutti gli esempi memorizzati in data ed e;
	 * inoltre ordina key, data e target in base ai valori presenti in key.
	 * Restituisce la media dei valori del target in accordo agli esempi precedenti
	 * e ne calcola la distanza.
	 * @param e
	 * @param k
	 * @return avg 
	 */
	public double avgClosest(Example e, int k) throws ExampleSizeException {
		
		ArrayList<Double> key = new ArrayList<>();
		Example x = scaledExample(e);

		for (Example it : data) {
			Example s = scaledExample(it);
			key.add(s.distance(x));
		}

		quicksort(key, 0, key.size()-1);

		String sorted = new String("Sorted: \n");
		for (Iterator<Example> i = data.iterator(); i.hasNext();){
			for (Iterator<Double> j = target.iterator(); j.hasNext();)
			   	sorted = sorted + i.next() + " " + j.next() + "\n";
			}
		System.out.println(sorted);
	
		String dist = new String("Distances: [");
		for (Iterator<Double> i = key.iterator(); i.hasNext();){
			 	dist = dist + i.next() + " , ";
			}
		dist = dist + "]";
		System.out.println(dist);

		double sum = 0;
		
		for (int i = 0; i<k; i++){
			sum = sum + target.get(i);
		}
		double avg = sum/k;
		
		return avg; 
	}

	@Override
	public String toString() {
		String s = "Training set: \n";
		Iterator<Double> it = target.iterator();

		for (Example example : data) {

			s = s + example + " " + " " + it.next() + "\n";

		}

		return s;
	}

	

	/**
	 * metodo che legge l'esempio.
	 *
	 * @param out 
	 * @param in  
	 *
	 * @throws IOException, ClassNotFoundException, ClassCastException
	 * @return e
	 */
	
	public Example readExample(ObjectOutputStream out, ObjectInputStream in)
			throws IOException, ClassNotFoundException, ClassCastException {
		Example e = new Example(getNumberOfExplanatoryAttributes());
		int i = 0;
		String verifica;
		boolean flag = false;

		for (Attribute a : explanatorySet) {
			if (a instanceof DiscreteAttribute) {
				do {
					out.writeObject("@READSTRING");
					out.writeObject("Inserisci valore discreto X[" + i + "]:");
					verifica = (String) in.readObject();
					if (verifica.equals("@STRINGAVUOTA")) {
						flag = false;
					} else {
						e.set(in.readObject(), i);
						flag = true;
					}
				} while (!flag);

			} else {
				do {
					out.writeObject("@READDOUBLE");
					out.writeObject("Inserisci valore continuo X[" + i + "]:");
					verifica = (String) in.readObject();

					if (verifica.equals("@DOUBLECORRETTO")) {

						e.set(in.readObject(), i);
						flag = true;

					} else if (verifica.equals("@DOUBLESBAGLIATO")) {

						flag = false;

					}
				} while (!flag);

			}
			i++;
		}
		out.writeObject("@ENDEXAMPLE");
		return e;
	}

	/**
	 * metodo che restituisce l'esempio scalato.
	 * @param e
	 * @return s 
	 */
	private Example scaledExample(Example e) {
		Example s = new Example(getNumberOfExplanatoryAttributes());
			for (int i = 0; i < getNumberOfExplanatoryAttributes(); i++){
				int j = 0;
				for (Attribute a: explanatorySet){
					if (a instanceof DiscreteAttribute){
						s.set(e.get(i), i);
					}
					else if (a instanceof ContinuousAttribute){
						double x = Double.parseDouble(e.get(j).toString());
						double scaled = ((ContinuousAttribute) a).scale(x);
						s.set(scaled, j);
					}
					j++;
				}
			}
			return s;

	}

}
