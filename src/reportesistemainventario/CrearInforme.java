package reportesistemainventario;

import conexionbasedatos.ConexionDB;
import java.util.HashMap;
import java.util.Map;
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
            System.out.println(e.toString() + e);
        }
    }

    public void generarReporteConsulta(String url, String consulta, int itemSeleccionado) {

        Map parametro = new HashMap();
        if (itemSeleccionado == 1) {
            parametro.put("Documento", consulta);
        }
        if (itemSeleccionado == 2) {
            parametro.put("Nombre", consulta);
        }
        if (itemSeleccionado == 3) {
            parametro.put("Fecha", consulta + "%");
        }

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(url + ".jasper", parametro,
                    con.conectar());

            JRViewer viewer = new JRViewer(jasperPrint);
            JFrame frame = new JFrame("Sistema de Reporte");
            frame.getContentPane().add(viewer);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);

        } catch (JRException | NullPointerException e) {
            errorReport(itemSeleccionado);
        }
    }

    /*Si eno encuentra lo seleccionado del comboBox estos son los mensajes de error*/
    private void errorReport(int itemSeleccionado) {
        if (itemSeleccionado == 1) {
            Alertas.mensajeErrorPers("No existe", "El Documento no existe en el Sistema.");
        }
        if (itemSeleccionado == 2) {
            Alertas.mensajeErrorPers("No existe", "El Nombre no existe en el Sistema.");
        }
        if (itemSeleccionado == 3) {
            Alertas.mensajeErrorPers("No existe", "La fecha no etsá registrada en el Sistema.");
        }
    }

    public void ticketVenta(String factura, String url) {
        Map parametro = new HashMap();
        try {
            parametro.put("operacion", factura);
            JasperPrint jasperPrint = JasperFillManager.fillReport(url + ".jasper", parametro,
                    con.conectar());

            JRViewer viewer = new JRViewer(jasperPrint);
            JFrame frame = new JFrame("Sistema de Reporte");
            frame.getContentPane().add(viewer);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (JRException e) {
            System.out.println(e.toString() + e);
        }
    }

    
}
