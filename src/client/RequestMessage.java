/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Carol
 */
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
