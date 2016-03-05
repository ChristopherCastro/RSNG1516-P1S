package client;

import error.InvalidRequestException;
import java.net.Socket;

public class RequestMessage {
    protected int channelId;
    protected int clientPort;
    protected String clientAddress;

    public RequestMessage(final Socket socket, final String command) throws InvalidRequestException {
        if (!command.startsWith("REQ")) {
            throw new InvalidRequestException("Petición inválida: " + command);
        }

        String[] parameter = command.split("\\s|\\t");
        this.channelId = Integer.parseInt(parameter[1]);
        this.clientPort = Integer.parseInt(parameter[2]);
        this.clientAddress =  parameter.length == 4 ? parameter[3] : socket.getInetAddress().toString();
    }

    /**
     * @return the channelId
     */
    public int getChannelId() {
        return this.channelId;
    }

    /**
     * @return the clientPort
     */
    public int getClientPort() {
        return this.clientPort;
    }

    /**
     * @return the clientAddress
     */
    public String getClientAddress() {
        return this.clientAddress;
    }
}
