import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.List;
 
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
	 System.out.println("Entró a insertar");
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
				 "<c>" + coordenadas[2] + "</c>"+
				 "<d>" + coordenadas[3] + "</d>";
		 xml += "</rectangulo>";
	 }
	 xml += "</locales>";
	 xml = xml.replaceAll("\\r", "");
	 System.out.println("EL XML ES: "+ xml);
	 String queryInsertCity = "INSERT INTO CITY (Nombre_ciudad,Locales) VALUES ('"+ciudad+"',XMLTYPE('" + xml + "'))";

	 try {
		 PreparedStatement preparedStatement = conn.prepareStatement(queryInsertCity);
		 preparedStatement.executeUpdate();
         System.out.println("Se ha insertado la ciudad exitosamente!");
         conn.close();
	 } catch(SQLException e) {
         System.out.println(e.getMessage());
	 } 
 }
 
 public String[] consultarCiudades() {
	 this.Conexion();
	 List<String> ciudadesAux = new ArrayList<>();
	 try {
		 sentencia = conn.createStatement();
		 resultado = sentencia.executeQuery("SELECT Nombre_ciudad FROM CITY");
		 while(resultado.next()){
			 ciudadesAux.add(resultado.getString("Nombre_ciudad"));
		 }
		 conn.close();
	 } catch(SQLException e) {
		 System.out.println(e.getMessage());
	 }
	 String[] ciudades = ciudadesAux.toArray(new String[0]);
	return ciudades;
 }
 
 public void insertarVentas(String ventas, String Ciudad, String codVendedor){
//	 System.out.println("Entró a insertar");
	 this.Conexion();
	 ventas = ventas.replaceAll(" ", "");
	 ventas = ventas.replaceAll("\\r", "");
	 String[] lista_ventas = ventas.split("\n");
	 for(String venta:lista_ventas) {
		 Boolean aux = true;
		 String query = "INSERT INTO VVCITY VALUES(" + codVendedor + ", '" + Ciudad+"', nest_venta("+"coor_tip("+venta+")))";
//		 System.out.println("Query = "+query);
		 
		 try {
			 PreparedStatement preparedStatement = conn.prepareStatement(query);
			 preparedStatement.executeUpdate();
		 } catch (SQLIntegrityConstraintViolationException e) {
			 try {
				 sentencia = conn.createStatement();
				 query = "SELECT codigoVendedor, Ciudad, t.* FROM VVCITY c, TABLE(c.ventas) t "
							+ "WHERE codigoVendedor = "+codVendedor+ "AND Ciudad = '"+Ciudad+"'";
				 resultado = sentencia.executeQuery(query);	
				 while(resultado.next()) {
					 String[] coord = venta.split(",");
//					 System.out.println("RESULTADO X = " + resultado.getString("X") + "COOR X = "+ coord[0]);
//					 System.out.println("RESULTADO Y = " + resultado.getString("Y") + "COOR Y = "+ coord[1]);
					 if(resultado.getString("X").equals(coord[0])  && resultado.getString("Y").equals(coord[1])) {
						 query = "UPDATE TABLE(SELECT ventas FROM VVCITY " + 
						 		  "WHERE codigoVendedor = " +codVendedor+ " AND Ciudad = '"+Ciudad+"')"
						 		  + "SET v = v + "+coord[2]+" WHERE x = "+coord[0]+" AND y = "+coord[1];
//						 System.out.println("Se sumó el resultado\n"+query);
						 resultado = sentencia.executeQuery(query);
						 aux = false;
						 break;
					 }
				 }
				 if(aux) {
					 query = "INSERT INTO TABLE( SELECT ventas FROM VVCITY "+
							 "WHERE codigoVendedor =" +codVendedor+ "AND Ciudad = '"+Ciudad+"')"+
							 "VALUES ("+venta+")";
//					 System.out.println("Se agregó el resultado\n"+query);
					 resultado = sentencia.executeQuery(query);
					 aux = false;
				 }
			 }catch(SQLException f){
				 System.out.println(f.getMessage());
			 }
			 
		 }catch (SQLException e) {
			 System.out.println(e.getMessage());
		 }
		 
	 }

 }
}