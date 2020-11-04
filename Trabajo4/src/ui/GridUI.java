package ui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		JButton btnTime = new JButton("Time");
		btnTime.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnTime.setBackground(new Color(0, 250, 154));
		btnTime.setForeground(new Color(0, 0, 0));
		btnTime.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ejecutar codigo de btnTime
				
			}
		});
		
		
		JButton btnValueusd = new JButton("Value_usd");
		btnValueusd.setForeground(Color.BLACK);
		btnValueusd.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnValueusd.setBackground(new Color(0, 250, 154));
		btnValueusd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ejecutar codigo de btnValueusd
				
			}
		});
		
		
		JButton btnFeeusd = new JButton("Fee_usd");
		btnFeeusd.setForeground(Color.BLACK);
		btnFeeusd.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnFeeusd.setBackground(new Color(0, 250, 154));
		btnFeeusd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ejecutar codigo de btnFeeusd
				
			}
		});
		
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
							.addComponent(btnTime)
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
						.addComponent(btnTime)
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
            	int a = value.getX() + (value.getWidth())/2;
            	int b = value.getY() + (value.getHeight())/2;
            	System.out.println(value);
            	System.out.println(String.format("a: %d, b: %d", a, b));
            	
            	g.drawString(Integer.toString(value.getId()), a, b);
            	g.drawRect(value.getX(), value.getY(), value.getWidth(), value.getHeight());
            });
            g.setColor(Color.black);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2d.dispose();
        }

    }
}
