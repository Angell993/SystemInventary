package articuloControladorVistas;

import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.isDouble(txtPrecioVenta.getText()) && MetodosJavaClass.isDouble(txtPrecioCosto.getText())) {
                if (MetodosJavaClass.esNumero(txtCodBarras.getText())) {
                    if (MetodosJavaClass.cmbSeleccionado(cmbArticulo) && MetodosJavaClass.cmbSeleccionado(cmbProveedor)) {

                        String sentencia = SentenciasSQL.ingresarArticulo + "('" + cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion()
                                + "', " + Double.valueOf(txtPrecioVenta.getText())
                                + " , " + Double.valueOf(txtPrecioCosto.getText()) + " , " + txtStock.getText()
                                + " , " + cmbTipoArticulo.getSelectionModel().getSelectedItem().getId()
                                + " , '" + cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor()
                                + "' , '" + Fecha.fechaSQl() + "', " + cmbArticulo.getSelectionModel().getSelectedItem().getId() + " )";

                        ConexionInventario.EjecutarSQL(sentencia);
                        clearArticulo(event);
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
}
