package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
/**
 * Client
 * <p>
 * Classe che stabilisce la connessione al Server e, una volta avvenuta la
 * connessione, invia e riceve messaggi, dipendentemente dalla scelta effettuata
 * dall'utente.
 * </p>
 *
 */
public class Client extends Application {


/**
 * <p>
 * Socket per la connessione al server.
 * </p>
 */
static Socket socket = null;
/**
 * <p>
 * Stream per passare informazioni al server.
 * </p>
 */
static ObjectOutputStream out = null;
/**
 * <p>
 * Stream per prelevare informazioni dal server.
 * </p>
 */
static ObjectInputStream in = null;

/**
 * <p>
 * Metodo che permette la connessione con il server, tramite un indirizzo ip e
 * la porta.
 * </p>
 *
 * @param stage variabile utilizzata per inizializzare la GUI
 * @throws ClassNotFoundException
 * @throws IOException
 *
 */
@Override
public void start(Stage stage) throws ClassNotFoundException, IOException {
		
		@SuppressWarnings("unused")
		InetAddress addr;
		try {
			addr = InetAddress.getByName("127.0.0.1");
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
			return;
		}
		try {
			Connection("127.0.0.1", 2025);
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		} catch (NumberFormatException e) {
			System.out.println(e.toString());
			return;
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			return;

		}

		try {
			stage.setTitle("K-Nearest Neighbors");
			Image icon = new Image("Knn logo.png");
			stage.getIcons().add(icon);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_1.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			scene_scaling(scene, root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * <p>
	 * Metodo che contribuisce a scalare il contenuto della scena.
	 * </p>
	 *
	 * @param scene variabile utilizzata per inizializzare la GUI
	 * @param root  variabile per effettuare la scalatura
	 *
	 */
public static void scene_scaling(final Scene scene, final AnchorPane root) {
	final double i_height= scene.getHeight();
	final double i_width = scene.getWidth();
	final double rto = i_width / i_height;
	Scene_ScalingListener change_listener = new Scene_ScalingListener(scene, rto, i_height, i_width, root);
	scene.heightProperty().addListener(change_listener);
	scene.widthProperty().addListener(change_listener);
	
	}
	

	/**
	 * <p>
	 * Metodo che contribuisce a scalare il contenuto della scena.
	 * </p>
	 *
	 */
public static class Scene_ScalingListener implements ChangeListener<Number> {
	private final Scene scene;
	private final double ratio;
	private final double initHeight;
	private final double initWidth;
	private final AnchorPane contentPane;

	public Scene_ScalingListener(Scene scene, double ratio, double initHeight, double initWidth, AnchorPane contentPane) {
		this.scene = scene;
		this.ratio = ratio;
		this.initHeight = initHeight;
		this.initWidth = initWidth;
		this.contentPane = contentPane;
	}

	@Override
	public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
		final double newWidth = scene.getWidth();
		final double newHeight = scene.getHeight();

		double scaleFactor = newWidth / newHeight > ratio ? newHeight / initHeight : newWidth / initWidth;

		if (scaleFactor >= 1) {
			Scale scale = new Scale(scaleFactor, scaleFactor);
			scale.setPivotX(0);
			scale.setPivotY(0);
			scene.getRoot().getTransforms().setAll(scale);
			contentPane.setPrefHeight(newHeight / scaleFactor);
			contentPane.setPrefWidth(newWidth / scaleFactor);
		} else {
			contentPane.setPrefHeight(Math.max(initHeight, newHeight));
			contentPane.setPrefWidth(Math.max(initWidth, newWidth));
		}

	}
}

	public static void Connection(String address, int port) throws IOException, ClassNotFoundException {
		setSocket(new Socket(address, port));
		System.out.println(getSocket());
		setOut(new ObjectOutputStream(getSocket().getOutputStream()));
		setIn(new ObjectInputStream(getSocket().getInputStream()));
	}

	/**
	 * Metodo get() per stream out.
	 *
	 * @return out
	 *
	 */
	public static ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * Metodo set() per stream out.
	 *
	 * @param ObjectOutputStream oggetto dello stream
	 *
	 */
	public static void setOut(ObjectOutputStream out) {
		Client.out = out;
	}

	/**
	 * Metodo get() per stream in.
	 *
	 * @return in
	 *
	 */
	public static ObjectInputStream getIn() {
		return in;
	}

	/**
	 * Metodo set() per stream in.
	 *
	 * @param in oggetto dell'input stream
	 *
	 */
	public static void setIn(ObjectInputStream in) {
		Client.in = in;
	}

	/**
	 * Metodo get() per socket.
	 *
	 */
	public static Socket getSocket() {
		return socket;
	}

	/**
	 * Metodo set() per socket.
	 *
	 * @param socket socket per la comunicazione con il server
	 *
	 */
	public static void setSocket(Socket socket) {
		Client.socket = socket;
	}

/**
 * <p>
 * Metodo che avvia l'interfaccia grafica.
 * </p>
 *
 * @param args variabile per inizializzare il main
 */
public static void main(String args[]) {
	Application.launch();
}

}

