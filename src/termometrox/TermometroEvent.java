package termometrox;

import java.util.EventObject;

public class TermometroEvent extends EventObject {
    private int temperatura;
    private Object fuente;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TermometroEvent(Object fuente, int temperatura) {
        super(fuente);

        this.temperatura = temperatura;
        this.fuente = fuente;
    }

    public int getTemperatura() {
        return temperatura;
    }
    public Object getFuente() {
        return fuente;
    }
}
