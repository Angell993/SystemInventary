package opciones;

import conexionbasedatos.ConexionInventario;
import configuracionadministrador.FXMLAdministradorConfigController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;
import clasesjava.CrearInforme;

public class FXMLOpcionesController implements Initializable {
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnConfig, btnTicket, btnFactura, btnCodeBar, btnCorreo;
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    
    @FXML
    private void factura() {
        String ruta = "/opciones/FXMLFactura.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }
    
    @FXML
    private void imprimirticket() {
        String idFact = null;
        ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlTicket);
        try {
            while (dato.next()) {
                idFact = dato.getString(1);
            }
            String url = "src/ticket&factura/Ticket";
            CrearInforme ventaTicket = new CrearInforme();
            ventaTicket.ticketVenta(idFact, url);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void correo() {
        String ruta = "/correo/FXMLCorreo.fxml";
        visualizarInterfaz.mostarVentana(ruta, rootPane);
    }
    
    @FXML
    private void configAdmin() {
        try {
            String ruta = "/configuracionadministrador/FXMLAdministradorConfig.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            AnchorPane root = loader.load();
            FXMLAdministradorConfigController config = loader.getController();
            config.distintasInicializacion(true);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLOpcionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void imprimirCodeBar() {
        visualizarInterfaz.mostarVentana("/opciones/FXMLGenerarCodeBar.fxml", rootPane);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void admin(int user) {
        if (user == 2) {
            btnConfig.setVisible(false);
            btnFactura.setVisible(false);
            btnCorreo.setVisible(false);
            btnCodeBar.setVisible(false);
        } else {
            btnConfig.setVisible(true);
            btnFactura.setVisible(true);
            btnCorreo.setVisible(true);
            btnCodeBar.setVisible(true);            
        }
        if (user == 0) {
            btnTicket.setVisible(false);
            btnConfig.setVisible(false);
            btnFactura.setVisible(false);
            btnCorreo.setVisible(false);
            btnCodeBar.setVisible(false);
        }
    }
    
    public void numeroEmpleado(int empleado) {
        admin(empleado);
    }
    
}
