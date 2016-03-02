package main;

import java.net.InetAddress;

/**
 *
 * Esta clase embebe toda la configuración del servidor 
 */
public class Configuration {
    ChannelCollection channels; //Colección de canales
    int serverPort; // Puerto en el que escucha el servidor
    InetAddress mcastAddress; //Dirección a la que realizar los anuncios multicast
    int mcastPort; //Puerto al que realizar los anuncios multicast

    public Configuration(String channels_filepath ,InetAddress mcastAddress, int serverPort, int mcastPort) {
        this.channels = new ChannelCollection(channels_file_path);
        this.mcastAddress = mcastAddress;
        this.serverPort = serverPort;
        this.mcastPort = mcastPort;
        System.out.println("[Configuration] Fichero ");
    }

    //Recuperar puerto
    public int getServerPort() {
        return this.serverPort;
    }
    
    //Recuperar dirección a la que realizar los anuncios multicast
    public InetAddress getMcastDir() {
        return this.mcastAddress;
    }
    
    public int getMcastPort(){
        return this.mcastPort;
    }
}
