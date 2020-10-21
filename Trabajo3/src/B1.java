import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class B1 {
	private JFrame frame;
	private JTextField txtT1;
	private JTextField txtT2;
	private JTextField txtT3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B1 window = new B1();
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
	public B1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 764, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txtT2 = new JTextField();
		txtT2.setText("DEPTO");
		txtT2.setColumns(10);
		
		JLabel labelTabla1 = new JLabel("Tabla 1");
		
		JLabel label1M = new JLabel("1 - M");
		
		JLabel labelAtributos = new JLabel("Atributos");
		
		JTextPane txtAtrT1 = new JTextPane();
		txtAtrT1.setText("idp \nnomp");
		
		JTextPane txtAtrT2 = new JTextPane();
		txtAtrT2.setText("codd \nndep \nidp \ncapital");
		
		JLabel labelS2 = new JLabel("Consultas varias tablas");
		
		JLabel labelS1T2 = new JLabel("Consultas Tabla 2");
		
		JLabel labelTabla2 = new JLabel("Tabla 2");
		
		JTextPane txtS3 = new JTextPane();
		txtS3.setText("nomp, codd, ndep");
		
		JTextPane txtS1T2 = new JTextPane();
		txtS1T2.setText("codd, capital");
		
		txtT1 = new JTextField();
		txtT1.setText("PA\u00CDS");
		txtT1.setColumns(10);
		
		JLabel labelT3 = new JLabel("Tabla 3");
		
		txtT3 = new JTextField();
		txtT3.setText("IDIOMA");
		txtT3.setColumns(10);
		
		JTextPane txtAtrT3 = new JTextPane();
		txtAtrT3.setText("idp \nnombre \ncantidad");
		
		JLabel labelS1T3 = new JLabel("Consultas Tabla 3");
		
		JTextPane txtS1T3 = new JTextPane();
		txtS1T3.setText("cant");
		
		JLabel lblM = new JLabel("M - 1");
		
		JButton btnQ = new JButton("Generar Q'");
		btnQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Tabla t1 = new Tabla(txtT1.getText(), txtAtrT1.getText());
				Tabla t2 = new Tabla(txtT2.getText(), txtAtrT2.getText());
				Tabla t3 = new Tabla(txtT2.getText(), txtAtrT3.getText());
				Tabla.regla4(t1, t2, t3, txtS1T2.getText(), txtS3.getText());
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(105)
							.addComponent(labelT3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtS1T3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(labelS1T3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
					.addGap(26)
					.addComponent(lblM, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(35)
							.addComponent(labelTabla1)
							.addGap(207)
							.addComponent(labelTabla2))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(103)
							.addComponent(txtS1T2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtT1)
								.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelAtributos, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
							.addGap(35)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label1M)
									.addGap(38)
									.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(10)
										.addComponent(labelS1T2))
									.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))))
						.addComponent(labelS2))
					.addGap(66))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(310)
					.addComponent(btnQ)
					.addContainerGap(341, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(labelT3)
							.addComponent(labelTabla1))
						.addComponent(labelTabla2))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label1M))
						.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblM)))
					.addGap(15)
					.addComponent(labelAtributos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(labelS1T2)
								.addComponent(labelS2)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(labelS1T3)))
					.addGap(2)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtS1T2, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtS1T3, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addComponent(btnQ)
					.addGap(24))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
