package conexionbasedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import metodosjavaClass.Alertas;

public class ConexionInventario {

    private static ConexionDB conn = new ConexionDB();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet datosRest = null;

    //se ejecuta correctamente pero no se que ase los if
    public static void EjecutarSQL(String sentencia) {
        try {
            preparedStatement = conn.conectar().prepareStatement(sentencia);
            int i = preparedStatement.executeUpdate();
            if(!mensajeConsulta(i, sentencia)){
                Alertas.mensajeError("Error al ejecutar la sentencia.");
            }
        } catch (SQLException  e) {
            if (sentencia.contains("DELETE FROM proveedor")) {
                Alertas.mensajeAdvertencia("Proveedor", "El proveedor no se puede eliminar,\nhace referencia a un articulo registrado en la tabla artículo.");
            }else{
                e.printStackTrace();
                Alertas.errorSQL("Error al ejecutar la sentencia. ", e);                
            }
            
        }
    }

    public static ResultSet sSQL(String sentencia) {
        try {
            preparedStatement = conn.conectar().prepareStatement(sentencia);
            datosRest = preparedStatement.executeQuery();

        } catch (SQLException e) {
            Alertas.errorSQL("Error al ejecutar la sentencia.", e);
        }
        return datosRest;
    }

    public static boolean EjecutarSQL_TRANSACT(Connection conTransat, String sentencia) {
        try {
            preparedStatement = conTransat.prepareStatement(sentencia);
            int i = preparedStatement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            System.out.println(e);
            Alertas.errorSQL("Error al ejecutar la sentencia.", e);
        }
        return false;
    }

    public static ResultSet sSQL_TRANSACT(Connection conTransat, String sentencia) {
        try {
            preparedStatement = conTransat.prepareStatement(sentencia);
            datosRest = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            Alertas.errorSQL("Error al ejecutar la sentencia.", e);
        }
        return datosRest;
    }

    /*
    
     */
    private static boolean mensajeConsulta(int i, String sentencia) {
        if (i > 0) {
            if (sentencia.startsWith("UPDATE")) {
                Alertas.mensajeConfirmacion("ACTUALIZACIÓN", "Se ha actualizado correctamente en la Base de Datos.");
                conn.cerrarConexion();
                return true;
            }
            if (sentencia.startsWith("DELETE")) {
                Alertas.mensajeConfirmacion("BORRADO", "Se ha eliminado de la Base de Datos.");
                conn.cerrarConexion();
                return true;
            }
            if (sentencia.startsWith("INSERT")) {
                Alertas.mensajeConfirmacion("INSERCIÓN", "Se ha registrado correctamente en la Base de Datos.");
                conn.cerrarConexion();
                return true;
            }
        }
        return false;
    }
}
