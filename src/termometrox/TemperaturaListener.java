package termometrox;


import java.util.EventListener;

public interface TemperaturaListener extends EventListener {

    public void iniciarAlerta(TermometroEvent evt);

}
