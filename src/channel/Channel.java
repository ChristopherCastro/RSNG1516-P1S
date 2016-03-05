package channel;

public class Channel {
    
    protected int id;
    protected String script;
    protected String title;

    public Channel(final int getId, final String script, final String title) {
        this.id = getId;
        this.script = script;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getScript() {
        return this.script;
    }

    public void setScript(final String script) {
        this.script = script;
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
        return String.format("PRG %i %s", this.id, this.title);
    }
}
