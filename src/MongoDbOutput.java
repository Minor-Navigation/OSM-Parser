



import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDbOutput {

    private DB db;

    public MongoDbOutput() throws UnknownHostException {
        try {
            Mongo m = new Mongo( "localhost" , 27017 );
            db = m.getDB( "osm" );
            
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void addNodes(List<DBObject> records) {
        DBCollection nodes = db.getCollection("nodes");
        nodes.ensureIndex(new BasicDBObject("id", 1), new BasicDBObject("unique", true));
        nodes.ensureIndex(new BasicDBObject("loc", 1), new BasicDBObject("unique", false));
        nodes.insert(records);
    }

	public void addWay(BasicDBObject record) {
		// TODO Auto-generated method stub
		DBCollection ways = db.getCollection("ways");
		ways.ensureIndex(new BasicDBObject("id", 1), new BasicDBObject("unique", true));
		ways.insert(record);
		
	}
	
	public void addRelation(BasicDBObject record) {
		// TODO Auto-generated method stub
		DBCollection relations = db.getCollection("relations");
		relations.ensureIndex(new BasicDBObject("id", 1), new BasicDBObject("unique", true));
		relations.insert(record);
	}
    
	public DBCollection getWay() {
		// TODO Auto-generated method stub
		DBCollection ways = db.getCollection("ways");
		return ways;
		
	}
	
	public DBCollection getNodes() {
		// TODO Auto-generated method stub
		DBCollection nodes = db.getCollection("nodes");
		return nodes;
		
	}
	
	public DBCollection getRelations() {
		// TODO Auto-generated method stub
		DBCollection rel = db.getCollection("relations");
		return rel;
		
	}

	
}
