/**
 * Se pretende construir un sistema de anuncio de videos bajo demanda o canales de streaming para una red IPv6.
 * Repositorio: https://github.com/ChristopherCastro/RSNG1516-P1S
 */
package rsng1516.p1s;


import java.io.File;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Clase primigénea. Se encarga de validar los argumentos y lanzar 2 hilos. Uno para atender peticiones de los clientes y otro para enviar anuncios multicast.
 * @author Carolina Barcelo
 * @author Christpher Castro
 * @author Iñigo Ezcurdia
 */
public class s_server {

    /**
     * s_server -f conffile -p puerto -m mdir -o mpuerto
     * conffile: fichero de programas a servir
     * puerto: puerto en el que el servidor escuchará peticiones de clientes
     * mdir: dirección a la que el servidor enviará los anuncios
     * mpuerto: puerto al que el servidor enviará los anuncios
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // Parseo de argumentos
        OptionParser parser = new OptionParser( "f:p:m:o:" );
        OptionSet options = parser.parse( args );
        checkArgs(options);
        
        MultiCaster multicaster = new MultiCaster((String) options.valueOf("f"), (String) options.valueOf("m"), Integer.parseInt((String) options.valueOf("o")));
        new Thread(multicaster).start();
        
        ClientDesk desk = new ClientDesk((String) options.valueOf("f"), Integer.parseInt((String) options.valueOf("p")));
        new Thread(desk).start();
    }
    
    //Comprueba que se tienen todos los argumentos necesarios y con el formato adecuado.
    public static void checkArgs(OptionSet options) throws Exception{
        System.out.println("╓░░▒▒▓▓██████[CONFIGURACIÓN INICIAL]██████▓▓▒▒░░");
        //Comprobación de que se incluyen todas las opciones necesarias
        if (!(options.has("f") && options.has("p") && options.has("m") && options.has("o"))){
           throw new Exception("El servidor debe lanzarse de la siguiente manera: s_server -f conffile -p puerto -m mdir -o mpuerto"); 
        }
        //Comprobación de que todas las opciones van acompañadas de un argumento
        if (!(options.hasArgument("f") && options.hasArgument("p") && options.hasArgument("m") && options.hasArgument("o"))){ //Falta algún argumento a alguna opción
           throw new Exception("Falta información sobre algún argumento"); 
        }
        
        //Comprobación de que el fichero de configuración existe
        File f = new File((String) options.valueOf("f"));
        if(f.exists() && !f.isDirectory()) { 
            System.out.println("╟Fichero de configuración: " + (String) options.valueOf("f"));
        }else{
            throw new Exception("El fichero de configuración <<" + (String) options.valueOf("f") + ">> no existe. ¿Seguro que ha introducido bien la ruta?"); 
        }
        
        //Comprobación de que se ha dado un puerto de escucha válido
        if ((Integer.parseInt((String) options.valueOf("p"))<0 || Integer.parseInt((String) options.valueOf("p"))>65535)){
            throw new Exception("El puerto " + options.valueOf("p") + " no es un puerto de escucha válido."); 
        }else{
            System.out.println("╟Puerto de escucha: " + options.valueOf("p"));
        }
        
        //Comprobación de que se ha dado una dirección de multicast válida
        if (Utilities.isIpAddress((String) options.valueOf("m"))){
            System.out.println("╟Dirección de anunciado multicast: " + options.valueOf("m"));
        }else{
            throw new Exception("La dirección para multicast <<" + options.valueOf("m") + ">> no es una dirección válida. Debe tener formato ipv4 o ipv6"); 
        }

        //Comprobación de que se ha dado un puerto de multicast válido
        if ((Integer.parseInt((String) options.valueOf("o"))<0 || Integer.parseInt((String) options.valueOf("o"))>65535)){
            throw new Exception("El puerto " + options.valueOf("o") + " no es un puerto de multicast válido."); 
        }else{
            System.out.println("╟Puerto de anunciado multicast: " + options.valueOf("o"));
        }
        System.out.println("╙░░▒▒▓▓██████[Argumentos correctos]██████▓▓▒▒░░");
    }
    
}