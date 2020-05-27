package configuracionadministrador;

import conexionbasedatos.ConexionDB;
import correo.FXMLCorreoController;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import metodosjavaClass.Alertas;
import metodosjavaClass.MetodosJavaClass;
import static metodosjavaClass.VentanaRootPane.closeVentana;

public class FXMLAdministradorConfigController implements Initializable {

    @FXML
    private TextField txtUrl, txtUsuario, txtPass, txtDB, txtPuerto, txtCorreoEmpresa, txtCorreoPass;
    @FXML
    private Label lblUrl, lblUrl1, lblConexion, lblCorreo;
    @FXML
    private Button siguiente;
    private Boolean verifica;
    private ConexionDB conexion;
    private final Fichero fich = new Fichero();

    @FXML
    private void ingresarSistema(ActionEvent event) {
        if (lblConexion.getText().contains("OK") || lblCorreo.getText().contains("OK")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/FXMLIngresar.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene_page = new Scene(root);
                stage.setTitle("INVENTARIO");
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.show();
                closeVentana(event);
                mystage.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLAdministradorConfigController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void comprobarConexionDB() {
            String server = lblUrl.getText() + txtUrl.getText() + lblUrl1.getText();
            conexion = new ConexionDB(server, txtUsuario.getText(), txtPass.getText());
            if (conexion.conectar() == null) {
                lblConexion.setText("Conexión Fallida!!");
            } else {
                lblConexion.setText("Conexón OK");
                fich.escribirObjeto(server, txtUsuario.getText(), txtPass.getText());
                fich.leerObjetoDB();
            }
    }

    @FXML
    private void comprobarConexionCorreo() {
        enviarCorreoVerificacion();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUrl.setText("jdbc:mysql://");
        lblUrl1.setText("?verifyServerCertificate=false&useSSL=true");
        txtUsuario.setText("root");
        txtPass.setText("root");
        txtPuerto.setText("localhost:3306/");
        txtDB.setText("sistemainventario");
        txtUrl.setText(txtPuerto.getText() + txtDB.getText());
        completarURL();
    }

    private void completarURL() {
        txtDB.textProperty().addListener((ov, oldValue, newValue) -> {
            if (txtPuerto.getText().contains("localhost")) {
                txtUrl.setText(txtPuerto.getText() + txtDB.getText());
            }
            txtUrl.setText(txtPuerto.getText() + "/" + txtDB.getText());
        });
        txtPuerto.textProperty().addListener((ov, oldValue, newValue) -> {
            if (txtPuerto.getText().contains("localhost")) {
                txtUrl.setText(txtPuerto.getText());
            }
            txtUrl.setText(txtPuerto.getText() + "/");
        });
    }

    private void enviarCorreoVerificacion() {
        try {
            if (comprobarCampos()) {
                MimeMessage mail = new MimeMessage(conexionServidorCorreo());
                mail.setFrom(new InternetAddress(txtCorreoEmpresa.getText()));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(txtCorreoEmpresa.getText()));
                mail.setSubject("Biembenido al Sistema de Inventario");
                mail.setText("Verificación de  correo OK, Sistema de Inventario\n----------------------------------------\nConercial 4 Cantos S.L"
                        + "\nNo responder a este mensaje.", "ISO-8859-1");

                Transport transport = conexionServidorCorreo().getTransport("smtp");
                transport.connect(txtCorreoEmpresa.getText(), txtCorreoPass.getText());
                transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transport.close();

                lblCorreo.setText("Comprueba tu correo electónico OK");
                fich.escribirObjeto(txtCorreoEmpresa.getText(), txtCorreoPass.getText());
            }
        } catch (AddressException ex) {
            Logger.getLogger(FXMLCorreoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Alertas.mensajeInformación("Correo", "No has ingredo un correo electrónico válido o contraseña incorrecta.");
            //Logger.getLogger(FXMLCorreoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Session conexionServidorCorreo() {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        Session sesion = Session.getDefaultInstance(propiedad);
        return sesion;
    }

    private Boolean comprobarCampos() {
        if (!txtCorreoEmpresa.getText().isEmpty() && txtCorreoEmpresa.getText().contains("@gmail.")) {
            return true;
        }
        Alertas.mensajeInformación("Correo", "No has ingredo un correo electrónico válido.");
        return false;
    }

    public void distintasInicializacion(Boolean verifica) {
        this.verifica = verifica;
        if (verifica) {
            siguiente.setVisible(false);
            txtPass.setText(fich.leerObjetoDB().getPass());
        }
    }
}
