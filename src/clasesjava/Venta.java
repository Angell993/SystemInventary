package clasesjava;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author juanc
 */
public class Venta {

    private StringProperty numeroFactura;
    private IntegerProperty numeroCompra;
    private StringProperty nombreArticulo;
    private IntegerProperty cantidadCompra;
    private DoubleProperty precioArticulo;
    private DoubleProperty totalCompra;

    public Venta(String numeroFactura,String nombreArticulo, int cantidadCompra, Double precioArticulo, Double totalCompra) {
        this.numeroFactura = new SimpleStringProperty(numeroFactura);
        this.nombreArticulo = new SimpleStringProperty(nombreArticulo);
        this.cantidadCompra = new SimpleIntegerProperty(cantidadCompra);
        this.precioArticulo = new SimpleDoubleProperty(precioArticulo);
        this.totalCompra = new SimpleDoubleProperty(totalCompra);
    }

    public Venta(String numeroFactura, int numeroCompra, String nombreArticulo, int cantidadCompra, Double precioArticulo, Double totalCompra) {
        this.numeroFactura = new SimpleStringProperty(numeroFactura);
        this.numeroCompra = new SimpleIntegerProperty(numeroCompra);
        this.nombreArticulo = new SimpleStringProperty(nombreArticulo);
        this.cantidadCompra = new SimpleIntegerProperty(cantidadCompra);
        this.precioArticulo = new SimpleDoubleProperty(precioArticulo);
        this.totalCompra = new SimpleDoubleProperty(totalCompra);
    }

    public Venta() {

    }

    public final String getNumeroCoFactura() {
        return numeroFactura.get();
    }

    public final void setNumeroFactura(String value) {
        numeroFactura = new SimpleStringProperty(value);
    }

    public final int getNumeroCompra() {
        return numeroCompra.get();
    }

    public final void setNumeroCompra(int value) {
        numeroCompra = new SimpleIntegerProperty(value);
    }

    public final String getNombreArticulo() {
        return nombreArticulo.get();
    }

    public final void setNombreArticulo(String value) {
        nombreArticulo = new SimpleStringProperty(value);
    }

    public final int getCantidadCompra() {
        return cantidadCompra.get();
    }

    public final void setCantidadCompra(int value) {
        cantidadCompra = new SimpleIntegerProperty(value);
    }

    public final Double getPrecioArticulo() {
        return precioArticulo.get();
    }

    public final void setPrecioArticulo(Double value) {
        precioArticulo = new SimpleDoubleProperty(value);
    }

    public final Double getTotalCompra() {
        return totalCompra.get();
    }

    public final void setTotalCompra(Double value) {
        totalCompra = new SimpleDoubleProperty(value);
    }

    @Override
    public String toString() {
        return "Venta{" + "numeroFactura=" + numeroFactura + ", numeroCompra=" + numeroCompra + ", nombreArticulo=" + nombreArticulo + ", cantidadCompra=" + cantidadCompra + ", precioArticulo=" + precioArticulo + ", totalCompra=" + totalCompra + '}';
    }

}
