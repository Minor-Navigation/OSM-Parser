//chanchur
//SaveDataFromMongoDBToFile

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

public class DataFromMongo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{   
			
			MongoDbOutput output = new MongoDbOutput();
	    	  
	    	 System.out.println("Connect to database successfully");
				
	         
	         DBCollection coll_nodes = output.getNodes();
	         
	         File file_node = new File("CompleteNodeData.dat");
	         File file_ind = new File("NodeIndex_temp.ind");
	         
	         
	         if(!file_node.exists()){
	        	 file_node.createNewFile();
	         }
	         if(!file_ind.exists())
	         {
	        	 file_ind.createNewFile();
	         }
	         
	         FileOutputStream nodes = new FileOutputStream(file_node);
	         FileOutputStream index = new FileOutputStream(file_ind);
	         
	        DBCursor cursor = coll_nodes.find();
	        int i = 1;
			String k;
			long pntr=0;
			DBObject node=null;
			int ma=0;
			
			ma=27;
			
			ArrayList<String> s = new ArrayList<String>();
			boolean check = true;
			long it=0;
	        byte bi[] = new byte[ma],c[];
	        
	        
//	        node= cursor.next();
//	        byte[] b=(node.toString()+"\n").getBytes();
//        	nodes.write(b);
//	        
	         while (cursor.hasNext()) {
	        //for(int ii=0; ii<10; ii++){	 
	            //System.out.println(cursor.next());
	        	node= cursor.next();
	        	
	        	k=node.get("id").toString();
	        	pntr=nodes.getChannel().position();
	        	byte[] b=(node.get("tags").toString()+"\n").getBytes();
	        	
	        	nodes.write(b);
	        	String si=k+"\t"+Long.toString(pntr)+"\t"+b.length+"\n";
	        	//System.out.println(si);
	        	
	        	//s.add(si);
	        	//ma = Math.max(ma, si.getBytes().length);
	        	
	        	c=si.getBytes();
	        	for(int iter=0;iter<ma;iter++)
	        	{
	        		if(iter<c.length)
	        			bi[iter]=c[iter];
	        		else
	        			bi[iter]=0;
	        	}
	        	index.write(bi);
	        	it++;
	        	
	        	//pntr+=b.length;
	        	
	        	
	         }
	         System.out.println("Done Writing Nodes \tMax Size : "+ma);
	         
//	         for(String si : s)
//	         {	
//	        	 //index.getChannel().position(it);
//	        	 c=si.getBytes();
//	        	 for(int iter=0;iter<ma;iter++)
//	        	 {
//	        		 if(iter<c.length)
//	        			 bi[iter]=c[iter];
//	        		 else
//	        			 bi[iter]=0;
//	        	 }
//	        	 index.write(bi);
//	        	 
//	        	 
//	        	 it++;
//
//	         }
	         it--;
	         System.out.println(ma+"\t"+it);
	         nodes.flush();
	         nodes.close();
	         index.flush();
	         index.close();
				
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}

}