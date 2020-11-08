package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import multichain.command.CommandElt;
import multichain.command.CommandManager;
import multichain.command.MultichainException;
import multichain.object.BalanceAssetGeneral;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.awt.event.ActionEvent;

public class pagosMultichain {

	private JFrame frmPagostransferencias;
	private JTextField textUsuarioRecibe;
	private JTextField textPago;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pagosMultichain window = new pagosMultichain("");
					window.frmPagostransferencias.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public pagosMultichain(String usuario) {
		initialize(usuario);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String usuario) {
		frmPagostransferencias = new JFrame();
		frmPagostransferencias.setTitle("Pagos/Transferencias");
		frmPagostransferencias.setType(Type.UTILITY);
		frmPagostransferencias.setBounds(100, 100, 450, 300);
		frmPagostransferencias.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPagostransferencias.setVisible(true);
		
		textUsuarioRecibe = new JTextField();
		textUsuarioRecibe.setColumns(10);
		
		textPago = new JTextField();
		textPago.setColumns(10);
		
		JButton btnTransferir = new JButton("Transferir");
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String valorPago = textPago.getText();
					Integer pago = Integer.parseInt(valorPago);
					
					String usuarioDestino = textUsuarioRecibe.getText();
					usuarioDestino = usuarioDestino.replaceAll("\\s", "");
					usuarioDestino = usuarioDestino.toLowerCase();
					
					Connection conn = Conexion.dbConexion();
					Statement sentencia = null;
					Statement sentencia1= null;
					Statement sentencia2= null;
					ResultSet resultado,resultado1, resultadoPrueba;
					
					String query  = "SELECT DIRECCION FROM USUARIOS WHERE NOMBRE_USUARIO = '"+ usuario+"'";
					try {
						sentencia = conn.createStatement();
						resultado = sentencia.executeQuery(query);
						String direccionSalida = null;
						while(resultado.next()) {
							direccionSalida = resultado.getString("DIRECCION");							
							
						}
						String query1  = "SELECT DIRECCION FROM USUARIOS WHERE NOMBRE_USUARIO = '"+ usuarioDestino+"'";
						sentencia1 = conn.createStatement();
						sentencia2 = conn.createStatement();
						resultado1 = sentencia1.executeQuery(query1);
						resultadoPrueba = sentencia2.executeQuery(query1);
						
						if(resultadoPrueba.next()) {
							
							while(resultado1.next()) {
								String direccionLlegada = resultado1.getString("DIRECCION");	
								CommandManager commandManager = conexionMultichain.multichainConn();
								
								try {
									String message = (String)commandManager.invoke(CommandElt.SENDASSETFROM, direccionSalida, direccionLlegada,"bdcoin",  pago);
									JOptionPane.showMessageDialog(null, "Pago/Transferencia Realizado exitosamente",
					                        "Exito", JOptionPane.INFORMATION_MESSAGE);
									textUsuarioRecibe.setText("");
									textPago.setText("");
											
								}catch (MultichainException e1) {
									
									JOptionPane.showMessageDialog(null, "El Pago/Transferencia no se pudo realizar\n"
											+ "Revise si su saldo es apto para realizar la transacción",
					                        "Error", JOptionPane.INFORMATION_MESSAGE);
									System.out.println(e1.getMessage());
									
								}
								
							}
							
							
						}
						else {
							JOptionPane.showMessageDialog(null, "El Usuario ingresado no existe en el Sistema",
			                        "Error", JOptionPane.INFORMATION_MESSAGE);
							
						}
					
						
					}catch(SQLException e1) {
						System.out.println(e1.getMessage());
						
					}
					
					
					
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "El valor de transferencia ingresado no es valido",
	                        "Error", JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				
					
			}
		});
		
		JLabel lblNewLabel = new JLabel("New label");
		
		JLabel lblNewLabel_1 = new JLabel("Usuario Destino");
		
		JLabel lblNewLabel_1_1 = new JLabel("Valor Transferencia ");
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				principal_UI prp = new principal_UI();
				frmPagostransferencias.setVisible(false);
			}
		});
		
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarioMultichain usmr = new usuarioMultichain(usuario);
				frmPagostransferencias.setVisible(false);
			}
		});
		
		
		
		GroupLayout groupLayout = new GroupLayout(frmPagostransferencias.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(178, Short.MAX_VALUE)
					.addComponent(btnTransferir)
					.addGap(167))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_1_1))
							.addGap(70)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textUsuarioRecibe)
								.addComponent(textPago, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
							.addComponent(btnCerrarSesion))))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(345, Short.MAX_VALUE)
					.addComponent(btnVolver))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(btnCerrarSesion))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textUsuarioRecibe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1))
					.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
					.addComponent(btnTransferir)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnVolver)
					.addGap(5))
		);
		frmPagostransferencias.getContentPane().setLayout(groupLayout);
	}
}
