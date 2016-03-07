package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se queda a la escucha en la entrada del socket.
 *
 * @author Chris
 */
public class SocketInspectorThread extends Thread {

    protected ClientHandler parent;
    protected BufferedReader inputStream;

    public SocketInspectorThread(final ClientHandler parent, final BufferedReader br) {
        super();
        this.parent = parent;
        this.inputStream = br;
    }

    @Override
    public void run() {
        String line = null;
        try {
            line = this.inputStream.readLine(); // bloqueante
        } catch (Exception ex) {
            // mostrar error solo si el socket sigue abierto, sino significa
            // que ha sido cerrado a la fuerza (caso controlado)
            if (!this.parent.getSocket().isClosed()) {
                Logger.getLogger(SocketInspectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            if (this.parent.getSocket().isClosed()) { // null si el socket se cierra de forma inesperada
                this.parent.onSocketClose();
            }
        }
    }

    void closeSocket() {
        try {
            System.out.println("[SocketThread]: Socket will be closed now");
            this.parent.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(SocketInspectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
