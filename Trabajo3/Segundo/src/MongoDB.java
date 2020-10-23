import com.mongodb.client.MongoClients;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MongoDB {
	
	public static void insertDocument(MongoCollection<Document> collection, Document doc) {
		collection.insertOne(doc);
	}
	
	public static MongoCursor<Document> getDocumentsByCollection(MongoCollection<Document> collection) {		
		FindIterable<Document> curs = collection.find();
		MongoCursor<Document> cursor = curs.iterator();
		
		return cursor;
	}
	
	public static Document toDocument(Model model) {
		
		Document doc;
		
		if (model instanceof Model1) {
			doc = new Document()
					.append("codigo", model.getCodigo())
					.append("nombre", model.getNombre())
					.append("totalVentas", model.getTotalVentas());
		} else if (model instanceof Model2) {
			doc = new Document()
					.append("nombre", model.getNombre())
					.append("descripcion", model.getDescripcion())
					.append("totalVentas", model.getTotalVentas());
		} else {
			doc = new Document()
				.append("nombre", model.getNombre())
				.append("totalVentas", model.getTotalVentas());
		}
		
		return doc;
	}
	
	public static Model toModel(Document doc, String type) {
		
		Model model;
		
		if (type.equals("porPais")) {
			model = new Model( doc.getString("nombre"), doc.getInteger("totalVentas") );
		} else if (type.equals("porMarca")) {
			model = new Model2( doc.getString("nombre"), doc.getString("descripcion"), doc.getInteger("totalVentas") );
		} else {
			model = new Model1( doc.getInteger("codigo"), doc.getString("nombre"), doc.getInteger("totalVentas") );
		}
		
		return model;
	}
}
