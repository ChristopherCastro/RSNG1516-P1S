package error;

/**
 * Clase base para mensajes de error producto de una petición (request) inválida
 * por parte de un cliente.
 *
 * @author Chris
 */
public class RequestBaseException extends Exception {
    public RequestBaseException(final String message) {
        super(String.format("REQ FAIL %s", message));
    }

    public RequestBaseException(final Exception ex) {
        super(String.format("REQ FAIL %s", ex.toString()));
    } 
}
