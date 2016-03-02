package cast;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Encargado de realizar los anuncios multicast de los programas indicados en el fichero de configuracion
 */

    
class MultiCaster implements Runnable{
    MulticastSocket s; //Socket encargado de enviar los anuncios multicast
    Configuration config;
    
    
    MultiCaster(Configuration config) throws UnknownHostException, IOException {
        this.config = config;
        this.s = new MulticastSocket(this.config.getMcastPort());
        this.s.joinGroup(this.config.getMdir());
        System.out.println("[INFO]Iniciando servicio de anunciado multicast a " + this.config.getMdir() + ":" + this.s.getLocalPort()+ "...");
    }

    @Override
    public void run() {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}