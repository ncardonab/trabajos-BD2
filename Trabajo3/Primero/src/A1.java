import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JButton;
import java.awt.Font;

public class A1 {

	private JFrame frame;
	private JTextField txtT1;
	private JTextField txtT2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A1 window = new A1();
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
	public A1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 487, 463);
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
		
		JLabel labelS2 = new JLabel("S2");
		
		JLabel lblNewLabel = new JLabel("S1");
		
		JLabel labelTabla2 = new JLabel("Tabla 2");
		
		JTextPane txtS2 = new JTextPane();
		txtS2.setText("nomp, codd, ndep");
		
		JTextPane txtS1 = new JTextPane();
		txtS1.setText("codd, capital");
		
		txtT1 = new JTextField();
		txtT1.setText("PA\u00CDS");
		txtT1.setColumns(10);
		
		JLabel labelQp = new JLabel("");
		labelQp.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JButton btnQ = new JButton("Generar Q'");
		btnQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Tabla t1 = new Tabla(txtT1.getText(), txtAtrT1.getText());
				Tabla t2 = new Tabla(txtT2.getText(), txtAtrT2.getText());
				 String Qp = Tabla.regla1(t1, t2, txtS1.getText(), txtS2.getText());
				labelQp.setText(Qp);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(61)
					.addComponent(labelTabla1)
					.addGap(222)
					.addComponent(labelTabla2))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(202)
					.addComponent(labelAtributos, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(labelS2)
					.addGap(247)
					.addComponent(lblNewLabel))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(202)
					.addComponent(btnQ))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(txtT1)
						.addComponent(txtAtrT1, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label1M)
							.addGap(38)
							.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelQp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(txtS2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addGap(103)
							.addComponent(txtS1, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)))
					.addGap(38))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelTabla1)
						.addComponent(labelTabla2))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(label1M))
								.addComponent(txtT2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(15)
							.addComponent(labelAtributos))
						.addComponent(txtT1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtAtrT1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtAtrT2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelS2)
						.addComponent(lblNewLabel))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtS2, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtS1, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(btnQ)
					.addGap(39)
					.addComponent(labelQp)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
