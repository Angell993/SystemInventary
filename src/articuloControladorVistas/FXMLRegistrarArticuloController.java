package articuloControladorVistas;

import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import metodosjavaClass.Alertas;
import metodosjavaClass.Fecha;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;

public class FXMLRegistrarArticuloController implements Initializable {

    @FXML
    private TextField txtPrecioVenta, txtPrecioCosto;
    @FXML
    private TextField txtStock, txtFecha, txtCodBarras;
    @FXML
    private ComboBox<Item> cmbArticulo;
    @FXML
    private ComboBox<Item> cmbTipoArticulo;
    @FXML
    private ComboBox<Item> cmbProveedor;
    private ObservableList<Item> listaTipoArticulo;
    private ObservableList<Item> listaArticulo;
    private ObservableList<Item> listaProveedor;
    private final LLenarCombos llenacomb = new LLenarCombos();

    @FXML
    private void ingresarArticulo(ActionEvent event) {
        int stock_minimo = 5, stock_maximo = 30;
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.isDouble(txtPrecioVenta.getText()) && MetodosJavaClass.isDouble(txtPrecioCosto.getText())) {
                if (MetodosJavaClass.esNumero(txtCodBarras.getText())) {
                    if (MetodosJavaClass.cmbSeleccionado(cmbArticulo) && MetodosJavaClass.cmbSeleccionado(cmbProveedor)) {
                        if (Integer.parseInt(txtStock.getText()) > stock_minimo) {
                            if (verificarArticulo()) {
                                String sentencia = SentenciasSQL.ingresarArticulo + "('" + cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion()
                                        + "', " + Double.valueOf(txtPrecioVenta.getText())
                                        + " , " + Double.valueOf(txtPrecioCosto.getText()) + " , " + txtStock.getText()
                                        + "," + stock_minimo + "," + stock_maximo
                                        + " , " + cmbTipoArticulo.getSelectionModel().getSelectedItem().getId()
                                        + " , '" + cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor()
                                        + "' , '" + Fecha.fechaSQl() + "', " + cmbArticulo.getSelectionModel().getSelectedItem().getId() + " )";

                                ConexionInventario.EjecutarSQL(sentencia);
                                clearArticulo(event);
                            }
                        } else {
                            Alertas.mensajeAdvertencia("Stock", "El stock minimo es 5 y el maximo es 30.");
                        }
                    }
                }
            }
        }

    }

    @FXML
    private void clearArticulo(ActionEvent event) {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
        cmbTipoArticulo.getSelectionModel().select(-1);
        cmbProveedor.getSelectionModel().select(-1);
        cmbArticulo.getSelectionModel().select(-1);
    }

    @FXML
    private void articuloSeleccionado() {
        if (!cmbArticulo.getSelectionModel().isSelected(-1)) {
            llenacomb.llenarComboProveedor(listaProveedor, cmbProveedor, SentenciasSQL.sqlProveedorComb
                    + cmbArticulo.getSelectionModel().getSelectedItem().getId());

            txtCodBarras.setText(cmbArticulo.getSelectionModel().getSelectedItem().getDocProveedor());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenacomb.llenarComboBox(listaTipoArticulo, cmbTipoArticulo, SentenciasSQL.sqlTipArticulo);
        llenacomb.articuloCodBar(listaArticulo, cmbArticulo, SentenciasSQL.sqlProducto);
        txtFecha.setText(Fecha.fecha());
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtPrecioVenta);
        listaDatos.add(txtPrecioCosto);
        listaDatos.add(txtStock);
        listaDatos.add(txtCodBarras);

        return listaDatos;
    }

    private Boolean verificarArticulo() {
        try {
            ResultSet articulo = ConexionInventario.sSQL("select proveedor.No_documento, producto.codigo_barras from articulo "
                    + " inner join producto on articulo.codigo_barras = producto.id_articulo "
                    + " inner join proveedor on articulo.cod_proveedor = proveedor.No_documento;");
            while (articulo.next()) {
                if (txtCodBarras.getText().equals(articulo.getString("producto.codigo_barras"))
                        && cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor().equals(articulo.getString("proveedor.No_documento"))) {
                    Alertas.mensajeInformación("Articulo", "El artículo ya esta registrado.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
