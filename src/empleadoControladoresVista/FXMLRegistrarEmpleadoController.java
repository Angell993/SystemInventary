package empleadoControladoresVista;

import clasesjava.Item;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodosjavaClass.Alertas;
import metodosjavaClass.CalcularDocumentoIdentidadCIF;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import sistemaInventarioControladorVistas.FXMLSistemaInventarioController;

public class FXMLRegistrarEmpleadoController implements Initializable {

    @FXML
    TextField txtEmpleado, txtDocumento, txtNombre;
    @FXML
    TextField txtApellido, txtEmail;
    @FXML
    private ComboBox<Item> cmbPuesto;
    @FXML
    private ComboBox<Item> cmbDocumento;
    private ObservableList<Item> listaPuesto;
    private ObservableList<Item> listaDocumento;

    private final MetodosJavaClass metodosJavaclass = new MetodosJavaClass();
    private final LLenarCombos llenarComb = new LLenarCombos();

    @FXML
    private void registrarEmpleado(ActionEvent event) {
        if (MetodosJavaClass.txtVacios(datosArray()) && MetodosJavaClass.isNumero(txtEmpleado.getText())) {
            if (MetodosJavaClass.cmbSeleccionado(cmbPuesto) && MetodosJavaClass.cmbSeleccionado(cmbDocumento)) {
                String sentencia = SentenciasSQL.ingresarEmpleado + " (" + Integer.parseInt(txtEmpleado.getText()) + ", " + cmbDocumento.getSelectionModel().getSelectedItem().getId()
                        + " , '" + txtDocumento.getText() + "', '" + txtNombre.getText() + "', '" + txtApellido.getText() + "', '" + txtEmail.getText()
                        + "', " + cmbPuesto.getSelectionModel().getSelectedItem().getId() + ")";
                ConexionInventario.EjecutarSQL(sentencia);
                cancelar(event);
                registrarContrasenia(event);
                txtEmpleado.setText(String.valueOf(metodosJavaclass.identificadorEmpleado()));

            }
        }

    }

    @FXML
    private void cancelar(ActionEvent event) {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
        cmbDocumento.getSelectionModel().select(-1);
        cmbPuesto.getSelectionModel().select(-1);
    }

    private void registrarContrasenia(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/empleadoControladoresVista/FXMLRegistroContraseña.fxml"));
        try {
            Parent root = loader.load();
            FXMLRegistroContraseñaController empleado = loader.getController();
            empleado.recogerCodEmpleado(txtEmpleado.getText());

            Scene scene_page = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Metodo Pago");
            //Con el Modality.APPLICATION_MODAL nos permite que hasta que no terminemos con la ventana que se abre
            //no dejara que utilize la otra ventana.                    
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene_page);
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
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
        txtEmpleado.setText(String.valueOf(metodosJavaclass.identificadorEmpleado()));
        while (!existeEmpleado(Integer.parseInt(txtEmpleado.getText()))) {
            txtEmpleado.setText(String.valueOf(metodosJavaclass.identificadorEmpleado()));
        }
        llenarComb.llenarComboBox(listaDocumento, cmbDocumento, SentenciasSQL.sqlDocumento);
        llenarComb.llenarComboBox(listaPuesto, cmbPuesto, SentenciasSQL.sqlPuesto);
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtDocumento);
        listaDatos.add(txtNombre);
        listaDatos.add(txtApellido);
        listaDatos.add(txtEmail);

        return listaDatos;
    }

    private Boolean existeEmpleado(int numEmple) {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultaCodigoEmpleado);
            while (dato.next()) {
                if (dato.getInt(1) == numEmple) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLModificarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
