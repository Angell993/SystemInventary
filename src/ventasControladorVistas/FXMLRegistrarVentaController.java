package ventasControladorVistas;

import conexionbasedatos.ConexionInventario;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metodosjavaClass.SentenciasSQL;
import clasesjava.Item;
import clasesjava.Venta;
import conexionbasedatos.ConexionDB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import metodosjavaClass.Alertas;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.MetodosJavaClass;
import sistemaInventarioControladorVistas.FXMLSistemaInventarioController;

public class FXMLRegistrarVentaController implements Initializable {

    @FXML
    private TextField nFactura, precioArticulo, txtTotalCompra;

    @FXML
    ComboBox<Item> cmbArticulo;
    @FXML
    ComboBox<Item> cmbCantidad;
    @FXML
    private TableColumn<Venta, Integer> clmNumVenta;
    @FXML
    private TableColumn<Venta, String> clmNombre;
    @FXML
    private TableColumn<Venta, Integer> clmCantidad;
    @FXML
    private TableColumn<Venta, Float> clmPrecio;
    @FXML
    private TableColumn<Venta, Float> clmTotal;
    @FXML
    private Button aniadir;

    @FXML
    private TableView<Venta> tblVenta;

    private final LLenarCombos llenarCmb = new LLenarCombos();
    private final ConexionDB con = new ConexionDB();
    private Connection transaction;
    private ObservableList<Venta> listaArticulo;
    private ObservableList<Item> listaArticulos;
    private ObservableList<Item> listaCantidad;
    private ObservableList<Double> listaTotalCompra;
    private ObservableList<Item> idCantidadComprada;
    private String codEmpleado;
    private int n_Compra = 0;
    private Float total;
    private Venta venta;

