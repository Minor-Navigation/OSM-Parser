import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Leveling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			//MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDbOutput output = new MongoDbOutput();
			/**** Get database ****/

//			DB db = mongo.getDB("osm_india");
//			String type=null;
			DBCollection table=null;
			for(int i=0; i<3;i++){
				
				if(i==0){
					//type="nodes";
					table=output.getNodes();
					
				}
				else if(i==1){
					//type="ways";
					table=output.getWay();
				}
				else if(i==2){
					//type="relations";
					table=output.getRelations();
				}
				
				 //= db.getCollection(type);
	
				
				// db.getCollection("nodes").find({ tags.name: { $exists: true } } );
	
				DBObject query1 = new BasicDBObject("tags.highway", new BasicDBObject("$exists", true));
				DBCursor result1 = table.find(query1);
				
				String highway = null;
				String building = null;
				
				while (result1.hasNext()) {
					// i++;
					DBObject obj = result1.next();
	
					DBObject tag = (DBObject) (obj.get("tags"));
					
					highway = (String) (tag.get("highway"));
					int admil_level;
					if (highway.equals("motorway") || highway.equals("motorway_link") || highway.equals("trunk") || highway.equals("trunk_link")) {
						admil_level=1;
					} else if (highway.equals("primary") || highway.equals("primary_link")) {
						admil_level=2;
					} else if (highway.equals("secondary") || highway.equals("secondary_link")) {
						admil_level=3;
					} else if (highway.equals("tertiary") || highway.equals("tertiary_link")) {
						admil_level=4;
					} else if (highway.equals("unclassified")) {
						admil_level=5;
					} else if (highway.equals("residential")) {
						admil_level=6;
					} else if ( highway.equals("living_street")) {
						admil_level=7;
					} else  {
						admil_level=8;
					}
					
					
					
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.put("tags.admin_level", admil_level);
					
					
					BasicDBObject updateObj = new BasicDBObject();
					updateObj.put("$set", newDocument);
					table.update(obj, updateObj);
	
				}
				DBObject query2 = new BasicDBObject("tags.building", new BasicDBObject("$exists", true));
				DBCursor result2 = table.find(query2);
				
				while (result2.hasNext()) {
					// i++;
					DBObject obj = result2.next();
	
					DBObject tag = (DBObject) (obj.get("tags"));
					
					building = (String) (tag.get("building"));
					int admil_level;
					
					if ( building.equals("train_station") || building.equals("transportation")  ) {
						admil_level=3;
					} else if (building.equals("commercial") || building.equals("stadium")  ) {
						admil_level=4;
					} else if ( building.equals("industrial")  || building.equals("warehouse")  ) {
						admil_level=5;
					} else if (building.equals("cathedral")  ) {
						admil_level=6;
					} else if (building.equals("public") ) {
						admil_level=4;
					} else if ( building.equals("hospital") || building.equals("university")  ) {
						admil_level=5;
					} else if (building.equals("school")) {
						admil_level=6;
					} else if (building.equals("hotel") ) {
						admil_level=7;
					} else if (building.equals("apartments") ) {
						admil_level=8;
					} else{
						admil_level=9;
					}
					
					
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.put("tags.admin_level", admil_level);
					
					
					BasicDBObject updateObj = new BasicDBObject();
					updateObj.put("$set", newDocument);
					table.update(obj, updateObj);
	
				}
				
				query1 = new BasicDBObject("tags.highway", new BasicDBObject("$exists", false));
				query2 = new BasicDBObject("tags.building", new BasicDBObject("$exists", false));
//				BasicDBList query3 = new BasicDBList();
//				query3.add(query1);
//				query3.add(query2);
				
//				BasicDBObject res = new BasicDBObject("$and", query3);
//				DBCursor result3 = table.find(res);
//				System.out.println("count="+result3.count());
//				
				
				BasicDBList and = new BasicDBList();
				and.add(query1);
				and.add(query2);
				DBObject query = new BasicDBObject("$and", and);
				DBCursor result3 = table.find(query);
				//System.out.println("count="+result3.count());

				
				//BasicDBObject query = new BasicDBObject("$or", or);
				
				while (result3.hasNext()) {
					//System.out.println("here");
					DBObject obj = result3.next();
					int admil_level = 10;
					
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.put("tags.admin_level", admil_level);
					
					BasicDBObject updateObj = new BasicDBObject();
					updateObj.put("$set", newDocument);
					table.update(obj, updateObj);
					
				}
				
				
				
				
				
				
				
			}
			
			//DBObject query3 = new BasicDBObject("tags.building", new BasicDBObject("$exists", true));
			//DBCursor result3 = table.find(query2);
			

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
			// updateObj.append("$currentDate", new
			// BasicDBObject().append("lastModified", true));
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
