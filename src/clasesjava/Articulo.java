package clasesjava;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Articulo {

    private IntegerProperty idArticulo;
    private StringProperty nombreArticulo;
    private FloatProperty precioVenta;
    private FloatProperty precioCosto;
    private IntegerProperty cantidadStock;
    private IntegerProperty stock_minimo;
    private IntegerProperty stock_maximo;
    private StringProperty descripcionArticulo;
    private StringProperty nombreProveedor;
    private StringProperty fecha;
    private IntegerProperty codigoBarras;

    public Articulo() {
    }

    public Articulo(int idArticulo, String nombreArticulo, float precioVenta,
            float precioCosto, int cantidadStock, int stock_minimo, int stock_maximo, String descripcionArticulo, String nombreProveedor, String fecha, int codigoBarras) {
        this.idArticulo = new SimpleIntegerProperty(idArticulo);
        this.nombreArticulo = new SimpleStringProperty(nombreArticulo);
        this.precioVenta = new SimpleFloatProperty(precioVenta);
        this.precioCosto = new SimpleFloatProperty(precioCosto);
        this.cantidadStock = new SimpleIntegerProperty(cantidadStock);
        this.stock_minimo = new SimpleIntegerProperty(stock_minimo);
        this.stock_maximo = new SimpleIntegerProperty(stock_maximo);
        this.descripcionArticulo = new SimpleStringProperty(descripcionArticulo);
        this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
        this.fecha = new SimpleStringProperty(fecha);
        this.codigoBarras = new SimpleIntegerProperty(codigoBarras);

    }

    public final int getIdArticulo() {
        return idArticulo.get();
    }

    public final void setIdArticulo(int value) {
        idArticulo.set(value);
    }

    public IntegerProperty idArticuloProperty() {
        return idArticulo;
    }

    public final String getNombreArticulo() {
        return nombreArticulo.get();
    }

    public final void setNombreArticulo(String value) {
        nombreArticulo.set(value);
    }

    public StringProperty nombreArticuloProperty() {
        return nombreArticulo;
    }

    public final float getPrecioVenta() {
        return precioVenta.get();
    }

    public final void setPrecioVenta(float value) {
        precioVenta.set(value);
    }

    public FloatProperty precioVentaProperty() {
        return precioVenta;
    }

    public final float getPrecioCosto() {
        return precioCosto.get();
    }

    public final void setPrecioCosto(float value) {
        precioCosto.set(value);
    }

    public FloatProperty precioCostoProperty() {
        return precioCosto;
    }

    public final int getCantidadStock() {
        return cantidadStock.get();
    }

    public final void setCantidadStock(int value) {
        cantidadStock.set(value);
    }

    public IntegerProperty cantidadStockProperty() {
        return cantidadStock;
    }

    public final String getDescripcionArticulo() {
        return descripcionArticulo.get();
    }

    public final void setDescripcionArticulo(String value) {
        descripcionArticulo.set(value);
    }

    public StringProperty descripcionArticuloProperty() {
        return descripcionArticulo;
    }

    public final String getNombreProveedor() {
        return nombreProveedor.get();
    }

    public final void setNombreProveedor(String value) {
        nombreProveedor.set(value);
    }

    public StringProperty nombreProveedorProperty() {
        return nombreProveedor;
    }

    public final String getFecha() {
        return fecha.get();
    }

    public final void setFecha(String value) {
        fecha.set(value);
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public final int getCodigoBarras() {
        return codigoBarras.get();
    }

    public final void setCodigoBarras(int value) {
        codigoBarras.set(value);
    }

    public IntegerProperty codigoBarrasProperty() {
        return codigoBarras;
    }

    public final int getStock_minimo() {
        return cantidadStock.get();
    }
    
    public IntegerProperty getStock_minimoProperty() {
        return stock_minimo;
    }

    public final void setStock_minimo(int value) {
       stock_minimo.set(value);
    }
    
    public final int getStock_maximo() {
        return cantidadStock.get();
    }

    public IntegerProperty stock_maximoProperty() {
        return stock_maximo;
    }

    public void setStock_maximo(int value) {
        stock_maximo.set(value);
    }

    @Override
    public String toString() {
        return "Articulo{" + "idArticulo=" + idArticulo + ", nombreArticulo=" + nombreArticulo + ", precioVenta=" + precioVenta + ", precioCosto=" + precioCosto + ", cantidadStock=" + cantidadStock + ", stock_minimo=" + stock_minimo + ", stock_maximo=" + stock_maximo + ", descripcionArticulo=" + descripcionArticulo + ", nombreProveedor=" + nombreProveedor + ", fecha=" + fecha + ", codigoBarras=" + codigoBarras + '}'+"\n";
    }

   

}
