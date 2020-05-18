package clasesjava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private IntegerProperty idCliente;
    private StringProperty documento;
    private IntegerProperty idTipoDoc;
    private StringProperty nombreCliente;
    private StringProperty apellidoCliente;
    private StringProperty telefonoCliente;

    private StringProperty paisCliente;
    private StringProperty provinciaCliente;

    private StringProperty ciudadCliente;
    private StringProperty direccionCliente;

    private IntegerProperty codigoPostalCliente;
    private StringProperty emailCliente;
    private IntegerProperty numeroEmpleado;

    public Cliente() {
    }

    public Cliente(int idCliente, String documento, int idTipoDoc, String nombreCliente, String apellidoCliente,
            String telefonoCliente, String paisCliente, String provinciaCliente, String ciudadCliente,
            String direccionCliente, int codigoPostalCliente, String emailCliente, int numeroEmpleado) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.idTipoDoc = new SimpleIntegerProperty(idTipoDoc);
        this.documento = new SimpleStringProperty(documento);
        this.nombreCliente = new SimpleStringProperty(nombreCliente);
        this.apellidoCliente = new SimpleStringProperty(apellidoCliente);
        this.telefonoCliente = new SimpleStringProperty(telefonoCliente);
        this.paisCliente = new SimpleStringProperty(paisCliente);
        this.provinciaCliente = new SimpleStringProperty(provinciaCliente);
        this.ciudadCliente = new SimpleStringProperty(ciudadCliente);
        this.direccionCliente = new SimpleStringProperty(direccionCliente);
        this.codigoPostalCliente = new SimpleIntegerProperty(codigoPostalCliente);
        this.emailCliente = new SimpleStringProperty(emailCliente);
        this.numeroEmpleado = new SimpleIntegerProperty(numeroEmpleado);
    }

    public final int getIdCliente() {
        return idCliente.get();
    }

    public final void setIdCliente(int value) {
        idCliente.set(value);
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public final String getDocumento() {
        return documento.get();
    }

    public final void setDocumento(String value) {
        documento.set(value);
    }

    public StringProperty documentoProperty() {
        return documento;
    }

    public final int getIdTipoDoc() {
        return idTipoDoc.get();
    }

    public final void setIdTipoDoc(int value) {
        idTipoDoc.set(value);
    }

    public IntegerProperty idTipoDocProperty() {
        return idTipoDoc;
    }

    public final String getNombreCliente() {
        return nombreCliente.get();
    }

    public final void setNombreCliente(String value) {
        nombreCliente.set(value);
    }

    public StringProperty nombreClienteProperty() {
        return nombreCliente;
    }

    public final String getApellidoCliente() {
        return apellidoCliente.get();
    }

    public final void setApellidoCliente(String value) {
        apellidoCliente.set(value);
    }

    public StringProperty apellidoClienteProperty() {
        return apellidoCliente;
    }

    public final String getTelefonoCliente() {
        return telefonoCliente.get();
    }

    public final void setTelefonoCliente(String value) {
        telefonoCliente.set(value);
    }

    public StringProperty telefonoClienteProperty() {
        return telefonoCliente;
    }

    public final String getPaisCliente() {
        return paisCliente.get();
    }

    public final void setPaisCliente(String value) {
        paisCliente.set(value);
    }

    public StringProperty paisClienteProperty() {
        return paisCliente;
    }

    public final String getProvinciaCliente() {
        return provinciaCliente.get();
    }

    public final void setProvinciaCliente(String value) {
        provinciaCliente.set(value);
    }

    public StringProperty provinciaClienteProperty() {
        return provinciaCliente;
    }

    public final String getCiudadCliente() {
        return ciudadCliente.get();
    }

    public final void setCiudadCliente(String value) {
        ciudadCliente.set(value);
    }

    public StringProperty ciudadClienteProperty() {
        return ciudadCliente;
    }

    public final String getDireccionCliente() {
        return direccionCliente.get();
    }

    public final void setDireccionCliente(String value) {
        direccionCliente.set(value);
    }

    public StringProperty direccionClienteProperty() {
        return direccionCliente;
    }

    public final int getCodigoPostalCliente() {
        return codigoPostalCliente.get();
    }

    public final void setCodigoPostalCliente(int value) {
        codigoPostalCliente.set(value);
    }

    public IntegerProperty codigoPostalClienteProperty() {
        return codigoPostalCliente;
    }

    public final String getEmailCliente() {
        return emailCliente.get();
    }

    public final void setEmailCliente(String value) {
        emailCliente.set(value);
    }

    public StringProperty emailClienteProperty() {
        return emailCliente;
    }

    public final int getNumeroEmpleado() {
        return numeroEmpleado.get();
    }

    public final void setNumeroEmpleado(int value) {
        numeroEmpleado.set(value);
    }

    public IntegerProperty numeroEmpleadoProperty() {
        return numeroEmpleado;
    }

    
    @Override
    public String toString() {
        return "Cliente{" + "documento=" + documento + ", idTipoDoc=" + idTipoDoc + ", nombreCliente=" + nombreCliente + ", apellidoCliente=" + apellidoCliente + ", telefonoCliente=" + telefonoCliente + ", paisCliente=" + paisCliente + ", provinciaCliente=" + provinciaCliente + ", ciudadCliente=" + ciudadCliente + ", direccionCliente=" + direccionCliente + ", codigoPostalCliente=" + codigoPostalCliente + ", emailCliente=" + emailCliente + ", numeroEmpleado=" + numeroEmpleado + '}';
    }



}
