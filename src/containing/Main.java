package containing;

import containing.xml.Record;
import containing.xml.XMLReader;

/**
 * test
 *
 * @author Umit Aksu Umit Test shit Jacco test shit Umit branch test AGV
 */
public class Main {
    
    public static void main(String[] args){
//        Simulation s = new Simulation();
//        s.startSimulation();
        
        XMLReader reader = new XMLReader("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml1.xml");
        
        XMLReader reader1 = new XMLReader();
        
        System.out.println("Record   s : " + reader1.records.size());
        
        for(Record re : reader.records)
        {
            System.out.println("Records id  : " + re.getRecordId());
                    
        }
        
        
        
    }
}

