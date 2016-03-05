package client;

import channel.Channel;
import channel.ChannelCollection;
import error.ChannelNotFoundException;
import error.InvalidRequestException;
import error.RequestBaseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.SocketThread;
import threads.StreamingThread;

public class ClientHandler implements Runnable {

    protected Socket socket;
    protected BufferedReader in = null;
    protected PrintWriter out = null;
    protected ChannelCollection channels;
    protected boolean block = false;

    public ClientHandler(Socket s, ChannelCollection channels) {
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

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
    

    @Override
    public void run() {
        try {
            String line = this.in.readLine();
            RequestMessage request = new RequestMessage(this.socket, line);

            /**
             * Crear y lanzar DOS hilos (y mantener sus referencias). Sus
             * constructores deberian recibir como unico argumento una instancia
             * de ClientHandler ("padre" el cual los generó)
             *
             * - Hilo 1: Encargado de iniciar el streaming de video - - Hilo 2:
             * Encargado de mantener el socket activo
             *
             * No puede haber nunca un unico hilo activo, si uno de ellos
             * finaliza el otro debe hacerlo también. Esto se consigue
             * utilizando ClientHandler como mediador (utilizando métodos
             * publicos).
             *
             * Por ejemplo, si el Hilo 2 finaliza porque el socket ha sido
             * cerrado por el cliente entonces el Hilo 2 deberá notificar esto a
             * su padre (invocando un método, por ejemplo "onSocketDestroy()"),
             * a continuación el padre deberá coger la instancia del Hilo 1 e
             * indicarle que finalice (debera finalizar el streaming y luego
             * terminar su ejecición)... por ello cada Hilo debería también
             * definir un método de finalización que será invocado por su padre,
             * por ejemplo "secureStop()".
             */
            
            SocketThread socketAlive = new SocketThread(this);
            StreamingThread streaming = new StreamingThread(this,request);
            
            socketAlive.start();
            streaming.start();
            
            socketAlive.join();
            streaming.join();
            

        } catch (InvalidRequestException ex) {
            this.out.println(ex);
        } catch (IOException ex) {
            this.out.println(new RequestBaseException(ex));
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
