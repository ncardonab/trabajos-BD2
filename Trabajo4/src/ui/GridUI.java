package ui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;

public class GridUI {

	private JFrame frame;
	private JTextField textField;

	private HashMap<Integer, Rectangle> rects;
	private List<int[]> colors;
	private int[][] scales;
	
	/**
	 * Create the application.
	 */
	public GridUI() {
		initialize();
	}
    public GridUI(HashMap<Integer, Rectangle> rects, List<int[]> colors, int[][] scales) {
    	this.rects = rects;
    	this.colors = colors;
    	this.scales = scales;
    	initialize();
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GridUI window = new GridUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 592, 477);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.add(new Grid());
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(0, 250, 154));
		panel_1.setBorder(new LineBorder(new Color(0, 250, 154), 2, true));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)))
		);
		
		JLabel lblNewLabel = new JLabel("Ingrese n\u00FAmero de cuadr\u00EDcula:");
		
		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setFont(new Font("Roboto", Font.PLAIN, 12));
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ordenar por:");
		
		JButton btnNewButton = new JButton("Time");
		btnNewButton.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(0, 250, 154));
		btnNewButton.setForeground(new Color(0, 0, 0));
		
		JButton btnValueusd = new JButton("Value_usd");
		btnValueusd.setForeground(Color.BLACK);
		btnValueusd.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnValueusd.setBackground(new Color(0, 250, 154));
		
		JButton btnFeeusd = new JButton("Fee_usd");
		btnFeeusd.setForeground(Color.BLACK);
		btnFeeusd.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnFeeusd.setBackground(new Color(0, 250, 154));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(btnValueusd))
						.addComponent(btnFeeusd))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnValueusd))
					.addGap(18)
					.addComponent(btnFeeusd)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}

	public class Grid extends JPanel {
		
        public Grid() {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            rects.forEach((key, value) -> {
            	Color color = new Color(0, 250, 154);
            	
            	for (int i = 0; i < scales.length; i++) {
//            		System.out.println(String.format("%d <= %d <= %d", scales[i][0], value.getTransactions(), scales[i][1]));
            		if (value.getTransactions() >= scales[i][0] && value.getTransactions() <= scales[i][1]) {
            			color = new Color(colors.get(i)[0], colors.get(i)[1], colors.get(i)[2]);            			
            		}
            	}
            	
            	g.setColor(color);
            	g.fillRect(value.getX(), value.getY(), value.getWidth(), value.getHeight());            	
            	g.setColor(Color.black);
//            	g.drawString(Integer.toBinaryString(value.getId()), (value.getX()+value.getY())/2, (value.getWidth()+value.getHeight())/2);
            	g.drawRect(value.getX(), value.getY(), value.getWidth(), value.getHeight());
            });
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2d.dispose();
        }

    }
}
