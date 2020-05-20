package proveedorControladorVistas;

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
import metodosjavaClass.CalcularDocumentoIdentidadCIF;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;

public class FXMLRegistrarProveedorController implements Initializable {

    @FXML
    TextField txtDocumento, txtNombre, txtApellido;
    @FXML
    TextField txtComercio, txtTelefono, txtEmail, txtPais;
    @FXML
    TextField txtDireccion, txtProducto;
    @FXML
    private ComboBox<Item> cmbProvincia;
    @FXML
    private ComboBox<Item> cmbMunicipio;
    @FXML
    private ComboBox<Item> cmbDocumento;

    private ObservableList<Item> listaProvincias;
    private ObservableList<Item> listaCiudades;
    private ObservableList<Item> listaDocumento;
    private final MetodosJavaClass metodosJavaclass = new MetodosJavaClass();
    private final LLenarCombos llenarCmb = new LLenarCombos();

    @FXML
    private void seleccionarID(ActionEvent event) {
        if (!cmbProvincia.getSelectionModel().isSelected(-1)) {
            int idProvincia = cmbProvincia.getSelectionModel().getSelectedItem().getId();
            llenarCmb.llenarComboBox(listaCiudades, cmbMunicipio, SentenciasSQL.sqlMunicipios + idProvincia);
        }
    }

    @FXML
    private void registrarProveedor(ActionEvent event) {

        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.cmbSeleccionado(cmbDocumento) && MetodosJavaClass.cmbSeleccionado(cmbProvincia)
                    && MetodosJavaClass.cmbSeleccionado(cmbMunicipio)) {
                //if (documentoValido(cif.getText())) {
                if (existeProveedor()) {
                    String sInsert = SentenciasSQL.ingresarProveedor + "( '" + txtDocumento.getText() + "', " + cmbDocumento.getSelectionModel().getSelectedItem().getId()
                            + ", ' " + txtNombre.getText() + "', '" + txtApellido.getText() + "+', '" + txtComercio.getText() + "', '"
                            + txtTelefono.getText() + "', '" + txtEmail.getText() + "', '" + txtPais.getText()
                            + "', '" + cmbProvincia.getSelectionModel().getSelectedItem().getDescripcion()
                            + "', '" + cmbMunicipio.getSelectionModel().getSelectedItem().getDescripcion()
                            + "', '" + txtDireccion.getText() + "', '" + txtProducto.getText() + "')";

                    ConexionInventario.EjecutarSQL(sInsert);
                    cancelar(event);
                    //}
                }

            }
        }

    }

    @FXML
    private void cancelar(ActionEvent event) {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
    }

    /*Cualquier otro metodo que no se inyecte al controlador*/
    private boolean documentoValido(String identificacionDocumento) {
        CalcularDocumentoIdentidadCIF documento = new CalcularDocumentoIdentidadCIF();
        if (documento.isvalidoDocumentoIdentificacion(identificacionDocumento)) {
            System.out.println("Este Documento es valido " + identificacionDocumento);
            return true;
        } else {
            System.out.println("Este Documento no es valido!!!!! " + identificacionDocumento);
            Alertas.mensajeErrorPers("ERROR", "Documento Inválido!!!");
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPais.setText("España");
        llenarCmb.llenarComboBox(listaProvincias, cmbProvincia, SentenciasSQL.sqlProvincia);
        llenarCmb.llenarComboBox(listaDocumento, cmbDocumento, SentenciasSQL.sqlDocumento);
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtDocumento);
        listaDatos.add(txtNombre);
        listaDatos.add(txtApellido);
        listaDatos.add(txtComercio);
        listaDatos.add(txtTelefono);
        listaDatos.add(txtEmail);
        listaDatos.add(txtDireccion);
        listaDatos.add(txtProducto);

        return listaDatos;
    }

    private Boolean existeProveedor() {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarDocumentoProveedor);
            while (dato.next()) {
                if (dato.getString(1).equals(txtDocumento.getText())) {
                    Alertas.mensajeErrorPers("Proveedor", "El Proveedor ya existe.");
                    return false;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
