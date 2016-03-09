package channel;

import client.Client;
import java.io.File;

public class Channel {

    /**
     * Identificador de canal
     */
    protected int id;
    
    /**
     * Ruta el fichero de video
     */
    protected String videoPath;
    
    /**
     * Título descriptivo del video
     */
    protected String title;
    
    /**
     * Ruta el directorio que contiene los scripts de streaming
     */
    protected String scriptsPath;

    public Channel(final String scriptsPath, final int getId, final String path, final String title) {
        this.scriptsPath = scriptsPath;
        this.id = getId;
        this.videoPath = path;
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
     * Generates a string line suitable for UDP multi-casting announcement.
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
     * @param client Cliente para el cual preparar el comando
     * @return 
     */
    public String streamingCommand(final Client client) {
        String pattern = System.getProperty("os.name").toLowerCase().contains("win") ? "bash %s %s %s %s" : "%s %s %s %s";
        String base = System.getProperty("os.name").toLowerCase().contains("win") ? "/send.bat" : "/send.bash";
        File scriptFile = new File(this.scriptsPath + base);

        return String.format(
            pattern,
            scriptFile.getAbsolutePath(),
            client.getAddress(),
            client.getReceivePort(),
            this.getVideoPath()
        );
    }
}
