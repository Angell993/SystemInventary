package configuracionadministrador;

import java.io.Serializable;

public class ConfigDB implements Serializable{
    private String server, usuario,pass;

    public ConfigDB() {
    }

    public ConfigDB(String server, String usuario, String pass) {
        this.server = server;
        this.usuario = usuario;
        this.pass = pass;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "Configuración Data Base\n" + "server=" + server + "\nusuario=" + usuario + "\npass=" + pass ;
    }
    
}
