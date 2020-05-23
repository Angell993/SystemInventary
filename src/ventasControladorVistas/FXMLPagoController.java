package ventasControladorVistas;

import clasesjava.Item;
import clasesjava.Venta;
import conexionbasedatos.ConexionDB;
import conexionbasedatos.ConexionInventario;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import metodosjavaClass.Alertas;
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
    @FXML
    private Label lblSinIva, lblIva, lblTotal, lblImporte, lblAdevolver, lblTotalIva, lblCambio;
    private final LLenarCombos llenarComb = new LLenarCombos();
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    private AnchorPane rootPane;
    private final ConexionDB con = new ConexionDB();
    private final Connection transaction = con.conectar();
    private ObservableList<Item> idCantidadCompra;
    private ObservableList<Item> listaPago;
    private ObservableList<Venta> listaVenta = FXCollections.observableArrayList();
    private Venta venta = new Venta();
    private double dinero;
    private String sentencia;
    private double money;

    @FXML
    private void regitrarMas(ActionEvent event) {
        try {
            transaction.setAutoCommit(false);
            ConexionInventario.EjecutarSQL_TRANSACT(transaction, SentenciasSQL.sqlEliminarDetalleFactura + " cod_factura = '" + txtFactura.getText() + "'");
            for (int i = 0; i < idCantidadCompra.size(); i++) {
                int stock = Integer.parseInt(idCantidadCompra.get(i).getDescripcion()) + MetodosJavaClass.obtenerId(SentenciasSQL.sqlConsultarStockArticulo + " " + idCantidadCompra.get(i).getId());
                sentencia = SentenciasSQL.sqlActualizarArticuloDb + " stock = " + stock
                        + " where id_articulo = " + idCantidadCompra.get(i).getId();
                if (!ConexionInventario.EjecutarSQL_TRANSACT(transaction, sentencia)) {
                    Alertas.mensajeInformación("Error", "La actualizacion no se pudo realizar.");
                }
            }
            transaction.commit();
            transaction.close();
            regresarAVenta();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void anularTransaccion(ActionEvent event) {
        try {
            transaction.setAutoCommit(false);
            ConexionInventario.EjecutarSQL_TRANSACT(transaction, SentenciasSQL.sqlEliminarDetalleFactura + " cod_factura = '" + txtFactura.getText() + "'");
            for (int i = 0; i < idCantidadCompra.size(); i++) {
                int stock = Integer.parseInt(idCantidadCompra.get(i).getDescripcion()) + MetodosJavaClass.obtenerId(SentenciasSQL.sqlConsultarStockArticulo + " " + idCantidadCompra.get(i).getId());
                sentencia = SentenciasSQL.sqlActualizarArticuloDb + " stock = " + stock
                        + " where id_articulo = " + idCantidadCompra.get(i).getId();
                if (!ConexionInventario.EjecutarSQL_TRANSACT(transaction, sentencia)) {
                    Alertas.mensajeInformación("Error", "La actualizacion no se pudo realizar.");
                }
            }
            transaction.commit();
            transaction.close();
            cargarNuevaVentana();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Recibir Informacion desde la ventana de registrar Venta*/
    public void recibirInformacionPago(String factura, String empleado, String neto, ObservableList<Item> idCantidadCompra, AnchorPane rootPane, ObservableList<Venta> listaVenta) {
        this.listaVenta = listaVenta;
        this.rootPane = rootPane;
        System.out.println("Acabo de recibir los datos: " + Arrays.toString(listaVenta.toArray()));
        this.idCantidadCompra = idCantidadCompra;
        txtFactura.setText(factura);
        codEmpleado.setText(empleado);
        llenarComb.llenarComboBox(listaPago, combPago, SentenciasSQL.sqlPago);
        txtTotal.setText(neto);
        dinero = Double.parseDouble(neto);
        lblSinIva.setText(neto);
    }

    private void obtenerPrecioConIVA() {
        txtIva.textProperty().addListener((ov, oldValue, newValue) -> {
            try {
                if (Integer.parseInt(newValue) < 100) {
                    double iva = Double.parseDouble(newValue) / 100;
                    money = new BigDecimal(dinero * iva).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                    double acobrar = money + dinero;
                    txtTotal.setText(String.valueOf(Math.rint(acobrar * 10) / 10));
                    lblTotal.setText(txtTotal.getText());
                    lblTotalIva.setText(String.valueOf(money));
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
        if (cambio() >= 0) {
            String url = "C:\\Users\\bruno\\JaspersoftWorkspace\\MyReports\\TicketVenta";
            if (MetodosJavaClass.txtVacios(datosArray())) {
                if (MetodosJavaClass.cmbSeleccionado(combPago)) {
                    sentencia = SentenciasSQL.insertarFactura + "('" + txtFactura.getText() + "', '" + txtDocumento.getText()
                            + "', " + Integer.parseInt(codEmpleado.getText()) + " ,'" + Fecha.fechaSQl()
                            + "', " + combPago.getSelectionModel().getSelectedItem().getId()
                            + " , " + Double.parseDouble(txtTotal.getText()) + " , " + Double.parseDouble(txtIva.getText()) + " )";

                    ConexionInventario.EjecutarSQL(sentencia);
                    Alertas.alertaPers("Cambio", "El cambio a recibir.", String.valueOf(cambio()));

                    CrearInforme ventaTicket = new CrearInforme();
                    ventaTicket.ticketVenta(txtFactura.getText(), url);
                    cargarNuevaVentana();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerPrecioConIVA();
        fechaPago.setText(Fecha.fecha());
        lblDinero();
    }

    private double cambio() {
        double devolver = 0;
        try {
            if (Double.parseDouble(txtImporte.getText()) < Double.parseDouble(txtTotal.getText())) {
                lblCambio.setText("Falta");
                lblCambio.getStyleClass().add("cambio");
                devolver = Double.parseDouble(txtTotal.getText()) - Double.parseDouble(txtImporte.getText());
            } else {
                lblCambio.setText("Cambio");
                lblCambio.getStyleClass().add("dinero-completo");
                devolver = Double.parseDouble(txtImporte.getText()) - Double.parseDouble(txtTotal.getText());
            }
        } catch (NumberFormatException e) {
        }
        return devolver;
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

    private void lblDinero() {
        txtImporte.textProperty().addListener((ov, oldValue, newValue) -> {
            lblImporte.setText(txtImporte.getText());
            lblAdevolver.setText(String.valueOf(cambio()));
        });
        txtIva.textProperty().addListener((ov, oldValue, newValue) -> {
            lblIva.setText(txtIva.getText());
        });
    }

    private void cargarNuevaVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventasControladorVistas/FXMLRegistrarVenta.fxml"));
            AnchorPane root = loader.load();
            FXMLRegistrarVentaController registrar = loader.getController();
            registrar.recuperarEmpleado(codEmpleado.getText());
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void regresarAVenta() {
        try {
            System.out.println("Estoy enviando datos: " + Arrays.toString(this.listaVenta.toArray()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventasControladorVistas/FXMLRegistrarVenta.fxml"));
            AnchorPane root = loader.load();
            FXMLRegistrarVentaController registrar = loader.getController();
            registrar.recuperarRegistros(this.listaVenta, txtFactura.getText(), codEmpleado.getText(), rootPane);
            rootPane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
