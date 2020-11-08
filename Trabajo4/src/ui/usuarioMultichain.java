package ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.BalanceAssetGeneral;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.awt.event.ActionEvent;



public class usuarioMultichain {

	private JFrame frame;
	private JButton btnPagar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usuarioMultichain window = new usuarioMultichain("");
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
	public usuarioMultichain(String usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String usuario) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		

		JLabel lblNewLabel = new JLabel("");
		
		JButton btnConsultaSaldo = new JButton("Consultar Saldo");
		btnConsultaSaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection conn = Conexion.dbConexion();
				Statement sentencia = null;
				ResultSet resultado;
				
				Integer saldo = 0 ;
				
				String query = "SELECT DIRECCION FROM USUARIOS WHERE NOMBRE_USUARIO = '"+ usuario+"'";
				try {
					sentencia = conn.createStatement();
					resultado = sentencia.executeQuery(query);
					
					while(resultado.next()) {
						String direccion = resultado.getString("DIRECCION");
						
						CommandManager commandManager = conexionMultichain.multichainConn();
						try {
							List<BalanceAssetGeneral> balance = (List<BalanceAssetGeneral>)commandManager.invoke(CommandElt.GETADDRESSBALANCES, direccion);
							
							if(balance.size() == 0) {
								lblNewLabel.setText("Su Saldo es: "+saldo);
								
							}
							else {
								
								List<BalanceAssetGeneral> consBalance = (List<BalanceAssetGeneral>) commandManager.invoke(CommandElt.GETADDRESSBALANCES, direccion);
								String regex = consBalance.get(0).toString();
								regex = regex.replaceAll("\\[|]","");
								String[] regexLista = regex.split(",");
								String cantidad = regexLista[2];
								cantidad = cantidad.replaceAll("[a-zA-Z]*\\=","");
								cantidad = cantidad.replaceAll("\\.[0-9]", "");
								cantidad = cantidad.replaceAll("\\s", "");
								saldo = Integer.parseInt(cantidad);
								lblNewLabel.setText("Su Saldo es: "+saldo);
															
							}
						} catch (MultichainException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					}
					
				}catch(SQLException e1) {
					System.out.println(e1.getMessage());
					
				}
				
				
				
				
			}
		});
		
		btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pagosMultichain pgmtc = new pagosMultichain(usuario);
				frame.setVisible(false);
			}
		});
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesi\u00F3n");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				principal_UI prp = new principal_UI();
				frame.setVisible(false);
				
			}
		});
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addComponent(btnConsultaSaldo)
							.addGap(54)
							.addComponent(btnPagar, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel))
					.addContainerGap(121, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(337, Short.MAX_VALUE)
					.addComponent(btnCerrarSesion))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnCerrarSesion)
					.addGap(62)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnConsultaSaldo)
						.addComponent(btnPagar))
					.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(43))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
