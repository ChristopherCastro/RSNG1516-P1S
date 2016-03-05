
package client;


public class RequestMessage {
    
    protected String req;
    protected String id;
    protected String port;
    protected String clientAdress;

    public RequestMessage(String command) {
        String[] parameter = command.split("(' ')|(\t)");
        this.req = parameter[0];
        this.id = parameter[1];
        this.port = parameter[2];
        this.clientAdress =  parameter[3];
    }
}
