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

    private final String rutaDB = "./configDB.dat";
    private final String rutaCorreo = "./configCorreo.dat";

    private File creacionVerificacionFichero(String ruta) {
        File fichero = new File(ruta);
        try {
            fichero.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fichero;
    }

    public void escribirObjeto(String server, String user, String pass) {
        ConfigDB config;
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaDB), true))) {
            config = new ConfigDB(server, user, pass);
            objOut.writeObject(config);
            objOut.close();

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
        ConfigDB config = new ConfigDB();
        try {
            try (ObjectInputStream objetInput = new ObjectInputStream(new FileInputStream(rutaDB))) {
                config = (ConfigDB) objetInput.readObject();
            } catch (StreamCorruptedException | EOFException ex) {
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }

    public ConfigCorreo leerObjetoCorreo() {
        ConfigCorreo correoConfig = new ConfigCorreo();
        try {
            try (ObjectInputStream objetInpu = new ObjectInputStream(new FileInputStream(creacionVerificacionFichero(rutaCorreo).toString()))) {
                correoConfig = (ConfigCorreo) objetInpu.readObject();
            } catch (StreamCorruptedException | EOFException ex) {
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correoConfig;
    }

}
