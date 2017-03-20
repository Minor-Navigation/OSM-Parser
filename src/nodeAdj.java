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
	
	public static DBCollection ways=null,nodes=null,relations=null;
	
	public static long getFirst(String type, long ref)
	{
		if(type.equals("node"))
			return ref;
		
		
		DBObject ob=null;
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("id", ref);
	    
		if(type.equals("way"))
		{
			ob=ways.find(whereQuery).next();
			return (long)((BasicDBList) ob.get("nodes")).get(0);
		}
		if(type.equals("relation"))
		{
			ob=relations.find(whereQuery).next();
			ob= (DBObject)((BasicDBList)ob.get("members")).get(0);
			return getFirst(ob.get("type").toString(),(long)ob.get("ref"));
		}
		return 0;
	}
	public static long getLast(String type, long ref)
	{
		if(type.equals("node"))
			return ref;
		
		
		DBObject ob=null;
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("id", ref);
	    
		if(type.equals("way"))
		{
			ob=ways.findOne(whereQuery);
			return (long)((BasicDBList) ob.get("nodes")).get( ((BasicDBList) ob.get("nodes")).size()-1 );
		}
		if(type.equals("relation"))
		{
			ob=relations.findOne(whereQuery);
			ob= (DBObject)((BasicDBList)ob.get("members")).get( ((BasicDBList)ob.get("members")).size()-1 );
			return getLast(ob.get("type").toString(),(long)ob.get("ref"));
		}
		return 0;
		
	}

	public static void startAdj() {
		// TODO Auto-generated method stub
		System.out.println("Adjacency List Creating");
		MongoDbOutput output=null;
		try {
            output = new MongoDbOutput();
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		ways = output.getWay();
		nodes = output.getNodes();
		relations=output.getRelations();
		
		
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
		
		
		DBCursor cursor_relations = relations.find();
		DBObject find=null,push=null;
		while (cursor_relations.hasNext()) 
		{
			
			BasicDBList x = (BasicDBList) cursor_relations.next().get("members");
			
			for (int i=0;i<x.size()-1;i++)
			{
				
				DBObject o1 = (DBObject)x.get(i);
				DBObject o2 = (DBObject)x.get(i+1);
				
				try{
				
				find = new BasicDBObject("id", getLast(o1.get("type").toString(),(long)o1.get("ref")) );
				push = new BasicDBObject("$push", new BasicDBObject(
				                        "adjacent", getFirst(o2.get("type").toString(),(long)o2.get("ref")) ));
				nodes.update(find, push);
				}
				catch (Exception r){}
				
				
			}
			
		}
		
		
		
		System.out.println("Done");
	}

}
