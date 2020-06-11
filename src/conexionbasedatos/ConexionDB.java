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

    public Connection conectar() {
        try {
            extraerDatos();
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(getServer(), getUser(), getPass());
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR, no se puede conectar a la Data Base.\n" + ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Alertas.mensajeInformación("Conexión", "Usuario, Contraseña o\nBase de Datos incorrectas");
        }
        return conexion;
    }
    
    public Connection conectarDiferenteDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(getServer(), getUser(), getPass());
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR, no se puede conectar a la Data Base.\n" + ex.toString());
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
            conexion = DriverManager.getConnection(espaniadb, getUser(), getPass());
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR, no se puede conectar a la Data Base.\n" + ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }

    private void extraerDatos() {
        File file = new File("src/configuracionadministrador/configDB.dat");
        if (file.exists()) {
            Fichero f = new Fichero();
            setServer(f.leerObjetoDB().getServer());
            setUser(f.leerObjetoDB().getUsuario());
            setPass(f.leerObjetoDB().getPass());
        }
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
