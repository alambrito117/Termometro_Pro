package termometrox;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class funcionFechaHora {

    public static String fecha(){
        Date fecha = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");

        return sdf.format(fecha);
    }

    public static String hora(){
        int hora,minutos,segundos;
        String cadenaTiempo = "";
        Calendar calendario = new GregorianCalendar();
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);

        cadenaTiempo = hora+":"+minutos + ":"+ segundos ;

        return cadenaTiempo;
    }

}
