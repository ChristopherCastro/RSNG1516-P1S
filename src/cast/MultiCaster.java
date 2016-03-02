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
        String paquete = "";
        int num_canal_paquete = 1;
        for (Channel canal : this.config.getChannelCollection().getCollection()) {
            if ((num_canal_paquete % this.config.getUDPMaxNumChannels()) == 1){ //Si es el inicio de un paquete
                //Escribo SSER puertoservidor [ipservidor]
                paquete="SSER "+this.config.getServerPort() +"\n"; //TODO: Añadir IP del servidor opcionalmente
                
                //Escribo canal 
                paquete += canal.getChannelAnnounce()+"\n";
                
                //Si también es el ultimo canal ( primer y ultimo del paquete, ultimo del listado)
                if (num_canal_paquete == this.config.getChannelCollection().getNumCanales()){
                    paquete += "END";
                }
                
            }else if ((num_canal_paquete % this.config.getUDPMaxNumChannels()) == 0){ //Si es el final de un paquete
                //Escribo canal
                //Si era el ultimo de todos los canales, acabo con un END
                //Si todavía quedan canales, acabo con un MORE
            
            }else{ //Si es un canal intermedio
                //Escribo canal
                //Si soy el último escribo END
            }
            num_canal_paquete++;//Aumento el contador de canales tratados
        } //Recorro hiteando con cada objeto
    }
}
