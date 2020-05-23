package proveedorControladorVistas;

import clasesjava.Proveedor;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodosjavaClass.Alertas;
import metodosjavaClass.SentenciasSQL;

public class FXMLModificarEliminarProveedorController implements Initializable {

    @FXML
    private TableView<Proveedor> tblProveedor;

    @FXML
    private TableColumn<Proveedor, String> clmDocumento;
    @FXML
    private TableColumn<Proveedor, String> clmNombre;
    @FXML
    private TableColumn<Proveedor, String> clmApellidos;
    @FXML
    private TableColumn<Proveedor, String> clmNombComercial;
    @FXML
    private TableColumn<Proveedor, String> clmTelefono;
    @FXML
    private TableColumn<Proveedor, String> clmEmail;
    @FXML
    private TableColumn<Proveedor, String> clmPais;
    @FXML
    private TableColumn<Proveedor, String> clmDireccion;
    @FXML
    private TableColumn<Proveedor, String> clmProducto;
    @FXML
    private TextField fieldDocumento;
    @FXML
    private Button btnBuscar;
    
    private ObservableList<Proveedor> listaProveedores;
    private ObservableList<Proveedor> listaSeleccionProveedores;
    private Proveedor proveedor ;
    private AnchorPane rootPane;

    private ObservableList<Proveedor> llenarTablaProveedores(ObservableList<Proveedor> provLsita, String sWhere) {
        provLsita = FXCollections.observableArrayList();
        try {
            String sSentencia = SentenciasSQL.sqlConsultaProveedorTabla+ " WHERE No_documento LIKE ('%" + sWhere + "%') ORDER BY Nombre ";
            ResultSet rSet = ConexionInventario.sSQL(sSentencia);
            while (rSet.next()) {
                provLsita.add(new Proveedor(rSet.getInt(1),rSet.getString(2), rSet.getInt(3), rSet.getString(4), rSet.getString(5),
                        rSet.getString(6), rSet.getString(7), rSet.getString(8), rSet.getString(9), rSet.getString(10),
                        rSet.getString(11), rSet.getString(12), rSet.getString(13)));
            }
            clmDocumento.setCellValueFactory(new PropertyValueFactory<>("cifProveedor"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
            clmApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidosProveedor"));
            clmNombComercial.setCellValueFactory(new PropertyValueFactory<>("nombreComecial"));
            clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoProveedor"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<>("emailProveedor"));
            clmPais.setCellValueFactory(new PropertyValueFactory<>("paisProveedor"));
            clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionProveedor"));
            clmProducto.setCellValueFactory(new PropertyValueFactory<>("productosProveedor"));

        } catch (SQLException e) {
            Alertas.errorSQL("ERROR SQL", e);
        }
        return provLsita;
    }

    @FXML
    private void modificarProveedor(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                proveedor = tblProveedor.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/proveedorControladorVistas/FXMLModificarProveedor.fxml"));
                Parent root = loader.load();
                FXMLModificarProveedorController enviarDatos = loader.getController();
                enviarDatos.datosProveedor(proveedor, rootPane);

                Scene scene_page = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.showAndWait();
                
                Proveedor prov = enviarDatos.getProveedor();
                if (prov != null) {
                    tblProveedor.refresh();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblProveedor.setItems(llenarTablaProveedores(listaProveedores, ""));
    }

     @FXML
    public void buscarProveedor(KeyEvent event) {
        String sWhere = fieldDocumento.getText();
        if (fieldDocumento.getText() != null && !fieldDocumento.getText().contentEquals("")) {
            listaSeleccionProveedores = FXCollections.observableArrayList();
            tblProveedor.setItems(llenarTablaProveedores(listaSeleccionProveedores, sWhere));
            if (tblProveedor.getItems().isEmpty()) {
                Alertas.mensajeErrorPers("Consulta errónea", "El proveedor con el nº de documento " + fieldDocumento.getText() + " no existe.\nPor favor, introduzca un documento válido");
                fieldDocumento.deleteText(sWhere.length() - 1, sWhere.length());
            }
        }else  
            tblProveedor.setItems(llenarTablaProveedores(listaProveedores, ""));
    }
    
    public void recibirInformacion(AnchorPane rootPane){
        this.rootPane = rootPane;
    }
}
