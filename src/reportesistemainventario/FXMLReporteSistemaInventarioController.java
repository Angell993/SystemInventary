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
    private void buscarRuta(){
        JFileChooser dlg = new JFileChooser();
        int option = dlg.showSaveDialog(dlg);
        if (option == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            txtRuta.setText(f.toString());
        }
    }
    
    @FXML
    private String itemSeleccionadoTabla() {
        String itemSeleccionado = cmbTabla.getSelectionModel().getSelectedItem().getDescripcion();
        return itemSeleccionado;
    }

    @FXML
    private String itemSeleccionadoConsulta() {
        String itemSeleccionado = cmbConsulta.getSelectionModel().getSelectedItem().getDescripcion();

        return itemSeleccionado;
    }

    @FXML
    private void borrar(ActionEvent event) {
        cmbTabla.getSelectionModel().isSelected(-1);
        cmbConsulta.getSelectionModel().isSelected(-1);
        txtConsulta.setText(null);
    }

    @FXML
    private void consultar() {
        Alertas.mensajeInformación("Fecha", "Formato Fecha  'YYYY-MM-DD' Ejmpl: (2016-02-16) ");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LLenarCombos.tablasDB(cmbTabla);
        LLenarCombos.consultaDB(cmbConsulta);

    }

    @FXML
    private void crearReporte(ActionEvent event) {
        
            if (itemSeleccionadoTabla().equals("Articulo")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Articulo/Articulo";
            report.generarReporte(url);
            }
            articulo();
            }
            
            if (itemSeleccionadoTabla().equals("Cliente")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Cliente/Cliente";
            report.generarReporte(url);
            }
            cliente();
            }
            
            if (itemSeleccionadoTabla().equals("Factura")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Factura/Factura";
            report.generarReporte(url);
            }
            factura();
            
            }
            
            if (itemSeleccionadoTabla().equals("Detalle Factura")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/DetalleFactura/DetalleFactura";
            report.generarReporte(url);
            }
            detalleFactura();
            }
            
            if (itemSeleccionadoTabla().equals("Devolucion")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Devolucion/Devolucion";
            report.generarReporte(url);
            }
            devolucion();
            }
            
            if (itemSeleccionadoTabla().equals("Empleado")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Empleado/Empleado";
            report.generarReporte(url);
            }
            empleado();
            }
            
            if (itemSeleccionadoTabla().equals("Proveedor")) {
            if (cmbConsulta.getSelectionModel().isSelected(-1)) {
            url = "src/reportesistemainventario/Proveedor/Proveedor";
            report.generarReporte(url);
            }
            }

    }

    private void articulo() {
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

    private void cliente() {
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

    private void factura() {
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

    private void detalleFactura() {
         if (itemSeleccionadoConsulta().equals("Identificador")) {
            url = "src/reportesistemainventario/DetalleFactura/DetalleFacturaDocumento";
            i = 1;
            report.generarReporteConsulta(url, txtConsulta.getText(), i);
        }
    }   

    private void devolucion() {
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

    private void empleado() {
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
