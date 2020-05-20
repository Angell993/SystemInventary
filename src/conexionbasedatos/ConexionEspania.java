package conexionbasedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConexionEspania {

    private static final ConexionDB conn = new ConexionDB();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet rs = null;

    public static ResultSet sSQL(String sentencia) {

        try {
            preparedStatement = conn.conectarEspania().prepareStatement(sentencia);
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("�ERROR!");
            alert.setHeaderText("Error al ejecutar la sentencia");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        return rs;
    }

}
