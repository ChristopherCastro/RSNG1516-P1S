package channel;

import client.Client;

public class Channel {
    
    protected int id;
    protected String videoPath;
    protected String title;

    public Channel(final int getId, final String script, final String title) {
        this.id = getId;
        this.videoPath = script;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(final String script) {
        this.videoPath = script;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Generates a string line suitable for UDP announcement multi-casting.
     *
     * One UDP announcement may contain multiple lines from different channels.
     * 
     * @return 
     */
    public String getChannelAnnouncement() {
        return String.format("PRG %d %s", this.id, this.title);
    }

    /**
     * Prepara el comando de invocación al SO para este canal en particular.
     * 
     * Funciona tanto para Windows como Linux.
     * 
     * @param channel
     * @return 
     */
    public String streamingCommand(final Client client) {
        String pattern = "%s %s %s %s";
        
        // TODO: change to IPv6
        String scriptPath = System.getProperty("os.name").toLowerCase().contains("win") ? "./test/scripts/v4/send.bat" : "./test/scripts/v4/send.bash";

        return String.format(
            pattern,
            scriptPath,
            client.getAddress(),
            client.getReceivePort(),
            this.getVideoPath()
        );
    }
}
