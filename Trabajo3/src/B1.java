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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

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
		frame.setBounds(100, 100, 772, 475);
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
		txtS3.setText("nomp, codd, ndep \ncantidad, nomp, ndep");
		
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
		txtS1T3.setText("cantidad");
		
		JLabel lblM = new JLabel("M - 1");
		
		JLabel labelQp = new JLabel("");
		
		JButton btnQ = new JButton("Generar Q'");
		btnQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Tabla t1 = new Tabla(txtT1.getText(), txtAtrT1.getText());
				Tabla t2 = new Tabla(txtT2.getText(), txtAtrT2.getText());
				Tabla t3 = new Tabla(txtT3.getText(), txtAtrT3.getText());
				String Qp = Tabla.regla4(t1, t2, t3, txtS1T2.getText(), txtS1T3.getText(), txtS3.getText());
				labelQp.setText(Qp);
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(labelT3)
					.addGap(199)
					.addComponent(labelTabla1)
					.addGap(203)
					.addComponent(labelTabla2))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(lblM)
					.addGap(37)
					.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(label1M)
					.addGap(38)
					.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(298)
					.addComponent(labelAtributos, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(93)
					.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(103)
					.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(labelS1T3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(93)
					.addComponent(labelS2)
					.addGap(139)
					.addComponent(labelS1T2))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(325)
					.addComponent(btnQ))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelQp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(txtS1T3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(93)
							.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(103)
							.addComponent(txtS1T2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelT3)
						.addComponent(labelTabla1)
						.addComponent(labelTabla2))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblM))
						.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label1M))
						.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(labelAtributos)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelS1T3)
						.addComponent(labelS2)
						.addComponent(labelS1T2))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtS1T3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtS1T2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(btnQ)
					.addGap(30)
					.addComponent(labelQp)
					.addGap(47))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
