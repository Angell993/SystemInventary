package correo;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import metodosjavaClass.Alertas;

public class FXMLCorreoController implements Initializable {

    @FXML
    private TextField txtDestinatario, txtAsunto, txtPropietario, txtRuta;
    @FXML
    private TextArea txtACuerpo;
    private File f = null;
    private final String correoEnvia = "funakyangel@gmail.com";
    private final String pieDeMensaje = "\n-------------------------------------------------------------------- \nConercial 4 Cantos S.L";
    private final String aviso = "\nNo responda a este mensaje.";

    @FXML
    private void enviarCorreo() {
        //Agregar Correo desde el cual puedes enviar a los clientes o empleados.
        try {
            if (comprobarCampos()) {

                String contrasenia = "saladino";
                
                MimeMultipart multipart = new MimeMultipart();
                
                BodyPart texto = new MimeBodyPart();
                texto.setText(txtACuerpo.getText());
                
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(new DataHandler(new FileDataSource(f)));
                if (txtRuta.getText().contains(".png")) {
                    adjunto.setFileName("Imagen.png");
                multipart.addBodyPart(adjunto);
                }
                if (txtRuta.getText().contains(".jpg")) {                    
                adjunto.setFileName("Imagen.jpg");
                multipart.addBodyPart(adjunto);
                }
                if (txtRuta.getText().contains(".pdf")) {       
                adjunto.setFileName("Factura.pdf");
                multipart.addBodyPart(adjunto);
                }
                
                multipart.addBodyPart(texto);
                //multipart.addBodyPart(adjunto);

                MimeMessage mail = new MimeMessage(conexionServidorCorreo());
                mail.setFrom(new InternetAddress(correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(txtDestinatario.getText()));
                mail.setSubject(txtAsunto.getText());
                mail.setContent(multipart,"ISO-8859-1");
                //mail.setText(txtACuerpo.getText(),"ISO-8859-1");

                Transport transport = conexionServidorCorreo().getTransport("smtp");
                transport.connect(correoEnvia, contrasenia);
                transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transport.close();

                Alertas.mensajeInformación("Correo", "El correo fue enviado");
                txtDestinatario.setText(" ");
                txtAsunto.setText(" ");
                txtRuta.setText(" ");
                txtACuerpo.setText(" " + pieDeMensaje + aviso);
            }
        } catch (AddressException ex) {
            Logger.getLogger(FXMLCorreoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(FXMLCorreoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void adjuntar(){
        JFileChooser dlg = new JFileChooser();
        int option = dlg.showSaveDialog(dlg);
        if (option == JFileChooser.APPROVE_OPTION) {
            f = dlg.getSelectedFile();
            txtRuta.setText(f.toString());
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
        if (!txtDestinatario.getText().isEmpty() && txtDestinatario.getText().contains("@")) {
            return true;
        }
            Alertas.mensajeInformación("Correo", "No has ingredo un correo electrónico válido.");
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtPropietario.setText(correoEnvia);
        txtACuerpo.setText(pieDeMensaje + aviso);
    }

}
