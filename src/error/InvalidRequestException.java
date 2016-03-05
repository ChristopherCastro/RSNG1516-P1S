package error;

/**
 * Representa un mensaje de error para una petición inválida por parte de un
 * cliente.
 * 
 * @author Chris
 */
public class InvalidRequestException extends RequestBaseException {
    public InvalidRequestException(final String message) {
        super(message);
    }
}
