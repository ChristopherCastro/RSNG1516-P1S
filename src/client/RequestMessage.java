package client;

import error.InvalidRequestException;
import java.net.Socket;
import tools.IpTools;

public class RequestMessage {
    protected int channelId;
    protected Client client;

    public RequestMessage(final Socket socket, final String command) throws InvalidRequestException {
        if (!command.startsWith("REQ")) {
            throw new InvalidRequestException("Petición inválida: " + command);
        }

        try {
            String[] parameter = command.split("\\s|\\t");
            this.channelId = Integer.parseInt(parameter[1]);
            int clientPort = Integer.parseInt(parameter[2]);
            String clientAddress =  parameter.length == 4 ? parameter[3] : IpTools.clientAddress(socket);
            this.client = new Client(clientAddress, clientPort);
        } catch (Exception ex) {
            throw new InvalidRequestException("Petición inválida: " + command);
        }
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
    public Client getClient() {
        return this.client;
    }
}
