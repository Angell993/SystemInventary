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
    private TextField txtCodArticulo, txtNomArticulo, txtPrecioVenta, txtPrecioCosto;
    @FXML
    private TextField txtStock, txtFecha, txtCodBarras;
    @FXML
    private ComboBox<Item> cmbArticulo;
    @FXML
    private ComboBox<Item> cmbProveedor;
    @FXML
    private ObservableList<Item> listaArticulo;
    @FXML
    private ObservableList<Item> listaProveedor;
    private LLenarCombos llenacomb = new LLenarCombos();

    @FXML
    private void ingresarArticulo(ActionEvent event) {
        System.out.println(cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor());
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.isDouble(txtPrecioVenta.getText()) && MetodosJavaClass.isDouble(txtPrecioCosto.getText())) {
                if (MetodosJavaClass.esNumero(txtCodBarras.getText())) {
                    if (MetodosJavaClass.cmbSeleccionado(cmbArticulo) && MetodosJavaClass.cmbSeleccionado(cmbProveedor)) {
                        String sentencia = SentenciasSQL.ingresarArticulo + "('" + txtNomArticulo.getText() + "', " + Double.valueOf(txtPrecioVenta.getText())
                                + " , " + Double.valueOf(txtPrecioCosto.getText()) + " , " + txtStock.getText()
                                + " , " + cmbArticulo.getSelectionModel().getSelectedItem().getId()
                                + " , '" + cmbProveedor.getSelectionModel().getSelectedItem().getDocProveedor()
                                + "' , '" + Fecha.fechaSQl() + "', " + Integer.parseInt(txtCodBarras.getText()) + " )";
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
        cmbArticulo.getSelectionModel().select(-1);
        cmbProveedor.getSelectionModel().select(-1);
        ;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //codArticulo.setText(String.valueOf(metodosJavaclass.identificador()));
        llenacomb.llenarComboBox(listaArticulo, cmbArticulo, SentenciasSQL.sqlArticulo);
        llenacomb.llenarComboProveedor(listaProveedor, cmbProveedor, SentenciasSQL.sqlProveedorComb);
        txtFecha.setText(Fecha.fecha());
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
}
