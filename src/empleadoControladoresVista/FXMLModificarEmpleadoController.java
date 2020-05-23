package empleadoControladoresVista;

import clasesjava.Empleado;
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
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;

public class FXMLModificarEmpleadoController implements Initializable {

    @FXML
    private TextField txtEmpleado, txtDocumento, txtNombre, txtApellido, txtEmail;
    @FXML
    private ComboBox<Item> cmbDocumento;
    private ObservableList<Item> listaDocumento;
    @FXML
    private ComboBox<Item> cmbPuesto;
    private ObservableList<Item> listaPuesto;
    private final LLenarCombos llenarComb = new LLenarCombos();
    private String sentencia;
    private int idEmpleado;
    private Empleado empleado = new Empleado();

    public void actualizarTabla() {
        empleado.setIdEmpleado(idEmpleado);
        empleado.setCodigoEmpleado(Integer.parseInt(txtEmpleado.getText()));
        empleado.setTipoDocumento(cmbDocumento.getSelectionModel().getSelectedItem().getId());
        empleado.setDocumentoEmpleado(txtDocumento.getText());
        empleado.setNombreEmpleado(txtNombre.getText());
        empleado.setApellidosEmpleado(txtApellido.getText());
        empleado.setEmailEmpleado(txtEmail.getText());
        empleado.setPuesto(cmbPuesto.getSelectionModel().getSelectedItem().getDescripcion());

    }

    @FXML
    private void modificarEmpleado(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && MetodosJavaClass.isNumero(txtEmpleado.getText())) {
            if (existeEmpleado()) {
                sentencia = SentenciasSQL.sqlModificarEmpleado + " codigoEmpleado = " + Integer.parseInt(txtEmpleado.getText())
                        + ", cod_tipodocumento = " + cmbDocumento.getSelectionModel().getSelectedItem().getId()
                        + ", DNI_NIE = '" + txtDocumento.getText() + "', Nombre = '" + txtNombre.getText()
                        + "', Apellidos = '" + txtApellido.getText() + "', Email = '" + txtEmail.getText()
                        + "', Puesto = " + cmbPuesto.getSelectionModel().getSelectedItem().getId()
                        + " WHERE codigoEmpleado = " + Integer.parseInt(txtEmpleado.getText());
                ConexionInventario.EjecutarSQL(sentencia);
                actualizarTabla();
                cerrarVentana(event);
            }
        }

    }

    @FXML
    private void eliminarEmpleado(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.isNumero(txtEmpleado.getText())) {
                if (existeEmpleado()) {
                    if (Alertas.eliminarConfirmacion()) {
                        sentencia = SentenciasSQL.sqlEliminarEmpleado + " codigoEmpleado = " + Integer.parseInt(txtEmpleado.getText());
                        ConexionInventario.EjecutarSQL(sentencia);
                        cerrarVentana(event);
                    }
                }
            }
        }

    }

    public void recibirDatos(Empleado empleado) {
        this.empleado = empleado;
        idEmpleado = empleado.getIdEmpleado();
        txtEmpleado.setText(String.valueOf(empleado.getCodigoEmpleado()));
        txtDocumento.setText(empleado.getDocumentoEmpleado());
        txtNombre.setText(empleado.getNombreEmpleado());
        txtApellido.setText(empleado.getApellidosEmpleado());
        txtEmail.setText(empleado.getEmailEmpleado());
        
        cmbDocumento.getSelectionModel().select(new Item(empleado.getTipoDocumento(), MetodosJavaClass.obtenerTipoDoc(empleado.getTipoDocumento())));
        cmbPuesto.getSelectionModel().select(new Item(MetodosJavaClass.obtenerId(SentenciasSQL.sqlIdPuesto + "'" + empleado.getPuesto()+ "'"), empleado.getPuesto()));

        llenarComb.llenarComboBox(listaPuesto, cmbPuesto, SentenciasSQL.sqlPuesto);
        llenarComb.llenarComboBox(listaDocumento, cmbDocumento, SentenciasSQL.sqlDocumento);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComb.llenarComboBox(listaPuesto, cmbPuesto, SentenciasSQL.sqlPuesto);
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtEmpleado);
        listaDatos.add(txtDocumento);
        listaDatos.add(txtNombre);
        listaDatos.add(txtApellido);
        listaDatos.add(txtEmail);

        return listaDatos;
    }

    private Boolean existeEmpleado() {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultaCodigoEmpleado);
            while (dato.next()) {
                if (dato.getInt(1) == Integer.parseInt(txtEmpleado.getText())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alertas.mensajeErrorPers("NO existe", "El Empleado que desea Modificar o"
                + " Eliminar no existe");
        return false;
    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        VentanaRootPane.closeVentana(event);
    }

    public Empleado getEmpleado() {
        return empleado;
    }

}
