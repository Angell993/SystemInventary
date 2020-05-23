package clienteControladorVistas;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import clasesjava.Cliente;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import metodosjavaClass.Alertas;
import metodosjavaClass.SentenciasSQL;

public class FXMLModificarEliminarClienteController implements Initializable {

    @FXML
    private TableColumn<Cliente, String> clmDni;
    @FXML
    private TableColumn<Cliente, String> clmNombre;
    @FXML
    private TableColumn<Cliente, String> clmApellidos;
    @FXML
    private TableColumn<Cliente, String> clmTelefono;
    @FXML
    private TableColumn<Cliente, String> clmEmail;
    @FXML
    private TableColumn<Cliente, String> clmPais;
    @FXML
    private TableColumn<Cliente, Number> clmProvincia;
    @FXML
    private TableColumn<Cliente, Number> clmCiudad;
    @FXML
    private TableColumn<Cliente, String> clmDireccion;
    @FXML
    private TableColumn<Cliente, Number> clmCodigoPostal;
    // Componentes GUI
    @FXML
    private TableView<Cliente> tblCliente;
    @FXML
    private TextField fieldDocumento;
    @FXML
    private Button btnBuscar;
    /* Fechas con Datepicker Radiobuttons con RadioButton */
    // Colecciones
    private ObservableList<Cliente> listaClientes;
    private ObservableList<Cliente> listaSelectClientes;
    private Cliente cliente;
    private AnchorPane rootPane;

    public ObservableList<Cliente> llenarTabla(ObservableList<Cliente> clienteLista, String sWhere) {
        clienteLista = FXCollections.observableArrayList();
        try {
            ResultSet rSet = ConexionInventario.sSQL(SentenciasSQL.sqlClienteTabla+" where Documento LIKE ('%" + sWhere + "%') order by Nombre ;");
            while (rSet.next()) {

                clienteLista.add(new Cliente(rSet.getInt("id_Cliente"),rSet.getString("Documento"), rSet.getInt("cod_tipo_documento"), rSet.getString("Nombre"),
                        rSet.getString("Apellidos"), rSet.getString("Telefono"),
                        rSet.getString("Pais"), rSet.getString("Ciudad"),
                        rSet.getString("Localidad"), rSet.getString("Direccion"),
                        rSet.getInt("CodigoPostal"), rSet.getString("email"),
                        rSet.getInt("Empleado")));

            }
            // Enlazar columnas con atributos
            clmDni.setCellValueFactory(new PropertyValueFactory<>("documento"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
            clmApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
            clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
            clmPais.setCellValueFactory(new PropertyValueFactory<>("paisCliente"));
            clmProvincia.setCellValueFactory(new PropertyValueFactory<>("provinciaCliente"));
            clmCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudadCliente"));
            clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
            clmCodigoPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostalCliente"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<>("emailCliente"));

        } catch (SQLException e) {
            Alertas.errorSQL("Consulta errónea", e);
        }
        return clienteLista;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblCliente.setItems(llenarTabla(listaClientes, ""));
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        btnBuscar.fire();
    }

    @FXML
    public void buscarCliente(ActionEvent event) {
            String sWhere = fieldDocumento.getText();
        if (fieldDocumento.getText() != null && !fieldDocumento.getText().contentEquals("")) {
            listaSelectClientes = FXCollections.observableArrayList();
            tblCliente.setItems(llenarTabla(listaSelectClientes, sWhere));
        } else if (fieldDocumento.getText() == null || fieldDocumento.getText().contentEquals("")) {
            Alertas.mensajeErrorPers("Búsqueda errónea", "Debe introducir un número de DNI o Nº Cliente para buscar");
        }
    }

    @FXML
    private void modificarCliente(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                cliente = tblCliente.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/clienteControladorVistas/FXMLModificarCliente.fxml"));
                Parent root = loader.load();
                FXMLModificarClienteController enviarDatos = loader.getController();
                enviarDatos.datosCliente(listaClientes, cliente, rootPane);

                Scene scene_page = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.showAndWait();

                Cliente aux = enviarDatos.getCliente();
                if (aux != null) {
                    this.tblCliente.refresh();
                }else{
                   this.tblCliente.refresh(); 
                }
            } catch (IOException e) {
                Alertas.mensajeError(e.getLocalizedMessage());
            }
        }

    }

    public void recibirInformacion(AnchorPane rootPane){
        this.rootPane = rootPane;
    }

}
