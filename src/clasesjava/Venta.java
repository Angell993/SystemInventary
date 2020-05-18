package clasesjava;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
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
    private FloatProperty precioArticulo;
    private FloatProperty totalCompra;

    public Venta(String numeroFactura, int numeroCompra, String nombreArticulo, int cantidadCompra, Float precioArticulo, Float totalCompra) {
        this.numeroFactura = new SimpleStringProperty(numeroFactura);
        this.numeroCompra = new SimpleIntegerProperty(numeroCompra);
        this.nombreArticulo = new SimpleStringProperty(nombreArticulo);
        this.cantidadCompra = new SimpleIntegerProperty(cantidadCompra);
        this.precioArticulo = new SimpleFloatProperty(precioArticulo);
        this.totalCompra = new SimpleFloatProperty(totalCompra);
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

    public final float getPrecioArticulo() {
        return precioArticulo.get();
    }

    public final void setPrecioArticulo(Float value) {
        precioArticulo = new SimpleFloatProperty(value);
    }

    public final float getTotalCompra() {
        return totalCompra.get();
    }

    public final void setTotalCompra(Float value) {
        totalCompra = new SimpleFloatProperty(value);
    }

    @Override
    public String toString() {
        return "Venta{" + "numeroFactura=" + numeroFactura + ", numeroCompra=" + numeroCompra + ", nombreArticulo=" + nombreArticulo + ", cantidadCompra=" + cantidadCompra + ", precioArticulo=" + precioArticulo + ", totalCompra=" + totalCompra + '}';
    }

}
