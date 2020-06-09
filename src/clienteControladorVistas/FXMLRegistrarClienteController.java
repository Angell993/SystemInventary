package clienteControladorVistas;

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

public class FXMLRegistrarClienteController implements Initializable {

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
    private TextField txtEmail, txtEmpleado;
    @FXML
    private ComboBox<Item> cmbDocumento;
    @FXML
    private ComboBox<Item> cmbProvincia;
    @FXML
    private ComboBox<Item> cmbMunicipio;

    private final LLenarCombos llenarCmb = new LLenarCombos();
    private ObservableList<Item> listaProvincias;
    private ObservableList<Item> listaCiudades;
    private ObservableList<Item> listaDocumento;

    @FXML
    private void seleccionarID(ActionEvent event) {
        if (!cmbProvincia.getSelectionModel().isSelected(-1)) {
            int idProvincia = cmbProvincia.getSelectionModel().getSelectedItem().getId();
            llenarCmb.llenarComboBox(listaCiudades, cmbMunicipio, SentenciasSQL.sqlMunicipios + idProvincia);
        }
    }

    @FXML
    private void registrarCliente(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && MetodosJavaClass.isDouble(txtCodigoPostal.getText()) && MetodosJavaClass.isDouble(txtEmpleado.getText())) {
            if (MetodosJavaClass.cmbSeleccionado(cmbDocumento) && MetodosJavaClass.cmbSeleccionado(cmbProvincia) && MetodosJavaClass.cmbSeleccionado(cmbMunicipio)) {
                if (MetodosJavaClass.verificarEmail(txtEmail)) {
                    if (!cmbDocumento.getSelectionModel().getSelectedItem().getDescripcion().equals("CIF")) {
                        ///if (documentoValido(txtDni.getText())) {
                        if (existeCliente()) {
                            String sInsert = SentenciasSQL.ingresarCliente + "('" + txtDni.getText()
                                    + "', " + cmbDocumento.getSelectionModel().getSelectedItem().getId() + " , '" + txtNombre.getText() + "', '" + txtApellido.getText() + "', '"
                                    + txtTelefono.getText() + "','" + txtEmail.getText() + "', '" + txtPais.getText() + "', '"
                                    + cmbProvincia.getSelectionModel().getSelectedItem().getDescripcion() + "', '"
                                    + cmbMunicipio.getSelectionModel().getSelectedItem().getDescripcion() + "', '"
                                    + txtDireccion.getText() + "' , " + Integer.valueOf(txtCodigoPostal.getText()) + " , "
                                    + Integer.parseInt(txtEmpleado.getText()) + ");";
                            ConexionInventario.EjecutarSQL(sInsert);
                            cleanFields(event);
                        }
                        // }
                    }else{
                        Alertas.mensajeInformación("Tipo de Documento", "El Tipo de Documento no es válido.\n\tNo puede ser CIF.");
                    }
                }
            }
        }
    }

    /*Le pedimos el numero de empleado al iniciar el sistema*/
    public void codEmpleado(int numeroEmple) {
        txtEmpleado.setText(String.valueOf(numeroEmple));
    }

    /*Cualquier otro metodo que no se inyecte al controlador*/
    private boolean documentoValido(String identificacionDocumento) {
        CalcularDocumentoIdentidadCIF documento = new CalcularDocumentoIdentidadCIF();
        if (documento.isvalidoDocumentoIdentificacion(identificacionDocumento)) {
            return true;
        } else {
            Alertas.mensajeError( "Documento Inválido!!!");
            return false;
        }
    }

    @FXML
    private void cleanFields(ActionEvent event) {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
        txtPais.setText("España");
        cmbDocumento.getSelectionModel().select(-1);
        cmbProvincia.getSelectionModel().select(-1);
        cmbMunicipio.getSelectionModel().select(-1);

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
        listaDatos.add(txtDni);
        listaDatos.add(txtNombre);
        listaDatos.add(txtApellido);
        listaDatos.add(txtTelefono);
        listaDatos.add(txtEmail);
        listaDatos.add(txtDireccion);
        listaDatos.add(txtCodigoPostal);

        return listaDatos;
    }

    private Boolean existeCliente() {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarDocumentoCliente);
            while (dato.next()) {
                if (dato.getString(1).equals(txtDni.getText())) {
                    Alertas.mensajeError("El Cliente, ya está registrado.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
