package main;

import java.net.InetAddress;
import channel.*;

/**
 *
 * Esta clase embebe toda la configuración del servidor 
 */
public class Configuration {
    int announceInterval; //Intervalo de tiempo en ms entre anuncios
    int UDPMaxNumChannels = 5; //Numero de canales a enviar en cada paquete UDP de anunciado multicast

    public int getUDPMaxNumChannels() {
        return UDPMaxNumChannels;
    }
    ChannelCollection channels; //Colección de canales
    int serverPort; // Puerto en el que escucha el servidor
    InetAddress mcastAddress; //Dirección a la que realizar los anuncios multicast
    int mcastPort; //Puerto al que realizar los anuncios multicast

    public Configuration(String channelsFilePath ,InetAddress mcastAddress, int serverPort, int mcastPort) {
        this.channels = new ChannelCollection(channelsFilePath);
        this.mcastAddress = mcastAddress;
        this.serverPort = serverPort;
        this.mcastPort = mcastPort;
        System.out.println("[Configuration] Configuración del server completada con éxito");
    }

    //Recuperar puerto
    public int getServerPort() {
        return this.serverPort;
    }
    
    //Recuperar dirección a la que realizar los anuncios multicast
    public InetAddress getMcastDir() {
        return this.mcastAddress;
    }
    
    //Recupera el puerto al que realizar los anuncios multicast
    public int getMcastPort(){
        return this.mcastPort;
    }
    
    //Recupera el numero máximo de canales a incluir en cada paquete UDP
    public ChannelCollection getChannelCollection(){
        return this.channels;
    }
    
    
}
