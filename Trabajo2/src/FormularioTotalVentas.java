import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class FormularioTotalVentas {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormularioTotalVentas window = new FormularioTotalVentas();
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
	public FormularioTotalVentas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel textGraficarCiudad = new JLabel("Escoja ciudad a gr\u00E1ficar");
		
		JComboBox selectCiudad = new JComboBox();
		ConexionBD conexion = new ConexionBD();
		String[] ciudades = conexion.consultarCiudades();
		selectCiudad.setModel(new DefaultComboBoxModel(ciudades));
		
		JButton btnGraficar = new JButton("Graficar");
		btnGraficar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GraficoTotalVentas DrawWindow = new GraficoTotalVentas(selectCiudad.getSelectedItem().toString());
				   
				DrawWindow.setSize(500,500);
				DrawWindow.setResizable(false);
				DrawWindow.setLocation(200, 50);
				DrawWindow.setTitle("Locales de "+selectCiudad.getSelectedItem().toString());
				DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				DrawWindow.setVisible(true);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textGraficarCiudad)
								.addComponent(selectCiudad, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addComponent(btnGraficar)))
					.addContainerGap(283, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textGraficarCiudad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(selectCiudad, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnGraficar)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
