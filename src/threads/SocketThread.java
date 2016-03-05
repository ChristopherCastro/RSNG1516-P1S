/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import client.ClientHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carol
 */
public class SocketThread extends Thread {

    private ClientHandler parent;

    public SocketThread(ClientHandler parent) {
        super();
        this.parent = parent;
    }

    public void run() {
        if (this.isInterrupted()) {
            this.parent.setBlock(true);
        }

        if (this.parent.isBlock() && this.isAlive()) {
            try {
                this.interrupt();
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
