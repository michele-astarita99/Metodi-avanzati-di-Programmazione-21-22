package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller_4 {

	static Stage stage;
	static Scene scene;
	static Parent root;
	public Double prediction;
	public static String passaretabella;

	@FXML
	Label nameLabel, nameLabel1, nameLabel2, nameLabel3, nameLabel4, opaco1, opaco2, opaco3, erroredouble, errorek;
	@FXML
	Label stringavuota;

	@FXML
	TextField discreto, continuo, knn;
	@FXML
	Button bottone1, bottone2, bottone3, predizione;

	/**
	 * <p>
	 * Metodo che stampa nell'interfaccia grafica il nome della tabella digitato
	 * dall'utente.
	 *
	 * @throws ClassNotFoundException, IOException, InterruptedException
	 *
	 * @param tabella nome della tabella passato per input
	 */
	public void displayName(String tabella) throws ClassNotFoundException, IOException, InterruptedException {
		predizione.setDisable(true);
		//nameLabel.setText("La tabella inserita è : " + tabella);
		passaretabella = tabella;
		String a = Client.getIn().readObject().toString();
		displayExample(a);

	}

	/**
	 * <p>
	 * Metodo che preleva l'example e ne verifica la correttezza.
	 *
	 * @param string stringa ricevuta dallo stream
	 *
	 * @throws ClassNotFoundException, IOException, InterruptedException
	 *
	 */
	public void displayExample(String string) throws ClassNotFoundException, IOException, InterruptedException {
		predizione.setDisable(true);
		if (string.equals("@READSTRING")) {
			bottone2.setDisable(true);
			bottone3.setDisable(true);
			continuo.setDisable(true);
			knn.setDisable(true);
			opaco1.setOpacity(0);
			stringavuota.setOpacity(0);
			String msg = (String) (Client.getIn().readObject());
			readDiscreteAttribute(msg);
			discreto.clear();

		} else if (string.equals("@READDOUBLE")) {
			stringavuota.setOpacity(0);
			continuo.setDisable(false);
			bottone1.setDisable(true);
			bottone2.setDisable(false);
			bottone3.setDisable(true);
			knn.setDisable(true);
			discreto.setDisable(true);
			opaco2.setOpacity(0);

			String msg = (String) (Client.getIn().readObject());
			readContinuousAttribute(msg);
			continuo.clear();

		} else if (string.equals("@ENDEXAMPLE")) {
			continuo.setDisable(true);
			bottone2.setDisable(true);
			knn.setDisable(false);
			bottone3.setDisable(false);
			String msg = (String) (Client.getIn().readObject());
			readk(msg);
		}

	}

	/**
	 * <p>
	 * Metodo che preleva il k e ne verifica la correttezza.
	 *
	 * @param msg stringa ricevuta dallo stream
	 *
	 * @throws ClassNotFoundException, IOException
	 *
	 */

	private void readk(String msg) throws ClassNotFoundException, IOException {
		predizione.setDisable(true);
		opaco3.setOpacity(0);
		nameLabel3.setText(msg);

		bottone3.setOnAction(new EventHandler<ActionEvent>() {
			int k = 0;

			@Override
			public void handle(ActionEvent arg0) {
				try {
					errorek.setOpacity(0);
					@SuppressWarnings("unused")
					String risposta = knn.getText();

					try {
						k = Integer.parseInt(knn.getText());

					} catch (NumberFormatException e) {
						errorek.setOpacity(1);
						knn.clear();
						readk("Inserisci valore k>=1:");
					}
					Client.getOut().writeObject(k);
					predizione.setDisable(false);
					String controllo = Client.getIn().readObject().toString();

					if (controllo.equals("@CORRETTO")) {
						prediction();
					} else if (controllo.equals("@ERRORE")) {
						errorek.setOpacity(1);
						knn.clear();
						readk("Inserisci valore k>=1:");

					}

				} catch (IOException e) {
					// catch eccezione 
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// catch eccezione
					e.printStackTrace();
				}

			}

			/**
			 * <p>
			 * Metodo che preleva la predizione dal server.
			 *
			 * @throws ClassNotFoundException, IOException
			 *
			 */
			private void prediction() throws ClassNotFoundException, IOException {
				bottone3.setDisable(true);
				knn.setDisable(true);
				prediction = (Double) Client.getIn().readObject();
			}

		});

	}

	/**
	 * <p>
	 * Metodo che permette di cambiare scena.
	 *
	 * @param event evento implicato nella funzione
	 * @throws IOException
	 *
	 */
	public void carica_scena7(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_7.fxml"));
		AnchorPane root = (AnchorPane) loader.load();

		Controller_7 Controller_7 = loader.getController();
		Controller_7.displayPrediction(prediction);

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Client.scene_scaling(scene, root);

	}

	/**
	 * <p>
	 * Metodo che permette di inserire il valore successivo.
	 *
	 * @throws IOException, ClassNotFoundException, InterruptedException
	 *
	 */

	public void next() throws ClassNotFoundException, IOException, InterruptedException {
		erroredouble.setOpacity(0);
		String b = Client.getIn().readObject().toString();
		displayExample(b);

	}

	/**
	 * <p>
	 * Metodo che preleva l'attributo discreto e ne verifica la correttezza.
	 *
	 * @param msg stringa ricevuta dallo stream
	 * @throws ClassNotFoundException, IOException
	 *
	 */
	private void readDiscreteAttribute(String msg) throws IOException, InterruptedException {
		nameLabel1.setText(msg);
		bottone1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {

					String risposta = discreto.getText();
					if (risposta.isEmpty()) {
						Client.getOut().writeObject("@STRINGAVUOTA");
						String string = Client.getIn().readObject().toString();
						displayExample(string);
						stringavuota.setOpacity(1);
					} else if (risposta.isBlank()) {
						Client.getOut().writeObject("@STRINGAVUOTA");
						String string = Client.getIn().readObject().toString();
						displayExample(string);
						stringavuota.setOpacity(1);
					} else {
						Client.getOut().writeObject("@STRINGACORRETTA");
						Client.getOut().writeObject(risposta);
						next();
					}

				} catch (ClassNotFoundException | IOException | InterruptedException e) {

					e.printStackTrace();
				}

			}

		});

	}

	/**
	 * <p>
	 * Metodo che preleva l'attributo continuo e ne verifica la correttezza.
	 *
	 * @param msg stringa ricevuta dallo stream
	 * @throws ClassNotFoundException, IOException
	 *
	 */
	public void readContinuousAttribute(String msg) throws IOException {
		continuo.clear();

		nameLabel2.setText(msg);

		bottone2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					String risposta;

					double x = 0.0;
					risposta = continuo.getText();
					if (isDecimalNumber(risposta) == 1) {
						Client.getOut().writeObject("@DOUBLECORRETTO");
						x = Double.parseDouble(risposta);
						Client.getOut().writeObject(x);
						next();

					} else if (isDecimalNumber(risposta) == 0) {
						erroredouble.setOpacity(1);
						Client.getOut().writeObject("@DOUBLESBAGLIATO");
						String string = Client.getIn().readObject().toString();
						displayExample(string);
					}

				} catch (ClassNotFoundException | IOException | InterruptedException e) {

					e.printStackTrace();
				}

			}

		});

	}

	/**
	 * <p>
	 * Metodo che verifica se un numero è un double.
	 *
	 * @param num stringa ricevuta in input
	 *
	 */

	private int isDecimalNumber(String num) {
		try {
			Double.parseDouble(num);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
}
