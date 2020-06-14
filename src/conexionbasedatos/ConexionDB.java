package conexionbasedatos;

import configuracionadministrador.Fichero;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import metodosjavaClass.Alertas;

public class ConexionDB {

    private String user, pass, server;
    private final String espaniadb = "jdbc:mysql://localhost:3306/espania?verifyServerCertificate=false&useSSL=true";
    private Connection conexion;

    public ConexionDB() {
    }

    public ConexionDB(String server, String user, String pass) {
        this.server = server;
        this.user = user;
        this.pass = pass;
    }

    /*public Connection conectarPrueba() {
        Alertas.mensajeError("Estoy en el metodo conectarPrueba");
        try {
            extraerDatos();
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemainventario?verifyServerCertificate=false&useSSL=true",
                    "root", "saladino");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Alertas.mensajeConfirmacion("URL", getServer());
            Alertas.mensajeConfirmacion("User", getUser());
            Alertas.mensajeConfirmacion("Pass", getUser());
            Alertas.errorSQL("Conexion", ex);
            Alertas.mensajeInformación("Conexión", "Usuario, Contraseña o\nBase de Datos incorrectas");
        }
        return conexion;
    }*/

    public Connection conectar() {
        try {
            if (extraerDatos()) {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(getServer(), getUser(), getPass());
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(getServer(), getUser(), getPass());
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) { 
            Alertas.errorSQL("Conexion", ex);
            Alertas.mensajeInformación("Conexión", "Usuario, Contraseña o\nBase de Datos incorrectas");
        }
        return conexion;
    }

    public Connection conectarDiferenteDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(getServer(), getUser(), getPass());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Alertas.mensajeInformación("Conexión", "Usuario, Contraseña o\nBase de Datos incorrectas");
        }
        return conexion;
    }

    public Connection conectarEspania() {
        try {
            extraerDatos();
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(espaniadb, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    private boolean extraerDatos() {
        File file = new File("./configDB.dat");
        if (file.exists()) {
            Fichero datosDB = new Fichero();
            setServer(datosDB.leerObjetoDB().getServer());
            setUser(datosDB.leerObjetoDB().getUsuario());
            setPass(datosDB.leerObjetoDB().getPass());
            return true;
        }
        return false;
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
