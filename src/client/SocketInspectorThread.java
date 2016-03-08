package client;

import java.io.BufferedReader;
import java.io.IOException;
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
        System.out.println("[SocketInspectorThread] Starting");

        while (true) {
            try {
                line = this.inputStream.readLine(); // bloqueante
            } catch (IOException ex) {
                line = null;
            }

            if (line == null) {
                System.out.println("[SocketInspectorThread] Client has closed the connection");
                this.parent.onSocketClose();
                break;
            }
        }
    }

    void closeSocket() {
        try {
            System.out.println("[SocketInspectorThread]: Socket will be closed now");
            this.parent.getSocket().close();
        } catch (IOException ex) {
            Logger.getLogger(SocketInspectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
