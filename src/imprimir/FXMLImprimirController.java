/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imprimir;

import conexionbasedatos.ConexionInventario;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;
import reportesistemainventario.CrearInforme;

/**
 * FXML Controller class
 *
 * @author bruno
 */
public class FXMLImprimirController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();

    @FXML
    private void factura() {
        String ruta = "/imprimir/FXMLFactura.fxml";
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
            String url = "src/ticket&factura/TicketVenta";
            CrearInforme ventaTicket = new CrearInforme();
            ventaTicket.ticketVenta(idFact, url);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLImprimirController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
