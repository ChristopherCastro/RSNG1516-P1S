package error;

/**
 * Clase base para mensajes de error producto de una petición (request) inválida
 * por parte de un cliente.
 * 
 * Estas excepciones pueden ser casteados a string de forma segura para
 * comunicar al cliente siguiendo el protocolo de nivel de aplicación.
 *
 * @author Chris
 */
public class RequestBaseException extends Exception {
    public RequestBaseException(final String message) {
        super(String.format("REQ FAIL %s", message));
    }

    public RequestBaseException(final Exception ex) {
        super(String.format("REQ FAIL %s", ex.getMessage()));
    }

    @Override
    public String toString()
    {
        return this.getMessage();
    }
}
