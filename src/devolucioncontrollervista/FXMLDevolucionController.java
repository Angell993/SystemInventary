package devolucioncontrollervista;

import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import metodosjavaClass.Alertas;
import metodosjavaClass.Fecha;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;

public class FXMLDevolucionController implements Initializable {

    @FXML
    private TextField txtFactura, txtArticulo, txtMotivo, txtFecha, txtCantidad, txtEmpleado;

    @FXML
    private void registrarDevolucion() {
        //Con este if compruebo en la base de datos si ya esta registrado la devolucion
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.isDouble(txtCantidad.getText())) {
                if (existeDevolucion() && existeDevolucionFactura()) {
                    String sentencia = SentenciasSQL.ingresarDevolucion + "('" + txtFactura.getText() + "', " + Integer.parseInt(txtArticulo.getText())
                            + ", '" + txtMotivo.getText() + "', '" + Fecha.fechaSQl()
                            + "', " + Integer.parseInt(txtCantidad.getText()) + ", " + Integer.parseInt(txtEmpleado.getText()) + " )";
                    ConexionInventario.EjecutarSQL(sentencia);
                    borrar();
                }
            }
        }

    }

    @FXML
    private void borrar(){
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
    }
    
    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtFactura);
        listaDatos.add(txtArticulo);
        listaDatos.add(txtMotivo);
        listaDatos.add(txtCantidad);

        return listaDatos;
    }

    public void recuperarDatos(int codEmpleado) {
        txtEmpleado.setText(String.valueOf(codEmpleado));
        txtFecha.setText(Fecha.fecha());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    
    private Boolean existeDevolucion() {
        try {
            ResultSet codFactura = ConexionInventario.sSQL(SentenciasSQL.sqlDevolucion);
            while (codFactura.next()) {
                if (txtFactura.getText().equals(codFactura.getString(1))) {
                    Alertas.mensajeErrorPers("DEvolución", "El nº de Devolución " + txtFactura.getText() + " ya está registrado ");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    private Boolean existeDevolucionFactura() {
        try {
            ResultSet codFactura = ConexionInventario.sSQL(SentenciasSQL.sqlDevolucion);
            while (codFactura.next()) {
                if (!txtFactura.getText().equals(codFactura.getString(1))) {
                    Alertas.mensajeErrorPers("Factura", "El nº de Factura " + txtFactura.getText() + " no existe");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
