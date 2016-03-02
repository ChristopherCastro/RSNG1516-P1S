package cast;

import channel.Channel;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Iterator;
import main.Configuration;

/**
 * Encargado de realizar los anuncios multicast de los programas indicados en
 * el fichero de configuracion
 */
public class MultiCaster implements Runnable {
    MulticastSocket s; //Socket encargado de enviar los anuncios multicast
    Configuration config;

    public MultiCaster(Configuration config) throws UnknownHostException, IOException {
        this.config = config;
        this.s = new MulticastSocket(this.config.getMcastPort());
        this.s.joinGroup(this.config.getMcastDir());
        System.out.printf("[MULTICASTER] Iniciando servicio de anunciado multicast a %s:%s ...", this.config.getMcastDir(), this.s.getLocalPort());
    }

    @Override
    public void run() {
        for (Channel canal : this.config.getChannelCollection().getCollection()) {
            
        } //Recorro hiteando con cada objeto
    }
}
