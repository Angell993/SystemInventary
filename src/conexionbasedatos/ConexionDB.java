package conexionbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionDB {
    private final String user = "root";
    private final String pass = "root";
    private final String server = "jdbc:mysql://localhost:3306/sistemainventario?verifyServerCertificate=false&useSSL=true";
    private final String espaniadb = "jdbc:mysql://localhost:3306/espania?verifyServerCertificate=false&useSSL=true";
    private Connection conexion;
    
    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(server, user, pass);
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR, no se puede conectar a la Data Base.\n" + ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public Connection conectarEspania() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(espaniadb, user, pass);
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR, no se puede conectar a la Data Base.\n" + ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public void cerrarConexion(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
