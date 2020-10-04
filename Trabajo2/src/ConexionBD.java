/*Este ejemplo fue tomado de internet y fue modificado para acceder y
 pintar figuras desde la BD*/
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
import java.util.*;
 
public class ConexionBD extends JFrame {
	
    Connection conn;
    Statement sentencia;
    ResultSet resultado;
    PreparedStatement preparedStatement;
    
 public void Conexion() {
	try{ // Se carga el driver JDBC-ODBC
	Class.forName ("oracle.jdbc.driver.OracleDriver");
	   } catch( Exception e ) {
	     System.out.println("No se pudo cargar el driver JDBC");
	 return;           
	 }
	
    try{ // Se establece la conexión con la base de datos Oracle Express
        this.conn = DriverManager.getConnection  
        ("jdbc:oracle:thin:@DESKTOP-LDB790I:1521:xe","di","1537");
       } catch( SQLException e ) {
         System.out.println("No hay conexión con la base de datos.");
         return;
      }    
 }

 public Connection getConnection() {
     return conn; // Retorno el objeto Connection
 }
 
 public void insertarCiudad(String ciudad, String locales) {
	 this.Conexion();
	 System.out.println(locales);
	 locales = locales.replaceAll(" ", "");
	 String xml = "<locales>";
	 
	 String[] local = locales.split("\n");
	 for (String l:local) {
		 xml += "<rectangulo>";
		 String[] coordenadas = l.split(",");
		 xml += "<a>" + coordenadas[0] + "</a>"+
				 "<b>" + coordenadas[1] + "</b>"+
				 "<c>" + coordenadas[1] + "</c>"+
				 "<d>" + coordenadas[1] + "</d>";
		 xml += "</rectangulo>";
	 }
	 xml += "</locales>";
	 System.out.println("EL XML ES: "+ xml);
	 String queryInsertCity = "INSERT INTO CITY (Nombre_ciudad,Locales) VALUES ('"+ciudad+"',XMLTYPE('" + xml + "'))";

	 try {
		 PreparedStatement preparedStatement = conn.prepareStatement(queryInsertCity);
		 preparedStatement.executeUpdate();
         System.out.println("Record is inserted into DBUSER table!");
         conn.close();
	 } catch(SQLException e) {
         System.out.println(e.getMessage());
	 } 
 }
 
}