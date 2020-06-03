package metodosjavaClass;

import articuloControladorVistas.FXMLRegistrarArticuloController;
import clasesjava.Item;
import conexionbasedatos.ConexionDB;
import conexionbasedatos.ConexionEspania;
import conexionbasedatos.ConexionInventario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class LLenarCombos {

    private final ConexionDB conn = new ConexionDB();

    /*Cargo una clase Item, que con esto me permite añadir datos a cualquier ComboBox*/
    public void llenarComboBox(ObservableList<Item> listaItem, ComboBox<Item> cargarDatos, String sentencia) {
        listaItem = FXCollections.observableArrayList();
        //Este es un try con recursos que me permite cerrar la conexion cuando ya se termine el ResulSet
        ResultSet datos = null;
        try {
            if (sentencia.contains("provincia")) {
                datos = ConexionEspania.sSQL(sentencia);
            } else {
                datos = ConexionInventario.sSQL(sentencia);
            }
            while (datos.next()) {
                if (sentencia.contains("stock")) {
                    for (int i = 0; i < datos.getInt(2); i++) {
                        listaItem.add(new Item(datos.getInt(1), String.valueOf(i + 1)));
                    }
                } else {
                    listaItem.add(new Item(datos.getInt(1), datos.getString(2)));
                }
            }
            cargarDatos.setItems(listaItem);

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        //conn.cerrarConexion();

    }

    /*Insertar datos en ComboCox Proveedor dos String utilizando Item*/
    public void llenarComboProveedor(ObservableList<Item> listaItem, ComboBox<Item> cargarDatos, String sentencia) {
        listaItem = FXCollections.observableArrayList();
        //Este es un try con recursos que me permite cerrar la conexion cuando ya se termine el ResulSet
        try (ResultSet datos = ConexionInventario.sSQL(sentencia)) {
            while (datos.next()) {
                listaItem.add(new Item(datos.getString(1), datos.getString(2)));
            }
            cargarDatos.setItems(listaItem);
        } catch (SQLException e) {
            Alertas.errorSQL("Error: ", e);
        }
    }
    
    public void articuloCodBar(ObservableList<Item> listaArticulo,ComboBox<Item> cmbArticulo, String sentencia) {
        listaArticulo = FXCollections.observableArrayList();
        try {
            ResultSet datos = ConexionInventario.sSQL(sentencia);
            while (datos.next()) {
                listaArticulo.add(new Item(datos.getInt(1), datos.getString(2), datos.getString(3)));
            }
            cmbArticulo.setItems(listaArticulo);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarArticuloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Metodos del Reporte*/
    public static void tablasDB(ComboBox<Item> cmbTabla) {
        ObservableList<Item> tablas = FXCollections.observableArrayList();
        tablas.add(new Item(1, "Articulo"));
        tablas.add(new Item(2, "Cliente"));
        tablas.add(new Item(3, "Factura"));
        tablas.add(new Item(4, "Detalle Factura"));
        tablas.add(new Item(5, "Devolucion"));
        tablas.add(new Item(6, "Empleado"));
        tablas.add(new Item(7, "Proveedor"));
        cmbTabla.setItems(tablas);
    }

    public static void consultaDB(int idSelected, ComboBox<Item> cmbOpciones) {
        ObservableList<Item> opciones = FXCollections.observableArrayList();
        if (idSelected == 1) { // Opciones para Artículo
            opciones.remove(opciones);
            opciones.add(new Item(1, "Nombre"));
            opciones.add(new Item(2, "Fecha"));
        }
        if (idSelected == 3 || idSelected == 5) { // Opciones para Factura y Devolución
            opciones.remove(opciones);
            opciones.add(new Item(1, "Identificador"));
            opciones.add(new Item(2, "Fecha"));
        }
        if (idSelected == 2 || idSelected == 6 || idSelected == 7) { // Opciones para Cliente, Empleado y Proveedor
            opciones.remove(opciones);
            opciones.add(new Item(1, "Documento o Identificador"));
            opciones.add(new Item(2, "Nombre"));
        }
        if (idSelected == 4) { // Opciones para DetalleFactura  
            opciones.remove(opciones);
            opciones.add(new Item(1, "Identificador"));
        }
        cmbOpciones.setItems(opciones);
    }
}
