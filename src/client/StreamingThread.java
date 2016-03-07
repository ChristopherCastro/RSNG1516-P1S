package client;

import channel.Channel;
import error.ChannelNotFoundException;
import error.RequestBaseException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class StreamingThread extends Thread {

    protected ClientHandler parent;
    protected RequestMessage request;
    protected Process scriptRunning = null;

    public StreamingThread(final ClientHandler parent, final RequestMessage request) {
        super();
        this.parent = parent;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            Channel video = this.parent.getChannels().getById(this.request.getChannelId());
            this.scriptRunning = Runtime.getRuntime().exec(video.getScript());
            this.scriptRunning.waitFor(); // bloqueante
        } catch (ChannelNotFoundException ex) {
            this.parent.getOut().println(ex);
        } catch (InterruptedException ex) {
            this.parent.getOut().println(new RequestBaseException(ex));
        } catch (IOException ex) {
            Logger.getLogger(SocketInspectorThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // finaliza
            System.out.println("[StreamingThread]: Finishing streaming");
            this.parent.onStreamingFinish();
        }
    }

    public void stopStreaming() {
        System.out.println("[StreamingThread]: Streaming will be killed now");
        this.scriptRunning.destroy();
    }
}
