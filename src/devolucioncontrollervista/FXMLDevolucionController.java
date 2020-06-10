package devolucioncontrollervista;

import clasesjava.Item;
import clasesjava.Venta;
import conexionbasedatos.ConexionInventario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import metodosjavaClass.Alertas;
import metodosjavaClass.Fecha;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;
import reportesistemainventario.CrearInforme;

public class FXMLDevolucionController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private Button btnEliminar, btnAniadir;
    @FXML
    private TextField txtFactura, txtArticulo, txtMotivo, txtCantidad, txtTotal;
    @FXML
    private Label lblCant, lblCod, lblFact, lblMotivo;
    @FXML
    private TableView<Venta> tblVenta;
    @FXML
    private TableColumn<Venta, Integer> clmNumVenta;
    @FXML
    private TableColumn<Venta, String> clmArticulo;
    @FXML
    private TableColumn<Venta, Integer> clmCantidad;
    @FXML
    private TableColumn<Venta, Double> clmPrecio;
    @FXML
    private TableColumn<Venta, Double> clmTotal;
    @FXML
    private ComboBox<Item> cmbDevolucion;
    private ResultSet codFactura;
    private Venta venta;
    private ObservableList<Venta> listaArticulo;
    private ObservableList<Item> opciones;
    private ObservableList<Item> cantidadCodeBar;
    private ObservableList<Item> listaMotivo;
    private ObservableList<Double> listaTotalCompra;
    private ObservableList<Venta> listaVenta;
    private int empleado, n_Compra = 1;
    private double total;

    private void imprimirTicket() {
        CrearInforme ventaTicket = new CrearInforme();
        ventaTicket.ticketVenta(txtFactura.getText(), "src/ticket&factura/TicketDevolucion");
    }

    @FXML
    private void registrarDevolucion() {
        //Con este if compruebo en la base de datos si ya esta registrado la devolucion
        if (MetodosJavaClass.txtVacios(datosArray())) {
            if (existeDevolucionFactura()) {
                if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 1) {
                    for (int i = 0; i < listaVenta.size(); i++) {
                        String sentencia = SentenciasSQL.ingresarDevolucion + "('" + listaVenta.get(i).getNumeroCoFactura()
                                + "', " + MetodosJavaClass.obtenerId("SELECT id_articulo from articulo where nombre= '" + listaVenta.get(i).getNombreArticulo() + "'")
                                + ", '" + txtMotivo.getText() + "', '" + Fecha.fechaSQl()
                                + "', " + listaVenta.get(i).getCantidadCompra()
                                + " , " + listaVenta.get(i).getTotalCompra()
                                + ", " + empleado + " )";
                        ConexionInventario.EjecutarSQL(sentencia);
                        Alertas.mensajeInformación("cambio", "A devolver es: \n"
                                + sumarDineroTotal());
                        imprimirTicket();
                        listaVenta.clear();
                        tblVenta.setItems(listaVenta);
                    }
                } else {
                    for (int i = 0; i < listaArticulo.size(); i++) {
                        String sentencia = SentenciasSQL.ingresarDevolucion + "('" + listaArticulo.get(i).getNumeroCoFactura()
                                + "', " + MetodosJavaClass.obtenerId("SELECT id_articulo from articulo where nombre= '" + listaArticulo.get(i).getNombreArticulo() + "'")
                                + ", '" + opciones.get(i).getDescripcion() + "', '" + Fecha.fechaSQl()
                                + "', " + listaArticulo.get(i).getCantidadCompra()
                                + " , " + listaArticulo.get(i).getTotalCompra()
                                + ", " + empleado + " )";
                        ConexionInventario.EjecutarSQL(sentencia);
                        Alertas.mensajeInformación("cambio", "A devolver es: \n"
                                + sumarDineroTotal());
                        imprimirTicket();
                        listaArticulo.clear();
                        tblVenta.setItems(listaArticulo);
                    }
                }
                borrar();
            }
        }
    }

    @FXML
    private void añadirArticulo() {
        if (MetodosJavaClass.txtVacios(datosArray())) {
            añadirRegistroTbl();
        }
    }

    @FXML
    private void borrar() {
        for (int i = 0; i < datosArray().size(); i++) {
            datosArray().get(i).setText(null);
        }
        txtTotal.setText(null);
    }

    @FXML
    private void seleccionDevolucion() {
        if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 1) {
            gridPane.setVisible(true);
            lblFact.setVisible(true);
            txtFactura.setVisible(true);
            lblCant.setVisible(false);
            lblCod.setVisible(false);
            lblMotivo.setVisible(true);
            txtArticulo.setVisible(false);
            txtCantidad.setVisible(false);
            txtMotivo.setVisible(true);
            btnAniadir.setVisible(false);
            btnEliminar.setVisible(false);
            listaArticulo.clear();
            txtTotal.setText(null);
            tblVenta.setItems(listaArticulo);
            nFactura();
        }
        if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 2) {
            txtFactura.setText(null);
            gridPane.setVisible(true);
            lblFact.setVisible(true);
            txtFactura.setVisible(true);
            lblCant.setVisible(true);
            lblCod.setVisible(true);
            lblMotivo.setVisible(true);
            txtArticulo.setVisible(true);
            txtCantidad.setVisible(true);
            txtMotivo.setVisible(true);
            btnAniadir.setVisible(true);
            btnEliminar.setVisible(true);
            txtTotal.setText(null);
            listaArticulo.clear();
            tblVenta.setItems(listaArticulo);
        }
    }

    @FXML
    private void eliminar() {
        if (tblVenta.getSelectionModel().getSelectedIndex() <= -1) {
            Alertas.mensajeInformación("Devolución", "Añada una Artículo.");
        } else {
            try {
                Venta vent = tblVenta.getSelectionModel().getSelectedItem();
                listaArticulo.remove(vent);
                listaTotalCompra.remove(tblVenta.getSelectionModel().getSelectedIndex());
                sumarDineroTotal();
                n_Compra = 1;
                for (int i = 0; i < listaArticulo.size(); i++) {
                    listaArticulo.get(i).setNumeroCompra(n_Compra);
                    n_Compra++;
                }
                //n_Compra -= 1;
                tblVenta.refresh();
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                txtTotal.setText(null);
                listaTotalCompra.clear();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaArticulo = FXCollections.observableArrayList();
        listaTotalCompra = FXCollections.observableArrayList();
        cmbDevolucion.setItems(rellenarCombo());
        visualizatrCampos();
    }

    private void visualizatrCampos() {
        lblFact.setVisible(false);
        txtFactura.setVisible(false);
        lblCant.setVisible(false);
        lblCod.setVisible(false);
        lblMotivo.setVisible(false);
        txtArticulo.setVisible(false);
        txtCantidad.setVisible(false);
        txtMotivo.setVisible(false);
        btnAniadir.setVisible(false);
        btnEliminar.setVisible(false);
    }

    public void recuperarDatos(int codEmpleado) {
        empleado = codEmpleado;
    }

    private void nFactura() {
        if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 1) {
            txtFactura.textProperty().addListener((ov, oldValue, newValue) -> {
                if (MetodosJavaClass.esNumero(txtFactura.getText())) {
                    tblVenta.setItems(facturaCompleta(txtFactura.getText()));
                    n_Compra = 1;
                }
            });
        }
    }

    private ObservableList<Venta> facturaCompleta(String factura) {
        listaVenta = FXCollections.observableArrayList();
        codFactura = ConexionInventario.sSQL(SentenciasSQL.sqlFacturaImprimir + " '" + factura + "';");
        try {
            while (codFactura.next()) {
                listaVenta.add(new Venta(txtFactura.getText(), n_Compra, codFactura.getString(1), codFactura.getInt(2), codFactura.getDouble(3), codFactura.getDouble(4)));
                n_Compra++;
                txtTotal.setText(String.valueOf(codFactura.getDouble(5)));
            }
            añadirCeldasTbl();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaVenta;
    }

    private void añadirCeldasTbl() {
        clmNumVenta.setCellValueFactory(new PropertyValueFactory<>("numeroCompra"));
        clmArticulo.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
        clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precioArticulo"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));
    }

    private void añadirRegistroTbl() {
        listaMotivo = FXCollections.observableArrayList();
        venta = new Venta();
        if (existeCodeBar(txtArticulo.getText())) {
            if (MetodosJavaClass.esNumero(txtCantidad.getText())) {
                ObservableList<Venta> articulo = articuloTabla(txtArticulo.getText(), Integer.parseInt(txtCantidad.getText()), txtFactura.getText());
                for (int i = 0; i < articulo.size(); i++) {
                    total = MetodosJavaClass.quitarComa(String.valueOf((Math.rint(articulo.get(i).getTotalCompra() * 100) / 100)));
                    venta.setNumeroFactura(articulo.get(i).getNumeroCoFactura());
                    venta.setNumeroCompra(articulo.get(i).getNumeroCompra());
                    venta.setNombreArticulo(articulo.get(i).getNombreArticulo());
                    venta.setCantidadCompra(articulo.get(i).getCantidadCompra());
                    venta.setPrecioArticulo(articulo.get(i).getPrecioArticulo());
                    venta.setTotalCompra(total);
                    listaArticulo.add(venta);

                    listaTotalCompra.add(total);
                    listaMotivo.add(new Item(i + 1, txtMotivo.getText()));
                }

                System.out.println(Arrays.toString(cantidadCodeBar.toArray()));
                añadirCeldasTbl();
                tblVenta.setItems(listaArticulo);
                sumarDineroTotal();
            } else {
                Alertas.mensajeError("La cantidad debe ser un número.");
            }
        }
    }

    private ObservableList<Venta> articuloTabla(String codeBar, int cantidad, String factura) {
        listaVenta = FXCollections.observableArrayList();
        cantidadCodeBar = FXCollections.observableArrayList();
        try {
            codFactura = ConexionInventario.sSQL(SentenciasSQL.verRegistroCodeBar + "'" + codeBar + "' and detalle_factura.cod_factura = "
                    + "'" + factura + "'");
            while (codFactura.next()) {
                if (codFactura.getString("producto.codigo_barras").equals(codeBar)) {
                    if (cantidad <= codFactura.getInt(3) && cantidad > 0) {
                        listaVenta.add(new Venta(codFactura.getString(1), n_Compra, codFactura.getString(2), cantidad,
                                codFactura.getDouble(4), (cantidad * codFactura.getDouble(4))));
                        n_Compra++;
                        cantidadCodeBar.add(new Item(codFactura.getInt(3), codFactura.getString("producto.codigo_barras")));
                    } else {
                        Alertas.mensajeInformación("Artículo", "La cantidad del articulo es incorrecta.\n"
                                + "Hay: " + codFactura.getInt(3) + " artiulo");
                    }
                } else {
                    Alertas.mensajeError("El artículo no está registrado en el ticket de compra.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (true) {

        }
        return listaVenta;
    }

    private double sumarDineroTotal() {
        double sum = 0;
        for (int i = 0; i < listaTotalCompra.size(); i++) {
            sum += new BigDecimal(listaTotalCompra.get(i)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        txtTotal.setText(MetodosJavaClass.quitarDecimal(sum));
        return sum;
    }

    private Boolean existeDevolucionFactura() {
        try {
            codFactura = ConexionInventario.sSQL(SentenciasSQL.sqlDevolucionFactura);
            while (codFactura.next()) {
                if (txtFactura.getText().equals(codFactura.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alertas.mensajeError("El nº de Factura " + txtFactura.getText() + " no existe");
        return false;
    }

    private Boolean existeCodeBar(String codeBar) {
        try {
            codFactura = ConexionInventario.sSQL(SentenciasSQL.sqlExisteCodeBar + " '" + codeBar + "'");
            while (codFactura.next()) {
                if (codeBar.equals(codFactura.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDevolucionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alertas.mensajeError("El Código de Barras no existe consulte con el encargado.");
        return false;
    }

    private ObservableList<TextField> datosArray() {
        ObservableList<TextField> listaDatos = FXCollections.observableArrayList();
        listaDatos.removeAll(listaDatos);
        if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 1) {
            listaDatos.add(txtFactura);
            listaDatos.add(txtMotivo);
        }
        if (cmbDevolucion.getSelectionModel().getSelectedItem().getId() == 2) {
            listaDatos.add(txtFactura);
            listaDatos.add(txtArticulo);
            listaDatos.add(txtMotivo);
            listaDatos.add(txtCantidad);
        }
        return listaDatos;
    }

    private ObservableList<Item> rellenarCombo() {
        opciones = FXCollections.observableArrayList();
        opciones.add(new Item(1, "Devolver Ticket"));
        opciones.add(new Item(2, "Devolver articulo"));
        return opciones;
    }
}
