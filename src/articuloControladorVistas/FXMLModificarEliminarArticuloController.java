package articuloControladorVistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import clasesjava.Articulo;
import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.Alertas;
import metodosjavaClass.SentenciasSQL;

public class FXMLModificarEliminarArticuloController implements Initializable {

    @FXML
    private TableView<Articulo> tblArticulo;
    @FXML
    private TableColumn<Articulo, String> clmNombre;
    @FXML
    private TableColumn<Articulo, Integer> clmCodigoBarras;
    @FXML
    private TableColumn<Articulo, Float> clmPrecioVenta;
    @FXML
    private TableColumn<Articulo, Float> clmPrecioCosto;
    @FXML
    private TableColumn<Articulo, Integer> clmStock;
    @FXML
    private TableColumn<Articulo, String> clmDescArticulo;
    @FXML
    private TableColumn<Articulo, String> clmNombProveedor;
    @FXML
    private TextField fieldDocumento;
    @FXML
    private Button btnBuscar;

    private ObservableList<Articulo> listaArticulos;
    private ObservableList<Articulo> listaSelectArticulo;
    private ObservableList<Item> listaStock;
    private Articulo articulo;
    private AnchorPane rootPane;

    private ObservableList<Articulo> llenarTabla(ObservableList<Articulo> articulosLista, String sWhere) {
        listaStock = FXCollections.observableArrayList();
        int stock, minimo;
        try {
            String sSelect = SentenciasSQL.sqlConsultaArticulotabla + " where producto.codigo_barras LIKE ('%" + sWhere + "%') "
                    + " or articulo.Nombre LIKE ('%" + sWhere + "%') "
                    + " order by articulo.id_articulo; ";
            ResultSet rSet = ConexionInventario.sSQL(sSelect);
            while (rSet.next()) { 
                articulosLista.add(new Articulo(rSet.getInt("articulo.id_articulo"), rSet.getString("articulo.Nombre"), rSet.getFloat("articulo.precio_venta"),
                        rSet.getFloat("articulo.precio_costo"), rSet.getInt("articulo.stock"), rSet.getInt("articulo.stock_minimo"), rSet.getInt("articulo.stock_maximo"),
                        rSet.getString("tipo_articulo.descripcion_articulo"), rSet.getString("proveedor.Nombre_comercial"), rSet.getString("articulo.fecha_ingreso"), rSet.getInt("producto.codigo_barras")));
                listaStock.add(new Item(rSet.getInt("articulo.stock"), String.valueOf(rSet.getInt("articulo.stock_minimo"))));

            }
            clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
            clmCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
            clmPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            clmPrecioCosto.setCellValueFactory(new PropertyValueFactory<>("precioCosto"));
            clmStock.setCellValueFactory(new PropertyValueFactory<>("cantidadStock"));
            clmDescArticulo.setCellValueFactory(new PropertyValueFactory<>("descripcionArticulo"));
            clmNombProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        } catch (SQLException e) {
            Alertas.errorSQL("Consulta errónea XDXD", e);
        }

        return articulosLista;
    }

    @FXML
    private void modificarArticulo(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                articulo = tblArticulo.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/articuloControladorVistas/FXMLModificarArticulo.fxml"));
                Parent root = loader.load();

                FXMLModificarArticuloController enviarDatos = loader.getController();
                enviarDatos.recibirDatos(articulo, rootPane);

                Scene scene_page = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.showAndWait();

                Articulo art = enviarDatos.getArticulo();
                if (art != null) {
                    tblArticulo.refresh();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaArticulos = FXCollections.observableArrayList();
        tblArticulo.setItems(llenarTabla(listaArticulos, ""));
    }

    @FXML
    public void buscarArtículo(KeyEvent event) {
        String sWhere = fieldDocumento.getText();
        if (fieldDocumento.getText() != null && !fieldDocumento.getText().contentEquals("")) {
            listaSelectArticulo = FXCollections.observableArrayList();
            tblArticulo.setItems(llenarTabla(listaSelectArticulo, sWhere));
            if (tblArticulo.getItems().isEmpty()) {
                Alertas.mensajeError("El artículo con el código de barras " + fieldDocumento.getText() + " no existe.\nPor favor, introduzca un código de barras válido");
                fieldDocumento.deleteText(sWhere.length() - 1, sWhere.length());
            }
        } else {
            tblArticulo.setItems(llenarTabla(listaArticulos, ""));
        }
    }

    public void recibirInformacion(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }
    
    public void informacionModificada(AnchorPane rootPane){
        this.rootPane = rootPane;
    }
}
