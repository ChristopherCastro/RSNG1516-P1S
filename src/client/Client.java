package client;

/**
 * Representa un cliente.
 *
 * @author Chris
 */
public class Client {

    /**
     * Dirección IP en donde puede ser encontrado el cliente
     */
    protected String address;
    
    /**
     * Puerto en donde el cliente acepta la recepción de videos.
     */
    protected int receivePort;

    public Client(final String address, final int receivePort) {
        this.address = address;
        this.receivePort = receivePort;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @return the receivePort
     */
    public int getReceivePort() {
        return this.receivePort;
    }
}
