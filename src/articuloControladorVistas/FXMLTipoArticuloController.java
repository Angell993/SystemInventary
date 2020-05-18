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
    TextField codArticulo, descripcionArt;

    @FXML
    private void registrarTipoArticulo(ActionEvent event) {
        System.out.println("Estoy en botón de registrar *-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        String sentencia = SentenciasSQL.ingresarTipoArticulo + "(' " + descripcionArt.getText() + " ')";
        ConexionInventario.EjecutarSQL(sentencia);
        borrarDescripcion(event);
    }
    
    @FXML private void borrarDescripcion(ActionEvent event){
        descripcionArt.setText(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
