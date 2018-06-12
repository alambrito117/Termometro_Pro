package termometrox;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.util.Vector;

public class Termometro extends JComponent implements Runnable,Serializable {

    private int temp;
    private int a = 0;
    private  int b = 0;
    private  int c=0;
    Thread hilo;

    //private int temperatura;
    private boolean alertaTemp = false;

    private Vector tempListener = new Vector();

    public synchronized void addTempetaturaListener(TemperaturaListener listener){
        tempListener.addElement(listener);
    }

    public synchronized  void removeTemperaturaListener(TemperaturaListener listener) {
        tempListener.addElement(listener);
    }


    Termometro(int temperatura){
        temp = temperatura;
    }



    //creamos un metodo de paintConponent para dibujar
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.drawRoundRect(200,10,25,400,50,50);
        g.setColor(Color.GREEN);
        g.fillOval(165,370,100,100);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(170,375,90,90);

        //recuadro que esta encima de todo
        g.setColor(Color.PINK);
        g.drawRoundRect(90,5,250,480,50,50);

        //dibujo de lineal
        //--Obtenemos la altura
        //Dibujar en grados °C
        g.setColor(Color.black);
        int inicio = 20;
        int conta = 0;

        g.setFont(new Font("TimesRoman", Font.BOLD, 20) );
        g.drawString("°C",100,30);
        g.setFont(new Font("TimesRoman", Font.BOLD, 12) );
        String [] gradosC = {"50","40","30","20","10","0","-10","-20","-30","-40"};
        for(int i = 0 ; i < 360; i +=36){
            g.drawLine(180,inicio+i,195,inicio+i);
            g.drawString(gradosC[conta],160,inicio+i);
            conta ++;
        }

        //Dibujaren grados °F
        int inicio2 = 20;
        int conta2 = 0;

        g.setFont(new Font("TimesRoman", Font.BOLD, 20) );
        g.drawString("°F",300,30);
        g.setFont(new Font("TimesRoman", Font.BOLD, 12) );
        String [] gradosF = {"120","100","80","60","40","20","0","-20","-40"};
        for(int i = 0 ; i < 360; i +=40){
            g.drawLine(230,inicio2+i,245,inicio2+i);
            g.drawString(gradosF[conta2],247,inicio2+i);
            conta2 ++;
        }

        //aplicamos las medidas de Colores
        g.setColor(Color.GREEN);
        g.fillRoundRect(275,70,50,15,10,10);

        g.setColor(Color.ORANGE);
        g.fillRoundRect(275,210,50,15,10,10);

        g.setColor(Color.blue);
        g.fillRoundRect(275,280,50,15,10,10);


        // g.drawRoundRect(200,(370 - this.temp)*4,25,0,50,50);


        System.out.println("::::"+this.temp);
        /*g.drawRect(200, 50, 20, 100);
        g.drawOval(50, 70, 100, 100);
        g.drawString("-10ºC", 230, 150);
        g.drawString("+100ºC", 230, 50);
        g.drawString("50ºC", 20, 120);
        g.drawString("75ºC", 90, 180);
        g.drawString("100ºC", 150, 120);*/
        repaint();

        g.translate(0,0);
    }



    public int getTemp() {
        return temp;
    }


    //envia cambio de temperatura
    public void setTemp(int temp) {
        this.temp = temp;

        TermometroEvent evento =new  TermometroEvent(this, temp);

        ejecutarMetodosControladoresCambioTemperatura(evento);

    }

    private void ejecutarMetodosControladoresCambioTemperatura(TermometroEvent evt){
        Vector lista;
        synchronized (this){
            lista = (Vector) tempListener.clone();
        }

        for(int i =0; i < lista.size(); i ++){
            TemperaturaListener listener = (TemperaturaListener) lista.elementAt(i);
            listener.iniciarAlerta(evt);
        }


    }

    public  void iniciaAlerta(){
        //cambiar el color de un Graphics
        if(this.temp >=35 && this.temp <=36){

        }else{
            alertaTemp = false;
        }
        System.out.print("Activa");



        alertaTemp = true;

        //creamos el evento
        TermometroEvent event = new TermometroEvent(this,this.temp);

        //llamamos a los metodos controladoes
        ejecutarMetodosControladoresCambioTemperatura(event);
    }

    public void  start(){
        if(hilo ==null){
            hilo = new Thread(this,"termometro");
            hilo.start();
        }
    }

    @Override
    public void run() {
        while(true){
            this.repaint();
            //Dispara el evento

            try{
                hilo.sleep(1000);
            }catch (Exception error){
                error.printStackTrace();
            }
        }
    }

    //
}
