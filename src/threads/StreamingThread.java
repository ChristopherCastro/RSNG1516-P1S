/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import channel.Channel;
import client.ClientHandler;
import client.RequestMessage;
import error.ChannelNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carol
 */
public class StreamingThread extends Thread {

    protected ClientHandler parent;
    protected RequestMessage request;

    public StreamingThread(ClientHandler parent, RequestMessage req) {
        super();
        this.parent = parent;
        this.request = req;
    }

    public void run() {
        try {
            Channel video = this.parent.getChannels().getById(this.request.getChannelId());
            Process bash = Runtime.getRuntime().exec(video.getScript());

            if (this.isInterrupted() || bash.exitValue()!=0 || !bash.isAlive()) {
                this.parent.setBlock(true);
            }

            if (this.parent.isBlock() && this.isAlive()) {
                bash.destroyForcibly();
                this.interrupt();
                this.finalize();
            }

        } catch (ChannelNotFoundException ex) {
            Logger.getLogger(StreamingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StreamingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
