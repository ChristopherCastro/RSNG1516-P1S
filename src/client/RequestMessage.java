
package client;


public class RequestMessage {
    
    protected String req;
    protected String id;
    protected String port;
    protected String clientAdress;

    public RequestMessage(String command) {
        String[] parameter = command.split("(' ')|(\t)");
        this.req = req;
        this.id = id;
        this.port = port;
        this.clientAdress = clientAdress;
    }
    
    
    
    
}
