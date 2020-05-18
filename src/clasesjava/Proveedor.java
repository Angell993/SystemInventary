package clasesjava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proveedor {

    private IntegerProperty idProveedor;
    private StringProperty cifProveedor;
    private IntegerProperty cod_tipo_doc;
    private StringProperty nombreProveedor;
    private StringProperty apellidosProveedor;
    private StringProperty nombreComecial;
    private StringProperty telefonoProveedor;
    private StringProperty emailProveedor;
    private StringProperty paisProveedor;
    private StringProperty provinciaProveedor;
    private StringProperty ciudadProveedor;
    private StringProperty direccionProveedor;
    private StringProperty productosProveedor;

    public Proveedor() {
    }


    public Proveedor(int idProveedor,String cifProveedor, int cod_tipo_doc, String nombreProveedor, String apellidosProveedor,
            String nombreComecial, String telefonoProveedor, String emailProveedor, String paisProveedor, String provinciaProveedor,
            String ciudadProveedor, String direccionProveedor, String productosProveedor) {
        this.idProveedor = new SimpleIntegerProperty(idProveedor);
        this.cifProveedor =  new SimpleStringProperty(cifProveedor);
        this.cod_tipo_doc = new SimpleIntegerProperty(cod_tipo_doc);
        this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
        this.apellidosProveedor = new SimpleStringProperty(apellidosProveedor);
        this.nombreComecial = new SimpleStringProperty(nombreComecial);
        this.telefonoProveedor = new SimpleStringProperty(telefonoProveedor);
        this.emailProveedor = new SimpleStringProperty(emailProveedor);
        this.paisProveedor = new SimpleStringProperty(paisProveedor);
        this.provinciaProveedor = new SimpleStringProperty(provinciaProveedor);
        this.ciudadProveedor = new SimpleStringProperty(ciudadProveedor);
        this.direccionProveedor = new SimpleStringProperty(direccionProveedor);
        this.productosProveedor = new SimpleStringProperty(productosProveedor);
    }

    public final int getIdProveedor() {
        return idProveedor.get();
    }

    public final void setIdProveedor(int value) {
        idProveedor.set(value);
    }

    public IntegerProperty idProveedorProperty() {
        return idProveedor;
    }

    public final String getCifProveedor() {
        return cifProveedor.get();
    }

    public final void setCifProveedor(String value) {
        cifProveedor.set(value);
    }

    public StringProperty cifProveedorProperty() {
        return cifProveedor;
    }

    public final int getCod_tipo_doc() {
        return cod_tipo_doc.get();
    }

    public final void setCod_tipo_doc(int value) {
        cod_tipo_doc.set(value);
    }

    public IntegerProperty cod_tipo_docProperty() {
        return cod_tipo_doc;
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

    public final String getApellidosProveedor() {
        return apellidosProveedor.get();
    }

    public final void setApellidosProveedor(String value) {
        apellidosProveedor.set(value);
    }

    public StringProperty apellidosProveedorProperty() {
        return apellidosProveedor;
    }

    public final String getNombreComecial() {
        return nombreComecial.get();
    }

    public final void setNombreComecial(String value) {
        nombreComecial.set(value);
    }

    public StringProperty nombreComecialProperty() {
        return nombreComecial;
    }

    public final String getTelefonoProveedor() {
        return telefonoProveedor.get();
    }

    public final void setTelefonoProveedor(String value) {
        telefonoProveedor.set(value);
    }

    public StringProperty telefonoProveedorProperty() {
        return telefonoProveedor;
    }

    public final String getEmailProveedor() {
        return emailProveedor.get();
    }

    public final void setEmailProveedor(String value) {
        emailProveedor.set(value);
    }

    public StringProperty emailProveedorProperty() {
        return emailProveedor;
    }

    public final String getPaisProveedor() {
        return paisProveedor.get();
    }

    public final void setPaisProveedor(String value) {
        paisProveedor.set(value);
    }

    public StringProperty paisProveedorProperty() {
        return paisProveedor;
    }

    public final String getProvinciaProveedor() {
        return provinciaProveedor.get();
    }

    public final void setProvinciaProveedor(String value) {
        provinciaProveedor.set(value);
    }

    public StringProperty provinciaProveedorProperty() {
        return provinciaProveedor;
    }

    public final String getCiudadProveedor() {
        return ciudadProveedor.get();
    }

    public final void setCiudadProveedor(String value) {
        ciudadProveedor.set(value);
    }

    public StringProperty ciudadProveedorProperty() {
        return ciudadProveedor;
    }

    public final String getDireccionProveedor() {
        return direccionProveedor.get();
    }

    public final void setDireccionProveedor(String value) {
        direccionProveedor.set(value);
    }

    public StringProperty direccionProveedorProperty() {
        return direccionProveedor;
    }

    public final String getProductosProveedor() {
        return productosProveedor.get();
    }

    public final void setProductosProveedor(String value) {
        productosProveedor.set(value);
    }

    public StringProperty productosProveedorProperty() {
        return productosProveedor;
    }

    
    @Override
    public String toString() {
        return "Proveedor{" + "cifProveedor=" + cifProveedor + ", cod_tipo_doc=" + cod_tipo_doc + ", nombreProveedor=" + nombreProveedor + ", apellidosProveedor=" + apellidosProveedor + ", nombreComecial=" + nombreComecial + ", telefonoProveedor=" + telefonoProveedor + ", emailProveedor=" + emailProveedor + ", paisProveedor=" + paisProveedor + ", provinciaProveedor=" + provinciaProveedor + ", ciudadProveedor=" + ciudadProveedor + ", direccionProveedor=" + direccionProveedor + ", productosProveedor=" + productosProveedor + '}';
    }

}
