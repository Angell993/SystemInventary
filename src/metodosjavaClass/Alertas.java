package metodosjavaClass;

import static com.lowagie.tools.BuildTutorial.action;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {

    //      Errores
    public static void mensajeError(String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Operación errónea");
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

    public static void mensajeErrorPers(String header, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //      Alertas
    public static void alertaPers(String titulo,String header, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    /*Mensajes de Informacion en el sistema*/

    public static void mensajeConfirmacion(String aceptarTitulo, String mensajeConfirmacion) {
        Alert mensaje = new Alert(Alert.AlertType.CONFIRMATION);
        mensaje.setTitle(aceptarTitulo);
        mensaje.setHeaderText(mensajeConfirmacion);
        mensaje.show();
    }

    public static void mensajeInformación(String aceptarTitulo, String mensajeConfirmacion) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle(aceptarTitulo);
        mensaje.setHeaderText(mensajeConfirmacion);
        mensaje.show();
    }

    public static Boolean Confirmacion() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Estas seguro que deseas eliminar el registro?");
        Optional<ButtonType> action = alerta.showAndWait();
        return action.get() != ButtonType.CANCEL;
    }
    
    public static Boolean puestoConfirmacion() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Puesto");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Estas seguro que deseas darle el puesto de Administrador Total?");
        Optional<ButtonType> action = alerta.showAndWait();
        return action.get() != ButtonType.CANCEL;
    }
    
     public static void información(String aceptarTitulo, String cuerpoMensaje) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle(aceptarTitulo);
        mensaje.setContentText(cuerpoMensaje);
        mensaje.show();
    }
}
