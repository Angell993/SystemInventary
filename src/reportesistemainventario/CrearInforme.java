package reportesistemainventario;

import clasesjava.Cliente;
import conexionbasedatos.ConexionDB;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.swing.JFrame;
import metodosjavaClass.Alertas;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class CrearInforme {

    private final ConexionDB con = new ConexionDB();

    public void generarReporte(String url) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(url + ".jasper", null,
                    con.conectar());
            JRViewer viewer = new JRViewer(jasperPrint);
            JFrame frame = new JFrame("Sistema de Reporte");
            frame.getContentPane().add(viewer);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (JRException e) {
            Alertas.mensajeInformación("Reporte", "El reporte no existe.");
        }
    }


    public void ticketVenta(String factura, String url) {
        Map parametro = new HashMap();
        try {
            parametro.put("factura", factura);
            JasperPrint jasperPrint = JasperFillManager.fillReport(url + ".jasper", parametro,
                    con.conectar());

            JRViewer viewer = new JRViewer(jasperPrint);
            JFrame frame = new JFrame("Ticket");
            frame.getContentPane().add(viewer);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (JRException e) {
            Alertas.mensajeInformación("Ticket", "El ticket no existe.");
        }
    }

    public void factura(String factura, String url, ObservableList<Cliente> datosCliente) {
        Map parametro = new HashMap();
        try {
            parametro.put("operacion", factura);
            JasperPrint jasperPrint = null;
            for (int i = 0; i < datosCliente.size(); i++) {
                parametro.put("nombre", (datosCliente.get(i).getNombreCliente()+" "+datosCliente.get(i).getApellidoCliente()));
                parametro.put("direccion", datosCliente.get(i).getDireccionCliente());
                parametro.put("ciudad", datosCliente.get(i).getCiudadCliente());
                parametro.put("cp", datosCliente.get(i).getCodigoPostalCliente());
                parametro.put("telefono", datosCliente.get(i).getTelefonoCliente());
                parametro.put("documento", datosCliente.get(i).getDocumento());   
            jasperPrint = JasperFillManager.fillReport(url + ".jasper", parametro,
                    con.conectar());             
            }

            JRViewer viewer = new JRViewer(jasperPrint);
            JFrame frame = new JFrame("Factura");
            frame.getContentPane().add(viewer);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (JRException e) {
            Alertas.mensajeInformación("Factura", "El número de factura no existe.");
        }
    }
    
}
