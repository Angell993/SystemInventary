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

    @FXML
    private String itemSeleccionadoTabla() {
        String itemSeleccionado = cmbTabla.getSelectionModel().getSelectedItem().getDescripcion();
        LLenarCombos.consultaDB(cmbTabla.getSelectionModel().getSelectedItem().getId(), cmbConsulta);
        return itemSeleccionado;
    }

    @FXML
    private String itemSeleccionadoConsulta() {
        String itemSeleccionadoConsulta = cmbConsulta.getSelectionModel().getSelectedItem().getDescripcion();
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

        if (itemSeleccionadoTabla().equals("Articulo")) {
            if (!cmbConsulta.getSelectionModel().isSelected(0)) {
                System.out.println("Voy a ingresar a otro reporte");
            } else {
                url = "src/reportesistemainventario/reportes/Articulos";
                report.generarReporte(url);
            }
        }

        if (itemSeleccionadoTabla().equals("Cliente")) {
            url = "src/reportesistemainventario/reportes/Clientes";
            report.generarReporte(url);
        }

        if (itemSeleccionadoTabla().equals("Factura")) {
            url = "src/reportesistemainventario/reportes/Facturas";
            report.generarReporte(url);

        }

        if (itemSeleccionadoTabla().equals("Devolucion")) {
            url = "src/reportesistemainventario/reportes/Devoluciones";
            report.generarReporte(url);

        }

        if (itemSeleccionadoTabla().equals("Empleado")) {
            url = "src/reportesistemainventario/reportes/Empleados";
            report.generarReporte(url);
        }

        if (itemSeleccionadoTabla().equals("Proveedor")) {
            url = "src/reportesistemainventario/reportes/Proveedores";
            report.generarReporte(url);

        }

    }

    private void articulo() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Nombre")) {
                url = "src/reportesistemainventario/Articulo/ArticuloNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Fecha")) {
                url = "src/reportesistemainventario/Articulo/ArticuloFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void cliente() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/Cliente/ClienteDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Nombre")) {
                url = "src/reportesistemainventario/Cliente/ClienteNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void factura() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/Factura/FacturaDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Fecha")) {
                url = "src/reportesistemainventario/Factura/FacturaFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void detalleFactura() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/DetalleFactura/DetalleFacturaDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void devolucion() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/Devolucion/DevolucionDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Fecha")) {
                url = "src/reportesistemainventario/Devolucion/DevolucionFecha";
                i = 3;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void empleado() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/Empleado/EmpleadoDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Nombre")) {
                url = "src/reportesistemainventario/Empleado/EmpleadoNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

    private void proveedor() {
        if (!txtConsulta.getText().isEmpty()) {
            if (itemSeleccionadoConsulta().equals("Identificador")) {
                url = "src/reportesistemainventario/Proveedor/ProveedorDocumento";
                i = 1;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
            if (itemSeleccionadoConsulta().equals("Nombre")) {
                url = "src/reportesistemainventario/Proveedor/ProveedorNombre";
                i = 2;
                report.generarReporteConsulta(url, txtConsulta.getText(), i);
            }
        }
    }

}
