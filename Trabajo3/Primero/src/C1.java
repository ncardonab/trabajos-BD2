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

public class C1 {


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
					C1 window = new C1();
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
	public C1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 772, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel labelTabla1 = new JLabel("Tabla 1");
		
		JLabel label1M = new JLabel("1 - M");
		
		JLabel labelAtributos = new JLabel("Atributos");
		
		JLabel labelS3 = new JLabel("Consultas varias tablas");
		
		JLabel labelS1 = new JLabel("Consultas una tabla");
		
		JLabel labelTabla2 = new JLabel("Tabla 2");
		
		JTextPane txtS3 = new JTextPane();
		txtS3.setText("nomc, codd, capital \nnomp, codd");
		
		JTextPane txtS1 = new JTextPane();
		txtS1.setText("codd, capital \nnomc, poblacion, codd");
		
		JLabel labelT3 = new JLabel("Tabla 3");
		
		JLabel lblM = new JLabel("1 - M");
		
		txtT1 = new JTextField();
		txtT1.setText("PA\u00CDS");
		txtT1.setColumns(10);
		
		txtT2 = new JTextField();
		txtT2.setText("DEPTO");
		txtT2.setColumns(10);
		
		txtT3 = new JTextField();
		txtT3.setText("CIUDAD");
		txtT3.setColumns(10);
		
		JTextPane txtAtrT1 = new JTextPane();
		txtAtrT1.setText("idp \nnomp");
		
		JTextPane txtAtrT2 = new JTextPane();
		txtAtrT2.setText("codd \nndep \nidp \ncapital");
		
		JTextPane txtAtrT3 = new JTextPane();
		txtAtrT3.setText("codd \nidc \nnomc \npoblacion");
		JButton btnQ = new JButton("Generar Q'");
		
		JLabel labelQp = new JLabel("");
		
		btnQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Tabla t1 = new Tabla(txtT1.getText(), txtAtrT1.getText());
				Tabla t2 = new Tabla(txtT2.getText(), txtAtrT2.getText());
				Tabla t3 = new Tabla(txtT3.getText(), txtAtrT3.getText());
				String Qp = Tabla.regla5(t1, t2, t3, txtS1.getText(), txtS3.getText());
				labelQp.setText(Qp);
			}
		});
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(labelTabla1)
					.addGap(199)
					.addComponent(labelTabla2)
					.addGap(203)
					.addComponent(labelT3))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(lblM)
					.addGap(37)
					.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(label1M)
					.addGap(38)
					.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(298)
					.addComponent(labelAtributos, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(298)
					.addComponent(labelS3)
					.addGap(134)
					.addComponent(labelS1))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(325)
					.addComponent(btnQ))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(labelQp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGap(242)
								.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addGap(103)
								.addComponent(txtS1, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(93)
							.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(103)
							.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addGap(66))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(labelTabla1))
						.addComponent(labelTabla2)
						.addComponent(labelT3))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblM))
						.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(label1M))
						.addComponent(txtT3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(labelAtributos)
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAtrT3, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelS3)
						.addComponent(labelS1))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtS3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtS1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(btnQ)
					.addGap(29)
					.addComponent(labelQp)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

}
