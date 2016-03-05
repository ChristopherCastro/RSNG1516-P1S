package error;

/**
 * Representa el mensaje de error al solicitar un canal/programa de video
 * inexistente.
 * 
 * @author Chris
 */
public class ChannelNotFoundException extends RequestBaseException {
    public ChannelNotFoundException(final String message) {
        super(message);
    }
}
