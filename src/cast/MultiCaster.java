package cast;

import channel.Channel;
import java.io.IOException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.printf("[MULTICASTER] Iniciando servicio de anunciado multicast a %s:%s ...\n", this.config.getMcastDir(), this.s.getLocalPort());
    }

    @Override
    public void run() {
        System.out.println("[MULTICASTER] Multicaster iniciado");
        while(true){
            anunciar();
            try {
                Thread.currentThread().sleep(this.config.getAnnounceInterval()*1000);
            } catch (InterruptedException e) {
                //TODO: Manejar excepcion
            } 
        }
    }

    private void anunciar(){
        System.out.println("[MULTICASTER] Hora de anunciar!");
        String paquete="";
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
                    sendDatagram(this.s, paquete);
                }
                
            }else if ((num_canal_paquete % this.config.getUDPMaxNumChannels()) == 0){ //Si es el final de un paquete
                //Escribo canal
                paquete += canal.getChannelAnnounce()+"\n";
                //Si era el ultimo de todos los canales, acabo con un END
                if (num_canal_paquete == this.config.getChannelCollection().getNumCanales()){
                    paquete += "END";
                    sendDatagram(this.s, paquete);
                }else{//Si todavía quedan canales, acabo con un MORE
                    paquete += "MORE";
                    sendDatagram(this.s, paquete);
                }
            
            }else{ //Si es un canal intermedio
                //Escribo canal
                paquete += canal.getChannelAnnounce()+"\n";
                //Si soy el último escribo END
                if (num_canal_paquete == this.config.getChannelCollection().getNumCanales()){
                    paquete += "END";
                    sendDatagram(this.s, paquete);
                }
            }
            num_canal_paquete++;//Aumento el contador de canales tratados
        }
    }
    
    //Enviar por el socket multicast s el string paquete
    private void sendDatagram(MulticastSocket s, String paquete) {
        System.out.println("[MULTICASTER] Enviado el siguiente paquete: \n" + paquete);
    }
}
