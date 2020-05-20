package clienteControladorVistas;

import clasesjava.Cliente;
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
import metodosjavaClass.VentanaRootPane;

public class FXMLModificarClienteController implements Initializable {

    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtPais;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private TextField txtEmail, numEmpleado;

    @FXML
    ComboBox<Item> cmbProv;
    @FXML
    ComboBox<Item> cmbMun;
    @FXML
    ComboBox<Item> cmbDocumento;

    private ObservableList<Item> listaProvincia;
    private ObservableList<Item> listaMunicipio;
    private ObservableList<Cliente> listaCliente;
    private Cliente cliente = new Cliente();
    private String tipodocumento;
    private int id;
    private final LLenarCombos llenarCmb = new LLenarCombos();

    @FXML
    public void seleccion(ActionEvent event) {
        int idProvincia = cmbProv.getSelectionModel().getSelectedItem().getId();
        llenarCmb.llenarComboBox(listaMunicipio, cmbMun, SentenciasSQL.sqlMunicipios + idProvincia);
    }

    private void actualizarTablaModificada() {
        cliente.setIdCliente(id);
        cliente.setDocumento(txtDni.getText());
        cliente.setIdTipoDoc(cmbDocumento.getSelectionModel().getSelectedItem().getId());
        cliente.setNombreCliente(txtNombre.getText());
        cliente.setApellidoCliente(txtApellido.getText());
        cliente.setTelefonoCliente(txtTelefono.getText());
        cliente.setPaisCliente(txtPais.getText());
        cliente.setProvinciaCliente(cmbProv.getSelectionModel().getSelectedItem().getDescripcion());
        cliente.setCiudadCliente(cmbMun.getSelectionModel().getSelectedItem().getDescripcion());
        cliente.setDireccionCliente(txtDireccion.getText());
        cliente.setCodigoPostalCliente(Integer.valueOf(txtCodigoPostal.getText()));
        cliente.setEmailCliente(txtEmail.getText());
        cliente.setNumeroEmpleado(Integer.valueOf(numEmpleado.getText()));
    }

    @FXML
    private void modificarRegistro(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && MetodosJavaClass.isNumero(txtCodigoPostal.getText()) && MetodosJavaClass.isNumero(numEmpleado.getText())) {
            //if (documentoValido(txtDni.getText())) {
            String modificar = SentenciasSQL.sqlModificarCliente + " Documento ='" + txtDni.getText() + "' ,"
                    + " cod_tipo_documento = " + cmbDocumento.getSelectionModel().getSelectedItem().getId() + " ,"
                    + " Nombre = '" + txtNombre.getText() + "' ,"
                    + " Apellidos = '" + txtApellido.getText() + "',"
                    + " Telefono = '" + txtTelefono.getText() + "' ,"
                    + " email = '" + txtEmail.getText() + "' ,"
                    + " Pais = '" + txtPais.getText() + "' ,"
                    + " Ciudad  = '" + cmbProv.getSelectionModel().getSelectedItem().getDescripcion() + "' ,"
                    + " Localidad = '" + cmbMun.getSelectionModel().getSelectedItem().getDescripcion() + "' ,"
                    + " Direccion = '" + txtDireccion.getText() + "' ,"
                    + " CodigoPostal = " + Integer.valueOf(txtCodigoPostal.getText()) + " ,"
                    + " Empleado = " + Integer.valueOf(numEmpleado.getText()) + " WHERE id_Cliente = " + id;
            ConexionInventario.EjecutarSQL(modificar);
            actualizarTablaModificada();
            cerrarVentana(event);
            //}            
        }
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && MetodosJavaClass.isNumero(txtCodigoPostal.getText()) && MetodosJavaClass.isNumero(numEmpleado.getText())) {
            //if (documentoValido(txtDni.getText())) {
            if (Alertas.eliminarConfirmacion()) {
                String eliminar = SentenciasSQL.sqlEliminarCliente + " id_Cliente = " + id;
                ConexionInventario.EjecutarSQL(eliminar);
                cerrarVentana(event);
            }
            //}
        }
    }

    public void datosCliente(ObservableList<Cliente> listaCliente, Cliente cliente) {
        this.listaCliente = listaCliente;
        this.cliente = cliente;
        id = cliente.getIdCliente();

        txtDni.setText(cliente.getDocumento());
        txtNombre.setText(cliente.getNombreCliente());
        txtApellido.setText(cliente.getApellidoCliente());
        txtTelefono.setText(cliente.getTelefonoCliente());
        txtPais.setText(cliente.getPaisCliente());
        txtDireccion.setText(cliente.getDireccionCliente());
        txtCodigoPostal.setText(String.valueOf(cliente.getCodigoPostalCliente()));
        txtEmail.setText(cliente.getEmailCliente());
        numEmpleado.setText(String.valueOf(cliente.getNumeroEmpleado()));

        cmbProv.getSelectionModel().select(new Item(cliente.getProvinciaCliente()));
        cmbMun.getSelectionModel().select(new Item(cliente.getCiudadCliente()));
        cmbDocumento.getSelectionModel().select(new Item(cliente.getIdTipoDoc(), MetodosJavaClass.obtenerTipoDoc(cliente.getIdTipoDoc())));

        llenarCmb.llenarComboBox(listaProvincia, cmbProv, SentenciasSQL.sqlProvincia);
        llenarCmb.llenarComboBox(listaProvincia, cmbDocumento, SentenciasSQL.sqlDocumento);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        VentanaRootPane.closeVentana(event);
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtDni);
        listaDatos.add(txtNombre);
        listaDatos.add(txtApellido);
        listaDatos.add(txtTelefono);
        listaDatos.add(txtEmail);
        listaDatos.add(txtDireccion);
        listaDatos.add(txtCodigoPostal);

        return listaDatos;
    }

    /*Cualquier otro metodo que no se inyecte al controlador*/
    private boolean documentoValido(String identificacionDocumento) {
        CalcularDocumentoIdentidadCIF documento = new CalcularDocumentoIdentidadCIF();
        if (documento.isvalidoDocumentoIdentificacion(identificacionDocumento)) {
            return true;
        } else {
            Alertas.mensajeErrorPers("ERROR", "Documento Inválido!!!");
            return false;
        }
    }

    private Boolean existeCliente(String documento) {
        try {
            ResultSet datos = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarDocumentoCliente);
            while (datos.next()) {
                if (!datos.getString(1).equals(documento)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alertas.mensajeErrorPers("Cliente ", "El cliente no existe");
        return false;
    }

    public Cliente getCliente() {
        return cliente;
    }

}
