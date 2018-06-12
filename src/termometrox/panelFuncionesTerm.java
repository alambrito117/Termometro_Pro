package termometrox;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class panelFuncionesTerm extends JPanel implements ActionListener,TemperaturaListener {
    int dat = 0;
    private JSlider miSlider;
    private TableModel tabla;
    private JTextField jtfDatosC;
    private JLabel lbTex1;
    private JButton btnInsertar;
    private String fecha;
    private String tiempo;
    private JButton btnConexion;
    private ConexionBD interfazSQL;
    panelFuncionesTerm(){

        this.setBackground(Color.GRAY);
        //this.setPreferredSize(new Dimension(300,300));
        /*crearemos un obj para llamar a termometro
        y enviar los datos de la  temperatura
         */
        //Realizara la conexion a la base de datos.
        btnConexion = new JButton("Conectar");
        btnConexion.setActionCommand("acConectar");
        btnConexion.addActionListener(this);


        btnInsertar = new JButton("Enviar la temperatura");
        btnInsertar.setActionCommand("acInsertar");
        btnInsertar.addActionListener(this);

        setLayout(new GridLayout(2,0,5,5));
        Termometro term = new Termometro(dat);

        //term.start();
        miSlider = new JSlider();
        miSlider.setPreferredSize(new Dimension(100,20));
        miSlider.setOrientation(JSlider.HORIZONTAL); // establecemos la orientación del slider
        miSlider.setMinimum(-40); // valor mínimo que puede tomar el slider
        miSlider.setMaximum(50); // valor máximo que puede tomar el slider
        miSlider.setValue(1); // valor inicial que toma el slider

        // creamos la tabla y añadimos los elementos
        jtfDatosC = new JTextField();
        jtfDatosC.setFont(new Font("Agency FB", Font.BOLD, 150));
        jtfDatosC.setEditable(false);
        miSlider.setValue(-40);
        miSlider.setPaintLabels(true); //muestra las etiquetas numéricas
        miSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(miSlider.getValue()) ;
                dat = miSlider.getValue();
                String x = String.valueOf(miSlider.getValue());
                jtfDatosC.setText(x + " °C");
                term.setTemp(dat);

                panelFuncionesTerm.this.repaint();
            }
        });

        add(term);
        term.addTempetaturaListener(this);

        add(miSlider);
        add(jtfDatosC);
        add(btnConexion);
        add(btnInsertar);
        //llamamos al metodo de la clase funcionFechaHora
        fecha = funcionFechaHora.fecha();

        System.out.println(fecha);
        //System.out.println(tiempo);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("acInsertar")){
            tiempo = funcionFechaHora.hora();
            try{
                int nRegistros = interfazSQL.insertarCliente(fecha,tiempo,dat);
                System.out.println("Numeros de registros Afectados ::"+nRegistros);
                JOptionPane.showMessageDialog(this,"Los datos han sido guardado");
            }catch(Exception error){
                JOptionPane.showMessageDialog(this,"No esta conectado" +
                        "a la base de datos");
            }
            //para la tabla


        }else if(e.getActionCommand().equals("acConectar")){
            System.out.println("Temperatura actual : ");
            interfazSQL  = new ConexionBD();
            Connection csql = interfazSQL.Conectar();
            if(csql != null){
                JOptionPane.showMessageDialog(this,
                        "¡¡HAZ SIDO CONECTADO" +
                        "A LA BASE DE DATOS!!","ESTADO DE CONEXIÓN",JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    @Override
    public void iniciarAlerta(TermometroEvent evt) {

        int temperatura = evt.getTemperatura();

        if(temperatura == 40){
            JOptionPane.showMessageDialog(this,
                    "FUERTEMENTE CALIENTE",
                    "MUY CALIENTE ¡¡CUIDADO!!",
                    JOptionPane.WARNING_MESSAGE);
        }else if (temperatura == -40){
            JOptionPane.showMessageDialog(this,
                    "FUERTEMENTE HELADO",
                    "MUY FRÍO ¡¡CUIDADO!!",
                    JOptionPane.WARNING_MESSAGE);
        }else if(temperatura == 12){
            JOptionPane.showMessageDialog(this,
                    "NIVELADO",
                    "",
                    JOptionPane.WARNING_MESSAGE);
        }else if(temperatura == 22){
            JOptionPane.showMessageDialog(this,
                    "TEMPERATURA AMBIENTE",
                    "TEMPERATURA NORMAL",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
