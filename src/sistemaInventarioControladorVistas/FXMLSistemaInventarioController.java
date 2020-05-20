package sistemaInventarioControladorVistas;

import calculadoraControladorVista.FXMLCalculadoraController;
import clienteControladorVistas.FXMLRegistrarClienteController;
import conexionbasedatos.ConexionInventario;
import devolucioncontrollervista.FXMLDevolucionController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodosjavaClass.Fecha;
import metodosjavaClass.VentanaRootPane;
import ventasControladorVistas.FXMLRegistrarVentaController;

public class FXMLSistemaInventarioController extends Thread implements Initializable {

    //Label mostrar el nombre del empleado en el label
    @FXML
    MenuBar menuBar;
    @FXML
    private Label identificador, fecha, hora;
    @FXML
    private Menu cliente, venta, articulo, proveedor, devolucion, reporte, empleado;
    @FXML
    private MenuItem modCliente, modArticulo;
    //Mostrar por pantalla en anchorPane
    @FXML
    private AnchorPane rootPane, cabezeraRootPane;
    @FXML
    private AnchorPane root;
    @FXML
    private String ruta;
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    private FXMLLoader loader;
    private int numEmpleado;

    @FXML
    private void registrarCliente(ActionEvent event) throws IOException {
        ruta = "/clienteControladorVistas/FXMLRegistrarCliente.fxml";
        loader = new FXMLLoader(getClass().getResource(ruta));
        root = loader.load();
        FXMLRegistrarClienteController empleadoCod = loader.getController();
        empleadoCod.codEmpleado(numEmpleado);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void modificarEliminarCliente(ActionEvent event) {
        ruta = "/clienteControladorVistas/FXMLModificarEliminarCliente.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void registrarVentas(ActionEvent event) {
        try {
            ruta = "/ventasControladorVistas/FXMLRegistrarVenta.fxml";
            loader = new FXMLLoader(getClass().getResource(ruta));
            root = loader.load();
            FXMLRegistrarVentaController ventaEmpl = loader.getController();
            ventaEmpl.recibirCodEmpleado(String.valueOf(numEmpleado));
            rootPane.getChildren().setAll(root);

        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registrarArticulo(ActionEvent event) {
        ruta = "/articuloControladorVistas/FXMLRegistrarArticulo.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void registrarTipoArticulo(ActionEvent event) {
        ruta = "/articuloControladorVistas/FXMLTipoArticulo.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void modificarEliminarArticulo(ActionEvent event) {
        ruta = "/articuloControladorVistas/FXMLModificarEliminarArticulo.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void registrarEmpleado(ActionEvent event) {
        ruta = "/empleadoControladoresVista/FXMLRegistrarEmpleado.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void modificarEliminarEmpleado(ActionEvent event) {
        ruta = "/empleadoControladoresVista/FXMLModificarEliminarEmpleado.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void registrarProveedor(ActionEvent event) {
        ruta = "/proveedorControladorVistas/FXMLRegistrarProveedor.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void modificarEliminarProveedor(ActionEvent event) {
        ruta = "/proveedorControladorVistas/FXMLModificarEliminarProveedor.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void reporteSistema(ActionEvent event) {
        ruta = "/reportesistemainventario/FXMLReporteSistemaInventario.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    @FXML
    private void registrarDevolucion(ActionEvent event) {
        try {
            ruta = "/devolucioncontrollervista/FXMLDevolucion.fxml";
            loader = new FXMLLoader(getClass().getResource(ruta));
            root = loader.load();
            FXMLDevolucionController devolucion = loader.getController();
            devolucion.recuperarDatos(numEmpleado);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void calculadora(ActionEvent event) {
        System.out.println("------------ Inicializando calculadora ------------");
        ruta = "/calculadoraControladorVista/FXMLCalculadora.fxml";
        try {
            loader = new FXMLLoader(getClass().getResource(ruta));
            root = loader.load();
            FXMLCalculadoraController calculadora = loader.getController();
            Scene scene_page = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Calculadora");
            //Con el Modality.APPLICATION_MODAL nos permite que hasta que no terminemos con la ventana que se abre
            //no dejara que utilize la otra ventana.
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene_page);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(VentanaRootPane.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void imprimir() {
        ruta = "/imprimir/FXMLImprimir.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }

    public void permisosSistemas(int codEmpleado) {

        if (codEmpleado == 2) {
            proveedor.setVisible(false);
            devolucion.setVisible(false);
            reporte.setVisible(false);
            empleado.setVisible(false);
        }

    }

    public void recibirIdentificador(int user) {
        // esta variable se utiliza para enviar el cod Empleado a cualquier otra ventana que lo disponga
        numEmpleado = user;
        try {
            String empleado = null;
            int codJefe = 0;
            String sql = "Select nombre, apellidos, Puesto from empleado where CodigoEmpleado = " + user;
            ResultSet datos = ConexionInventario.sSQL(sql);
            while (datos.next()) {
                empleado = (datos.getString("Nombre") + " " + datos.getString("Apellidos"));
                codJefe = datos.getInt("Puesto");
            }

            //Le mostramos por pantalla el empleado, la fecha y la hora
            identificador.setText(empleado);
            fecha.setText(Fecha.fecha());
            permisosSistemas(codJefe);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
