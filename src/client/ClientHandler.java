package client;

import channel.ChannelCollection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Configuration;

public class ClientHandler implements Runnable {

    protected InputStream in = null;
    protected OutputStream out = null;
    protected ChannelCollection channels;

    public ClientHandler(Socket s, ChannelCollection channels) {
        try {
            this.in = s.getInputStream();
            this.out = s.getOutputStream();
            this.channels = channels;
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        (new Thread(this)).start();
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}