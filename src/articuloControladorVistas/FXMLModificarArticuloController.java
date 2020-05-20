package articuloControladorVistas;

import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import clasesjava.Articulo;
import javafx.event.ActionEvent;
import metodosjavaClass.Alertas;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.VentanaRootPane;

public class FXMLModificarArticuloController implements Initializable {

    @FXML
    private TextField txtNomArticulo, txtCodBarras, txtPrecioVenta, txtPrecioCosto, txtStock, txtFecha;
    @FXML
    private ComboBox<Item> cmbArticulo;
    @FXML
    private ComboBox<Item> cmbProveedor;
    private ObservableList<Item> listaArticulo;
    private ObservableList<Item> listaProveedor;
    private LLenarCombos llenarComb = new LLenarCombos();
    private Articulo articulo = new Articulo();
    private String sentencia;
    private int idArticulo;

    private void actualizarTabla() {
        articulo.setIdArticulo(idArticulo);
        articulo.setNombreArticulo(txtNomArticulo.getText());
        articulo.setPrecioVenta(Float.valueOf(txtPrecioVenta.getText()));
        articulo.setPrecioCosto(Float.valueOf(txtPrecioCosto.getText()));
        articulo.setCantidadStock(Integer.parseInt(txtStock.getText()));
        articulo.setDescripcionArticulo(cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion());
        articulo.setNombreProveedor(cmbProveedor.getSelectionModel().getSelectedItem().getDescripcion());
        articulo.setFecha(txtFecha.getText());
        articulo.setCodigoBarras(Integer.parseInt(txtCodBarras.getText()));
    }

    @FXML
    private void modificarArticulo(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            sentencia = SentenciasSQL.sqlModificarArticulo + " nombre = '" + txtNomArticulo.getText() + "', precio_venta = " + Double.valueOf(txtPrecioVenta.getText())
                    + " , precio_costo = " + Double.valueOf(txtPrecioCosto.getText()) + " , stock = " + Integer.parseInt(txtStock.getText())
                    + " , cod_tipo_articulo = " + cmbArticulo.getSelectionModel().getSelectedItem().getId()
                    + " , cod_proveedor = '" + cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor()
                    + "' , fecha_ingreso = '" + txtFecha.getText() + "', codigo_barras = " + Integer.parseInt(txtCodBarras.getText())
                    + " WHERE id_articulo = " + idArticulo;
            ConexionInventario.EjecutarSQL(sentencia);
            actualizarTabla();
            cerrarVentana(event);

        }
    }

    @FXML
    private void eliminarArticulo(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (Alertas.eliminarConfirmacion()) {
                sentencia = SentenciasSQL.sqlEliminarArticulo + " id_articulo = " + idArticulo;
                ConexionInventario.EjecutarSQL(sentencia);
                cerrarVentana(event);
            }
        }
    }

    public void recibirDatos(Articulo articulo) {
        this.articulo = articulo;
        idArticulo = articulo.getIdArticulo();
        txtNomArticulo.setText(articulo.getNombreArticulo());
        txtCodBarras.setText(String.valueOf(articulo.getCodigoBarras()));
        txtPrecioVenta.setText(String.valueOf(articulo.getPrecioVenta()));
        txtPrecioCosto.setText(String.valueOf(articulo.getPrecioCosto()));
        txtStock.setText(String.valueOf(articulo.getCantidadStock()));
        txtFecha.setText(articulo.getFecha());
        
        cmbArticulo.getSelectionModel().select(new Item(MetodosJavaClass.obtenerId(SentenciasSQL.sqlTipoArticulo + "'" + articulo.getDescripcionArticulo()+"'"),
                articulo.getDescripcionArticulo()));
        cmbProveedor.getSelectionModel().select(new Item(MetodosJavaClass.obtenerDocumentoProveedor(articulo.getNombreProveedor()), articulo.getNombreProveedor()));
        llenarComb.llenarComboBox(listaArticulo, cmbArticulo, SentenciasSQL.sqlArticulo);
        llenarComb.llenarComboProveedor(listaProveedor, cmbProveedor, SentenciasSQL.sqlProveedorComb);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtNomArticulo);
        listaDatos.add(txtPrecioVenta);
        listaDatos.add(txtPrecioCosto);
        listaDatos.add(txtStock);
        listaDatos.add(txtCodBarras);

        return listaDatos;
    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        VentanaRootPane.closeVentana(event);
    }

    public Articulo getArticulo() {
        return articulo;
    }

}
