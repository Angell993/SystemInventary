package articuloControladorVistas;

import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import metodosjavaClass.SentenciasSQL;

public class FXMLTipoArticuloController implements Initializable {

    @FXML
    TextField txtIva, txtArticulo;

    @FXML
    private void registrarTipoArticulo(ActionEvent event) {
        if (!txtArticulo.getText().isEmpty() && !txtIva.getText().isEmpty()) {
            String sentencia = SentenciasSQL.ingresarTipoArticulo + "(' " + txtArticulo.getText() + " ', " + txtIva.getText() + ")";
            ConexionInventario.EjecutarSQL(sentencia);
            borrarDescripcion(event);
        }
    }

    @FXML
    private void borrarDescripcion(ActionEvent event) {
        txtArticulo.setText(null);
        txtIva.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
