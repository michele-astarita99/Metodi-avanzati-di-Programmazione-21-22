package client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller_1 {
	@FXML
	TextArea intro;
	/**
	 * Attributo utilizzato per gestire interfaccia grafica.
	 *
	 */
	static Stage stage;
	/**
	 * Attributo utilizzato per gestire interfaccia grafica.
	 *
	 */
	static Scene scene;
	/**
	 * Attributo utilizzato per gestire interfaccia grafica.
	 *
	 */
	static Parent root;

	/**
	 * <p>
	 * Metodo che permette di cambiare alla scena 1.
	 *
	 * @param event evento implicato nella funzionet
	 * @throws IOException
	 *
	 */

	public void carica_scena1(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_1.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();
		Client.scene_scaling(scene, root);
	}

	/**
	 * <p>
	 * Metodo che permette di cambiare alla scena 2.
	 *
	 * @param event evento implicato nella funzione
	 * @throws IOException
	 *
	 */

	public void carica_scena2(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_2.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Client.scene_scaling(scene, root);

	}

	/**
	 * <p>
	 * Metodo che permette di cambiare alla scena 3.
	 *
	 * @param event evento implicato nella funzione
	 * @throws IOException
	 *
	 */

	public void carica_scena3(ActionEvent event) throws IOException {
		Client.getOut().writeObject(1);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_3.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Client.scene_scaling(scene, root);
	}

	/**
	 * <p>
	 * Metodo che permette di cambiare alla scena 5.
	 *
	 * @param event evento implicato nella funzione
	 * @throws IOException
	 *
	 */

	public void carica_scena5(ActionEvent event) throws IOException {
		Client.getOut().writeObject(2);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_5.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Client.scene_scaling(scene, root);
	}

	/**
	 * <p>
	 * Metodo che permette di cambiare alla Scene 6.
	 *
	 * @param event evento implicato nella funzione
	 * @throws IOException
	 *
	 */

	public void carica_scena6(ActionEvent event) throws IOException {
		Client.getOut().writeObject(3);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_6.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Client.scene_scaling(scene, root);
	}
}