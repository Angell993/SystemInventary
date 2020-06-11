package opciones;

import clasesjava.Cliente;
import clasesjava.Venta;
import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import metodosjavaClass.SentenciasSQL;
import reportesistemainventario.CrearInforme;

public class FXMLFacturaController implements Initializable {

    @FXML
    private TableView<Venta> tblVenta;
    @FXML
    private TableColumn<Venta, String> clmArticulo;
    @FXML
    private TableColumn<Venta, Integer> clmCantidad;
    @FXML
    private TableColumn<Venta, Float> clmPrecio;
    @FXML
    private TableColumn<Venta, Float> clmTotal;
    @FXML
    private TextField txtDocumento, txtFactura, txtTotalImporte;
    @FXML
    private Label lblIdentidad;
    private ObservableList<Venta> listaVenta;
    private ObservableList<Cliente> datosCliente;
    private ResultSet dato;

    @FXML
    private void imprimir() {
        if (!txtFactura.getText().isEmpty() && !txtDocumento.getText().isEmpty()) {
            String url = "src/ticket&factura/FacturaCliente";
            CrearInforme ventaTicket = new CrearInforme();
            ventaTicket.factura(txtFactura.getText(), url, datosCliente);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarFactura();
        inicializarCliente();
    }

    private void inicializarCliente() {

        txtDocumento.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!txtDocumento.getText().isEmpty()) {
                existeCliente(txtDocumento.getText());
            }else{
                lblIdentidad.setText(null);
            }
        });
    }

    private ObservableList<Venta> factura(String factura) {
        listaVenta = FXCollections.observableArrayList();
        ResultSet datos = ConexionInventario.sSQL(SentenciasSQL.sqlFacturaImprimir + " '" + factura + "'");
        try {
            double importe = 0;
            while (datos.next()) {
                listaVenta.add(new Venta(txtFactura.getText(), datos.getString(1), datos.getInt(2), datos.getDouble(3), datos.getDouble(4)));
                importe = datos.getDouble(5);
            }
            txtTotalImporte.setText(String.valueOf(importe));
            clmArticulo.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
            clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
            clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precioArticulo"));
            clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVenta;
    }

    private void inicializarFactura() {

        txtFactura.textProperty().addListener((ov, oldValue, newValue) -> {
            if (existeFactura(txtFactura.getText())) {
                tblVenta.setItems(factura(txtFactura.getText()));
            }
            if (txtFactura.getText().isEmpty()) {
                txtTotalImporte.setText(null);
                listaVenta.clear();
                tblVenta.setItems(listaVenta);
            }
        });
    }

    private void existeCliente(String cliente) {
        datosCliente = FXCollections.observableArrayList();
        try {
            dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsulCliente + " '" + cliente + "'");
            while (dato.next()) {
                if (dato.getString(1).equals(cliente)) {
                    lblIdentidad.setText(dato.getString(2) + "  " + dato.getString(3));
                   datosCliente.add( new Cliente(dato.getString(1),dato.getString(2),dato.getString(3),dato.getString(4),
                            dato.getString(5),dato.getString(6),dato.getInt(7)));
                } else {
                    txtDocumento.setText("");
                    lblIdentidad.setText("No existe el cliente");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean existeFactura(String factura) {
        try {
            dato = ConexionInventario.sSQL("SELECT Nnm_factura from factura where Nnm_factura = '" + factura + "'");
            while (dato.next()) {
                if (dato.getString(1).equals(factura)) {
                    return true;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
