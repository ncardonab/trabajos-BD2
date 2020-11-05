package ui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

public class TableUI {

	private JFrame frame;
	private static JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableUI window = new TableUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TableUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 970, 481);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"block_id", "x", "y", "sender", "recipient", "value_usd", "fee_usd", "time"},
			},
			new String[] {
				"block_id", "x", "y", "sender", "recipient", "value_usd", "fee_usd", "time"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(67);
		table.getColumnModel().getColumn(1).setPreferredWidth(25);
		table.getColumnModel().getColumn(2).setPreferredWidth(25);
		table.getColumnModel().getColumn(3).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setMinWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 421, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
	
	public void getQuery(int x, int y, int width, int height, String order) {
		Connection conn = Conexion.dbConexion();
		Statement sentencia = null;
		ResultSet resultado;
		
		int xw = x + width;
		int yh = y + height;
		
		x = x != 0 ? x + 1 : x;
		y = y != 0 ? y + 1 : y;
		
		String query = "SELECT block_id, x, y, sender, recipient, value_usd, fee_usd, time " + 
				"FROM tranAux " + 
				"WHERE x BETWEEN " + x +" AND " + xw +
				" AND y BETWEEN " + y +" AND " + yh +
				" ORDER BY " + order;
		
		try {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
		    sentencia = conn.createStatement();
		    resultado = sentencia.executeQuery(query);
		    while(resultado.next()) {
		    	model.addRow(new Object[]{resultado.getInt("block_id"), resultado.getString("x"), resultado.getInt("y"), resultado.getString("sender"), resultado.getString("recipient"), resultado.getFloat("value_usd"), resultado.getFloat("fee_usd"), resultado.getString("time")});
		    };
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
}
