package ui;
import java.sql.*;

public class Rectangle {
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private int transactions;

	public Rectangle(int id, int x, int y, int width, int height, int transactions) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.transactions = transactions;
	}
	
	public int getId() {
		return this.id;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getTransactions() {
		return this.transactions;
	}
	
	public String toString() {
		return String.format("Rectangle { id: %d, x: %d, y: %d, width: %d, height: %d, transactions: %d }", this.id, this.x, this.y, this.width, this.height, this.transactions);
	}
	
	public static int getNumTransacciones(int x, int y, int width, int height) {
		Connection conn = Conexion.dbConexion();
		Statement sentencia = null;
		ResultSet resultado;
		int xw = x + width;
		int yh = y + height;
		
		String query = "select COUNT(*) " + 
				"from tranAux " + 
				"WHERE x BETWEEN " + x +" AND " + xw +
				" AND y BETWEEN " + y +" AND " + yh;
		System.out.println(query);
		
		try {
		    sentencia = conn.createStatement();
		    resultado = sentencia.executeQuery(query);
		    while(resultado.next()) {
		    	System.out.println("CANTIDAD CUADRANTE = " + resultado.getInt("COUNT(*)"));
		    	return resultado.getInt("COUNT(*)");
		    };
		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return 1;
	}
	
	public static void createTableHours(String h0, String h1) {
		Connection conn = Conexion.dbConexion();
		Statement sentencia = null;
		ResultSet resultado;
		
		String query = "DROP TABLE tranAux";
		String query2 = "ALTER SESSION SET NLS_DATE_FORMAT='DD/MM/YYYY HH24:MI'";
		String query3 = "CREATE TABLE tranAux AS( " + 
				"	select * " + 
				"	from transaccion " + 
				"	WHERE TO_DATE(SUBSTR(TO_CHAR(time),-5), 'HH24:MI') BETWEEN TO_DATE('00:00', 'HH24:MI') AND TO_DATE('00:00', 'HH24:MI') " + 
				")";
		try {
		    sentencia = conn.createStatement();
		    sentencia.execute(query);
		} catch (SQLException e) {
		    System.out.println("Tabla aux no ha sido creada");
		}
		try {
		    sentencia = conn.createStatement();
		    sentencia.execute(query2);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		try {
		    sentencia = conn.createStatement();
		    sentencia.execute(query3);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
}