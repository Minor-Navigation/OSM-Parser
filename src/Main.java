
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main 
{
    public static void main( String[] args )
    {
        try {
            MongoDbOutput output = new MongoDbOutput();
            OsmXmlParser parser = new OsmXmlParser(output);
            parser.parse("india-latest.osm");
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        nodeAdj.startAdj();
    }
}
