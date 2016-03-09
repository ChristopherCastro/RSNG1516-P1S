/**
 * Se pretende construir un sistema de anuncio de videos bajo demanda o canales
 * de streaming para una red IPv6. Repositorio:
 * https://github.com/ChristopherCastro/RSNG1516-P1S
 */
package main;

import cast.MultiCaster;
import client.ClientDesk;

/**
 * Clase primigénea. Se encarga de validar los argumentos y lanzar 2 hilos. Uno
 * para atender peticiones de los clientes y otro para enviar anuncios
 * multicast.
 *
 * @author Carolina Barcelo
 * @author Christpher Castro
 * @author Iñigo Ezcurdia
 */
public class s_server {
    protected static Configuration config;

    /**
     * Modo de uso:
     * 
     * ```
     * ~$ s_server [config_file]
     * ```
     * 
     * En donde:
     * 
     * - [config_file]: Ruta al fichero de configuración para esta instancia de server
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        s_server.config = new Configuration(args[0]);

        MultiCaster multicaster = new MultiCaster(s_server.config);
        new Thread(multicaster).start();

        ClientDesk desk = new ClientDesk(s_server.config);
        new Thread(desk).start();
    }
}
