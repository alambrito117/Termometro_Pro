package termometrox;

import javax.swing.*;
import java.awt.*;

public class mainTermometro extends JFrame{

    private JButton btnInsertar;
    private ConexionBD interfazSQL;
    private String fech;
    private int tem;




    private int obtentemp;

    mainTermometro(){


        this.setSize(1400,530);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        Container contentPane = this.getContentPane();
        panelFuncionesTerm pft = new panelFuncionesTerm();
        contentPane.add(pft);



        this.setVisible(true);
    }


    public static void main(String[] args) {
        new mainTermometro();
    }

}
