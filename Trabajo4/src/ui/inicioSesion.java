package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class inicioSesion {

	private JFrame frame;
	private JTextField textUsuario;
	private JPasswordField passwordField;
	private final JButton button = new JButton("New button");
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inicioSesion window = new inicioSesion();
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
	public inicioSesion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JButton btnIniciar = new JButton("Iniciar Sesion");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuarioInsertado = textUsuario.getText().toString();
				usuarioInsertado = usuarioInsertado.replaceAll("\\s", "");
				usuarioInsertado = usuarioInsertado.toLowerCase();
				
				String contrasena = passwordField.getText().toString();
				
				Connection conn = Conexion.dbConexion();
				Statement sentencia = null;
				ResultSet resultado;
				String query = "SELECT CONTRASENA FROM USUARIOS WHERE NOMBRE_USUARIO ='"+ usuarioInsertado+"'";
				try {
					sentencia = conn.createStatement();
					resultado = sentencia.executeQuery(query);
					
					String pwdSistema = null;
					
					while(resultado.next()) {
						pwdSistema = resultado.getString("contrasena");
						if(pwdSistema.equals(contrasena)) {
							
							// Cambiar por la intefaz del punto 6
							usuarioMultichain usmr = new usuarioMultichain(usuarioInsertado);
							frame.setVisible(false);
							
							
						}else if(pwdSistema.equals(null)){
							JOptionPane.showMessageDialog(null, "Al parecer el usuario No existe en nuestra base de datos,"
									+ " Por favor revise la información o registrese en caso de ser necesario",
			                        "Error", JOptionPane.INFORMATION_MESSAGE);
							
						}else {
							
							JOptionPane.showMessageDialog(null, "Revise Los Datos Suministrados",
			                        "Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		
		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre de usuario");
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		
		passwordField = new JPasswordField();
		
		btnVolver = new JButton("Volver Atras");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				principal_UI prp = new principal_UI();
				frame.setVisible(false);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(189, Short.MAX_VALUE)
					.addComponent(btnIniciar)
					.addGap(150))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(424, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(343, Short.MAX_VALUE)
					.addComponent(btnVolver))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
						.addComponent(textUsuario, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
					.addGap(100))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addComponent(btnIniciar)
					.addGap(31)
					.addComponent(btnVolver)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
