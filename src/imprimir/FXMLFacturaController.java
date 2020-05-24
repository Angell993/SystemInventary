package imprimir;

import clasesjava.Item;
import clasesjava.Venta;
import clienteControladorVistas.FXMLRegistrarClienteController;
import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.Alertas;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import reportesistemainventario.CrearInforme;

public class FXMLFacturaController implements Initializable {

    @FXML
    private TableView<Item> tblFacturas;
    @FXML
    private TableColumn<Item, Number> clmFactura;
    @FXML
    private TableColumn<Item, String> clmFecha;

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
    private TextField txtDocumento, txtFactura;
    @FXML
    private AnchorPane facturaDB;
    @FXML
    private Label lblIdentidad;
    private ObservableList<Item> listaFactura;
    private ObservableList<Venta> listaVenta;

    @FXML
    private void Buscar(KeyEvent event) {
        if (!txtDocumento.getText().isEmpty()) {
            facturaDB.setVisible(true);
            tblFacturas.setItems(distintasFacturas(txtDocumento.getText()));

        }

    }

    @FXML
    private void buscarFacturaDB(MouseEvent event) {
        if (event.getClickCount() == 2) {
            txtFactura.setText(String.valueOf(tblFacturas.getSelectionModel().getSelectedItem().getId()));
        }
    }

    @FXML
    private void imprimir() {
        if (!txtFactura.getText().isEmpty()) {
            String url = "C:\\Users\\juanc\\OneDrive\\Documentos\\NetBeansProjects\\GitProyecto\\SystemInventary\\ticket&factura\\RegistroFactura";
            CrearInforme ventaTicket = new CrearInforme();
            ventaTicket.factura(txtFactura.getText(), url);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarFactura();
    }

    private ObservableList<Item> distintasFacturas(String documento) {
        listaFactura = FXCollections.observableArrayList();
        ResultSet datos = ConexionInventario.sSQL(SentenciasSQL.sqlDistintasFactura + " '" + documento + "'");
        try {
            while (datos.next()) {
                listaFactura.add(new Item(Integer.parseInt(datos.getString(1)), datos.getString(2)));
            }
            clmFactura.setCellValueFactory(new PropertyValueFactory<>("id"));
            clmFecha.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaFactura;
    }

    private ObservableList<Venta> factura(String factura) {
        listaVenta = FXCollections.observableArrayList();
        ResultSet datos = ConexionInventario.sSQL(SentenciasSQL.sqlFacturaImprimir + " '" + factura + "'");
        try {
            String doc = null;
            while (datos.next()) {
                listaVenta.add(new Venta(datos.getString(1), datos.getInt(2), datos.getFloat(3), datos.getFloat(4)));
                doc = datos.getString(5);
            }
            txtDocumento.setText(doc);
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
            if (MetodosJavaClass.esNumero(txtFactura.getText())) {
                tblVenta.setItems(factura(txtFactura.getText()));
                tblFacturas.setItems(distintasFacturas(txtDocumento.getText()));
                facturaDB.setVisible(true);
                existeCliente();

            }
        });
    }

    private Boolean existeCliente() {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsulCliente + " '" + txtDocumento.getText() + "'");
            while (dato.next()) {

                String nombre = dato.getString(1);
                String apellido = dato.getString(2);
                lblIdentidad.setText(nombre + "  " + apellido);
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtDocumento.setText("");
        lblIdentidad.setText("No existe el cliente");
        return false;
    }

}
