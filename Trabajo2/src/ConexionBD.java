import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
    	
        try{ // Se establece la conexion con la base de datos Oracle Express
        	this.conn = DriverManager.getConnection  
        			("jdbc:oracle:thin:@DESKTOP-LDB790I:1521:xe","di","1537");
           } catch( SQLException e ) {
             System.out.println("No hay conexion con la base de datos.");
             return;
          }    
     }
    
	public void insertarCiudad(String ciudad, String locales) {
		// Estableciendo la conexion con la base de datos
		this.Conexion();

		// Quitando el carriage return character y los espacios
		locales = locales.replaceAll(" ", "").replaceAll("\\r", "");

		// Parseando los locales a string XML
		String xml = XMLClass.parseToXML(locales);

		// Creando query para insercion
		String queryInsertCity = "INSERT INTO CITY (Nombre_ciudad,Locales) VALUES ('" + ciudad + "',XMLTYPE('" + xml
				+ "'))";

		// Si salta un excepcion de que se viola una restriccion de clave unica
		// actualiza la ciudad, si no se inserta normalmente
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(queryInsertCity);
			preparedStatement.executeUpdate();
			System.out.println("Record is inserted into DBUSER table!\n");
		} catch (SQLIntegrityConstraintViolationException err) {
			this.updateCity(ciudad, locales);
		} catch (SQLException e) {
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
	
	public void updateCity(String city, String premises) {
		// Estableciendo conexion con la base de datos
		this.Conexion();

		// Obteniendo la cantidad de locales en una ciudad
		int rectCount = this.getLocalsCount(city);

		int[][] oldPremises = this.getLocalByCity(city);
		int[][] newPremises = new int[oldPremises.length + 1][4];

		int[] newPremise = new int[4];
		String[] strPremises = premises.split(",");

		// Parsea todos los valores a int de textLocales.getText()
		for (int i = 0; i < newPremise.length; i++) {
			newPremise[i] = Integer.parseInt(strPremises[i]);
		}

		// Mapea los viejos locales en los nuevos para insertar el nuevo
		for (int i = 0; i < rectCount; i++) {
			newPremises[i] = oldPremises[i];
		}

		// inserta el nuevo element a los locales que ya estaban
		newPremises[rectCount] = newPremise;

		// Parseando el nuevo array de locales a XML
		String newXML = XMLClass.parseMatrixToXML(newPremises);

		String queryUpdateCity = "UPDATE city" + " SET locales = XMLTYPE('" + newXML + "') WHERE nombre_ciudad = '"
				+ city + "'";

		// Si hay un error de tipo SQLException lo imprime, de lo contrario actualiza la
		// fila de la tabla
		try {
			System.out.println("Asegurate de hacer commit; en tu tabla para que los cambios se vean reflejados");
			PreparedStatement preparedStatement = conn.prepareStatement(queryUpdateCity);
			preparedStatement.executeQuery();
			System.out.println("Record is updated into DBUSER table!\n");
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void insertarVentas(String ventas, String Ciudad, String codVendedor){
		 this.Conexion();
		 ventas = ventas.replaceAll(" ", "");
		 ventas = ventas.replaceAll("\\r", "");
		 String[] lista_ventas = ventas.split("\n");
		 for(String venta:lista_ventas) {
			 Boolean aux = true;
			 String query = "INSERT INTO VVCITY VALUES(" + codVendedor + ", '" + Ciudad+"', nest_venta("+"coor_tip("+venta+")))";
			 
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
						 if(resultado.getString("X").equals(coord[0])  && resultado.getString("Y").equals(coord[1])) {
							 query = "UPDATE TABLE(SELECT ventas FROM VVCITY " + 
							 		  "WHERE codigoVendedor = " +codVendedor+ " AND Ciudad = '"+Ciudad+"')"
							 		  + "SET v = v + "+coord[2]+" WHERE x = "+coord[0]+" AND y = "+coord[1];
							 resultado = sentencia.executeQuery(query);
							 aux = false;
							 break;
						 }
					 }
					 if(aux) {
						 query = "INSERT INTO TABLE( SELECT ventas FROM VVCITY "+
								 "WHERE codigoVendedor =" +codVendedor+ "AND Ciudad = '"+Ciudad+"')"+
								 "VALUES ("+venta+")";
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
	
	public int getLocalsCount(String city) {
		int rectCount = 0;

		// Si hay un error de tipo SQLException lo imprime, de lo contrario obtiene el
		// número de rectangulos que hay en una ciudad, básicamente el número de locales
		try {
			sentencia = conn.createStatement();
			String query = "SELECT EXTRACTVALUE(locales,'count(/locales/rectangulo)') AS count FROM CITY WHERE nombre_ciudad = '"
					+ city + "'";
			ResultSet resultado = sentencia.executeQuery(query);
			while(resultado.next()) {
				rectCount = resultado.getInt("count");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException err) {
			System.out.println(err.getMessage());
		}
		return rectCount;
	}
	
	public int[][] getLocalByCity(String city) {
		// Estableciendo conexión con la base de datos
		this.Conexion();
		
		String queryGetLocals = "";

		// Obteniendo la cantidad de locales en una ciudad
		int rectCount = this.getLocalsCount(city);

		int[][] locales = new int[rectCount][4];

		for (int i = 1; i <= rectCount; i++) {

			// Sentencia EXTRACTVALUE para obtener los atributos del rectangulo como a, b, c
			// y d
			queryGetLocals = "SELECT EXTRACTVALUE(locales, '/locales/rectangulo[" + i + "]/a') AS a,"
					+ " EXTRACTVALUE(locales, '/locales/rectangulo[" + i + "]/b') AS b,"
					+ " EXTRACTVALUE(locales, '/locales/rectangulo[" + i + "]/c') AS c,"
					+ " EXTRACTVALUE(locales, '/locales/rectangulo[" + i + "]/d') AS d" + " FROM city"
					+ " WHERE nombre_ciudad = '" + city + "'";

			// Si hay un error de tipo SQLException lo imprime, de lo contrario obtiene los
			// locales
			try {
				sentencia = conn.createStatement();
				ResultSet resultado = sentencia.executeQuery(queryGetLocals);
				while(resultado.next()) {
					locales[i - 1][0] = resultado.getInt("a");
					locales[i - 1][1] = resultado.getInt("b");
					locales[i - 1][2] = resultado.getInt("c");
					locales[i - 1][3] = resultado.getInt("d");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				break;
			}
		}

		return locales;
	}
}