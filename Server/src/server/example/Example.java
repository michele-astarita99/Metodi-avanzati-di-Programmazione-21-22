package server.example;

import java.io.Serializable;
import java.util.*;
/** Example
 * <p>
 * Classe che modella gli esempi.
 * </p>
 */
public class Example implements Serializable{

    private static final long serialVersionUID = 4L;
	/**
	 * Lista di oggetti che contiene un valore per ogni attributo
	 */
    private LinkedList<Object> example;
    /**
     * Metodo che costruisce la lista di dimensione size.
     * @param size
     */
    public Example(int size){
        example = new LinkedList<Object>();
        for (int i = 0; i < size; i++){
            example.add("0");
        }
    }
    /**
     * metodo che setta l'oggetto.
     * @param o
     * @param index
     */
    public void set(Object o, int index){
        //example[index] = o;
        example.set(index, o);
    }
    /**
     * metodo che restituisce l'oggetto.
     * @param index
     * @return this.example.get(index)
     */
    public Object get(int index){
        //return example[index];
        return this.example.get(index);
    }
    /**
     * metodo che scambia i valori contenuti nell'Example.
     * @param e
     */
    public void swap(Example e) {
        if (this.example.size() != e.example.size())
            throw new ExampleSizeException("Gli esempi non hanno la stessa dimensione");  
        Example temp = new Example(this.example.size());
        temp.example  = this.example;
        this.example = e.example;
        e.example = temp.example;
        
    }
    /**
     * metodo che calcola e restituisce la distanza di Hamming.
     * @param e
     * @return d
     */
   public double distance(Example e) {
         if (this.example.size() != e.example.size())
            throw new ExampleSizeException("Gli esempi non hanno la stessa dimensione");
        double d = 0;
        for (int i = 0; i < this.example.size(); i++){
            if (e.get(i) instanceof String){
                if (this.example.get(i).equals(e.example.get(i)) == false){
                    //System.out.println(this.example[i] + "," + e.example[i]);
                    //if(!this.example[i].equals(e))
                    d = d+1;
                }
            } else if (e.get(i) instanceof Double){
                d = d + Math.abs(Double.parseDouble(this.example.get(i).toString()) - Double.parseDouble(e.get(i).toString()));
            }
        }        
        return d;
    }
   @Override
    public String toString() {
        String s = new String();

            for (Iterator<Object> it = example.iterator(); it.hasNext();){
                
                s = s + " " + it.next() ;
                
             }
        //}
        
        
        return s;
     }

    
}


