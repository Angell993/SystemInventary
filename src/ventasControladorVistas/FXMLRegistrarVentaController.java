package ventasControladorVistas;

import conexionbasedatos.ConexionInventario;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import metodosjavaClass.SentenciasSQL;
import clasesjava.Item;
import clasesjava.Venta;
import conexionbasedatos.ConexionDB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.Alertas;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import sistemaInventarioControladorVistas.FXMLSistemaInventarioController;

public class FXMLRegistrarVentaController implements Initializable {

    @FXML
    private TextField nFactura, precioArticulo, txtTotalCompra;

    @FXML
    ComboBox<Item> cmbArticulo;
    @FXML
    ComboBox<Item> cmbCantidad;
    @FXML
    private TableColumn<Venta, Integer> clmNumVenta;
    @FXML
    private TableColumn<Venta, String> clmNombre;
    @FXML
    private TableColumn<Venta, Integer> clmCantidad;
    @FXML
    private TableColumn<Venta, Double> clmPrecio;
    @FXML
    private TableColumn<Venta, Double> clmTotal;
    @FXML
    private Button aniadir;

    @FXML
    private TableView<Venta> tblVenta;

    private final LLenarCombos llenarCmb = new LLenarCombos();
    private final ConexionDB con = new ConexionDB();
    private Connection transaction;
    private ObservableList<Venta> listaArticulo;
    private ObservableList<Double> listaTotalCompra;
    private ObservableList<Item> listaCmbArticulo;
    private ObservableList<Item> listaCantidad;
    private ObservableList<Item> idCantidadComprada;
    private String codEmpleado;
    private int n_Compra = 0;
    private Double total;
    private Venta venta;
    private AnchorPane anchorPane;

