package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller_3 {

	@FXML
	TextField nameTextField;
	@FXML
	Text ERRORE, SCRITTAERRORE;
	@FXML
	Button indietro;
	/**
	 * Attributo utilizzato per gestire interfaccia grafica.
	 *
	 */
	private Stage stage;
	/**
	 * Attributo utilizzato per gestire interfaccia grafica.
	 *
	 */
	private Scene scene;

	/**
	 * Attributo utilizzato per conservare il nome della tabella scelta dall'utente.
	 *
	 */
	public String tabella;

	/**
	 * <p>
	 * Metodo collegato al bottone "Cerca" dell'interfaccia grafica. Permette di
	 * verificare se la tabella digitata esiste e di conseguenza di prelevare il
	 * training Set.
	 *
	 * @param event evento implicato per la funzione
	 */
	public void cerca(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {

		String tabella = nameTextField.getText();
		Client.getOut().writeObject(tabella);
		String verifica = Client.getIn().readObject().toString();
		if (verifica.equals("@CORRECT")) {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_4.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Controller_4 Controller_4 = loader.getController();
			Controller_4.displayName(tabella);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			Client.scene_scaling(scene, root);
		}

		else if (verifica.equals("@ERROR")) {

			ERRORE.setOpacity(1);
			SCRITTAERRORE.setOpacity(1);
			nameTextField.clear();

		}


	}
	/**
	 * metodo che carica la scena 2 dell'interfaccia.
	 * @param event
	 * @throws IOException
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

}
