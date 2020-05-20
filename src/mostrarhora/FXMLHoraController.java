package mostrarhora;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class FXMLHoraController implements Initializable {

    @FXML Label hora;
    public void ejecutarReloj(Label hora) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    try {
                        while (true) {

                            Date dat = new Date();
                            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                            //System.out.println(formatoHora.format(dat));
                            hora.setText(formatoHora.format(dat));

                            Thread.sleep(1000);

                        }
                    } catch (InterruptedException e) {
                    }
                });
                return null;
            }

        };
        new Thread(task).start();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ejecutarReloj(hora);
    }    

    public void iniciarReloj() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
