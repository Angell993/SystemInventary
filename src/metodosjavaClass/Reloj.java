package metodosjavaClass;

import java.util.Calendar;
import javafx.scene.control.Label;
import javax.swing.Timer;

public class Reloj {

    private Calendar Cal = Calendar.getInstance();

    public void HoraActual(Label lbhora) {
       
        int j = 1000;
        Timer timer = new Timer(j, (e) -> {
            lbhora.setText(MostrarHora());
        });
        timer.start();
    }
    //este es le metodo para mostrar la hora

    public String MostrarHora() {

        String hora = Cal.get(Cal.HOUR_OF_DAY) + ":" + Cal.get(Cal.MINUTE) + ":" + Cal.get(Cal.SECOND);
        return hora;
    }

}
