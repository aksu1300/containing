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
        
//        XMLReader reader = new XMLReader("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml1.xml");
//      
        Simulation simu = new Simulation();
        simu.startSimulation();
        
        XMLReader reader1 = new XMLReader();
        
        System.out.println("Record   s : " + reader1.records.size());
        
        for(Record re : reader1.records)
        {
            System.out.println("Records i d  : " + re.getRecordId());
            System.out.println("Company  : " + re.getArival_company());
            System.out.println("Arival Date  : " + re.getArival_date());
            System.out.println("From  : " + re.getArival_from());
            System.out.println("To  : " + re.getArival_to());
            
            System.out.println("Records id  : " + re.getArival_transport());
            System.out.println("Container : " + re.getWeight());
            System.out.println("=============================================");
        }
        
        
        
    }
}

