/*Este ejemplo fue tomado de internet y fue modificado para acceder y
 pintar figuras desde la BD*/
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
 
public class SquaredPaper extends JFrame {
 public void paint(Graphics g) {
   Dimension d = getSize();
   int x = d.width;
   int y = d.height;
 
   g.setColor(Color.yellow);
   g.fillRect(0,0,x,y);
 
   g.setColor(Color.green);
   for (int i = 0; i < y; i+=25) g.drawLine(0,i,x,i);
   for (int i = 0; i < x; i+=25) g.drawLine(i,0,i,y);
 
   g.setColor(Color.red);
   g.drawLine(x/2,0,x/2,y);
   g.drawLine(0,y/2,x,y/2);
   g.setColor(Color.blue);  
  
    Connection conn;
    Statement sentencia;
    ResultSet resultado;

    try{ // Se carga el driver JDBC-ODBC
     Class.forName ("oracle.jdbc.driver.OracleDriver");
    } catch( Exception e ) {
      System.out.println("No se pudo cargar el driver JDBC");
      return;           
      }

    try{ // Se establece la conexión con la base de datos Oracle Express
     conn = DriverManager.getConnection  
     ("jdbc:oracle:thin:@DESKTOP-LDB790I:1521:xe","di","1537");
      sentencia = conn.createStatement();
    } catch( SQLException e ) {
      System.out.println("No hay conexión con la base de datos.");
      return;
      }
       
    try {
      //Se obtienen las coordenadas del rectangulo
      resultado = sentencia.executeQuery("SELECT EXTRACTVALUE(doc,'/figuras/rectangulo/a') AS a,EXTRACTVALUE(doc,'/figuras/rectangulo/b') AS b,EXTRACTVALUE(doc,'/figuras/rectangulo/c') AS c,EXTRACTVALUE(doc,'/figuras/rectangulo/d') AS d FROM figura");
      while (resultado.next())
      {
       g.drawRect(resultado.getInt("a"),resultado.getInt("b"),resultado.getInt("c"),resultado.getInt("d"));
      }
      //Se obtienen las coordenadas de la línea
      resultado = sentencia.executeQuery("SELECT EXTRACTVALUE(doc,'/figuras/linea/a') AS a,EXTRACTVALUE(doc,'/figuras/linea/b') AS b,EXTRACTVALUE(doc,'/figuras/linea/c') AS c, EXTRACTVALUE(doc,'/figuras/linea/d') AS d FROM figura");
      while (resultado.next())
      {
       g.drawLine(resultado.getInt("a"),resultado.getInt("b"),resultado.getInt("c"),resultado.getInt("d"));
      }
      //Se obtienen las coordenadas del círculo
      resultado = sentencia.executeQuery("SELECT EXTRACTVALUE(doc,'/figuras/circulo/a') AS a,EXTRACTVALUE(doc,'/figuras/circulo/b') AS b,EXTRACTVALUE(doc,'/figuras/circulo/c') AS c, EXTRACTVALUE(doc,'/figuras/circulo/d') AS d FROM figura");
      while (resultado.next())
      {
       g.setColor(Color.black);
       //x,y, width, height:
       g.fillOval(resultado.getInt("a"),resultado.getInt("b"),resultado.getInt("c"),resultado.getInt("d"));
      }
      //Se cierra la conexion con la BD
      conn.close();  
      //Se pintan dos círculos (que NO están en la BD)
      g.drawString("Dos circulos adicionales",50,150);
      g.drawOval(100, 100, 50, 50);
      g.drawOval(150, 150, 50, 50);	 
    } catch(SQLException e ){      
      System.out.println("Error: " + e.getMessage());
      }              
 }
 
 public static void main(String args[]) {
   SquaredPaper DrawWindow = new SquaredPaper();
 
   DrawWindow.setSize(500,500);
   DrawWindow.setResizable(false);
   DrawWindow.setLocation(200, 50);
   DrawWindow.setTitle("Pintando figuras almacenadas en la BD");
   DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   DrawWindow.setVisible(true);
 }
}