package cast;

import channel.Channel;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
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
        System.out.printf("[MULTICASTER] Iniciando servicio de anunciado multicast a %s:%s ...\n", this.config.getMcastDir(), this.config.getMcastPort());
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
        int maxSize = this.config.getUDPPacketSize();
        System.out.println("[MULTICASTER] ¡Anunciando!");
        String paquete="SSER "+this.config.getServerPort() +"\n"; //TODO: Añadir IP del servidor opcionalmente
        String nuevaLinea = "";
        for (Channel canal : this.config.getChannelCollection().getCollection()) { //Voy recorriendo todos los canales disponibles
            nuevaLinea= canal.getChannelAnnouncement()+"\n";
            if (paquete.length()+nuevaLinea.length()+"MORE".length() <= maxSize) {//Si cabe en el paquete, lo añado
                paquete += nuevaLinea;
            }else{ //Si no cabe es que tengo que enviar el paquete acumulado e introducir el canal en uno nuevo
                paquete += "MORE";
                sendDatagram(this.s, paquete);
                paquete="SSER "+this.config.getServerPort() +"\n"; //TODO: Añadir IP del servidor opcionalmente
                paquete += nuevaLinea;
            }
        }
        
        //Envio el último paquete
        paquete += "END";
        sendDatagram(this.s, paquete);
        
         
     }
    
    
    /*DEPRECATED: Cuando el tamaño del paquete se delimitaba por nº de canales/lineas a anuncias */
    
    private void anunciarPorLineas(){
        System.out.println("[MULTICASTER] ¡Anunciando!");
        String paquete="";
        int num_canal_paquete = 1;
        for (Channel canal : this.config.getChannelCollection().getCollection()) {
            if ((num_canal_paquete % this.config.getUDPMaxNumChannels()) == 1){ //Si es el inicio de un paquete
                //Escribo SSER puertoservidor [ipservidor]
                paquete="SSER "+this.config.getServerPort() +"\n"; //TODO: Añadir IP del servidor opcionalmente
                //Escribo canal 
                paquete += canal.getChannelAnnouncement()+"\n";
                
                //Si también es el ultimo canal ( primer y ultimo del paquete, ultimo del listado)
                if (num_canal_paquete == this.config.getChannelCollection().size()){
                    paquete += "END";
                    sendDatagram(this.s, paquete);
                }
                
            }else if ((num_canal_paquete % this.config.getUDPMaxNumChannels()) == 0){ //Si es el final de un paquete
                //Escribo canal
                paquete += canal.getChannelAnnouncement()+"\n";
                //Si era el ultimo de todos los canales, acabo con un END
                if (num_canal_paquete == this.config.getChannelCollection().size()){
                    paquete += "END";
                    sendDatagram(this.s, paquete);
                }else{//Si todavía quedan canales, acabo con un MORE
                    paquete += "MORE";
                    sendDatagram(this.s, paquete);
                }
            
            }else{ //Si es un canal intermedio
                //Escribo canal
                paquete += canal.getChannelAnnouncement()+"\n";
                //Si soy el último escribo END
                if (num_canal_paquete == this.config.getChannelCollection().size()){
                    paquete += "END";
                    sendDatagram(this.s, paquete);
                }
            }
            num_canal_paquete++;//Aumento el contador de canales tratados
        }
    }
    
    //Enviar por el socket multicast s el string paquete
    private void sendDatagram(MulticastSocket s, String paquete) {
        System.out.println("[MULTICASTER] Enviado el siguiente paquete:(Size " + paquete.length() + ") \n" + paquete);
        byte[] datagrama_contenido = paquete.getBytes();
        DatagramPacket datagrama = new DatagramPacket(datagrama_contenido,datagrama_contenido.length, this.config.getMcastDir(), this.config.getMcastPort());
        try {
            s.send(datagrama);
        } catch (IOException ex) {
            Logger.getLogger(MultiCaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
