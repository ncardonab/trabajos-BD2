package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import multichain.*;
import multichain.command.*;
import multichain.object.*;
import javax.swing.JPasswordField;



public class registroUsuario {

	private JFrame frame;
	private JTextField textUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registroUsuario window = new registroUsuario();
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
	public registroUsuario() {
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
		
		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		
		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioInsertado = textUsuario.getText().toString();
				usuarioInsertado = usuarioInsertado.replaceAll("\\s", "");
				usuarioInsertado = usuarioInsertado.toLowerCase();
				
				String contrasena = passwordField.getText().toString();
				
				registrarbd(usuarioInsertado, contrasena);
				
				
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("Usuario");
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a ");
		
		JButton btnVolver = new JButton("Volver atras");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				principal_UI prp = new principal_UI();
				frame.setVisible(false);
			}
		});
		
		passwordField = new JPasswordField();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(62, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(40))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(18)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(passwordField)
						.addComponent(textUsuario, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
					.addContainerGap(157, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(179, Short.MAX_VALUE)
					.addComponent(btnRegistrar)
					.addGap(168))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(343, Short.MAX_VALUE)
					.addComponent(btnVolver))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(btnRegistrar)
					.addGap(28)
					.addComponent(btnVolver))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void registrarbd (String usuario, String password) {
		Connection conn = Conexion.dbConexion();
		Statement sentencia = null;
		ResultSet resultado;
		
		String query = "INSERT INTO USUARIOS VALUES('"+ usuario+"', '"+password+"', '')";
		
    	
    	
		try {
				
			sentencia = conn.createStatement();
			resultado = sentencia.executeQuery(query);
			CommandManager commandManager = conexionMultichain.multichainConn();
	    	
	    	String result = null; 
	    	
	    	try {
				result = (String) commandManager.invoke(CommandElt.GETNEWADDRESS);
				String query1 = " UPDATE USUARIOS SET DIRECCION ='"+result+"' WHERE NOMBRE_USUARIO = '"+usuario+"'";
				sentencia = conn.createStatement();
				resultado = sentencia.executeQuery(query1);

				commandManager.invoke(CommandElt.GRANT, result, "connect,send,receive");
				
				JOptionPane.showMessageDialog(null, "El usuario ha sido Creado Satisfactoriamente",
                        "Registro Satisfactorio", JOptionPane.INFORMATION_MESSAGE);	
				textUsuario.setText("");
				passwordField.setText("");
			
				
				
			} catch (MultichainException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	    	
	    	
	    	conn.commit();
			conn.close();    	
		   
			
		} catch (SQLException e) {
			if (e.getErrorCode()==1) {				
				
				JOptionPane.showMessageDialog(null, "El usuario ya existe en el sistema, por favor ingrese otro usuario",
                        "Error", JOptionPane.INFORMATION_MESSAGE);			
			}
			
			
		}
		
		
		
		
	}
}
