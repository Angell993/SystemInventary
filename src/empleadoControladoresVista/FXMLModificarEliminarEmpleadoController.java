package empleadoControladoresVista;

import clasesjava.Empleado;
import conexionbasedatos.ConexionInventario;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodosjavaClass.Alertas;
import metodosjavaClass.SentenciasSQL;

public class FXMLModificarEliminarEmpleadoController implements Initializable {

    @FXML
    private TableView<Empleado> tblEmpleado;
    @FXML
    private TableColumn<Empleado, Integer> clmNumEmpleado;
    @FXML
    private TableColumn<Empleado, String> clmDocumento;
    @FXML
    private TableColumn<Empleado, String> clmNombre;
    @FXML
    private TableColumn<Empleado, String> clmApellidos;
    @FXML
    private TableColumn<Empleado, String> clmEmail;
    @FXML
    private TableColumn<Empleado, String> clmPuesto;
    @FXML
    private TextField fieldDocumento;
    @FXML
    private Button btnBuscar;

    private ObservableList<Empleado> listaEmpleados;
    private ObservableList<Empleado> listaSelectEmpleado;

    private ObservableList<Empleado> llenarTablaEmpleados(ObservableList<Empleado> empleadoLista, String sWhere) {
        empleadoLista = FXCollections.observableArrayList();
        try {
            String sSentencia = SentenciasSQL.sqlConsultaEmpleadoTabla + "WHERE CodigoEmpleado LIKE ('%" + sWhere + "%') "
                    + "ORDER BY Id_empleado";
            ResultSet rSet = ConexionInventario.sSQL(sSentencia);
            while (rSet.next()) {
                empleadoLista.add(new Empleado(rSet.getInt("Id_empleado"), rSet.getInt("CodigoEmpleado"), rSet.getInt("cod_tipodocumento"),
                        rSet.getString("DNI_NIE"), rSet.getString("Nombre"),
                        rSet.getString("Apellidos"), rSet.getString("Email"), rSet.getString("puesto_empleado.Descripcion")));
            }
            clmNumEmpleado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
            clmDocumento.setCellValueFactory(new PropertyValueFactory<>("documentoEmpleado"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
            clmApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidosEmpleado"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<>("emailEmpleado"));
            clmPuesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));

        } catch (SQLException e) {
            Alertas.errorSQL("ERROR SQL", e);
        }
        return empleadoLista;
    }

    @FXML
    private void modificarEmpleado(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                Empleado empleado = tblEmpleado.getSelectionModel().getSelectedItem();
                System.out.println(empleado.toString());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/empleadoControladoresVista/FXMLModificarEmpleado.fxml"));
                Parent root = loader.load();
                System.out.println("Voy a cargar el controlador de mod and delete...........................");
                FXMLModificarEmpleadoController enviarDatos = loader.getController();
                enviarDatos.recibirDatos(empleado);

                Scene scene_page = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.showAndWait();
                
                Empleado emp = enviarDatos.getEmpleado();
                if (emp != null) {
                    tblEmpleado.refresh();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblEmpleado.setItems(llenarTablaEmpleados(listaEmpleados, ""));
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        btnBuscar.fire();
    }

    @FXML
    public void buscarEmpleado(ActionEvent event) {
            String sWhere = fieldDocumento.getText();
        if (fieldDocumento.getText() != null && !fieldDocumento.getText().contentEquals("")) {
            listaSelectEmpleado = FXCollections.observableArrayList();
            llenarTablaEmpleados(listaSelectEmpleado, sWhere);
            tblEmpleado.setItems(listaSelectEmpleado);
        } else if (fieldDocumento.getText() == null || fieldDocumento.getText().contentEquals("")) {
            Alertas.mensajeErrorPers("Búsqueda errónea", "Debe introducir un número de DNI o Nº Cliente para buscar");
        }
    }
}
