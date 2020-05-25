package login;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        File f = new File("src/configuracionadministrador/configDB.dat");
        if (f.exists()) {
            Parent root = FXMLLoader.load(getClass().getResource("/login/FXMLIngresar.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Sistema Inventario");
            stage.getIcons().add(new Image(getClass().getResource("/imagenes/iconoInventario.png").toExternalForm()));
            stage.setScene(scene);
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/configuracionadministrador/FXMLAdministradorConfig.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Sistema Inventario");
            stage.getIcons().add(new Image(getClass().getResource("/imagenes/iconoInventario.png").toExternalForm()));
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
