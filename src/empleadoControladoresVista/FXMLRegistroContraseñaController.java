package empleadoControladoresVista;

import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import metodosjavaClass.Alertas;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;

public class FXMLRegistroContraseñaController implements Initializable {

    @FXML
    TextField txtEmpleado, txtContrasenia, repitContrasenia;

    @FXML
    private void registrarLogin(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && isIgual()) {
            String sentencia = SentenciasSQL.ingresarLogin + " (" + Integer.parseInt(txtEmpleado.getText()) + ", '" + txtContrasenia.getText() + "')";
            ConexionInventario.EjecutarSQL(sentencia);
            VentanaRootPane.closeVentana(event);
        }
    }

    public void recogerCodEmpleado(String codEmpleado) {
        txtEmpleado.setText(codEmpleado);
    }

    @FXML
    private void borrar(ActionEvent event) {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtContrasenia);
        listaDatos.add(repitContrasenia);

        return listaDatos;
    }
    
    private Boolean isIgual(){
        String aux = datosArray().get(0).getText();
        for (int i = 1; i < datosArray().size(); i++) {
            if (!aux.equals(datosArray().get(i).getText())) {
                Alertas.mensajeErrorPers("Contraseña", "Contraseñas no son iguales.");
                return false;
            }
        }
        return true;
    }

}
