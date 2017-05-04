import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class NodeDataTRIE {

	public static void main(String args[]) {

		try {
			
			BufferedWriter bw = null;
			FileWriter fw = null;
			//fw = new FileWriter("final_node_data.txt");
			fw = new FileWriter("NodeDataForTrie.txt");
			
			bw = new BufferedWriter(fw);
			
			MongoDbOutput output = new MongoDbOutput();

			DBCollection table = output.getNodes();

			/****
			 * Insert *** // create a document to store key and value
			 * BasicDBObject document = new BasicDBObject();
			 * document.put("name", "mkyong"); document.put("age", 30); //
			 * document.put("createdDate", new Date()); table.insert(document);
			 */

			/**** Find and display ****/

			// BasicDBObject searchQuery = new BasicDBObject();

			// db.getCollection("nodes").find({ tags.name: { $exists: true } }
			// );

			DBObject query = new BasicDBObject("tags.name", new BasicDBObject("$exists", true));
			DBCursor result = table.find(query);
			//DBCursor result = table.find();
			
			System.out.println(result.size());
			long id = 0;
			Double lati = null, longi = null;
			int admin_level=-1;
			String name=null;
			while (result.hasNext()) {
				DBObject obj = result.next();
				DBObject tag = (DBObject) (obj.get("tags"));
				DBObject loc = (DBObject) (obj.get("loc"));
				
				//List<Long> way_ids = (List<Long>) obj.get("way_ids");
				
				
				// double loc[] = (double[]) (obj.get("loc"));
				name=(String)tag.get("name");
				
				longi = (Double) (loc.get("0"));
				lati = (Double) (loc.get("1"));
				
				admin_level = (int) (tag.get("admin_level"));
				
				id = (long) (obj.get("id"));
				
				//bw.write("name=" + name + " id=" + id + " longi= " + longi + " lati=" + lati+"\n");
				//bw.write(id + " " + longi + " " + lati+ " " + admin_level);
				bw.write(name+ "$" +id + "$" + admin_level+"$" + longi+"$" + lati +"$");
				
//				for (Long wayid : way_ids) {
//					bw.write("$"+wayid);
//				}
				bw.write("\n");
				
				
				//System.out.println("name=" + name + " id=" + id + " longi=" + longi + " lati=" + lati);

				
			}
			bw.close();
			fw.close();

			// ({"IMAGE URL":{$ne:null}}) ;
			// searchQuery.put("name", "mkyong");

			// DBCursor cursor = table.find(searchQuery);
			//
			// while (cursor.hasNext()) {
			// System.out.println(cursor.next());
			// }
			//
			// /**** Update ****/
			// // search document where name="mkyong" and update it with new
			// values
			// BasicDBObject query = new BasicDBObject();
			// query.put("name", "mkyong");
			//
			// BasicDBObject newDocument = new BasicDBObject();
			// newDocument.put("name", "mkyong-updated");
			//
			//
			// BasicDBObject updateObj = new BasicDBObject();
			// updateObj.put("$set", newDocument);
			// updateObj.append("$currentDate", new BasicDBObject().append("lastModified", true));
			//
			// table.update(query, updateObj);
			//
			// /**** Find and display ****/
			// BasicDBObject searchQuery2
			// = new BasicDBObject().append("name", "mkyong-updated");
			//
			// DBCursor cursor2 = table.find(searchQuery2);
			//
			// while (cursor2.hasNext()) {
			// System.out.println(cursor2.next());
			// }
			//
			// /**** Done ****/
			// System.out.println("Done");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
