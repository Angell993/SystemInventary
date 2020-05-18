package metodosjavaClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha {

    private static String pantalla;
    private static String dia, mes, annio;

    public static String fecha() {
        Calendar c = new GregorianCalendar();

        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH) + 1);
        annio = Integer.toString(c.get(Calendar.YEAR));

        if (Integer.parseInt(dia) <= 9) {
            dia = (0 + dia);
        }
        if (Integer.parseInt(mes) <= 9) {
            mes = (0 + mes);
        }

        return pantalla = (dia + "-" + mes + "-" + annio);
    }

    public static String fechaSQl() {
        pantalla = (annio + "-" + mes+"-"+dia);
        return pantalla;
    }

}
