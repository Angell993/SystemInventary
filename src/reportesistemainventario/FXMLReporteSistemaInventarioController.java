package reportesistemainventario;

import clasesjava.CrearInforme;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXMLReporteSistemaInventarioController implements Initializable {

    private final CrearInforme report = new CrearInforme();

   
    @FXML
    private void articulo() {
        report.generarReporte("src/reportesistemainventario/reportes/Articulos");
    }

    @FXML
    private void cliente() {
       report.generarReporte("src/reportesistemainventario/reportes/Clientes"); 
    }

    @FXML
    private void factura() {
        report.generarReporte("src/reportesistemainventario/reportes/Facturas");
    }

    @FXML
    private void producto() {
        report.generarReporte("src/reportesistemainventario/reportes/Productos");
    }

    @FXML
    private void devolucion() {
       report.generarReporte("src/reportesistemainventario/reportes/Devoluciones");
    }

    @FXML
    private void empleado() {
       report.generarReporte("src/reportesistemainventario/reportes/Empleados");
    }

    @FXML
    private void proveedor() {
        report.generarReporte("src/reportesistemainventario/reportes/Proveedores");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
