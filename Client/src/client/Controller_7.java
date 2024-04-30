package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

	public class Controller_7 {

	@FXML
	Label predizionefinale, testouscita;

	/**
	 * <p>
	 * Metodo che stampa nell'interfaccia grafica la predizione.
	 *
	 * @param prediction predizione ricevuta dallo stream
	 * @throws IOException
	 *
	 */

	public void displayPrediction(Double prediction) throws IOException {
		predizionefinale.setText("La predizione è : " + prediction);
	}

	/**
	 * <p>
	 * Metodo che permette di inserire un nuovo esempio con lo stesso DataSet.
	 *
	 * @param event evento di riferimento
	 * @throws IOException
	 *
	 */
	public void carica_scena4(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {

		Client.getOut().writeObject("@REPEATEXAMPLE");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_4.fxml"));
		Controller_4.root = loader.load();
		String tabella = Controller_4.passaretabella;
		Controller_4 Controller_4 = loader.getController();
		Controller_4.displayName(tabella);

		client.Controller_4.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		client.Controller_4.scene = new Scene(client.Controller_4.root);
		client.Controller_4.stage.setScene(client.Controller_4.scene);
		client.Controller_4.stage.show();
		Client.scene_scaling(client.Controller_4.scene, (AnchorPane) client.Controller_4.root);
	}

	/**
	 * <p>
	 * Metodo che permette di reinserire un nuovo DataSet.
	 *
	 * @param event evento di riferimento
	 *
	 * @throws IOException
	 *
	 */

	public void carica_scena2(ActionEvent event) throws IOException {

		Client.getOut().writeObject("@REPEATDATASET");
		Client.getOut().writeObject("@REPEATDATASET");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_2.fxml"));
		Controller_1.root = loader.load();

		Controller_1.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Controller_1.scene = new Scene(Controller_1.root);
		Controller_1.stage.setScene(Controller_1.scene);
		Controller_1.stage.show();
		Client.scene_scaling(Controller_1.scene, (AnchorPane) Controller_1.root);

	}

	/**
	 * <p>
	 * Metodo che permette di uscire dall'applicazione
	 *
	 * @param event evento di riferimento
	 *
	 * @throws IOException
	 *
	 */
	public void esci(ActionEvent event) throws IOException, InterruptedException {

		Client.getOut().writeObject("@END");
		Client.getOut().writeObject("@END");
		Client.getSocket().close();
		System.exit(0);

	}
}
