package main;

import java.net.InetAddress;
import channel.*;

/**
 *
 * Esta clase embebe toda la configuración del servidor 
 */
public class Configuration {
    protected int announceInterval = 10; //Intervalo de tiempo en s entre anuncios
    protected int UDPMaxNumChannels = 5; //Numero de canales a enviar en cada paquete UDP de anunciado multicast (Debe ser >=2)
    protected int UDPPacketSize = 1250; //Tamaño en bytes del paquete UDP a enviar con el anunciado de canales
    protected ChannelCollection channels; //Colección de canales
    protected int serverPort; // Puerto en el que escucha el servidor
    protected InetAddress mcastAddress; //Dirección a la que realizar los anuncios multicast
    protected int mcastPort; //Puerto al que realizar los anuncios multicast

    public Configuration(
        final String channelsFilePath,
        final InetAddress mcastAddress,
        final int serverPort,
        final int mcastPort
    ) {
        this.channels = new ChannelCollection(channelsFilePath);
        this.mcastAddress = mcastAddress;
        this.serverPort = serverPort;
        this.mcastPort = mcastPort;

        System.out.println("[Configuration] Configuración del server completada con éxito");
    }

    public int getServerPort() {
        return this.serverPort;
    }
    
    public InetAddress getMcastDir() {
        return this.mcastAddress;
    }
    
    public int getMcastPort(){
        return this.mcastPort;
    }
    
    public ChannelCollection getChannelCollection(){
        return this.channels;
    }

    public int getAnnounceInterval() {
        return announceInterval;
    }

    public int getUDPMaxNumChannels() {
        return UDPMaxNumChannels;
    }

    public int getUDPPacketSize() {
        return UDPPacketSize;
    }
}
