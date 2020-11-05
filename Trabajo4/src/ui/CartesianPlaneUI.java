package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class CartesianPlaneUI {

	private JFrame frame;
	private HashMap<String, List<Double>> miners;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartesianPlaneUI window = new CartesianPlaneUI();
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
	public CartesianPlaneUI() {
		initialize();
	}
	
	public CartesianPlaneUI(HashMap<String, List<Double>> miners) {
		initialize();
		this.miners = miners;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Testing");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setForeground(Color.WHITE);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        contentPanel.setBorder(padding);

        frame.setContentPane(contentPanel);
        frame.getContentPane().add(new CartesianPlane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	public class CartesianPlane extends JPanel {
		
        public CartesianPlane() {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            g.setColor(Color.black);
            g.drawLine(0, 0, 0, getHeight());
            g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);  
            g.drawPolygon(new int[]{0, 0, 4}, new int[]{0, 10, 10}, 3);
            g.drawPolygon(new int[]{getWidth() - 6, getWidth() - 6, getWidth()}, new int[]{getHeight() - 6, getHeight(), getHeight()}, 3);
//            g.fillPolygon(new int[]{getHeight() - 16, getHeight(), getHeight()}, new int[]{getWidth() - 16, getWidth() - 16, getWidth()}, 3);
            
            miners.forEach((key, value) -> {
            	List<Double> earned = value;
            	Color color = new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
            	
            	// Chequeando si hay valores repetidos para un mismo miner
            	Set<Double> set = new HashSet<Double>(earned);
            	for (Double e: set) {
            		
            		int repeated = Collections.frequency(earned, e);
            		String label = repeated > 1 ? String.format("(%f, %d)", Math.round(e * 100.0) / 100.0, repeated) : String.format("(%f)", Math.round(e * 100.0) / 100.0);
            		
            		Integer money = (int)(e / 800);
            		g.setColor(color);
            		g.drawString(label, money, getHeight() - money - 2);
                    g.fillOval(money, getHeight() - money, 8, 8);
            		
//            		System.out.println(e + ": " + Collections.frequency(earned, e));
            	}
            });
            g2d.dispose();
        }

    }

}
