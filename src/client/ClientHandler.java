package client;

import channel.ChannelCollection;
import error.InvalidRequestException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    protected Socket socket;
    protected BufferedReader in = null;
    protected PrintWriter out = null;
    protected ChannelCollection channels;

    protected SocketInspectorThread socketThread = null;
    protected StreamingThread streamingThread = null;

    public ClientHandler(final Socket s, final ChannelCollection channels) {
        try {
            this.socket = s;
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);
            this.channels = channels;
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        (new Thread(this)).start();
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public ChannelCollection getChannels() {
        return channels;
    }

    @Override
    public void run() {
        RequestMessage request = null;
        String line = null;

        while (request == null) {
            try {
                line = this.in.readLine();
                
                // por si se cierra el socket de forma inesperada
                if (line != null) {
                    request = new RequestMessage(this.socket, line);
                }
            } catch (InvalidRequestException ex) {
                this.out.println(ex);
            } catch (IOException ex) {
                Logger.getLogger(SocketInspectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // solo si se realiza una peticion válida
        if (request != null) {
            System.out.printf("[ClientHandler] New channel requested (%d)\n", request.getChannelId());
            this.socketThread = new SocketInspectorThread(this, this.in);
            this.streamingThread = new StreamingThread(this, request);

            this.socketThread.start();
            this.streamingThread.start();
        }
    }

    /**
     * Callback, invocado por el hilo `SocketInspectorThread` para indicar que
     * ha cerrado el socket por parte del cliente.
     */
    public void onSocketClose()
    {
        if (this.streamingThread.isAlive()) {
            this.streamingThread.stopStreaming();
        }
    }

    /**
     * Callback utilizado por el hilo `StreamingThread` para indicar que ha
     * terminado el flujo de video entre servidor y cliente.
     */
    public void onStreamingFinish()
    {
        if (this.socketThread.isAlive()) {
            this.socketThread.closeSocket();
        }
    }
}
