package server.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import server.data.Data;
import server.data.TrainingDataException;
import server.database.DatabaseConnectionException;
import server.database.DbAccess;
import server.database.InsufficientColumnNumberException;
import server.example.ExampleSizeException;
import server.mining.KNN;

/** ServerOneClient
 * 
 * <p>
 * Classe che aiuta a mettere in comunicazione server e client .
 * </p>
 */
public class ServerOneClient extends Thread {
	/**
	 * <p>
	 * attributo socket per la connessione al server.
	 * </p>
	 */
	private Socket socket;
    /**
	 * <p>
	 * attributo stream per prendere in input delle informazioni.
	 * </p>
	 */
	private ObjectInputStream in;
    /**
	 * <p>
	 * attributo stream per passare informazioni al server.
	 * </p>
	 */
	private ObjectOutputStream out;

    /**
     * costruttore della classe
     * @param s
     * @throws IOException
     */
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		run();
	}

	@Override
	public void run() {

		KNN knn = null;
		int r = 0;
		String cycle = "";

		try {
			do {
				r = (int) in.readObject();
				switch (r) {
				case 1: {
					Data trainingSet = null;
					String file = null;
					Boolean flag = false;
					do {
						try {
							file = (String) in.readObject() + ".dat";
							trainingSet = new Data(file);
							out.writeObject("@CORRECT");
							flag = true;

						} catch (TrainingDataException | FileNotFoundException e) {
							System.out.println(e.getMessage());
							out.writeObject("@ERROR");
						}

					} while (!flag);

					knn = new KNN(trainingSet);

					try {
						knn.salva(file + ".dmp");
					} catch (Exception exc) {
						System.out.println(exc.getMessage());
					}

				}
					break;
				case 2: {
					boolean flag = false;

					do {
						try {
							String file = (String) in.readObject() + ".dmp";
							knn = KNN.carica(file);
							out.writeObject("@CORRECT");
							flag = true;

						} catch (IOException | ClassNotFoundException exc) {
							System.out.println(exc.getMessage());
							out.writeObject("@ERROR");
						}
					} while (!flag);
				}
					break;
				case 3: {
					Data trainingSet = null;
					String table = "";
					Boolean flag = false;
					do {
						try {
							DbAccess db = new DbAccess();
							table = (String) in.readObject();
							trainingSet = new Data(db, table);
							db.closeConnection();
							out.writeObject("@CORRECT");
							flag = true;
						} catch (InsufficientColumnNumberException | TrainingDataException exc1) {
							System.out.println(exc1.getMessage());
							out.writeObject("@ERROR");
						} catch (DatabaseConnectionException exc2) {
							out.writeObject("@ERROR");
							System.out.println(exc2.getMessage());
						} catch (ExampleSizeException e) {
							out.writeObject("@ERROR");
							e.printStackTrace();
						} catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
					} while (!flag);

					knn = new KNN(trainingSet);
					try {
						knn.salva(table + "DB.dmp");
					} catch (Exception exc) {
						System.out.println(exc.getMessage());
					}
				}
				}

				String control = "";
				do {
					double prediction = knn.predict(out, in);
					out.writeObject(prediction);
					control = (String) in.readObject();
				} while (control.equals("@REPEATEXAMPLE"));

				cycle = (String) in.readObject();

				if (cycle.equals("@END")) {
					break;
				}

			} while (cycle.equals("@REPEATDATASET"));

		} catch (IOException e) {
			System.err.println("IOException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				System.err.println("Socket not closed");
			}
		}

	}

}