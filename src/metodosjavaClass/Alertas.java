package metodosjavaClass;

import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {

    //      Errores
    public static void mensajeError(String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public static void errorSQL(String header, SQLException sql) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(sql.getLocalizedMessage());
        alert.showAndWait();
    }


    //      Alertas
    public static void mensajeAdvertencia(String titulo, String mensajeCuerpo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensajeCuerpo);
        alert.showAndWait();
    }
    
    /*Mensajes de Informacion en el sistema*/

    public static void mensajeConfirmacion(String titulo, String mensajeCuerpo) {
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle(titulo);
        mensaje.setHeaderText(null);
        mensaje.setContentText(mensajeCuerpo);
        mensaje.show();
    }

    public static void mensajeInformación(String titulo, String mensajeCuerpo) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle(titulo);
        mensaje.setHeaderText(null);
        mensaje.setContentText(mensajeCuerpo);
        mensaje.show();
    }

    public static Boolean ConfirmacionEleminarOModificar() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Estas seguro que deseas eliminar el registro?");
        Optional<ButtonType> action = alerta.showAndWait();
        return action.get() != ButtonType.CANCEL;
    }
    
    public static Boolean puestoConfirmacion(String puesto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Puesto");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Estas seguro que deseas darle el puesto de "+puesto+"?");
        Optional<ButtonType> action = alerta.showAndWait();
        return action.get() != ButtonType.CANCEL;
    }
    
}
