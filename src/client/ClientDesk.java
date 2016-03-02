package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Configuration;

/**
 * Se encarga de escuchar en un puerto determinado a la espera de peticiones por parte de los clientes.
 * Por cada petición creará un hilo para atenderla.
 */
public class ClientDesk implements Runnable {

    protected Configuration config;

    public ClientDesk(Configuration config) {
        this.config = config;
        System.out.printf("[INFO] Iniciando servicio desk de clientes en el puerto %d ...", this.config.getServerPort());
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(config.getServerPort());
            while (true) {
                new ClientHandler(serverSocket.accept(), this.config.getChannels());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientDesk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
