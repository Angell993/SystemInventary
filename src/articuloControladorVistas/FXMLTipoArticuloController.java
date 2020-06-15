package articuloControladorVistas;

import conexionbasedatos.ConexionInventario;
import empleadoControladoresVista.FXMLModificarEmpleadoController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;

public class FXMLTipoArticuloController implements Initializable {

    @FXML
    private TextField txtIva, txtArticulo, txtNombreArticulo, txtCodbar;
    private String sentencia;
    
    @FXML
    private void registrarTipoArticulo(ActionEvent event) {
        if (!txtArticulo.getText().isEmpty() && !txtIva.getText().isEmpty()) {
            registrarTipoAr();
        }
        if (!txtNombreArticulo.getText().isEmpty() && !txtCodbar.getText().isEmpty()) {
            registrarArticulo();
        }
    }

    private void registrarTipoAr() {
        sentencia = SentenciasSQL.ingresarTipoArticulo + "(' " + txtArticulo.getText() + " ', " + txtIva.getText() + ")";
        ConexionInventario.EjecutarSQL(sentencia);
        borrarTipo();
    }

    private void registrarArticulo() {
        sentencia = SentenciasSQL.ingresarProducto + "(' " + txtNombreArticulo.getText() + " ', " + txtCodbar.getText() + ")";
        ConexionInventario.EjecutarSQL(sentencia);
        borrarArticulo();
        txtCodbar.setText(String.valueOf(MetodosJavaClass.codeBar()));
    }

    private void borrarTipo() {
        txtArticulo.setText(null);
        txtIva.setText(null);
    }
    
    private void borrarArticulo(){
        txtNombreArticulo.setText(null);
        txtCodbar.setText(null);        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCodbar.setText(String.valueOf(MetodosJavaClass.codeBar()));
        while (!existeCodeBar(Integer.parseInt(txtCodbar.getText()))) {
            txtCodbar.setText(String.valueOf(MetodosJavaClass.codeBar()));
        }
    }

    private Boolean existeCodeBar(int code) {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlCodebar);
            while (dato.next()) {
                if (dato.getInt(1) == code) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
