package reportesistemainventario;

import clasesjava.Item;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import metodosjavaClass.Alertas;
import metodosjavaClass.LLenarCombos;

public class FXMLReporteSistemaInventarioController implements Initializable {

    private final CrearInforme report = new CrearInforme();
    private final LLenarCombos llenar = new LLenarCombos();
    private int i;
    private String url;

    @FXML
    private TextField txtConsulta, txtRuta;
    @FXML
    private TextArea txtAreaConsulta;
    @FXML
    private ComboBox<Item> cmbTabla;
    @FXML
    private ComboBox<Item> cmbConsulta;
    private String itemSeleccionadoConsulta;

    @FXML
    private String itemSeleccionadoTabla() {
        String itemSeleccionado = cmbTabla.getSelectionModel().getSelectedItem().getDescripcion();
        LLenarCombos.consultaDB(cmbTabla.getSelectionModel().getSelectedItem().getId(), cmbConsulta);
        return itemSeleccionado;
    }

    @FXML
    private String itemSeleccionadoConsulta() {
        itemSeleccionadoConsulta = cmbConsulta.getSelectionModel().getSelectedItem().getDescripcion();
        System.out.println(itemSeleccionadoConsulta);
        return itemSeleccionadoConsulta;
    }

    @FXML
    private void borrar(ActionEvent event) {
        cmbTabla.getSelectionModel().isSelected(0);
        cmbConsulta.getSelectionModel().isSelected(0);
        txtConsulta.setText(null);
    }

    @FXML
    private void consultar() {
        Alertas.mensajeInformación("Fecha", "Formato Fecha: 'YYYY-MM-DD'\nEjemplo: (2016-02-16) ");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LLenarCombos.tablasDB(cmbTabla);

    }

    @FXML
    private void crearReporte(ActionEvent event) {

        if (itemSeleccionadoTabla().equals("Articulo") && cmbConsulta.getSelectionModel().getSelectedIndex() == 0) {
            //if () {
                url = "src/reportesistemainventario/Articulo/Articulo";
                report.generarReporte(url);
            ///}
            //articulo();
        }else{
            articulo();
        }

        if (itemSeleccionadoTabla().equals("Cliente")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/Cliente/Cliente";
                report.generarReporte(url);
            }
            cliente();
        }

        if (itemSeleccionadoTabla().equals("Factura")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/Factura/Factura";
                report.generarReporte(url);
            }
            factura();

        }

        if (itemSeleccionadoTabla().equals("Detalle Factura")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/DetalleFactura/DetalleFactura";
                report.generarReporte(url);
            }
            detalleFactura();
        }

        if (itemSeleccionadoTabla().equals("Devolucion")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/Devolucion/Devolucion";
                report.generarReporte(url);
            }
            devolucion();
        }

        if (itemSeleccionadoTabla().equals("Empleado")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/Empleado/Empleado";
                report.generarReporte(url);
            }
            empleado();
        }

        if (itemSeleccionadoTabla().equals("Proveedor")) {
            if (cmbConsulta.getSelectionModel().isSelected(0)) {
                url = "src/reportesistemainventario/Proveedor/Proveedor";
                report.generarReporte(url);
            }
            proveedor();
        }

    }

    private void articulo() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Nombre")) {
                url = "src/reportesistemainventario/Articulo/ArticuloNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Fecha")) {
                url = "src/reportesistemainventario/Articulo/ArticuloFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void cliente() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/Cliente/ClienteDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Nombre")) {
                url = "src/reportesistemainventario/Cliente/ClienteNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void factura() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/Factura/FacturaDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Fecha")) {
                url = "src/reportesistemainventario/Factura/FacturaFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void detalleFactura() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/DetalleFactura/DetalleFacturaDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void devolucion() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/Devolucion/DevolucionDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Fecha")) {
                url = "src/reportesistemainventario/Devolucion/DevolucionFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void empleado() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/Empleado/EmpleadoDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Nombre")) {
                url = "src/reportesistemainventario/Empleado/EmpleadoNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void proveedor() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta.equals("Identificador")) {
                url = "src/reportesistemainventario/Proveedor/ProveedorDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta.equals("Nombre")) {
                url = "src/reportesistemainventario/Proveedor/ProveedorNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

}
