package ticketventa;

public class Ticket {
    
    private Venta venta;
    private String cliente;
    private String empleado;
    private String metopago;
    private double devolver;
    private String fecha;
    private String total;

    public Ticket() {
    }

    public Ticket(Venta venta, String cliente, String empleado, String metopago, double devolver, String fecha, String total) {
        this.venta = venta;
        this.cliente = cliente;
        this.empleado = empleado;
        this.metopago = metopago;
        this.devolver = devolver;
        this.fecha = fecha;
        this.total = total;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getMetopago() {
        return metopago;
    }

    public void setMetopago(String metopago) {
        this.metopago = metopago;
    }

    public double getDevolver() {
        return devolver;
    }

    public void setDevolver(double devolver) {
        this.devolver = devolver;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    
    
}
