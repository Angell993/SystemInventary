package ventasControladorVistas;

import clasesjava.Item;
import conexionbasedatos.ConexionDB;
import conexionbasedatos.ConexionInventario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import metodosjavaClass.Alertas;
import metodosjavaClass.CalcularDocumentoIdentidadCIF;
import metodosjavaClass.Fecha;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import metodosjavaClass.VentanaRootPane;
import reportesistemainventario.CrearInforme;

public class FXMLPagoController implements Initializable {

    @FXML
    private ComboBox<Item> combPago;
    @FXML
    private TextField txtDocumento, codEmpleado, fechaPago, txtFactura;
    @FXML
    private TextField txtIva, txtTotal, txtImporte;
    private final LLenarCombos llenarComb = new LLenarCombos();
    private final ConexionDB con = new ConexionDB();
    private final Connection transaction = con.conectar();
    private ObservableList<Item> idCantidadCompra;
    private ObservableList<Item> listaPago;
    private double dinero;
    private String sentencia;
    private boolean confirmacion;

    @FXML
    private void cerrarVentana(ActionEvent event) {
        VentanaRootPane.closeVentana(event);
    }

    @FXML
    private void cancelarCompra(ActionEvent event) {
        try {
            transaction.setAutoCommit(false);
            ConexionInventario.EjecutarSQL_TRANSACT(transaction,SentenciasSQL.sqlEliminarDetalleFactura + " cod_factura = '" + txtFactura.getText() + "'");
            for (int i = 0; i < idCantidadCompra.size(); i++) {
                int stock = Integer.parseInt(idCantidadCompra.get(i).getDescripcion()) + MetodosJavaClass.obtenerId(SentenciasSQL.sqlConsultarStockArticulo+" "+idCantidadCompra.get(i).getId());
                sentencia = SentenciasSQL.sqlActualizarArticuloDb +" stock = "+ stock
                        +" where id_articulo = "+ idCantidadCompra.get(i).getId();
                if(!ConexionInventario.EjecutarSQL_TRANSACT(transaction, sentencia)){
                    Alertas.mensajeInformación("Error", "La actualizacion no se pudo realizar.");
                }
            }
            confirmacion = false;
            transaction.commit();
            transaction.close();
            cerrarVentana(event);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Recibir Informacion desde la ventana de registrar Venta*/
    public void recibirInformacionPago(String factura, String empleado, String neto, ObservableList<Item> idCantidadCompra) {
        this.idCantidadCompra = idCantidadCompra;
        txtFactura.setText(factura);
        codEmpleado.setText(empleado);
        llenarComb.llenarComboBox(listaPago, combPago, SentenciasSQL.sqlPago);
        txtTotal.setText(neto);
        dinero = Double.parseDouble(neto);
    }

    /*Cualquier otro metodo que no se inyecte al controlador*/
    private boolean documentoValido(String identificacionDocumento) {
        CalcularDocumentoIdentidadCIF documento = new CalcularDocumentoIdentidadCIF();
        if (documento.isvalidoDocumentoIdentificacion(identificacionDocumento)) {
            System.out.println("Este Documento es valido " + identificacionDocumento);
            // MetodosJavaClass.mensajeConfirmacion("Documento VÃ¡lido", "El documento ingresado es Correcto XD");
            return true;
        } else {
            System.out.println("Este Documento no es valido!!!!! " + identificacionDocumento);
            Alertas.mensajeErrorPers("ERROR", "Documento InvÃ¡lido!!!");
            return false;
        }
    }

    private void obtenerPrecioConIVA() {
        txtIva.textProperty().addListener((ov, oldValue, newValue) -> {
            try {
                if (Integer.parseInt(newValue) < 100) {
                    double iva = Double.parseDouble(newValue) / 100;
                    double money = new BigDecimal(dinero * iva).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                    double acobrar = money + dinero;
                    txtTotal.setText(String.valueOf(Math.rint(acobrar * 10) / 10));
                } else {
                    Alertas.mensajeErrorPers("¡IVA!", "La cantidad de IVA no puede ser del 100%");
                    txtIva.setText(String.valueOf(21));
                }
            } catch (NumberFormatException e) {
                Alertas.mensajeError(e.getLocalizedMessage());
            }
        }
        );
    }

    @FXML
    private void registrarFacturPago(ActionEvent event) {
        String url = "C:\\Users\\bruno\\JaspersoftWorkspace\\MyReports\\TicketVenta";
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (MetodosJavaClass.cmbSeleccionado(combPago)) {
                sentencia = SentenciasSQL.insertarFactura + "('" + txtFactura.getText() + "', '" + txtDocumento.getText()
                        + "', " + Integer.parseInt(codEmpleado.getText()) + " ,'" + Fecha.fechaSQl()
                        + "', " + combPago.getSelectionModel().getSelectedItem().getId()
                        + " , " + Double.parseDouble(txtTotal.getText()) + " , " + Double.parseDouble(txtIva.getText()) + " )";

                ConexionInventario.EjecutarSQL(sentencia);
                cambio();
                confirmacion = true;
                CrearInforme ventaTicket = new CrearInforme();
                ventaTicket.ticketVenta(txtFactura.getText(), url);
                cerrarVentana(event);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerPrecioConIVA();
        fechaPago.setText(Fecha.fecha());
    }

    private void cambio() {
        double devolver = Double.parseDouble(txtImporte.getText()) - Double.parseDouble(txtTotal.getText());
        Alertas.alertaPers("Cambio", "El cambio a recibir.", String.valueOf(Math.rint(devolver * 10) / 10));

    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtDocumento);
        listaDatos.add(txtIva);

        return listaDatos;
    }

    private Boolean existeCliente(String documento) {
        try {
            ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlDocumentoNombreCliente + " '" + documento + "'");
            while (dato.next()) {
                if (!dato.getString(1).equals(documento)) {
                    Alertas.mensajeErrorPers("Cliente", "El Cliente no esta registrado.");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean isConfirmacion() {
        return confirmacion;
    }
    
}
