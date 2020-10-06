import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class FormularioInicial extends Object {

	private JFrame frame;
	private JTextField textCiudad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormularioInicial window = new FormularioInicial();
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
	public FormularioInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		
		JTextPane textLocales = new JTextPane();
		
		textCiudad = new JTextField();
		textCiudad.setColumns(10);
		
		JButton btnInsert = new JButton("Insertar");
		btnInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// Conexi�n con la base de datos
				ConexionBD conexion = new ConexionBD();
				
				// Obteniendo los datos del formulario
				String ciudad = textCiudad.getText();
				String locales = textLocales.getText();
				locales = locales.replaceAll(" ", "").replaceAll("\\r", "");
				
				// Dividiendo por ENTER
				String[] loc = locales.split("\n");
				
				// Trayendo los locales por ciudad
				int[][] localByCity = conexion.getLocalByCity(ciudad);
				int localCount = localByCity.length;
				
				if(localCount == 0) {
					conexion.insertarCiudad(ciudad, loc[0]);
				}else {
					outerloop: for (String l:loc) {
						String[] coord1 = l.split(",");
						Rectangle rect1 = new Rectangle(Integer.parseInt(coord1[0]), 
														Integer.parseInt(coord1[1]), 
														Integer.parseInt(coord1[2]), 
														Integer.parseInt(coord1[3]));
						
						
						for (int i = 0; i < localCount; i++) {
							int[] coord2 = localByCity[i];
							Rectangle rect2 = new Rectangle(coord2[0], 
															coord2[1], 
															coord2[2], 
															coord2[3]);
							
							// Si no hay intersecci�n, inserta
							if (!Rectangle.intersection(rect1, rect2)) {
								conexion.insertarCiudad(ciudad, locales);
								// Locales insertados!!
								
							} else {
								System.out.println("No se puede ingresar este local pues se intersecta con otro que existe en la ciudad!");
								break outerloop;
								// Locales no insertados :(
							}
						}
					}
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Ingreso de datos de los locales de una ciudad");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewLabel_1 = new JLabel("Nombre de la ciudad");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		textLocales.setForeground(new Color(0, 0, 0));
		textLocales.setBackground(Color.LIGHT_GRAY);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(41, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(41)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textLocales, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(68)
									.addComponent(textCiudad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(59)
									.addComponent(lblNewLabel_1)))
							.addGap(104))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnInsert)
							.addGap(256))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(textLocales, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(37)
							.addComponent(btnInsert))
						.addComponent(textCiudad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(184, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setBackground(Color.GRAY);
		frame.setBounds(100, 100, 654, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}