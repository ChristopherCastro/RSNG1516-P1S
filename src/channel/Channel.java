/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package channel;

public class Channel {
    
    protected int channelId;
    protected String script;
    protected String title;

    public Channel(int channelId, String bash, String title) {
        this.channelId = channelId;
        this.script = bash;
        this.title = title;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getChannelAnnounce(){
        return "PRG " + this.channelId + " " + this.title;
    }
    
    
}
