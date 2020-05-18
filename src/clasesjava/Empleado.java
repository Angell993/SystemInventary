package clasesjava;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado {

    private IntegerProperty idEmpleado;
    private IntegerProperty codigoEmpleado;
    private IntegerProperty tipoDocumento;
    private StringProperty documentoEmpleado;
    private StringProperty nombreEmpleado;
    private StringProperty apellidosEmpleado;
    private StringProperty emailEmpleado;
    private StringProperty puesto;

    public Empleado() {
    }

    public Empleado(int idEmpleado, int codigoEmpleado, int tipoDocumento, String documentoEmpleado, String nombreEmpleado, String apellidosEmpleado,
            String emailEmpleado, String puesto) {
        this.idEmpleado = new SimpleIntegerProperty(idEmpleado);
        this.codigoEmpleado = new SimpleIntegerProperty(codigoEmpleado);
        this.documentoEmpleado = new SimpleStringProperty(documentoEmpleado);
        this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
        this.apellidosEmpleado = new SimpleStringProperty(apellidosEmpleado);
        this.emailEmpleado = new SimpleStringProperty(emailEmpleado);
        this.tipoDocumento = new SimpleIntegerProperty(tipoDocumento);
        this.puesto = new SimpleStringProperty(puesto);
    }

    public final int getIdEmpleado() {
        return idEmpleado.get();
    }

    public final void setIdEmpleado(int value) {
        idEmpleado.set(value);
    }

    public IntegerProperty idEmpleadoProperty() {
        return idEmpleado;
    }

    public final int getCodigoEmpleado() {
        return codigoEmpleado.get();
    }

    public final void setCodigoEmpleado(int value) {
        codigoEmpleado.set(value);
    }

    public IntegerProperty codigoEmpleadoProperty() {
        return codigoEmpleado;
    }

    public final int getTipoDocumento() {
        return tipoDocumento.get();
    }

    public final void setTipoDocumento(int value) {
        tipoDocumento.set(value);
    }

    public IntegerProperty tipoDocumentoProperty() {
        return tipoDocumento;
    }

    public final String getDocumentoEmpleado() {
        return documentoEmpleado.get();
    }

    public final void setDocumentoEmpleado(String value) {
        documentoEmpleado.set(value);
    }

    public StringProperty documentoEmpleadoProperty() {
        return documentoEmpleado;
    }

    public final String getNombreEmpleado() {
        return nombreEmpleado.get();
    }

    public final void setNombreEmpleado(String value) {
        nombreEmpleado.set(value);
    }

    public StringProperty nombreEmpleadoProperty() {
        return nombreEmpleado;
    }

    public final String getApellidosEmpleado() {
        return apellidosEmpleado.get();
    }

    public final void setApellidosEmpleado(String value) {
        apellidosEmpleado.set(value);
    }

    public StringProperty apellidosEmpleadoProperty() {
        return apellidosEmpleado;
    }

    public final String getEmailEmpleado() {
        return emailEmpleado.get();
    }

    public final void setEmailEmpleado(String value) {
        emailEmpleado.set(value);
    }

    public StringProperty emailEmpleadoProperty() {
        return emailEmpleado;
    }

    public final String getPuesto() {
        return puesto.get();
    }

    public final void setPuesto(String value) {
        puesto.set(value);
    }

    public StringProperty puestoProperty() {
        return puesto;
    }

  

}
