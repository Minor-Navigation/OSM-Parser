
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdjacencyListFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			MongoDbOutput output = new MongoDbOutput();

			System.out.println("Connect to database successfully");

			DBCollection coll_nodes = output.getNodes();

			BufferedWriter bw = null;
			FileWriter fw = null;
			fw = new FileWriter("AdjacencyListFile.txt");
			bw = new BufferedWriter(fw);

			DBCursor cursor = coll_nodes.find();
			int i = 1;
			String k;
			long pntr = 0;
			DBObject node = null;
			int ma = 0;

			ma = 27;

			ArrayList<String> s = new ArrayList<String>();
			boolean check = true;
			long id;
			ArrayList<Long> adj;
			double lon, lat;
			long it = 0;
			byte bi[] = new byte[ma], c[];
			while (cursor.hasNext()) {

				node = cursor.next();
				// System.out.println(node);
				id = (Long) node.get("id");
				lon = (double) ((ArrayList<Double>) node.get("loc")).get(0);
				lat = (double) ((ArrayList<Double>) node.get("loc")).get(1);

				adj = (ArrayList<Long>) node.get("adjacent");
				//System.out.print(id + " " + lon + " " + lat + " " + adj.size() + " ");
				bw.write(id + " " + lon + " " + lat + " " + adj.size() + " ");
				for (long l : adj) {
					//System.out.print(l + " ");
					bw.write(l + " ");
				}
				
				
				bw.write("\n");
				

				// break;
			}
			bw.close();
			fw.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
