package sistemaInventarioControladorVistas;

import articuloControladorVistas.FXMLModificarEliminarArticuloController;
import calculadoraControladorVista.FXMLCalculadoraController;
import clienteControladorVistas.FXMLModificarEliminarClienteController;
import clienteControladorVistas.FXMLRegistrarClienteController;
import conexionbasedatos.ConexionInventario;
import devolucioncontrollervista.FXMLDevolucionController;
import empleadoControladoresVista.FXMLModificarEliminarEmpleadoController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.FXMLIngresarController;
import metodosjavaClass.Fecha;
import metodosjavaClass.VentanaRootPane;
import opciones.FXMLOpcionesController;
import proveedorControladorVistas.FXMLModificarEliminarProveedorController;
import ventasControladorVistas.FXMLRegistrarVentaController;

public class FXMLSistemaInventarioController extends Thread implements Initializable {

    //Label mostrar el nombre del empleado en el label
    @FXML
    private Button btnConexion;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label identificador, fecha, hora;
    @FXML
    private Menu cliente, venta, articulo, proveedor, devolucion, reporte, empleado;
    @FXML
    private MenuItem modCliente, modArticulo, artiRegistro;
    //Mostrar por pantalla en anchorPane
    @FXML
    private AnchorPane rootPane, cabezeraRootPane;
    @FXML
    private AnchorPane root;
    private String empleados;
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    private FXMLLoader loader;
    private int numEmpleado;

    @FXML
    private void registrarCliente() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/clienteControladorVistas/FXMLRegistrarCliente.fxml"));
        root = loader.load();
        FXMLRegistrarClienteController empleadoCod = loader.getController();
        empleadoCod.codEmpleado(numEmpleado);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void modificarEliminarCliente() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/clienteControladorVistas/FXMLModificarEliminarCliente.fxml"));
        root = loader.load();
        FXMLModificarEliminarClienteController modClient = loader.getController();
        modClient.recibirInformacion(rootPane);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void registrarVentas() {
        try {
            loader = new FXMLLoader(getClass().getResource("/ventasControladorVistas/FXMLRegistrarVenta.fxml"));
            root = loader.load();
            FXMLRegistrarVentaController ventaEmpl = loader.getController();
            ventaEmpl.recibirCodEmpleado(String.valueOf(numEmpleado), rootPane);
            rootPane.getChildren().setAll(root);

        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registrarArticulo() {
        visualizarInterfaz.mostarVentana("/articuloControladorVistas/FXMLRegistrarArticulo.fxml", rootPane);
    }

    @FXML
    private void registrarTipoArticulo() {
        visualizarInterfaz.mostarVentana("/articuloControladorVistas/FXMLTipoArticulo.fxml", rootPane);
    }

    @FXML
    private void modificarEliminarArticulo() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/articuloControladorVistas/FXMLModificarEliminarArticulo.fxml"));
        root = loader.load();
        FXMLModificarEliminarArticuloController modArti = loader.getController();
        modArti.recibirInformacion(rootPane);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void registrarEmpleado() {
        visualizarInterfaz.mostarVentana("/empleadoControladoresVista/FXMLRegistrarEmpleado.fxml", rootPane);
    }

    @FXML
    private void modificarEliminarEmpleado() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/empleadoControladoresVista/FXMLModificarEliminarEmpleado.fxml"));
        root = loader.load();
        FXMLModificarEliminarEmpleadoController modEmple = loader.getController();
        modEmple.recibirInformacion(rootPane);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void registrarProveedor() {
        visualizarInterfaz.mostarVentana("/proveedorControladorVistas/FXMLRegistrarProveedor.fxml", rootPane);
    }

    @FXML
    private void modificarEliminarProveedor() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/proveedorControladorVistas/FXMLModificarEliminarProveedor.fxml"));
        root = loader.load();
        FXMLModificarEliminarProveedorController modProvee = loader.getController();
        modProvee.recibirInformacion(rootPane);
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void reporteSistema() {
        visualizarInterfaz.mostarVentana("/reportesistemainventario/FXMLReporteSistemaInventario.fxml", rootPane);
    }

    @FXML
    private void registrarDevolucion() {
        try {
            loader = new FXMLLoader(getClass().getResource("/devolucioncontrollervista/FXMLDevolucion.fxml"));
            root = loader.load();
            FXMLDevolucionController devolu = loader.getController();
            devolu.recuperarDatos(numEmpleado);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void calculadora(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("/calculadoraControladorVista/FXMLCalculadora.fxml"));
            root = loader.load();
            FXMLCalculadoraController calculadora = loader.getController();
            Scene scene_page = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Calculadora");
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
    private void opciones() {
        try {
            loader = new FXMLLoader(getClass().getResource("/opciones/FXMLOpciones.fxml"));
            root = loader.load();
            FXMLOpcionesController opcion = loader.getController();
            opcion.numeroEmpleado(numEmpleado);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void permisosSistemas(int codEmpleado) {
        if (codEmpleado == 2) {
            proveedor.setVisible(false);
            devolucion.setVisible(false);
            reporte.setVisible(false);
            empleado.setVisible(false);
            cliente.setVisible(false);
            artiRegistro.setVisible(false);            
            registrarVentas();
        }
        if (codEmpleado == 1) {
            registrarVentas();            
        }

    }

    public void recibirIdentificador(int user) {
        // esta variable se utiliza para enviar el cod Empleado a cualquier otra ventana que lo disponga
        numEmpleado = user;
        try {
            empleados = null;
            int codJefe = 0;
            String sql = "Select nombre, apellidos, Puesto from empleado where CodigoEmpleado = " + user;
            ResultSet datos = ConexionInventario.sSQL(sql);
            while (datos.next()) {
                empleados = (datos.getString("Nombre") + " " + datos.getString("Apellidos"));
                codJefe = datos.getInt("Puesto");
            }

            //Le mostramos por pantalla el empleado, la fecha y la hora
            identificador.setText(empleados);
            fecha.setText(Fecha.fecha());
            permisosSistemas(codJefe);
            if (user == 0) {
                btnConexion.getStyleClass().add("desconexion");
                cliente.setVisible(false);
                venta.setVisible(false);
                articulo.setVisible(false);
                proveedor.setVisible(false);
                empleado.setVisible(false);
                reporte.setVisible(false);
                devolucion.setVisible(false);
                conexionDB();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void conexionDB() {
        try {
            loader = new FXMLLoader(getClass().getResource("/login/FXMLIngresar.fxml"));
            root = loader.load();
            FXMLIngresarController login = loader.getController();
            login.desconectarConexionDB(true, numEmpleado);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
