package ticketventa;


public class Venta {
   
    private int numero;
    private String articulo;
    private  int cantidad;
    private double precio;
    private double total;

    public Venta() {
    }

    public Venta(int numero, String articulo, int cantidad, double precio, double total) {
        this.numero = numero;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