    @FXML
    private void realizarPago(ActionEvent event) {
        try {
            transaction.setAutoCommit(false);
            registrarCompra();
            actualizarDatosDB();
            transaction.commit();
            transaction.close();
            System.out.println("---------- Inicializando Forma de Pago ----------");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventasControladorVistas/FXMLPago.fxml"));
            try {
                Parent root = loader.load();
                FXMLPagoController pagar = loader.getController();
                pagar.recibirInformacionPago(nFactura.getText(), codEmpleado, String.valueOf(sumarDineroTotal()), idCantidadComprada);
                Scene scene_page = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Metodo Pago");
                //Con el Modality.APPLICATION_MODAL nos permite que hasta que no terminemos con la ventana que se abre
                //no dejara que utilize la otra ventana.
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                Stage mystage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene_page);
                stage.showAndWait();

                if (pagar.isConfirmacion()) {
                    generarFactura();
                    cancelar();
                    transaction = con.conectar();
                } else {
                    transaction = con.conectar();
                }

            } catch (IOException ex) {
                Logger.getLogger(FXMLSistemaInventarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* else {
            System.out.println("no has registrado ninguna compra");
        }*/

    }

    @FXML
    private void obtenerArticulo(MouseEvent event) {
        if (event.getClickCount() == 2) {
            tblVenta.getSelectionModel().getSelectedIndex();
        }
    }

    @FXML
    private void llenarCantidad(ActionEvent event) {
        if (!cmbArticulo.getSelectionModel().isSelected(-1)) {
            recibirPrecio(cmbArticulo.getSelectionModel().getSelectedItem().getId());
            int idArticulo = cmbArticulo.getSelectionModel().getSelectedItem().getId();
            llenarCmb.llenarComboBox(listaCantidad, cmbCantidad, SentenciasSQL.sqlCantidad + idArticulo);
        }
    }

    @FXML
    private void añadirArticulo() {
        clmNumVenta.setCellValueFactory(new PropertyValueFactory<>("numeroCompra"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCompra"));
        clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precioArticulo"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("totalCompra"));

        tblVenta.setItems(guardarCompraOB());
        sumarDineroTotal();
        cmbArticulo.getSelectionModel().select(-1);
        cmbCantidad.getSelectionModel().select(-1);
        precioArticulo.setText("0,00");
    }

    @FXML
    private double sumarDineroTotal() {
        double sum = 0;
        for (int i = 0; i < listaTotalCompra.size(); i++) {
            sum += new BigDecimal(listaTotalCompra.get(i)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        txtTotalCompra.setText(String.valueOf(sum = Math.rint(sum * 10) / 10));
        return sum;
    }

    @FXML
    private void cancelar() {
        tblVenta.getItems().clear();
        n_Compra = 0;
        listaArticulo.clear();
        listaTotalCompra.clear();
        txtTotalCompra.setText("");

    }

    @FXML
    private void eliminarFilaVenta() {
        if (tblVenta.getSelectionModel().getSelectedIndex() <= -1) {
            Alertas.mensajeInformación("Compra", "Añade una Compra.");
        } else {
            if (listaTotalCompra.indexOf(-1) < 0) {
                Venta vent = tblVenta.getSelectionModel().getSelectedItem();
                listaArticulo.remove(vent);
                listaTotalCompra.clear();
                txtTotalCompra.setText("");
                total = 0.00f;
                n_Compra = 1;
                for (int i = 0; i < listaArticulo.size(); i++) {
                    listaArticulo.get(i).setNumeroCompra(n_Compra);
                    n_Compra++;
                    System.out.println(n_Compra);
                }
                n_Compra -= 1;
                tblVenta.refresh();
            } else {
                Venta vent = tblVenta.getSelectionModel().getSelectedItem();
                listaArticulo.remove(vent);
                listaTotalCompra.remove(tblVenta.getSelectionModel().getSelectedIndex());
                sumarDineroTotal();
                n_Compra = 1;
                for (int i = 0; i < listaArticulo.size(); i++) {
                    listaArticulo.get(i).setNumeroCompra(n_Compra);
                    n_Compra++;
                    System.out.println(n_Compra);
                }
                n_Compra -= 1;
                tblVenta.refresh();
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transaction = con.conectar();
        generarFactura();
        listaArticulo = FXCollections.observableArrayList();
        listaTotalCompra = FXCollections.observableArrayList();
        llenarCmb.llenarComboBox(listaArticulos, cmbArticulo, SentenciasSQL.sqlArticulos);
        precioArticulo.setText("0,00");
    }

    private void generarFactura() {
        nFactura.setText(String.valueOf(MetodosJavaClass.identificador()));
        while (!existeFactura(nFactura.getText())) {
            nFactura.setText(String.valueOf(MetodosJavaClass.identificador()));
        }
    }

    private void registrarCompra() {
        idCantidadComprada = FXCollections.observableArrayList();
        String regCompra;
        for (int i = 0; i < listaArticulo.size(); i++) {
            regCompra = SentenciasSQL.sqlRegCompra + " ('" + listaArticulo.get(i).getNumeroCoFactura() + "', "
                    + MetodosJavaClass.obtenerId(SentenciasSQL.sqlIdArticulo + " '" + listaArticulo.get(i).getNombreArticulo() + "'")
                    + ", " + listaArticulo.get(i).getCantidadCompra()
                    + "," + listaArticulo.get(i).getTotalCompra() + " )";
            ConexionInventario.EjecutarSQL_TRANSACT(transaction, regCompra);
            idCantidadComprada.add(new Item(MetodosJavaClass.obtenerId(SentenciasSQL.sqlIdArticulo + " '" + listaArticulo.get(i).getNombreArticulo() + "'"),
                    String.valueOf(listaArticulo.get(i).getCantidadCompra())));
        }
    }

    public void recibirCodEmpleado(String empleado) {
        codEmpleado = empleado;
    }

    //He modificado aquí, cerrando la conexion de resultset
    private void recibirPrecio(int idArticulo) {
        try {
            String sSelect = SentenciasSQL.obtenerPrecio + idArticulo + " ;";
            ResultSet rSet = conexionbasedatos.ConexionInventario.sSQL(sSelect);
            while (rSet.next()) {
                precioArticulo.setText(rSet.getString(1));
            }
            rSet.close();
        } catch (SQLException ex) {
            Alertas.errorSQL("ERROR DE SQL", ex);
        }
    }

    private boolean comprobarCampos() {

        if (cmbArticulo.getSelectionModel().isSelected(-1)) {
            Alertas.mensajeErrorPers(null, "Â¡Debe seleccionar un ArtÃ­culo!");
            return false;
        }
        if (cmbCantidad.getSelectionModel().isSelected(-1)) {
            Alertas.mensajeErrorPers(null, "Â¡Debe seleccionar una cantidad mÃ­nima!");
            return false;
        }
        return true;
    }

    //he cerrado la conexion de ResultSet
    private Boolean existeFactura(String codFactura) {
        try {
            try (ResultSet dato = ConexionInventario.sSQL(SentenciasSQL.sqlConsultarFactura)) {
                while (dato.next()) {
                    if (dato.getString(1).equals(codFactura)) {
                        return false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private ObservableList<Venta> guardarCompraOB() {
        venta = new Venta();
        if (comprobarCampos()) {
            String n_factura = String.valueOf(nFactura.getText());
            n_Compra++;
            String nombreArt = cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion();
            Float precio = Float.parseFloat(precioArticulo.getText());
            int cantidad = Integer.parseInt(cmbCantidad.getSelectionModel().getSelectedItem().getDescripcion());
            total = precio * cantidad;
            venta.setNumeroFactura(n_factura);
            venta.setNumeroCompra(n_Compra);
            venta.setNombreArticulo(nombreArt);
            venta.setCantidadCompra(cantidad);
            venta.setPrecioArticulo(precio);
            venta.setTotalCompra((float) (Math.rint(total * 10) / 10));
            listaArticulo.add(venta);

            listaTotalCompra.add(Double.valueOf(total));
        }
        return listaArticulo;
    }

    private void actualizarDatosDB() {
        for (int i = 0; i < listaArticulo.size(); i++) {
            ObservableList<Item> datosStock = comprobarStockId(listaArticulo.get(i).getNombreArticulo());
            for (int j = 0; j < datosStock.size(); j++) {
                int actualizarStock = Integer.parseInt(datosStock.get(j).getDescripcion()) - listaArticulo.get(i).getCantidadCompra();
                String sentencia = SentenciasSQL.sqlActualizarArticuloDb + " stock = " + actualizarStock + " where id_articulo = " + datosStock.get(j).getId();
                ConexionInventario.EjecutarSQL_TRANSACT(transaction, sentencia);
                break;
            }
        }
    }

    private ObservableList<Item> comprobarStockId(String articulo) {
        ObservableList<Item> idStock = FXCollections.observableArrayList();
        try {
            ResultSet datos = ConexionInventario.sSQL_TRANSACT(transaction, SentenciasSQL.sqlConsultarActualizarDb + " '" + articulo + "'");
            while (datos.next()) {
                idStock.add(new Item(datos.getInt(1), String.valueOf(datos.getInt(2))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idStock;
    }
}
