import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class nodeAdj {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MongoDbOutput output=null;
		try {
            output = new MongoDbOutput();
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		DBCollection ways = output.getWay();
		DBCollection nodes = output.getNodes();
		
		
		DBCursor cursor_ways = ways.find();
		while (cursor_ways.hasNext()) 
		{
			BasicDBList x = (BasicDBList) cursor_ways.next().get("nodes");
			
			for (int i=0;i<x.size()-1;i++)
			{
				DBObject find = new BasicDBObject("id", (Long)x.get(i));
				DBObject push = new BasicDBObject("$push", new BasicDBObject(
				                        "adjacent", (Long)x.get(i+1)));
				nodes.update(find, push);
			}
			
		}
		
		
		
		System.out.println("Done");
	}

}
