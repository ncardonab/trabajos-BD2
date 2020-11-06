package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class principal_UI {

	private JFrame frmMultichaion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					principal_UI window = new principal_UI();
					window.frmMultichaion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public principal_UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMultichaion = new JFrame();
		frmMultichaion.setTitle("Multichain");
		frmMultichaion.setBounds(100, 100, 450, 300);
		frmMultichaion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMultichaion.setVisible(true);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesi\u00F3n ");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inicioSesion iniUI = new inicioSesion();
				frmMultichaion.setVisible(false);
			}
		});
		
		JButton btnRegistro = new JButton("Registrarse");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object regisUI = new registroUsuario();
				frmMultichaion.setVisible(false);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmMultichaion.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(160)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnRegistro, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnIniciarSesion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(175, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addComponent(btnIniciarSesion)
					.addGap(56)
					.addComponent(btnRegistro)
					.addContainerGap(99, Short.MAX_VALUE))
		);
		frmMultichaion.getContentPane().setLayout(groupLayout);
	}
}
