
package clasesjava;

public class Item {
    private int id;
    private String descripcion;
    private String docProveedor;

    public Item() {
    }

    public Item(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Item( String docProveedor, String descripcion) {
        this.docProveedor= docProveedor;
        this.descripcion = descripcion;
    }

    public Item(String descripcion) {
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocProveedor() {
        return docProveedor;
    }

    public void setDocProveedor(String docProveedor) {
        this.docProveedor = docProveedor;
    }

   
    @Override
    public String toString() {
        return  descripcion ;
    }
    
}
