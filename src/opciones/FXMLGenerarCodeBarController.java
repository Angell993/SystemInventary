package opciones;

import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import metodosjavaClass.LLenarCombos;
import metodosjavaClass.SentenciasSQL;

public class FXMLGenerarCodeBarController implements Initializable {

    @FXML
    private TextField txtGuardar;
    @FXML
    private TextArea txtACodeBar;
    @FXML
    private ComboBox<Item> cmbArticulo;
    private final LLenarCombos llenacomb = new LLenarCombos();
    private CodigoBarras cb = new CodigoBarras();
    private File f;
    private ObservableList<Item> codebar;

    @FXML
    private void guardarPdf() {
        JFileChooser dlg = new JFileChooser();
        dlg.setDialogTitle("Guardar Código de Barras");
        int option = dlg.showSaveDialog(dlg);
        if (option == JFileChooser.APPROVE_OPTION) {
            f = dlg.getSelectedFile();
            txtGuardar.setText(f.toString());
        }
    }

    @FXML
    private void generarTodoCodeBar() {
        cb.crearCodeBarTodo();
    }

    @FXML
    private void guadarCodeB() {
        if (!cmbArticulo.getSelectionModel().isSelected(-1)) {
            cb.crearCodeBar(String.valueOf(cmbArticulo.getSelectionModel().getSelectedItem().getId()),
                    cmbArticulo.getSelectionModel().getSelectedItem().getDescripcion(),
                    cmbArticulo.getSelectionModel().getSelectedItem().getDocProveedor());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        etiquetaCombo();
    }
    
    
    private void etiquetaCombo(){
        codebar = FXCollections.observableArrayList();
        try {
            ResultSet cod = ConexionInventario.sSQL(SentenciasSQL.sqlEtiquetaCodeBar);
            while (cod.next()) {
                codebar.add(new Item(cod.getInt(1), cod.getString(2), cod.getString(3)));
            }
            cmbArticulo.setItems(codebar);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLGenerarCodeBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
