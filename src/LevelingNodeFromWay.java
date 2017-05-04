
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.naming.directory.SearchResult;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class LevelingNodeFromWay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			MongoDbOutput output = new MongoDbOutput();
			DBCollection ways = output.getWay();
			DBCollection nodes = output.getNodes();
			DBCursor cursor = nodes.find();
			
			//uncomment this for first run
			/*
			 * while (cursor.hasNext()) { DBObject obj = cursor.next();
			 * 
			 * List<Long> way_ids = new ArrayList<>();
			 * 
			 * BasicDBObject newDocument = new BasicDBObject();
			 * newDocument.put("way_ids", way_ids);
			 * 
			 * BasicDBObject updateObj = new BasicDBObject();
			 * updateObj.put("$set", newDocument);
			 * 
			 * nodes.update(obj, updateObj);
			 * 
			 * }
			 */

			long wayid = -1, nodeid = -1;
			int admin_level=-1;

			
			cursor = ways.find();
			
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				wayid = (long) (obj.get("id"));
				
				DBObject tag = (DBObject) (obj.get("tags"));
				admin_level= Integer.parseInt( (tag.get("admin_level")).toString());
				
				
				List<Long> nodes_array = (List<Long>) obj.get("nodes");
				for (long node : nodes_array) {
					BasicDBObject searchQuery = new BasicDBObject().append("id", node);
					DBCursor search_result = nodes.find(searchQuery);
					
					DBObject found_node = search_result.next();
					int al = Integer.parseInt( (((DBObject)found_node.get("tags")).get("admin_level")).toString());
					if(admin_level<al)
						nodes.update(found_node, new BasicDBObject("$set", new BasicDBObject("tags.admin_level", admin_level))) ;
				
					
					DBObject push = new BasicDBObject("$push", new BasicDBObject(
					                        "way_ids", wayid));
					nodes.update(found_node,push);
				}

			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
