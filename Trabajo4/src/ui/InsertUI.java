package ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import java.util.regex.*;

import java.util.*;

public class InsertUI {

	private JFrame frame;
	private JTextField hrMinInicial;
	private JTextField hrMinFinal;
	private JTextField gridSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertUI window = new InsertUI();
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
	public InsertUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		
		JTextArea txtrHoraYMinuto_1 = new JTextArea();
		txtrHoraYMinuto_1.setEditable(false);
		txtrHoraYMinuto_1.setWrapStyleWord(true);
		txtrHoraYMinuto_1.setText("Hora y minuto final:");
		txtrHoraYMinuto_1.setLineWrap(true);
		txtrHoraYMinuto_1.setFont(new Font("Roboto", Font.PLAIN, 13));
		
		hrMinFinal = new JTextField();
		hrMinFinal.setBackground(new Color(245, 245, 220));
		hrMinFinal.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 225, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrHoraYMinuto_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(hrMinFinal, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 40, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrHoraYMinuto_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hrMinFinal, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		
		JTextArea txtrTamaoLadoCuadrcula = new JTextArea();
		txtrTamaoLadoCuadrcula.setEditable(false);
		txtrTamaoLadoCuadrcula.setWrapStyleWord(true);
		txtrTamaoLadoCuadrcula.setText("Tama\u00F1o lado cuadr\u00EDcula:");
		txtrTamaoLadoCuadrcula.setLineWrap(true);
		txtrTamaoLadoCuadrcula.setFont(new Font("Roboto", Font.PLAIN, 13));
		
		gridSize = new JTextField();
		gridSize.setBackground(new Color(245, 245, 220));
		gridSize.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 225, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrTamaoLadoCuadrcula, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(gridSize, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 40, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrTamaoLadoCuadrcula, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(gridSize, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBorder(new LineBorder(new Color(176, 196, 222)));
		panel_3.setForeground(new Color(176, 224, 230));
		
		JButton btnNewButton = new JButton("Ver mapa por nro de transacciones");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(119, 136, 153));
		btnNewButton.setFont(new Font("Roboto", Font.PLAIN, 15));
		btnNewButton.setText("<html><center>"+"Ver mapa por nro de transacciones"+"</center></html>");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addGap(117))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		
		JTextArea txtrConfigurarEscala = new JTextArea();
		txtrConfigurarEscala.setTabSize(5);
		txtrConfigurarEscala.setEditable(false);
		txtrConfigurarEscala.setFont(new Font("Roboto Light", Font.PLAIN, 14));
		txtrConfigurarEscala.setLineWrap(true);
		txtrConfigurarEscala.setText("Configurar escala para nro de transacciones:");
		
		JTextArea scalesConfiguration = new JTextArea();
		scalesConfiguration.setBackground(new Color(245, 245, 220));
		scalesConfiguration.setLineWrap(true);
		scalesConfiguration.setWrapStyleWord(true);
		scalesConfiguration.setFont(new Font("Roboto", Font.PLAIN, 13));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(10)
							.addComponent(scalesConfiguration, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addContainerGap()
							.addComponent(txtrConfigurarEscala, GroupLayout.PREFERRED_SIZE, 163, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(txtrConfigurarEscala, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scalesConfiguration, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		
		JTextArea txtrHoraYMinuto = new JTextArea();
		txtrHoraYMinuto.setEditable(false);
		txtrHoraYMinuto.setWrapStyleWord(true);
		txtrHoraYMinuto.setText("Hora y minuto inicial:");
		txtrHoraYMinuto.setLineWrap(true);
		txtrHoraYMinuto.setFont(new Font("Roboto", Font.PLAIN, 13));
		
		hrMinInicial = new JTextField();
		hrMinInicial.setBackground(new Color(245, 245, 220));
		hrMinInicial.setColumns(10);
		
		// Aparte de toda la UI (Escuchador de eventos)
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Carga de archivos de la base de datos		
				
				// Formateo adecuado para los campos de tiempo, tamano cuadricula y configuracion de la escala de colores
				String a = hrMinInicial.getText();
				String b = hrMinFinal.getText();
				int gridSideSize = Integer.parseInt(gridSize.getText());
				String scaleConfStr = scalesConfiguration.getText();
				
				// Colores y configuracion de la escala
				String[] scaleConfig = scaleConfStr.split("\n");
				int colorsAmount = Integer.parseInt(scaleConfig[0]);
				String[] intervalsStr = Arrays.copyOfRange(scaleConfig, 1, scaleConfig.length);
				
				int[][] timeLimits = parseStringsToHour(a, b);
				int[][] scales = parseStringToScales(intervalsStr);	
				
				// Verificando si todos lo campos son correctos
				String warningMessage = "";
				if (!checkHourFormat(hrMinInicial.getText())) {
					warningMessage += "Asegurate de seguir el siguiente formato HH:MM \n";
				}
				
				if (!checkHourFormat(hrMinFinal.getText())) {
					warningMessage += "Asegurate de seguir el siguiente formato HH:MM \n";
				}
				
				if (checkExclusions(scales)) {
					warningMessage += "Asegurate de que los valores de la escala de colores sean excluyentes\n";
				} 
				
				if (!checkMultipleOf(5, gridSideSize)) {
					warningMessage += "Comprueba que el tamaño de cuadrícula sea multiplo de 5\n";
				}
				
				if (colorsAmount != intervalsStr.length) {
					warningMessage += "La cantidad de rangos debe ser la misma que la cantidad que estás especificada";
				}
				
				if (!warningMessage.isEmpty()) {
					JOptionPane.showMessageDialog(null, warningMessage);
				} else {
					// Todo está bien, podemos proceder
					
					// Lista de colores
					List<int[]> colors = new ArrayList<int[]>();
					colors.add(new int[]{245, 230, 232});
					colors.add(new int[]{213, 198, 224});
					colors.add(new int[]{170, 161, 200});
					colors.add(new int[]{160, 142, 181});
					colors.add(new int[]{150, 122, 161});
					colors.add(new int[]{245, 230, 232});
					colors.add(new int[]{98, 92, 129});
					colors.add(new int[]{46, 61, 97});
					colors.add(new int[]{36, 52, 89});
					colors.add(new int[]{25, 42, 81});
					
					// Lista de todos los rectangulos a mostrar
					HashMap<Integer, Rectangle> rects = new HashMap<Integer, Rectangle>();
					
					int scale = 3;
					int scaled = gridSideSize * scale;
					int size = scaled;
					int id = 1;
					double gridAmount = Math.ceil(300.0 / scaled);
					System.out.println(scaled);
					
					// Llenando el Hash de rectangulos 
		            int y = 0;
		            int height = size;
		            for (int i = 0; i < gridAmount; i++) {
		            	
		            	height = i == gridAmount - 1 ? 300 - y : size;
		                
		            	int x = 0;
		                int width = size;
		                for (int j = 0; j < gridAmount; j++) {
		                	
		                	width = j == gridAmount - 1 ? 300 - x : size;
		                	
		                	int rand = new Random().nextInt(scales[scales.length - 1][1] - scales[0][0]) + scales[0][0];
		                	rects.put( id, new Rectangle(id, x, y, width, height, rand) );
		                    x += size;
		                    id++;
		                }
		                y += size;
		            }
		            GridUI grid = new GridUI(rects, colors, scales);
				}
				
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtrHoraYMinuto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(hrMinInicial, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtrHoraYMinuto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hrMinInicial, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public boolean checkExclusions(int[][] intervals) {
		int times = 0;
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i][0] <= intervals[i-1][1]) {
				times++;
			}
		}
		
		boolean excluding = times > 0 ? true : false;
		return excluding;
	}
	
	public boolean checkMultipleOf(int multiple, int number) {
		return number % multiple == 0 ? true : false;
	}
	
	public boolean checkHourFormat(String str) {	
		return Pattern.matches("\\d{2}\\:\\d{2}", str);
	}
	public int[][] parseStringsToHour(String a, String b) {
		
		String[] hrMinInStr = a.split(":");
		String[] hrMinFnStr = b.split(":");

		int[][] timeIntervals = new int[2][2];
		int[] lowerLimit  = new int[2];
		int[] upperLimit = new int[2];
		
		for (int i = 0; i < hrMinInStr.length; i++) {
			lowerLimit[i] = Integer.parseInt(hrMinInStr[i]);
		}

		for (int i = 0; i < hrMinFnStr.length; i++) {
			upperLimit[i] = Integer.parseInt(hrMinFnStr[i]);
		}
		
		timeIntervals[0] = lowerLimit;
		timeIntervals[1] = upperLimit;
		
		return timeIntervals;
	}
	
	public int[][] parseStringToScales(String[] intervalsStr) {
		int[][] intervals = new int[intervalsStr.length][2];
		
		for (int i = 0; i < intervalsStr.length; i++) {
			String[] intervalStr = intervalsStr[i].trim().split(",");
			
			int lower = Integer.parseInt(intervalStr[0]);
			int upper = Integer.parseInt(intervalStr[1]);
			
			int[] interval = new int[2];
			interval[0] = lower;
			interval[1] = upper;
			
			intervals[i] = interval;
		}
		
		return intervals;
	}

	public void getTransactionsCoordinates(String h0, String h1) {
		// Traemos las coordenadas de las transacciones
		
	}
}
