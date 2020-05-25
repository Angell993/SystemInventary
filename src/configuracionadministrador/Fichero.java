package configuracionadministrador;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fichero {
    private final String rutaDB = "src/configuracionadministrador/configDB.dat";   
    private final String rutaCorreo = "src/configuracionadministrador/configCorreo.dat";

    private File creacionVerificacionFichero(String ruta) {
        File fichero = new File(ruta);
        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
                return fichero;
            } catch (IOException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fichero;
    }

    public void escribirObjeto(String server, String user, String pass) {
        ConfigDB config = new ConfigDB();
        
        try {
            try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaDB), true))) {
                config.setServer(server);
                config.setUsuario(user);
                config.setPass(pass);
                objOut.writeObject(config);            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void escribirObjeto(String correoEmpresa, String constrasenia) {
        ConfigCorreo configC = new ConfigCorreo();
         try {
            try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaCorreo), true))) {
                configC.setCorreo(correoEmpresa);
                configC.setContrasenia(constrasenia);
                objOut.writeObject(configC);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConfigDB leerObjetoDB() {
        ConfigDB config = null;
        try {
            try (ObjectInputStream objetInput = new ObjectInputStream(new FileInputStream(creacionVerificacionFichero(rutaDB).toString()))) {
                config = (ConfigDB) objetInput.readObject();
            } catch (StreamCorruptedException | EOFException ex) {
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }

    public ConfigCorreo leerObjetoCorreo() {
        ConfigCorreo correoConfig = null;
        try {
            try (ObjectInputStream objetInpu = new ObjectInputStream(new FileInputStream(creacionVerificacionFichero(rutaCorreo).toString()))) {
                correoConfig = (ConfigCorreo) objetInpu.readObject();
            } catch (StreamCorruptedException | EOFException ex) {
            }
        } catch (IOException | ClassNotFoundException  ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correoConfig;
    }
    
}
