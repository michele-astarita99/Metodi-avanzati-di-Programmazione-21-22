	package server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** MultiServer
 * 
 * Classe che gestisce la connessione del server.
 *
 */
public class MultiServer {
    /**
     * costruttore classe MultiServer attraverso
     * una porta prestabilita dall'utente.
     * @param port 
     */
	private int PORT = 2025;

	/**
	 * Costruttore di classe, inizializza la porta ed invoca run().
	 *
	 * @param port Variabile che indica la porta da inizializzare.
	 */
	public MultiServer(int port) {
		PORT = port;
		run();
	}

    /**
     * metodo che serve per inizializzare una nuuova connessione.
     */
	private void run() {
		try {
			ServerSocket s = new ServerSocket(PORT);
			System.out.println("Server avviato sulla porta: " + PORT);
			try {
				while (true) {
					Socket socket = s.accept();
					System.out.println("Connessione in corso . . . " + socket);
					try {
						new ServerOneClient(socket);
					} catch (IOException e) {
						socket.close();
					}
				}
			} finally {
				s.close();
				System.err.println("Disconnettendo il server . . . ");
			}
		} catch (IOException exc) {
			System.err.println(exc.toString());
		}
	}

    /**
     * metodo che avvia il server.
     * @param args
     */
	public static void main(String args[]) {
		@SuppressWarnings("unused")
		MultiServer server = new MultiServer(2025);
	}

}
