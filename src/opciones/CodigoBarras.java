package opciones;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import conexionbasedatos.ConexionInventario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import metodosjavaClass.Alertas;
import metodosjavaClass.MetodosJavaClass;
import metodosjavaClass.SentenciasSQL;

public class CodigoBarras {

    private Document doc;
    private PdfWriter pdf;
    private String ruta;
    private final Font estiloPrecio = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
    private final Font estiloprecioC = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private final Font estiloNombre = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private Boolean guardarPdf() {
        JFileChooser dlg = new JFileChooser();
        dlg.setDialogTitle("Guardar Código de Barras");
        int option = dlg.showSaveDialog(dlg);
        if (option == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            ruta = f.toString();
            return true;
        }
        Alertas.mensajeInformación("Guaradar", "No has selecionado donde guaradar el PDF");
        return false;
    }

    public void crearCodeBarTodo() {
        try {
            if (guardarPdf()) {
                doc = new Document();
                ResultSet codigoBarras = ConexionInventario.sSQL(SentenciasSQL.sqlEtiquetaCodeBar);
                pdf = PdfWriter.getInstance(doc, new FileOutputStream(ruta + ".pdf"));
                doc.open();

                Barcode128 codeEAN = new Barcode128();
                while (codigoBarras.next()) {
                    codeEAN.setCode(codigoBarras.getString(1));
                    Image img128 = codeEAN.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                    img128.scalePercent(150);
                    doc.add(textoNombre(codigoBarras.getString(2)));
                    doc.add(textoPrecio(String.valueOf(MetodosJavaClass.quitarDecimal(Double.parseDouble(codigoBarras.getString(3))))));
                    doc.add(textoprecio("€C/ud"));
                    doc.add(img128);
                }

                doc.close();
                Alertas.mensajeInformación("Guardar", "Se guardo Correctamente el PDF.");
            }
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(CodigoBarras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearCodeBar(String code, String nombre, String precio) {
        try {
            if (guardarPdf()) {
                doc = new Document();
                pdf = PdfWriter.getInstance(doc, new FileOutputStream(ruta + ".pdf"));
                doc.open();

                Barcode128 codeEAN = new Barcode128();
                codeEAN.setCode(code);
                Image img128 = codeEAN.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img128.scalePercent(150);
                doc.add(textoNombre(nombre));
                MetodosJavaClass.quitarDecimal(Double.parseDouble(precio));
                doc.add(textoPrecio(String.valueOf(MetodosJavaClass.quitarDecimal(Double.parseDouble(precio)))));
                doc.add(textoprecio("€C/ud"));
                doc.add(img128);
                doc.close();
                Alertas.mensajeInformación("Guardar", "Se guardo Correctamente el PDF.");
            }
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(CodigoBarras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Paragraph textoPrecio(String nombre) {
        Paragraph p = new Paragraph();
        p.setFont(estiloPrecio);
        p.add(nombre);
        p.setAlignment(Element.ALIGN_LEFT);
        return p;
    }

    private Paragraph textoNombre(String nombre) {
        Paragraph p = new Paragraph();
        p.setFont(estiloNombre);
        p.add(nombre);
        p.setAlignment(Element.ALIGN_LEFT);
        return p;
    }

    private Paragraph textoprecio(String nombre) {
        Paragraph p = new Paragraph();
        p.setFont(estiloprecioC);
        p.add(nombre);
        p.setAlignment(Element.ALIGN_LEFT);
        return p;
    }

}
