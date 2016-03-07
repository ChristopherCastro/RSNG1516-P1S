/**
 * Se pretende construir un sistema de anuncio de videos bajo demanda o canales
 * de streaming para una red IPv6. Repositorio:
 * https://github.com/ChristopherCastro/RSNG1516-P1S
 */
package main;

import tools.IpTools;
import cast.MultiCaster;
import client.ClientDesk;
import java.io.File;
import java.net.InetAddress;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

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
     * ~$ s_server -f conffile -p puerto -m mdir -o mpuerto
     * ```
     * 
     * En donde:
     * 
     * - conffile: fichero de programas a servir
     * - puerto: puerto en el que el servidor escuchará peticiones de clientes
     * - mdir: dirección a la que el servidor enviará los anuncios
     * - mpuerto: puerto al que el servidor enviará los anuncios
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        OptionParser parser = new OptionParser("f:p:m:o:");
        OptionSet options = parser.parse(args);
        checkArgs(options);
        s_server.config = new Configuration(
            (String) options.valueOf("f"),
            InetAddress.getByName((String) options.valueOf("m")),
            Integer.parseInt((String) options.valueOf("p")),
            Integer.parseInt((String) options.valueOf("o"))
        );

        MultiCaster multicaster = new MultiCaster(s_server.config);
        new Thread(multicaster).start();

        ClientDesk desk = new ClientDesk(s_server.config);
        new Thread(desk).start();
    }

    /**
     * Comprueba que se tienen todos los argumentos necesarios y con el formato
     * adecuado.
     * 
     * @param options
     * @throws Exception 
     */
    public static void checkArgs(OptionSet options) throws Exception {
        System.out.println("╓░░▒▒▓▓██████[CONFIGURACIÓN INICIAL]██████▓▓▒▒░░");
        // Comprobación de que se incluyen todas las opciones necesarias
        if (!(options.has("f") && options.has("p") && options.has("m") && options.has("o"))) {
            throw new Exception("El servidor debe lanzarse de la siguiente manera: s_server -f conffile -p puerto -m mdir -o mpuerto");
        }
        // Comprobación de que todas las opciones van acompañadas de un argumento
        if (!(options.hasArgument("f") && options.hasArgument("p") && options.hasArgument("m") && options.hasArgument("o"))) { //Falta algún argumento a alguna opción
            throw new Exception("Falta información sobre algún argumento");
        }

        // Comprobación de que el fichero de configuración existe
        File f = new File((String) options.valueOf("f"));
        if (f.exists() && !f.isDirectory()) {
            System.out.println("╟Fichero de configuración: " + (String) options.valueOf("f"));
        } else {
            throw new Exception("El fichero de configuración <<" + (String) options.valueOf("f") + ">> no existe. ¿Seguro que ha introducido bien la ruta?");
        }

        // Comprobación de que se ha dado un puerto de escucha válido
        if ((Integer.parseInt((String) options.valueOf("p")) < 0 || Integer.parseInt((String) options.valueOf("p")) > 65535)) {
            throw new Exception("El puerto " + options.valueOf("p") + " no es un puerto de escucha válido.");
        } else {
            System.out.println("╟Puerto de escucha del servidor: " + options.valueOf("p"));
        }

        // Comprobación de que se ha dado una dirección de multicast válida
        if (IpTools.isIpAddress((String) options.valueOf("m"))) {
            System.out.println("╟Dirección de anunciado multicast: " + options.valueOf("m"));
        } else {
            throw new Exception("La dirección para multicast <<" + options.valueOf("m") + ">> no es una dirección válida. Debe tener formato ipv4 o ipv6");
        }

        // Comprobación de que se ha dado un puerto de multicast válido
        if ((Integer.parseInt((String) options.valueOf("o")) < 0 || Integer.parseInt((String) options.valueOf("o")) > 65535)) {
            throw new Exception("El puerto " + options.valueOf("o") + " no es un puerto de multicast válido.");
        } else {
            System.out.println("╟Puerto de anunciado multicast: " + options.valueOf("o"));
        }
        System.out.println("╙░░▒▒▓▓██████[Argumentos correctos]██████▓▓▒▒░░");
    }
}
