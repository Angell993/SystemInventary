package conexionbasedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import metodosjavaClass.Alertas;

public class ConexionInventario {

    private static final ConexionDB conn = new ConexionDB();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet datosRest = null;

    //se ejecuta correctamente pero no se que ase los if
    public static void EjecutarSQL(String sentencia) {
        try {
            preparedStatement = conn.conectar().prepareStatement(sentencia);
            int i = preparedStatement.executeUpdate();
            if (!mensajeConsulta(i, sentencia)) {
                Alertas.mensajeErrorPers(null, "Error al ejecutar la sentencia");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alertas.errorSQL("Error al ejecutar la sentencia todo bien", e);
        }
    }

    public static ResultSet sSQL(String sentencia) {
        try {
            preparedStatement = conn.conectar().prepareStatement(sentencia);
            datosRest = preparedStatement.executeQuery();

        } catch (SQLException e) {
            Alertas.errorSQL("Error al ejecutar la sentencia XD", e);
        }
        return datosRest;
    }

    public static boolean EjecutarSQL_TRANSACT(Connection conTransat, String sentencia) {

        try {
            preparedStatement = conTransat.prepareStatement(sentencia);
            System.out.println(sentencia);
            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet sSQL_TRANSACT(Connection conTransat, String sentencia) {
        try {
            preparedStatement = conTransat.prepareStatement(sentencia);
            datosRest = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosRest;
    }

    /*
    
     */
    private static Boolean mensajeConsulta(int i, String sentencia) {
        if (i > 0) {
            if (sentencia.startsWith("UPDATE")) {
                Alertas.alertaPers("ACTUALIZACIÓN", "¡Ha modificado la Base de Datos!", "Se ha actualizado satisfactoriamente");
                conn.cerrarConexion();
                return true;
            }
            if (sentencia.startsWith("DELETE")) {
                Alertas.alertaPers("BORRADO", "¡Ha modificado la Base de Datos!", "Se ha eliminado de la Base de Datos");
                conn.cerrarConexion();
                return true;
            }
            if (sentencia.startsWith("INSERT")) {
                Alertas.alertaPers("INSERCIÓN", "¡Ha modificado la Base de Datos!", "Se ha registrado correctamente en la Base de Datos");
                conn.cerrarConexion();
                return true;
            }
        }
        return false;
    }
}
