package rsng1516.p1s;

import java.io.File;

/**
 * Se encarga de escuchar en un puerto determinado a la espera de peticiones por parte de los clientes.
 * Por cada petición creará un hilo para atenderla.
 */
class ClientDesk implements Runnable{
    File config; //Fichero que contiene el listado de de programas.
    int port; //Puerto de escucha para atender clientes
    
    ClientDesk(String config, int puerto) {
        this.config = new File(config);
        this.port = puerto;
        System.out.println("[INFO]Iniciando servicio desk de clientes en el puerto " + this.port + "...");
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}