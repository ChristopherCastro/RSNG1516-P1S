package main;

import java.net.InetAddress;
import channel.*;
import java.io.File;
import java.io.IOException;
import org.ini4j.Ini;

/**
 *
 * Esta clase embebe toda la configuración del servidor 
 */
public class Configuration {
    protected InetAddress mcastAddress;
    protected int mcastPort;
    protected int announceInterval = 10;
    protected int UDPPacketSize = 1250;

    protected String channelsPath;
    protected ChannelCollection channels;
    protected int serverPort;
    protected String scriptsPath;

    /**
     * - server.channels: fichero de programas a servir
     * - server.port: puerto en el que el servidor escuchará peticiones de clientes
     * - cast.addr: dirección a la que el servidor enviará los anuncios
     * - cast.port: puerto al que el servidor enviará los anuncios
     * 
     * @param configPath Ruta el fichero INI
     */
    public Configuration(final String configPath) throws IOException {
        Ini ini = new Ini(new File(configPath));

        // server stuff
        this.serverPort = ini.get("server", "port", int.class);
        this.scriptsPath = ini.get("server", "scripts_path", String.class);
        this.channelsPath = ini.get("server", "channels", String.class);
        this.channels = new ChannelCollection(this.channelsPath, this.scriptsPath);

        // mCaster stuff
        this.mcastAddress = InetAddress.getByName(ini.get("mcast", "addr", String.class));
        this.mcastPort = ini.get("mcast", "port", int.class);
        this.announceInterval = ini.get("mcast", "internval", int.class);
        this.UDPPacketSize = ini.get("mcast", "udp_size", int.class);

        System.out.println("[Configuration] Configuración del server completada con éxito");
        System.out.println(this);
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

    public int getUDPPacketSize() {
        return UDPPacketSize;
    }
    
    @Override
    public String toString() {
        String out = "[CONFIGURATION]\n";
        out += String.format(" - %s: %d\n", "server.port", this.serverPort);
        out += String.format(" - %s: %s\n", "server.scripts_path", this.scriptsPath);
        out += String.format(" - %s: %s\n", "server.channels", this.channelsPath);
        
        out += String.format(" - %s: %s\n", "mcast.addr", this.mcastAddress.toString());
        out += String.format(" - %s: %d\n", "mcast.port", this.mcastPort);
        out += String.format(" - %s: %d\n", "mcast.interval", this.announceInterval);
        out += String.format(" - %s: %d\n", "mcast.udp_size", this.UDPPacketSize);

        return out;
    }
}
