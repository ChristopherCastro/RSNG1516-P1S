package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Configuration;
import tools.IpTools;

/**
 * Se encarga de escuchar en un puerto determinado a la espera de peticiones por parte de los clientes.
 * Por cada petición creará un hilo para atenderla.
 */
public class ClientDesk implements Runnable {

    protected Configuration config;

    public ClientDesk(Configuration config) {
        this.config = config;
        System.out.printf("[INFO] Iniciando servicio desk de clientes en el puerto %d ...\n", this.config.getServerPort());
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(
                new InetSocketAddress(
                    InetAddress.getByName("::"),
                    config.getServerPort()
                )
            );

            // WAIT FOR CLIENTS
            System.out.println("[ClientDesk] Waiting for clients");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.printf("[ClientDesk] New client connected from %s", IpTools.clientAddress(clientSocket));
                new ClientHandler(clientSocket, this.config.getChannelCollection());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientDesk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