    @FXML
    private void realizarPago(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventasControladorVistas/FXMLPago.fxml"));
            Parent root = loader.load();
            FXMLPagoController pagar = loader.getController();
            pagar.recibirInformacionPago(nFactura.getText(), codEmpleado, String.valueOf(sumarDineroTotal()), idCantidadComprada, anchorPane, listaArticulo);
            anchorPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void obtenerArticulo(MouseEvent event) {
        if (event.getClickCount() == 2) {
            tblVenta.getSelectionModel().getSelectedIndex();
        }
    }

    @FXML
    private void llenarCantidad(ActionEvent event) {
        if (!cmbArticulo.getSelectionModel().isSelected(-1)) {
            recibirPrecio(cmbArticulo.getSelectionModel().getSelectedItem().getId());
            int idArticulo = cmbArticulo.getSelectionModel().getSelectedItem().getId();
            llenarCmb.llenarComboBox(listaCantidad, cmbCantidad, SentenciasSQL.sqlCantidad + idArticulo);
        }
    }

    @FXML
    private void añadirArticulo() {

        clmNumVenta.setCellValueFactory(new PropertyValueFactory<>("numeroCompra"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
        clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precioArticulo"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));

        tblVenta.setItems(guardarCompraOB());
        sumarDineroTotal();
        cmbArticulo.getSelectionModel().select(-1);
        cmbCantidad.getSelectionModel().select(-1);
        precioArticulo.setText("0,00");
        llenarCmb.llenarComboBox(listaCmbArticulo, cmbArticulo, SentenciasSQL.sqlArticulos);
    }

    private double sumarDineroTotal() {
        double sum = 0;
        for (int i = 0; i < listaTotalCompra.size(); i++) {
            sum += new BigDecimal(listaTotalCompra.get(i)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        txtTotalCompra.setText(MetodosJavaClass.quitarDecimal(sum));
        return sum;
    }

    @FXML
    private void cancelar() {
        tblVenta.getItems().clear();
        n_Compra = 0;
        listaArticulo.clear();
        listaTotalCompra.clear();
        txtTotalCompra.setText("");

    }

    @FXML
    private void eliminarFilaVenta() {
        if (tblVenta.getSelectionModel().getSelectedIndex() <= -1) {
            Alertas.mensajeInformación("Compra", "Añada una Compra");
        } else {
            try {
                Venta vent = tblVenta.getSelectionModel().getSelectedItem();
                listaArticulo.remove(vent);
                listaTotalCompra.remove(tblVenta.getSelectionModel().getSelectedIndex());
                sumarDineroTotal();
                n_Compra = 1;
                for (int i = 0; i < listaArticulo.size(); i++) {
                    listaArticulo.get(i).setNumeroCompra(n_Compra);
                    n_Compra++;
                }
                n_Compra -= 1;
                tblVenta.refresh();
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                listaTotalCompra.clear();
                txtTotalCompra.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transaction = con.conectar();
        generarNFactura();
        listaArticulo = FXCollections.observableArrayList();
        listaTotalCompra = FXCollections.observableArrayList();
        llenarCmb.llenarComboBox(listaCmbArticulo, cmbArticulo, SentenciasSQL.sqlArticulos);
        precioArticulo.setText("0,00");
    }

    private void generarNFactura() {
        nFactura.setText(String.valueOf(MetodosJavaClass.identificador()));
        while (!existeFactura(nFactura.getText())) {
            nFactura.setText(String.valueOf(MetodosJavaClass.identificador()));
        }
    }

    public void recibirCodEmpleado(String empleado, AnchorPane anchorPane) {
        codEmpleado = empleado;
        this.anchorPane = anchorPane;
    }

    //He modificado aquí, cerrando la conexion de resultset en try con recursos
    private void recibirPrecio(int idArticulo) {
        try {
            String sSelect = SentenciasSQL.obtenerPrecio + idArticulo + " ;";
            try (ResultSet rSet = conexionbasedatos.ConexionInventario.sSQL(sSelect)) {
                while (rSet.next()) {
                    double precio = rSet.getDouble(3);
                    precioArticulo.setText(MetodosJavaClass.quitarDecimal(precio));
                }
            }
        } catch (SQLException ex) {
            Alertas.errorSQL("ERROR DE SQL", ex);
        }
    }

    private boolean comprobarCampos() {

        if (cmbArticulo.getSelectionModel().isSelected(-1)) {
            Alertas.mensajeError("¡Debe seleccionar un Artículo!");
            return false;
        }
        if (cmbCantidad.getSelectionModel().isSelected(-1)) {
            Alertas.mensajeError("¡Debe seleccionar una cantidad mínima!");
            return false;
        }
        return true;
    }

    private Boolean existeFactura(String codFactura) {
        try {
            try (ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarFactura)) {
                while (dato.next()) {
                    if (dato.getString(1).equals(codFactura)) {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private ObservableList<Venta> guardarCompraOB() {
        venta = new Venta();
        if (comprobarCampos()) {
            n_Compra++;
            String nombreArt = cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion();
            Double precio = MetodosJavaClass.quitarComa(precioArticulo.getText());
            int cantidad = Integer.parseInt(cmbCantidad.getSelectionModel().getSelectedItem().getDescripcion());
            total = precio * cantidad;
            venta.setNumeroFactura(nFactura.getText());
            venta.setNumeroCompra(n_Compra);
            venta.setNombreArticulo(nombreArt);
            venta.setCantidadCompra(cantidad);
            venta.setPrecioArticulo(precio);
            venta.setTotalCompra(MetodosJavaClass.quitarComa(String.valueOf((Math.rint(total * 100) / 100))));
            listaArticulo.add(venta);

            listaTotalCompra.add(total);
            idCantidadConpraArray(nombreArt, cantidad);
        }
        return listaArticulo;
    }

    private void idCantidadConpraArray(String articulo, int cantidad) {
        idCantidadComprada = FXCollections.observableArrayList();
        idCantidadComprada.add(new Item(MetodosJavaClass.obtenerId(SentenciasSQL.sqlIdArticulo + " '" + articulo + "'"),
                String.valueOf(cantidad)));
    }

    public void anularCrearNuevaVenta(String empleado, AnchorPane anchorPane) {
        this.codEmpleado = empleado;
        this.anchorPane = anchorPane;
        transaction = con.conectar();
    }

    public void registrarMasCompra(ObservableList<Venta> listventa, String factura, String codEmpleado, AnchorPane rootPane) {
        this.anchorPane = rootPane;
        this.codEmpleado = codEmpleado;
        transaction = con.conectar();
        nFactura.setText(factura);
        for (int i = 0; i < listventa.size(); i++) {
            venta = new Venta();
            total = listventa.get(i).getPrecioArticulo() * listventa.get(i).getCantidadCompra();
            venta.setNumeroFactura(nFactura.getText());
            venta.setNumeroCompra(listventa.get(i).getNumeroCompra());
            venta.setNombreArticulo(listventa.get(i).getNombreArticulo());
            venta.setCantidadCompra(listventa.get(i).getCantidadCompra());
            venta.setPrecioArticulo(listventa.get(i).getPrecioArticulo());
            venta.setTotalCompra(total);
            listaArticulo.add(venta);
            listaTotalCompra.add(total);
            clmNumVenta.setCellValueFactory(new PropertyValueFactory<>("numeroCompra"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
            clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
            clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precioArticulo"));
            clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));

            tblVenta.setItems(listaArticulo);
            n_Compra = listventa.get(i).getNumeroCompra();
        }

        sumarDineroTotal();
        cmbArticulo.getSelectionModel().select(-1);
        cmbCantidad.getSelectionModel().select(-1);
        precioArticulo.setText("0,00");
    }
}
