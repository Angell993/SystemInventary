package proveedorControladorVistas;

import clasesjava.Item;
import clasesjava.Proveedor;
import conexionbasedatos.ConexionInventario;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.Alertas;
import clasesjava.CalcularDocumentoIdentidadCIF;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;

public class FXMLModificarProveedorController implements Initializable {

    @FXML
    TextField txtDocumento, txtNombre, txtApellido;
    @FXML
    TextField txtComercio, txtTelefono, txtEmail, txtPais;
    @FXML
    TextField txtDireccion;
    @FXML
    private ComboBox<Item> cmbProvincia;
    @FXML
    private ComboBox<Item> cmbMunicipio;
    @FXML
    private ComboBox<Item> cmbDocumento;
    @FXML
    private ComboBox<Item> cmbArticulo;

    private ObservableList<Item> listaProvincias;
    private ObservableList<Item> listaCiudades;
    private ObservableList<Item> listaDocumento;
    private ObservableList<Item> listaArticulo;
    private final LLenarCombos llenarCmb = new LLenarCombos();
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    private AnchorPane rootPane;
    private String sentencia;
    private Proveedor proveedor;
    private int id;

    @FXML
    private void seleccionarID() {
        if (!cmbProvincia.getSelectionModel().isSelected(-1)) {
            int idProvincia = cmbProvincia.getSelectionModel().getSelectedItem().getId();
            llenarCmb.llenarComboBox(listaCiudades, cmbMunicipio, SentenciasSQL.sqlMunicipios + idProvincia);
        }
    }

    private void actualizartabla() {
        proveedor.setIdProveedor(id);
        proveedor.setCifProveedor(txtDocumento.getText());
        proveedor.setCod_tipo_doc(cmbDocumento.getSelectionModel().getSelectedItem().getId());
        proveedor.setNombreProveedor(txtNombre.getText());
        proveedor.setApellidosProveedor(txtApellido.getText());
        proveedor.setNombreComecial(txtComercio.getText());
        proveedor.setTelefonoProveedor(txtTelefono.getText());
        proveedor.setEmailProveedor(txtEmail.getText());
        proveedor.setPaisProveedor(txtPais.getText());
        proveedor.setProvinciaProveedor(cmbProvincia.getSelectionModel().getSelectedItem().getDescripcion());
        proveedor.setCiudadProveedor(cmbMunicipio.getSelectionModel().getSelectedItem().getDescripcion());
        proveedor.setDireccionProveedor(txtDireccion.getText());
        proveedor.setProductosProveedor(cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion());
    }

    @FXML
    private void modificarProveedor(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.verificarEmail(txtEmail)) {
                if (cmbDocumento.getSelectionModel().getSelectedItem().getDescripcion().equals("CIF")) {
                    if (existeProveedor()) {
                        if (documentoValido(txtDocumento.getText())) {
                            sentencia = SentenciasSQL.sqlModificarProveedor + "No_documento = '" + txtDocumento.getText() + "', cod_tipo_documento = " + cmbDocumento.getSelectionModel().getSelectedItem().getId()
                                    + ", Nombre = '" + txtNombre.getText() + "', Apellido = '" + txtApellido.getText() + "', Nombre_comercial = '" + txtComercio.getText() + "',"
                                    + " Telefono = '" + txtTelefono.getText() + "', email = '" + txtEmail.getText() + "', Pais = '" + txtPais.getText()
                                    + "', Ciudad = '" + cmbProvincia.getSelectionModel().getSelectedItem().getDescripcion()
                                    + "', Localidad = '" + cmbMunicipio.getSelectionModel().getSelectedItem().getDescripcion()
                                    + "', direccion = '" + txtDireccion.getText()
                                    + "', Productos = " + cmbArticulo.getSelectionModel().getSelectedItem().getId()
                                    + " where id_proveedor = " + id;
                            ConexionInventario.EjecutarSQL(sentencia);
                            actualizartabla();
                            cerrarVentana(event);
                        }
                    }
                } else {
                    Alertas.mensajeInformación("Tipo de Documento", "El Tipo de Documento no es válido.\n\tDebe ser CIF");
                }
            }
        }

    }

    @FXML
    private void eliminarProveedor(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (existeProveedor()) {
                if (Alertas.ConfirmacionEleminarOModificar()) {
                    sentencia = SentenciasSQL.sqlEliminarProveedor + " id_proveedor = " + id;
                    ConexionInventario.EjecutarSQL(sentencia);
                    recargarVentana();
                    cerrarVentana(event);
                }
            }
        }
    }

    public void datosProveedor(Proveedor proveedor, AnchorPane rootPane) {
        this.rootPane = rootPane;
        this.proveedor = proveedor;
        id = proveedor.getIdProveedor();

        txtDocumento.setText(proveedor.getCifProveedor());
        txtNombre.setText(proveedor.getNombreProveedor());
        txtApellido.setText(proveedor.getApellidosProveedor());
        txtComercio.setText(proveedor.getNombreComecial());
        txtTelefono.setText(proveedor.getTelefonoProveedor());
        txtEmail.setText(proveedor.getEmailProveedor());
        txtPais.setText(proveedor.getPaisProveedor());
        txtDireccion.setText(proveedor.getDireccionProveedor());

        cmbDocumento.getSelectionModel().select(new Item(proveedor.getCod_tipo_doc(), MetodosJavaClass.obtenerTipoDoc(proveedor.getCod_tipo_doc())));
        cmbProvincia.getSelectionModel().select(new Item(proveedor.getProvinciaProveedor()));
        cmbMunicipio.getSelectionModel().select(new Item(proveedor.getCiudadProveedor()));
        cmbArticulo.getSelectionModel().select(new Item(MetodosJavaClass.obtenerId(SentenciasSQL.sqlCodigoBarrasID + "'' or descripcion ='" + proveedor.getProductosProveedor() + "'"),
                proveedor.getProductosProveedor()));

        llenarCmb.llenarComboBox(listaArticulo, cmbArticulo, SentenciasSQL.sqlProductoProveedor);
        llenarCmb.llenarComboBox(listaDocumento, cmbDocumento, SentenciasSQL.sqlDocumento);
        llenarCmb.llenarComboBox(listaProvincias, cmbProvincia, SentenciasSQL.sqlProvincia);
        seleccionarID();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private boolean documentoValido(String identificacionDocumento) {
        CalcularDocumentoIdentidadCIF documento = new CalcularDocumentoIdentidadCIF();
        if (documento.isvalidoDocumentoIdentificacion(identificacionDocumento)) {
            return true;
        } else {
            Alertas.mensajeError("Documento Inválido!!!");
            return false;
        }
    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        VentanaRootPane.closeVentana(event);
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

        return listaDatos;
    }

    private Boolean existeProveedor() {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarDocumentoProveedor);
            while (dato.next()) {
                if (!dato.getString(1).equals(txtDocumento.getText())) {
                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alertas.mensajeError("El Proveedor que deseas Modificar"
                + "o Eliminar no existe.");
        return false;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    private void recargarVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proveedorControladorVistas/FXMLModificarEliminarProveedor.fxml"));
            AnchorPane root = loader.load();
            FXMLModificarEliminarProveedorController info = loader.getController();
            info.informacionModificada(rootPane);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLModificarProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
