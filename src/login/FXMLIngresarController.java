package login;

import conexionbasedatos.ConexionDB;
import conexionbasedatos.ConexionInventario;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
    private TextField usuario;
    @FXML
    private TextField password;
    @FXML
    private Button entrar, btnSalir;
    private ConexionDB conectar;
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

            recuperarUser(event);
        }

    }

    @FXML
    public void onEnter(ActionEvent ae) {
        entrar.fire();

    }

    private void recuperarUser(ActionEvent event) {
        try {
            conectar = new ConexionDB();
            try (Connection conect = conectar.conectar()) {
                conect.setAutoCommit(false);
                String sql = "select * from login";
                datos = ConexionInventario.sSQL(sql);

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
                conectar.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLIngresarController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            error.setText("Contraseña o Usuario incorrecto!");
        }
    }

    private void sistemaInventario(ActionEvent event) {
        try {
            System.out.println("[********** Bienvenido al Sistema **********]");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemaInventarioControladorVistas/FXMLSistemaInventario.fxml"));
            Parent root = loader.load();
            //Hacemos una instancia del controlador del Sistema de Inventario o el controlador que deseamos enviar el parametro.
            FXMLSistemaInventarioController system = loader.getController();
            system.recibirIdentificador(Integer.parseInt(usuario.getText()));

            Stage stage = new Stage();
            Scene scene_page = new Scene(root);
            stage.setTitle("INVENTARIO");
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
    }

    public void desconectarConexionDB(boolean desconectar, int empleado) {
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        lbl2.setVisible(false);
        lbl3.setVisible(false);
        lbl4.setVisible(false);
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        fontCandado.getStyleClass().add("candado");        
        fontUser.getStyleClass().add("userllave");
        fontLlave.getStyleClass().add("userllave");
        
        btnSalir.setVisible(desconectar);
        this.empleado = empleado;
    }

    private boolean desconectarDB() {
        try {
            conectar = new ConexionDB();
            try (Connection conect = conectar.conectar()) {
                conect.setAutoCommit(false);
                if (empleado == Integer.valueOf(usuario.getText())) {
                    String sql = "select * from login where id_empleado=  " + empleado;
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
