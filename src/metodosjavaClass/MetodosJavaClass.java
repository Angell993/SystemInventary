package metodosjavaClass;

import clasesjava.Item;
import conexionbasedatos.ConexionInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MetodosJavaClass {

    /*Verificar si es número*/
    public static Boolean isDouble(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            Alertas.mensajeErrorPers("No es un numero", "El valor ingresado no es un número. " + numero);
        }
        return false;
    }

    public static Boolean esNumero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String quitarDecimal(Double precio) {
        String retorno = "";
        retorno = Double.toString(precio);
        if (precio % 1 == 0) {
            retorno = retorno.substring(0, retorno.length() - 2);
        } else {
            if (String.valueOf(precio).contains(".")) {
                precio = (Math.rint(precio * 100) / 100);
                retorno = String.valueOf(precio).replace(".", ",");
            }
        }
        return retorno;
    }

    public static Double quitarComa(String precio) {
        if (precio.contains(",")) {
            return Double.parseDouble(precio.replace(",", "."));
        }
        return Double.parseDouble(precio);
    }

    /*generar número de 5 cifrar para el identificador de cada ventana*/
    public static int identificador() {
        int identificador = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        return identificador;
    }

    public static int identificadorEmpleado() {
        int identificador = (int) (Math.random() * (99999999 - 10000000 + 1) + 10000000);
        return identificador;
    }

    public static Boolean txtVacios(ObservableList<TextField> datos) {
        int count = 0;
        int lengthDatos = datos.size();
        for (int i = 0; i < datos.size(); i++) {
            if (!datos.get(i).getText().isEmpty()) {
                count++;
            }
        }
        //Con este If compruebo si el contador es menor al tamaño de la arry
        if (count < lengthDatos) {
            Alertas.mensajeErrorPers("Texto Vacios", "Los campos están vacios ");
            return false;
        }
        return true;
    }

    public static Boolean cmbSeleccionado(ComboBox<Item> cmbSelecionado) {
        if (cmbSelecionado.getSelectionModel().isSelected(-1)) {
            Alertas.mensajeErrorPers("Selecciona", "No has seleccionado una opción");
            return false;
        }
        return true;
    }

    public static String obtenerTipoDoc(int idDoc) {
        String tipodocumento = null;
        try {
            ResultSet rSet = ConexionInventario.sSQL(SentenciasSQL.sqlDocumentoId + idDoc);
            while (rSet.next()) {
                tipodocumento = rSet.getString("Descripcion");
            }
        } catch (SQLException ex) {
            Alertas.errorSQL("Consulta errónea", ex);
        }
        return tipodocumento;
    }

    public static String obtenerDocumentoProveedor(String idDoc) {
        String tipodocumento = null;
        try {
            ResultSet rSet = ConexionInventario.sSQL(SentenciasSQL.sqlNomComercioProveedor + " '" + idDoc + "'");
            while (rSet.next()) {
                tipodocumento = rSet.getString("No_documento");
            }
        } catch (SQLException ex) {
            Alertas.errorSQL("Consulta errónea", ex);
        }
        return tipodocumento;
    }

    public static int obtenerId(String sentencia) {
        int tipodocumento = 0;
        try {
            ResultSet rSet = ConexionInventario.sSQL(sentencia);
            while (rSet.next()) {
                tipodocumento = rSet.getInt(1);
            }
        } catch (SQLException ex) {
            Alertas.errorSQL("Consulta errónea", ex);
        }
        return tipodocumento;
    }

    // Correo Electronico
    private static ObservableList<String> correoElectronico() {
        ObservableList<String> correo = FXCollections.observableArrayList();
        correo.remove(correo);
        correo.add("@hotmail.com");
        correo.add("@hotmail.es");
        correo.add("@yahoo.com");
        correo.add("@yahoo.es");
        correo.add("@gmail.com");
        correo.add("@gmail.es");
        return correo;
    }

    public static Boolean verificarEmail(TextField email) {
        for (int i = 0; i < correoElectronico().size(); i++) {
            if (email.getText().contains(MetodosJavaClass.correoElectronico().get(i))) {
                return true;
            }
        }
        Alertas.información("Email", "Correo electrónico incorrecto");
        return false;
    }

}
