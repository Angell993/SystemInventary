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

    @FXML  ///, txtTotal
    private ComboBox<Item> combPago;
    @FXML
    private TextField txtDocumento, codEmpleado, fechaPago, txtFactura;
    @FXML
    private TextField txtIva, txtImporte;
    @FXML
    private Label lblSinIva, lblIva, lblTotal, lblImporte, lblAdevolver, lblTotalIva, lblCambio, lblBase, lblTotalPorcent;
    private final LLenarCombos llenarComb = new LLenarCombos();
    private final VentanaRootPane visualizarInterfaz = new VentanaRootPane();
    private AnchorPane rootPane;
    private final ConexionDB con = new ConexionDB();
    private final Connection transaction = con.conectar();
    private ObservableList<Item> idCantidadCompra;
    private ObservableList<Item> listaPago;
    private ObservableList<Venta> listaVenta = FXCollections.observableArrayList();
    private ObservableList<Item> listaIva;
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
    public void recibirInformacionPago(String factura, String empleado, String total, ObservableList<Item> idCantidadCompra,
            AnchorPane rootPane, ObservableList<Venta> listaVenta) {
        this.listaVenta = listaVenta;
        this.rootPane = rootPane;
        this.idCantidadCompra = idCantidadCompra;
        txtFactura.setText(factura);
        codEmpleado.setText(empleado);
        llenarComb.llenarComboBox(listaPago, combPago, SentenciasSQL.sqlPago);
        //txtTotal.setText(MetodosJavaClass.quitarDecimal(Double.parseDouble(total)));
        dinero = Double.parseDouble(total);
        lblTotal.setText(MetodosJavaClass.quitarDecimal(Double.parseDouble(total)));
        ivaProcentaje();
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
                            + " , " + MetodosJavaClass.quitarComa(lblTotal.getText()) + " )";

                    ConexionInventario.EjecutarSQL(sentencia);
                    Alertas.alertaPers("Cambio", "El cambio a recibir.", String.valueOf(cambio()));

                    /*CrearInforme ventaTicket = new CrearInforme();
                    ventaTicket.ticketVenta(txtFactura.getText(), url);*/
                    cargarNuevaVentana();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaPago.setText(Fecha.fecha());
        lblDinero();
    }

    private double cambio() {
        double devolver = 0;
        try {
            if (Double.parseDouble(txtImporte.getText()) < MetodosJavaClass.quitarComa(lblTotal.getText())) {
                lblCambio.setText("Falta");
                lblCambio.getStyleClass().add("cambio");
                devolver = MetodosJavaClass.quitarComa(lblTotal.getText()) - Double.parseDouble(txtImporte.getText());
            } else {
                lblCambio.setText("Cambio");
                lblCambio.getStyleClass().add("dinero-completo");
                devolver = Double.parseDouble(txtImporte.getText()) - MetodosJavaClass.quitarComa(lblTotal.getText());
            }
        } catch (NumberFormatException e) {
        }
        return devolver;
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        listaDatos.add(txtDocumento);

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

    private void ivaProcentaje() {
        listaIva = FXCollections.observableArrayList();
        double sinIva = 0;
        for (int i = 0; i < idCantidadCompra.size(); i++) {
            ResultSet datos = ConexionInventario.sSQL(SentenciasSQL.sqlIvaPorcentaje + idCantidadCompra.get(i).getId());
            try {
                while (datos.next()) {
                    String porcentajeIva = String.valueOf((Math.rint(datos.getDouble(2) * 100) / 100) * (Double.parseDouble(idCantidadCompra.get(i).getDescripcion())));
                    String precioSinIva = String.valueOf((Math.rint(datos.getDouble(3) * 100) / 100) * (Double.parseDouble(idCantidadCompra.get(i).getDescripcion())));
                    listaIva.add(new Item(datos.getInt(1), porcentajeIva ,precioSinIva));
                    sinIva += (Math.rint(datos.getDouble(3) * 100) / 100) * (Double.parseDouble(idCantidadCompra.get(i).getDescripcion()));
                    System.out.println("IVA: "+datos.getInt(1));
                    System.out.println("Porcentaje: "+porcentajeIva);
                    System.out.println("PrecioTotal: "+precioSinIva);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Sin iva: " + Math.rint(sinIva * 100) / 100);
        lblSinIva.setText(String.valueOf(sinIva));
        desgloseIva(listaIva);
    }

    private void desgloseIva(ObservableList<Item> listaIva) {
        StringBuilder iva = new StringBuilder();
        StringBuilder porcentaje = new StringBuilder();
        StringBuilder precio = new StringBuilder();        
        int iva4 = 0;
        int iva10 = 0;
        int iva21 = 0;
        double porcentaje4 = 0;
        double porcentaje10 = 0;
        double porcentaje21 = 0;
        double precio4 = 0;
        double precio10 = 0;
        double precio21 = 0;
        int count1 = 1, count2 = 1, count3 = 1;
        for (int i = 0; i < listaIva.size(); i++) {
            if (listaIva.get(i).getId() == 4) {
                if (count1 > 0) {
                    iva4 = listaIva.get(i).getId();
                    count1--;
                }
                porcentaje4 += Double.parseDouble(listaIva.get(i).getDescripcion());
                precio4 += Double.parseDouble(listaIva.get(i).getDocProveedor());
            }
            if (listaIva.get(i).getId() == 10) {
                if (count2 > 0) {
                    iva10 = listaIva.get(i).getId();
                    count2--;
                }
                porcentaje10 += Double.parseDouble(listaIva.get(i).getDescripcion());
                precio10 += Double.parseDouble(listaIva.get(i).getDocProveedor());
            }
            if (listaIva.get(i).getId() == 21) {
                if (count3 > 0) {
                    iva21 = listaIva.get(i).getId();
                    count3--;
                }
                porcentaje21 += Double.parseDouble(listaIva.get(i).getDescripcion());
                precio21 += Double.parseDouble(listaIva.get(i).getDocProveedor());
            }
        }
        iva.append(iva4+"%\n"+iva10+"%\n"+iva21+"%\n");
        porcentaje.append(porcentaje4 +"€\n"+ porcentaje10 +"€\n"+ porcentaje21+"€\n");
        precio.append(precio4+"€\n" + precio10+"€\n" + precio21+"€\n");
        lblIva.setText(iva.toString());
        lblBase.setText(porcentaje.toString());
        lblTotalIva.setText(precio.toString());
        lblTotalPorcent.setText(String.valueOf(porcentaje4+porcentaje10+porcentaje21));
    }
}
