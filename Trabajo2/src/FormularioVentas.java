import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FormularioVentas {

	private JFrame frame;
	private JTextField codVendedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormularioVentas window = new FormularioVentas();
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
	public FormularioVentas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 738, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel textVentas = new JLabel("Ingreso de datos de las ventas de un vendedor en una ciudad");
		
		JTextPane textPaneVentas = new JTextPane();
		
		JLabel textSelectCiudad = new JLabel("Seleccionar ciudad");
		
		JComboBox selectCiudad = new JComboBox();
		ConexionBD conexion = new ConexionBD();
		String[] ciudades = conexion.consultarCiudades();
		selectCiudad.setModel(new DefaultComboBoxModel(ciudades));
//		selectCiudad.setModel(new DefaultComboBoxModel(new String[]{"Cali","MEdellin"}));

		
		JLabel textCodVendedor = new JLabel("C\u00F3digo vendedor");
		
		codVendedor = new JTextField();
		codVendedor.setColumns(10);
		
		JButton btnInsertVenta = new JButton("Insertar");
		btnInsertVenta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				conexion.insertarVentas(textPaneVentas.getText(), selectCiudad.getSelectedItem().toString(), codVendedor.getText());
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textPaneVentas, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(37)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(10)
											.addComponent(selectCiudad, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(textSelectCiudad))
									.addGap(63)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(codVendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textCodVendedor)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(132)
									.addComponent(btnInsertVenta))))
						.addComponent(textVentas))
					.addContainerGap(222, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textVentas)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textPaneVentas, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textSelectCiudad)
								.addComponent(textCodVendedor))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(codVendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(selectCiudad, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addComponent(btnInsertVenta)))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
