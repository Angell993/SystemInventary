package configuracionadministrador;

import java.io.Serializable;

public class ConfigCorreo implements Serializable{
    
    private String correo, contrasenia;

    public ConfigCorreo() {
    }

    public ConfigCorreo(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "ConfigCorreo{" + "correo=" + correo + ", contrasenia=" + contrasenia + '}';
    }

   
    
}
