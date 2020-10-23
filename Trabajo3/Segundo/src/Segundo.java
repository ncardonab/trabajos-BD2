import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import javax.swing.JTextPane;

public class Segundo extends Object {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Segundo window = new Segundo();
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
	public Segundo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 694, 487);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Hashtable<String, String> queries = new Hashtable<String, String>();
		queries.put("porSucursal", "SELECT sucursal.codigo cod_sucursal, sucursal.nombre nom_sucursal, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN sucursal ON venta.cod_sucursal=sucursal.codigo\r\n" + 
				"GROUP BY sucursal.codigo, sucursal.nombre");
		queries.put("porCiudad", "SELECT ciudad.codigo cod_ciudad, ciudad.nombre nom_ciudad, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN sucursal ON venta.cod_sucursal=sucursal.codigo\r\n" + 
				"RIGHT JOIN ciudad ON sucursal.cod_ciudad=ciudad.codigo\r\n" + 
				"GROUP BY ciudad.codigo, ciudad.nombre");
		queries.put("porDpto", "SELECT dpto.codigo cod_dpto, dpto.nombre nom_dpto, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN sucursal ON venta.cod_sucursal=sucursal.codigo\r\n" + 
				"RIGHT JOIN ciudad ON sucursal.cod_ciudad=ciudad.codigo\r\n" + 
				"RIGHT JOIN dpto ON ciudad.cod_dpto=dpto.codigo\r\n" + 
				"GROUP BY dpto.codigo, dpto.nombre");
		queries.put("porPais", "SELECT pais.nombre nom_pais, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN sucursal ON venta.cod_sucursal=sucursal.codigo\r\n" + 
				"RIGHT JOIN ciudad ON sucursal.cod_ciudad=ciudad.codigo\r\n" + 
				"RIGHT JOIN dpto ON ciudad.cod_dpto=dpto.codigo\r\n" + 
				"RIGHT JOIN pais ON dpto.nom_pais=pais.nombre\r\n" + 
				"GROUP BY pais.nombre");
		queries.put("porProducto", "SELECT producto.cod_barras codbar_producto, producto.nombre nom_producto, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN producto ON venta.codbar_producto=producto.cod_barras\r\n" + 
				"GROUP BY producto.cod_barras, producto.nombre");
		queries.put("porMarca", "SELECT marca.nombre nom_marca, marca.descripcion desc_marca, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN producto ON venta.codbar_producto=producto.cod_barras\r\n" + 
				"RIGHT JOIN marca ON producto.nom_marca=marca.nombre\r\n" + 
				"GROUP BY marca.nombre, marca.descripcion");
		queries.put("porVendedor", "SELECT vendedor.codigo cod_vendedor, vendedor.nombre nom_vendedor, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN vendedor ON venta.cod_vendedor=vendedor.codigo\r\n" + 
				"GROUP BY vendedor.codigo, vendedor.nombre");
		queries.put("porGremio", "SELECT gremio.codigo cod_gremio, gremio.nombre nom_gremio, SUM(venta.valor) total_ventas\r\n" + 
				"FROM venta\r\n" + 
				"RIGHT JOIN vendedor ON venta.cod_vendedor=vendedor.codigo\r\n" + 
				"RIGHT JOIN gremio ON vendedor.cod_gremio=gremio.codigo\r\n" + 
				"GROUP BY gremio.codigo, gremio.nombre");
		
        Set<String> setOfQueries = queries.keySet();
		
        JButton button1 = customButton("Generar y Cargar Estadisticas", 219, 121, 0);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				// Conexion con la base de datos Oracle
				ConexionOracle dbWriteHeavy = new ConexionOracle();
				
				Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
				// Estableciendo la conexion con MongoDB
		        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

		            MongoDatabase db = mongoClient.getDatabase("TotalVentas");
			        
					String pkFieldName = "";
					
					for(String key : setOfQueries) {
						List<Model> oracleData = dbWriteHeavy.getTable(key, queries.get(key));
						
						switch (key) {
							case "porPais":
								pkFieldName = "nombre";
								break;
							case "porMarca":
								pkFieldName = "nombre";
								break;
							default:													
								pkFieldName = "codigo";
								break;
						}
						mapOracleToMongo(oracleData, db, key, pkFieldName);
					}
				}

			}
		});
		
		JButton button2 = customButton("Visualizar Estadisticas", 0, 219, 219);		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
				// Estableciendo la conexion con MongoDB
		        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

		            MongoDatabase db = mongoClient.getDatabase("TotalVentas");
		            
		            DefaultTableModel model = (DefaultTableModel) table.getModel();
		            
		            for(String key : setOfQueries) {
			            // Crea si no existe u obtiene si existe
			    		MongoCollection<Document> coll = db.getCollection(key);			            
			            FindIterable<Document> curs = coll.find().sort(Sorts.descending("totalVentas")).limit(3);
			            
			            try (MongoCursor<Document> cursor = coll.find().sort(Sorts.descending("totalVentas")).limit(3).iterator()) {
			                while (cursor.hasNext()) {
			                	Document doc = cursor.next();
			     	    	   
			                    if (key.equals("porPais")) {
			                    	model.addRow(new Object[]{"", doc.getString("nombre"), doc.getInteger("totalVentas").toString()});
			            		} else if (key.equals("porMarca")) {
			            			model.addRow(new Object[]{doc.getString("nombre"), doc.getString("descripcion"), doc.getInteger("totalVentas").toString()});
			            		} else {
			            			model.addRow(new Object[]{doc.getInteger("codigo").toString(), doc.getString("nombre"), doc.getInteger("totalVentas").toString()});
			            		}
			                }
			            }
		            }
		        }
			}
		});
	
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {
				{"Codigo/Nombre/Null", "Nombre/Descripcion", "Total Ventas"}
			},
			new String[] {
				"Codigo/Nombre/Null", "Nombre/Descripcion", "Total Ventas"
			}
		));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(button2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(button1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(35)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(130, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(68)
							.addComponent(button1)
							.addGap(18)
							.addComponent(button2))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(table, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private static JButton customButton(String text, int red, int green, int blue) {
		JButton button = new JButton(text);
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(red, green, blue));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		Border line = new LineBorder(Color.WHITE);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
	}
	
	private static void mapOracleToMongo(List<Model> oracleTablesSet, MongoDatabase mongoDB, String collectionName, String primaryKeyFieldName) {
		// Crea si no existe u obtiene si existe
		MongoCollection<Document> coll = mongoDB.getCollection(collectionName);
		
		Document modelToDoc;
		for (int i = 0; i < oracleTablesSet.size(); i++) {
			// Parseando el modelo de tipo Model a Document
			modelToDoc = MongoDB.toDocument(oracleTablesSet.get(i));
			
			// Consulta para ver si el documento ya está en la base de datos de Mongo
			BasicDBObject whereQuery = new BasicDBObject();
			
			if (primaryKeyFieldName.equals("codigo")) {				
				whereQuery.put(primaryKeyFieldName, modelToDoc.getInteger("codigo"));							
			} else if (primaryKeyFieldName.equals("nombre")) {
				whereQuery.put(primaryKeyFieldName, modelToDoc.getString("nombre"));												
			}
			
			FindIterable<Document> curs = coll.find(whereQuery);
			MongoCursor<Document> cursor = curs.iterator();
			
			// Si no hay siguiente, es decir, no hay coincidencias de este documento en la BD lo inserta
			if(!cursor.hasNext()) {
				System.out.println("¡Documento: " + modelToDoc + " insertado!");
				MongoDB.insertDocument(coll, modelToDoc);
			}
		}
		MongoDB.getDocumentsByCollection(coll);
	}
}