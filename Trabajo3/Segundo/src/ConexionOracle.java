import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.awt.*;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.List;
 
public class ConexionOracle extends JFrame {
	
	private Connection conn;
	private Statement statement;
	private ResultSet result;
	private PreparedStatement preparedStatement;
    
    public void Conexion() {
    	try{ // Se carga el driver JDBC-ODBC
    	Class.forName ("oracle.jdbc.driver.OracleDriver");
    	   } catch( Exception e ) {
    	     System.out.println("No se pudo cargar el driver JDBC");
    	 return;           
    	 }
    	
        try{ // Se establece la conexion con la base de datos Oracle Express
        	this.conn = DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-QRMV4NQS:1521:xe","nclsc","pass");
           } catch( SQLException e ) {
             System.out.println("No hay conexion con la base de datos.");
             return;
          }    
     }
    
    public List<Model> getTable(String type, String query) {
    	
    	this.Conexion();
    	    	
    	List<Model> queryResult = new ArrayList<>();
    	
    	try {    		
    		statement = conn.createStatement();
    		result = statement.executeQuery(query);
    		while (result.next()) {
    			 if (type.equals("porPais")) {
    				String nombre = result.getString(1);
     	    		int totalVentas = result.getString(2) == null ? 0 : Integer.parseInt(result.getString(2)); 
     	    		
    	    		Model dbObject = new Model(nombre, totalVentas);
    	    		queryResult.add(dbObject); 
    	    		
    	    	} else if (type.equals("porMarca")) {
    	    		String nombre = result.getString(1);
    	    		String descripcion = result.getString(2);    	    		
    	    		int totalVentas = result.getString(3) == null ? 0 : Integer.parseInt(result.getString(3)); 
    	    		
    	    		Model2 dbObject = new Model2(nombre, descripcion, totalVentas);
    	    		queryResult.add(dbObject); 
    	    		
    	    	} else {
    	    		int codigo = Integer.parseInt(result.getString(1));
    	    		String nombre = result.getString(2);
    	    		int totalVentas = result.getString(3) == null ? 0 : Integer.parseInt(result.getString(3)); 
    	    		
    	    		Model1 dbObject = new Model1(codigo, nombre, totalVentas);
    	    		queryResult.add(dbObject);
    	    		
    	    	}
    		}
    		conn.close();
    	} catch (SQLException e) {
    		System.out.println("Hubo un error");
			System.out.println(e.getMessage());
    	}
    	
    	return queryResult;
    }
}