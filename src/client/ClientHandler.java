package client;

import channel.ChannelCollection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    protected BufferedReader in = null;
    protected PrintWriter out = null;
    protected ChannelCollection channels;

    public ClientHandler(Socket s, ChannelCollection channels) {
        try {
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);
            this.channels = channels;
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        (new Thread(this)).start();
    }
    
    @Override
    public void run() {
        try {
            String line = this.in.readLine();
            RequestMessage request = new RequestMessage(line);
            if ("REQ".equals(request.req)){
                throw new IllegalArgumentException("El mensaje no contiene una peticion valida. No es una peticion REQ \n");
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}