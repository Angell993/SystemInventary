package login;

import conexionbasedatos.ConexionDB;
import conexionbasedatos.ConexionInventario;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import metodosjavaClass.MetodosJavaClass;
import static metodosjavaClass.VentanaRootPane.closeVentana;
import sistemaInventarioControladorVistas.FXMLSistemaInventarioController;

/**
 *
 * @author bruno
 */
public class FXMLIngresarController implements Initializable {

    @FXML
    private Label error, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    @FXML
    private FontAwesomeIconView fontCandado, fontUser, fontLlave;
    @FXML
    private ImageView logo;
    @FXML
    private TextField usuario;
    @FXML
    private TextField password;
    @FXML
    private Button entrar, btnSalir;
    private ConexionDB conectar;
    private boolean desconectar = false;
    private ResultSet datos;
    private int empleado;

    @FXML
    private void ingresarSistema(ActionEvent event) {
        if (usuario.getText().isEmpty() && password.getText().isEmpty()) {
            error.setText("Contraseña o Usuario incorrecto!");
            usuario.getStyleClass().add("error-ingresar");
            password.getStyleClass().add("error-ingresar");
        } else if (usuario.getText().isEmpty()) {
            error.setText("Usuario incorrecto!");
            usuario.getStyleClass().add("error-ingresar");
        } else if (password.getText().isEmpty()) {
            error.setText("Contraseña incorrecto!");
            password.getStyleClass().add("error-ingresar");
        } else {

            ingresarSystem(event);
        }

    }

    @FXML
    public void onEnter(ActionEvent ae) {
        entrar.fire();
    }

    private void ingresarSystem(ActionEvent event) {
        try {
            conectar = new ConexionDB();
            String sql = "select * from login;";
            datos = ConexionInventario.sSQL(sql);
            try {
                while (datos.next()) {
                    int id = datos.getInt("id_Empleado");
                    String pass = datos.getString("password");
                    if (MetodosJavaClass.esNumero(usuario.getText())) {
                        int user = Integer.parseInt(usuario.getText());
                        if (id == user && password.getText().equals(pass)) {
                            sistemaInventario(event);
                            conectar.cerrarConexion();
                        } else {
                            usuario.getStyleClass().add("error-ingresar");
                            password.getStyleClass().add("error-ingresar");
                            error.setText("Contraseña o Usuario incorrecto!");
                        }
                    } else {
                        usuario.getStyleClass().add("error-ingresar");
                        password.getStyleClass().add("error-ingresar");
                        error.setText("Contraseña o Usuario incorrecto!");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLIngresarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            error.setText("Contraseña o Usuario incorrecto!");
        }
    }

    private void sistemaInventario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemaInventarioControladorVistas/FXMLSistemaInventario.fxml"));
            Parent root = loader.load();
            //Hacemos una instancia del controlador del Sistema de Inventario o el controlador que deseamos enviar el parametro.
            FXMLSistemaInventarioController system = loader.getController();
            system.recibirIdentificador(Integer.parseInt(usuario.getText()));

            Stage stage = new Stage();
            Scene scene_page = new Scene(root);
            stage.setTitle("INVENTARIO & VENTA");
            stage.getIcons().add(new Image(getClass().getResource("/imagenes/iconoInventario.png").toExternalForm()));
            Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene_page);
            stage.show();
            closeVentana(event);
            mystage.close();

        } catch (IOException ex) {
            Logger.getLogger(FXMLIngresarController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void desconectar(ActionEvent event) {
        if (desconectar) {
            if (usuario.getText().isEmpty() && password.getText().isEmpty()) {
                error.setText("Contraseña o Usuario incorrecto!");
                usuario.getStyleClass().add("error-ingresar");
                password.getStyleClass().add("error-ingresar");
            } else {
                if (desconectarDB()) {
                    usuario.setText("0");
                    sistemaInventario(event);
                }
            }
        }else{
            closeVentana(event);            
        }
    }

    public void desconectarConexionDB(boolean desconectar, int empleado) {
        this.desconectar = desconectar;
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        logo.setVisible(false);
        fontCandado.getStyleClass().add("candado");
        fontUser.getStyleClass().add("userllave");
        fontLlave.getStyleClass().add("userllave");

        btnSalir.setVisible(desconectar);
        this.empleado = empleado;
    }

    private boolean desconectarDB() {
        try {
            conectar = new ConexionDB();
            try {
                if (empleado == Integer.valueOf(usuario.getText())) {
                    String sql = "select * from login where id_empleado=  " + empleado+";";
                    datos = ConexionInventario.sSQL(sql);

                    while (datos.next()) {
                        int id = datos.getInt("id_Empleado");
                        String pass = datos.getString("password");
                        if (MetodosJavaClass.esNumero(usuario.getText())) {
                            int user = Integer.parseInt(usuario.getText());
                            if (id == user && password.getText().equals(pass)) {
                                conectar.cerrarConexion();
                                return true;
                            } else {
                                usuario.getStyleClass().add("error-ingresar");
                                password.getStyleClass().add("error-ingresar");
                                error.setText("Contraseña o Usuario incorrecto!");
                            }
                        } else {
                            usuario.getStyleClass().add("error-ingresar");
                            password.getStyleClass().add("error-ingresar");
                            error.setText("Contraseña o Usuario incorrecto!");
                        }
                    }
                    conectar.cerrarConexion();
                } else {
                    usuario.getStyleClass().add("error-ingresar");
                    password.getStyleClass().add("error-ingresar");
                    error.setText("Contraseña o Usuario incorrecto!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLIngresarController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            error.setText("Contraseña o Usuario incorrecto!");
        }
        return false;
    }
}
