package ui;
import javax.swing.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.*;
import java.util.*;


public class Conexion {

        public static Connection  dbConexion() {
        Connection conn = null;
        Statement sentencia = null;
        ResultSet resultado;

        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");

        }

        try { // Se establece la conexión con la base de datos Oracle Express
            conn = DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-ANL3RGG0:1521:xe", "prueba", "admin");
            sentencia = conn.createStatement();

        } catch (SQLException e1) {
            System.out.println("No hay conexión con la base de datos.");

        }
        return conn;
    }
    
}

//Connection conn = Conexion.dbConexion();
//Statement sentencia = null;
//ResultSet resultado;
//
//try {
//    sentencia = conn.createStatement();
//} catch (SQLException e) {
//    // TODO Auto-generated catch block
//    e.printStackTrace();
//}