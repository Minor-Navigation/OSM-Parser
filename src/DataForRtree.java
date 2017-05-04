
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DataForRtree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			BufferedWriter bw = null;
			FileWriter fw = null;
			// fw = new FileWriter("final_node_data.txt");
			fw = new FileWriter("NodeDataForRTreeWithCheck.txt");

			bw = new BufferedWriter(fw);

			MongoDbOutput output = new MongoDbOutput();
			DBCollection ways = output.getWay();
			DBCollection nodes = output.getNodes();
			DBCursor cursor = nodes.find();

			long nodeid = -1;

			cursor = nodes.find();
			long id = 0;
			Double lati = null, longi = null;
			int admin_level = -1;
			String name = null;

			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				DBObject tag = (DBObject) (obj.get("tags"));
				DBObject loc = (DBObject) (obj.get("loc"));

				List<Long> way_ids = (List<Long>) obj.get("way_ids");

				// double loc[] = (double[]) (obj.get("loc"));
				//name = (String) tag.get("name");

				longi = (Double) (loc.get("0"));
				lati = (Double) (loc.get("1"));

				admin_level= Integer.parseInt(tag.get("admin_level").toString());

				id = (long) (obj.get("id"));

				// bw.write("name=" + name + " id=" + id + " longi= " + longi +
				// " lati=" + lati+"\n");
				// bw.write(id + " " + longi + " " + lati+ " " + admin_level);
				bw.write(id + " " + longi + " " + lati + " " + admin_level);
				int flag=0;
				for (Long wayid : way_ids) {
					//wayid = (long) (obj.get("id"));

					// List<Long> nodes_array = (List<Long>) obj.get("nodes");
					 DBObject query = new BasicDBObject("tags.highway", new BasicDBObject( "$ne", "").append("$exists", true)).append("id", wayid);
					 if(ways.count(query)==1){
						 DBObject find = new BasicDBObject("id", (Long)wayid);
						 String highway =((DBObject)(ways.find(find).next().get("tags"))).get("highway").toString();
						 if(highway==null || highway.equals("services") || highway.equals("rest_area") || highway.equals("proposed") || highway.equals("construction") || highway.equals("cycleway") || highway.equals("pedestrian") || highway.equals("track") || highway.equals("bus_guideway") || highway.equals("escape") || highway.equals("raceway") || highway.equals("footway") || highway.equals("bridleway") || highway.equals("steps") || highway.equals("path"))
						{
							continue;
						}
						 //System.out.println("1, "+wayid);
						 bw.write(" " + 1);
						 flag=1;
						 break;
					}
					 
					
//					BasicDBObject searchQuery = new BasicDBObject().append("id", wayid);
//					DBCursor search_result = ways.find(searchQuery);
//					//System.out.println("here1");
//					
//					DBObject found_way = search_result.next();
//					//System.out.println("here2");
//					
//					if(found_way.containsKey("tags.highway")){
//						bw.write(" " + 1);
//						System.out.println("here2");
//						
//					}
//					else{
//						bw.write(" " + 0);
//					}
					

					//bw.write(" " + wayid);
				}
				if(flag==0){
					bw.write(" " + 0);
				}
				
				bw.write("\n");

			}

			bw.close();
			fw.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
