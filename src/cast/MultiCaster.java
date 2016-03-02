package cast;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Encargado de realizar los anuncios multicast de los programas indicados en el fichero de configuracion
 */

    
public class MultiCaster implements Runnable{
    File config; //Fichero que contiene el listado de de programas.
    MulticastSocket s; //Socket encargado de enviar los anuncios multicast
    
    
    public MultiCaster(String config, String mdir, int mpuerto) throws UnknownHostException, IOException {
        this.config = new File(config);
        this.s = new MulticastSocket(mpuerto);
        this.s.joinGroup(InetAddress.getByName(mdir));
        System.out.println("[INFO]Iniciando servicio de anunciado multicast a " + mdir + ":" + this.s.getLocalPort()+ "...");
    }

    @Override
    public void run() {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}