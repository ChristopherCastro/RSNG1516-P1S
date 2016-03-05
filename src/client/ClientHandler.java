package client;

import channel.Channel;
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
    
    protected Socket s;
    protected BufferedReader in = null;
    protected PrintWriter out = null;
    protected ChannelCollection channels;

    public ClientHandler(Socket s, ChannelCollection channels) {
        try {
            this.s = s;
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
            if (!request.req.equals("REQ")){
                throw new IllegalArgumentException("El mensaje contiene una peticion invalida. No es una peticion REQ \n");
                // send: "REQ FAIL mensaje. No se va a servir la peticion: Peticion invalida. No es una peticion REQ"
            }
            if(request.clientAdress==null || request.clientAdress==""){
                //añadir direccion cliente si no la tiene
                request.clientAdress = s.getInetAddress().toString();                
            }
            if(channels.isEmpty()){
                throw new IllegalArgumentException("La lista de canales no existe o esta vacia. \n");
                // send: "REQ FAIL mensaje. La lista de canales no existe o esta vacia"
            }else{
                Channel video = this.channels.getById(request.id);
                //script += video;  
                //¿como paso el video por parametro si exec espera un string y video es un channel?
                //Process bash = Runtime.getRuntime().exec(script);
                //¿Que hacemos con el atributo "out"?
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}