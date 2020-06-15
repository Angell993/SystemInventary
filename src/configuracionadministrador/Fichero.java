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
import metodosjavaClass.Alertas;

public class Fichero {

    private final String rutaDB = "./configDB.dat";
    private final String rutaCorreo = "./configCorreo.dat";

    private File creacionVerificacionFichero(String ruta, boolean verifica) {
        File fichero = new File(ruta);
        if (verifica) {
            fichero.delete();
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                fichero.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fichero;
    }

    public void escribirObjeto(String server, String user, String pass, boolean verificar) {
        ConfigDB config;
        if (verificar) {
            if (leerObjetoDB().getServer().equals(server) && leerObjetoDB().getUsuario().equals(user) && leerObjetoDB().getPass().equals(pass)) {
                Alertas.mensajeInformación("Archivo", "Cambios realizados.");
            } else {
                try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaDB, verificar), true))) {
                    config = new ConfigDB(server, user, pass);
                    objOut.writeObject(config);
                    objOut.close();
                    Alertas.mensajeInformación("Archivo", "Cambios realizados.");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaDB, false), true))) {
                config = new ConfigDB(server, user, pass);
                objOut.writeObject(config);
                objOut.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void escribirObjeto(String correoEmpresa, String constrasenia, boolean verifica) {
        ConfigCorreo configC = new ConfigCorreo();
        if (verifica) {
            try {
                if (leerObjetoCorreo().getCorreo().equals(correoEmpresa) && leerObjetoCorreo().getContrasenia().equals(constrasenia)) {
                    Alertas.mensajeInformación("Correo", "Cambios realizado");
                } else {
                    try {
                        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaCorreo, verifica), true))) {
                            configC.setCorreo(correoEmpresa);
                            configC.setContrasenia(constrasenia);
                            objOut.writeObject(configC);
                            Alertas.mensajeInformación("Correo", "Cambios realizado");
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Fichero.class
                                .getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(Fichero.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (NullPointerException e) {
                try {
                    try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaCorreo, false), true))) {
                        configC.setCorreo(correoEmpresa);
                        configC.setContrasenia(constrasenia);
                        objOut.writeObject(configC);
                        Alertas.mensajeInformación("Correo", "Cambios realizado");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Fichero.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (IOException ex) {
                    Logger.getLogger(Fichero.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            try {
                try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(creacionVerificacionFichero(rutaCorreo, false), true))) {
                    configC.setCorreo(correoEmpresa);
                    configC.setContrasenia(constrasenia);
                    objOut.writeObject(configC);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Fichero.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(Fichero.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
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
            Logger.getLogger(Fichero.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }

    public ConfigCorreo leerObjetoCorreo() {
        ConfigCorreo correoConfig = new ConfigCorreo();
        try {
            try (ObjectInputStream objetInpu = new ObjectInputStream(new FileInputStream(creacionVerificacionFichero(rutaCorreo, false).toString()))) {
                correoConfig = (ConfigCorreo) objetInpu.readObject();

            } catch (StreamCorruptedException | EOFException ex) {
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Fichero.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return correoConfig;
    }

}
