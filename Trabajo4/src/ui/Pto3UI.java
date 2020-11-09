package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class Pto3UI {

	private JFrame frame;
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pto3UI window = new Pto3UI();
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
	public Pto3UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 396, 269);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Primer Minero");
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		JLabel lblHoraYMinuto = new JLabel("Hora y minuto inicial");
		lblHoraYMinuto.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblHoraYMinuto_2 = new JLabel("Hora y minuto final");
		lblHoraYMinuto_2.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		JLabel lblSegundoMinero = new JLabel("Segundo Minero");
		lblSegundoMinero.setFont(new Font("Roboto", Font.PLAIN, 16));
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JButton btnComparacion = new JButton("<html><center>Graficar comparaci\u00F3n creciente value_usd</center></html>");
		btnComparacion.setFont(new Font("Roboto", Font.PLAIN, 16));
		btnComparacion.setBackground(new Color(0, 191, 255));
		
		btnComparacion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener todos los miners de la base de datos
				Connection conn = Conexion.dbConexion();
				Statement sentencia = null;
				ResultSet resultado;
				
				String query = "ALTER SESSION SET NLS_DATE_FORMAT='DD/MM/YYYY HH24:MI'";
				String query2 = "select bloque.miner, transaccion.value_usd  " + 
						"from transaccion " + 
						"INNER JOIN bloque ON transaccion.block_id = bloque.id " + 
						"Where bloque.miner = '"+textField_1.getText()+"' OR bloque.miner = '"+textField_3.getText()+"' AND " + 
						"TO_DATE(SUBSTR(TO_CHAR(transaccion.time),-5), 'HH24:MI') BETWEEN TO_DATE('"+textField.getText()+"', 'HH24:MI') AND TO_DATE('"+textField_2.getText()+"', 'HH24:MI')";
				
				HashMap<String, List<Double>> miners = new HashMap<String, List<Double>>();
				
				try {
				    sentencia = conn.createStatement();
				    sentencia.execute(query);
				} catch (SQLException f) {
					f.printStackTrace();
				}
				try {
				    sentencia = conn.createStatement();
				    resultado = sentencia.executeQuery(query2);
				    while(resultado.next()) {
				    	System.out.println(resultado.getString("miner")+ " "+ resultado.getDouble("value_usd"));
				    	if(miners.containsKey(resultado.getString("miner"))) {
				    		miners.get(resultado.getString("miner")).add(resultado.getDouble("value_usd"));
				    	} else {
				    		List<Double> l = new ArrayList<Double>();
				    		l.add(resultado.getDouble("value_usd"));
				    		miners.put(resultado.getString("miner"), l);
				    	}
				    };
				} catch (SQLException f) {
				    f.printStackTrace();
				}
				
//				miners.put("hodi", Arrays.asList(100500.89, 100500.89, 250500.29));
				CartesianPlaneUI cartesianPlane = new CartesianPlaneUI(miners);
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(700, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnComparacion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSegundoMinero, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblHoraYMinuto, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHoraYMinuto_2, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))))
					.addGap(18))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lblSegundoMinero, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblHoraYMinuto, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lblHoraYMinuto_2, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addComponent(btnComparacion, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(225, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		frame.getContentPane().setLayout(groupLayout);
	}
}
