/*Este ejemplo fue tomado de internet y fue modificado para acceder y
 pintar figuras desde la BD*/
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
 
public class GraficoTotalVentas extends JFrame {
	String ciudad;
 public GraficoTotalVentas(String ciudad) {
	 this.ciudad = ciudad;
 }
 public void paint(Graphics g) {
   Dimension d = getSize();
   int x = d.width;
   int y = d.height;
 
   g.setColor(Color.white);
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
    String query;

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
      //Se obtienen las coordenadas de los locales 
    	
    	ResultSet resCantidad = sentencia.executeQuery("SELECT EXTRACTVALUE(locales,'count(/locales/rectangulo)') AS c FROM CITY WHERE nombre_ciudad = '"+ciudad+"'");
    	resCantidad.next();
    	int c = resCantidad.getInt("c");
    	int auxTotalVentas = 0;
    	for(int i = 1; i<=c; i++) {
    		query = String.format("SELECT EXTRACTVALUE(locales,'/locales/rectangulo[%d]/a') AS a,"
    				+ "EXTRACTVALUE(locales,'/locales/rectangulo[%d]/b') AS b,EXTRACTVALUE(locales,'/locales/rectangulo[%d]/c') AS c,"
    				+ "EXTRACTVALUE(locales,'/locales/rectangulo[%d]/d') AS d "
    				+ "FROM city WHERE nombre_ciudad = '"+ciudad+"'", i,i,i,i);
    		resultado = sentencia.executeQuery(query);
    		resultado.next();
    		int xr = resultado.getInt("a");
    		int yr = resultado.getInt("b");
    		int wr = resultado.getInt("c");
    		int hr = resultado.getInt("d");
    		g.drawRect(resultado.getInt("a"),resultado.getInt("b"),resultado.getInt("c"),resultado.getInt("d"));
        	query = "SELECT t.x AS x, t.y AS y, t.v AS v " +
        			"FROM VVCITY c, TABLE(c.ventas) t " +
        			"WHERE Ciudad = '"+ciudad+"'";
        	ResultSet resultadoVentas = sentencia.executeQuery(query);
        	int auxSumaTotal = 0;        	
        	while(resultadoVentas.next()) {
        		int xv = resultadoVentas.getInt("x");
        		int yv = resultadoVentas.getInt("y");
        		if(xv <= xr+wr && xv >= xr && yv <= yr+hr && yv >= yr) {
        			auxSumaTotal += resultadoVentas.getInt("v");
        			auxTotalVentas -= resultadoVentas.getInt("v");
        		}
        		if(i==1) {
        			auxTotalVentas += resultadoVentas.getInt("v");
        		}
        	}
        	
    		g.fillOval((2*xr+wr)/2,(2*yr+hr)/2,20,20);
    		g.drawString("$"+auxSumaTotal,(2*xr+wr)/2,(2*yr+hr)/2);
    	}
    	g.drawString("Total de ventas por fuera de los locales",150,460);
		g.drawString("$"+auxTotalVentas,230,480);
    } catch(SQLException e ){      
      System.out.println("Error: " + e.getMessage());
      }              
 }
}