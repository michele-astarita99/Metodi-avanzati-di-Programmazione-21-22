package server.mining;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import server.data.Data;
import server.example.Example;
/** KNN
 * <p>
 * Classe che modella il miner del progetto.
 *</p>
 */
@SuppressWarnings("serial")
public class KNN implements Serializable {
	/**
	 * attributo che modella il training set.
	 */
	Data data;
	
	/**
	 * costruttore della classe che avvalora il training set.
	 * @param trainingSet 
	 */
	public KNN(Data trainingSet) {
		this.data = trainingSet;

	}

    /**
     * metodo che restituisce la predizione dell'esempio
     * in base al valore k preso dal client.
     * @return data.avgClosest(e, k)
     */

	public double predict(ObjectOutputStream out, ObjectInputStream in)
			throws IOException, ClassNotFoundException, ClassCastException {
		Example e = data.readExample(out, in);
		int k = 0;
		Boolean flag = false;
		out.writeObject("Inserisci valore k>=1:");
		try {
			do {

				k = (Integer) (in.readObject());

				if (k < 1) {
					out.writeObject("@ERRORE");
					flag = false;
				} else if (k >= 1) {
					out.writeObject("@CORRETTO");
					flag = true;
				}

			} while (!flag);

		} catch (NumberFormatException stringainput) {
			out.writeObject("@ERRORE");
			flag = false;
		}

		return data.avgClosest(e, k);

	}

    /**
     * metodo che serve per serializzare un'istanza di KNN.
     * @param nomeFile
     * @throws IOException
     */
	public void salva(String nomeFile) throws IOException {
        FileOutputStream outFile = new FileOutputStream(nomeFile);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this);
        outStream.close();
    }
	
    /**
     * metodo che serve per deserializzare un'istanza di KNN.
     * @param nomeFile
     * @return knn
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public static KNN carica(String nomeFile) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
		KNN knn = (KNN) in.readObject();
		in.close();
		return knn;
	}
}
