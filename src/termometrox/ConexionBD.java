package termometrox;

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConexionBD {
    public String db = "termometro";
    public String url = "jdbc:mysql://localhost/" + db
            + "?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";
    public String user = "root";
    public String pass = "caste0123";
    Connection link;
    public Connection Conectar(){
        link = null;

        try{
            //Class.forName("org.gjt.mm.mysql.Driver");  //version 5
            Class.forName("com.mysql.cj.jdbc.Driver");   //version 8

            link = DriverManager.getConnection(this.url, this.user, this.pass);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

        return link;
    }

    public int insertarCliente(String fecha, String hora, int temperatura){
        int numRegs = 0;

        try {
            PreparedStatement stInsertar = link.prepareStatement(
                    "insert into temperaturas(fecha, hora, temperatura)"
                            + " values(?, ?, ?)");

            stInsertar.setString(1, (fecha));
            stInsertar.setString(2, String.valueOf(hora));
            stInsertar.setInt(3, temperatura);

            numRegs = stInsertar.executeUpdate();


        }
        catch(SQLException error){
            error.printStackTrace();
        }

        return numRegs;
    }
    public ResultSet consultarClientes(){
        ResultSet registros = null;
        try{
            PreparedStatement stConsultar
                    = link.prepareStatement("select * from cliente");
            registros = stConsultar.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return registros;
    }
}
