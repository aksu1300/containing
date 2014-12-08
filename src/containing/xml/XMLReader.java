/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.xml;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author account
 */
public class XMLReader {
    File file;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;
    
    public List<Record> records = new ArrayList<Record>();
    
    public XMLReader(){
        
        List<File> files = new ArrayList<File>();
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml1.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml2.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml3.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml4.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml5.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml6.xml"));
        files.add(new File("C:\\Users\\account\\Documents\\Tweede Jaar\\containing\\containing\\src\\containing\\xml\\xml7.xml"));
        
        for(File file : files)
        {
        
            try{
                this.file = file;
                this.dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                doc =  db.parse(file);

                doc.getDocumentElement().normalize();

                System.out.println("Root element " + doc.getDocumentElement().getNodeName());
                NodeList nodeList = doc.getElementsByTagName("record");
                System.out.println("Information all records");

                for(int s = 0; s < nodeList.getLength(); s++ )
                {
                    Node fstNode = nodeList.item(s);


                    // Create a new Record object
                    Record record = new Record();

                    if(fstNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) fstNode;

                        record.recordId = eElement.getAttribute("id");

                        record.arival_date = eElement.getElementsByTagName("d").item(0).getTextContent() + "/" +   eElement.getElementsByTagName("m").item(0).getTextContent() + "/" +  eElement.getElementsByTagName("j").item(0).getTextContent();
                        record.arival_from = eElement.getElementsByTagName("van").item(0).getTextContent();
                        record.arival_to = eElement.getElementsByTagName("tot").item(0).getTextContent();

                        record.arival_transport = eElement.getElementsByTagName("soort_vervoer").item(0).getTextContent();
                        record.arival_company = eElement.getElementsByTagName("bedrijf").item(0).getTextContent();

                        record.owner = eElement.getElementsByTagName("naam").item(0).getTextContent();
                        record.containernmr = eElement.getElementsByTagName("containernr").item(0).getTextContent();

                        //
                        record.departure_date = eElement.getElementsByTagName("d").item(1).getTextContent() + "/" +   eElement.getElementsByTagName("m").item(1).getTextContent() + "/" +  eElement.getElementsByTagName("j").item(1).getTextContent();
                        record.departure_from = eElement.getElementsByTagName("van").item(1).getTextContent();
                        record.departure_to = eElement.getElementsByTagName("tot").item(1).getTextContent();
                        record.departure_transport = eElement.getElementsByTagName("soort_vervoer").item(1).getTextContent();
                        record.departure_company = eElement.getElementsByTagName("bedrijf").item(1).getTextContent(); 

                        record.size = eElement.getElementsByTagName("l").item(0).getTextContent() + " b " + eElement.getElementsByTagName("b").item(0).getTextContent() + " h " + eElement.getElementsByTagName("l").item(0).getTextContent();
                       record.weight = /*eElement.getElementsByTagName("leeg").item(0).getTextContent() + " inhoud : " +*/ eElement.getElementsByTagName("inhoud").item(0).getTextContent();

                        record.content_name = eElement.getElementsByTagName("naam").item(1).getTextContent();
                        record.content_type = eElement.getElementsByTagName("soort").item(0).getTextContent();
                        record.content_danger = eElement.getElementsByTagName("gevaar").item(0).getTextContent();

                        record.ISO = eElement.getElementsByTagName("ISO").item(0).getTextContent();

                        // Add the new records to the list
                        records.add(record);
                    }
                }

            }catch(Exception e){
                 e.printStackTrace();
            }
        }
    }
    
    public XMLReader(String fileName){
        try{
            this.file = new File(fileName);
            this.dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc =  db.parse(file);
            
            doc.getDocumentElement().normalize();
            
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("record");
            System.out.println("Information all records");
            
            for(int s = 0; s < nodeList.getLength(); s++ )
            {
                Node fstNode = nodeList.item(s);
                
                System.out.println("---------------------------------------------------------");
                
                System.out.println("\nCurrent Element :" + fstNode.getNodeName());
                
                if(fstNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) fstNode;
                    
                    System.out.println("Reccord id : " + eElement.getAttribute("id"));
                    
                    System.out.println("Aankomst datum : " + eElement.getElementsByTagName("d").item(0).getTextContent() + "/" +   eElement.getElementsByTagName("m").item(0).getTextContent() + "/" +  eElement.getElementsByTagName("j").item(0).getTextContent() );
                    System.out.println("Tijd van : " + eElement.getElementsByTagName("van").item(0).getTextContent());
                    System.out.println("Tijd tot : " + eElement.getElementsByTagName("tot").item(0).getTextContent());
                    
                    System.out.println("Soort vervoer : " + eElement.getElementsByTagName("soort_vervoer").item(0).getTextContent());
                    System.out.println("Bedrijf : " + eElement.getElementsByTagName("bedrijf").item(0).getTextContent());
                    
                    
                    System.out.println("Eigenaar naam : " + eElement.getElementsByTagName("naam").item(0).getTextContent());
                    System.out.println("Containernmr : " + eElement.getElementsByTagName("containernr").item(0).getTextContent());
                    

                    System.out.println("Vertrek datum : " + eElement.getElementsByTagName("d").item(1).getTextContent() + "/" +   eElement.getElementsByTagName("m").item(1).getTextContent() + "/" +  eElement.getElementsByTagName("j").item(1).getTextContent() );
                    System.out.println("Tijd van : " + eElement.getElementsByTagName("van").item(1).getTextContent());
                    System.out.println("Tijd tot : " + eElement.getElementsByTagName("tot").item(1).getTextContent());
                    System.out.println("Soort vervoer : "  + eElement.getElementsByTagName("soort_vervoer").item(1).getTextContent());
                    System.out.println("Bedrijf : " + eElement.getElementsByTagName("bedrijf").item(1).getTextContent() );
                    System.out.println("Afmeting : l " + eElement.getElementsByTagName("l").item(0).getTextContent() + " b " + eElement.getElementsByTagName("b").item(0).getTextContent() + " h " + eElement.getElementsByTagName("l").item(0).getTextContent());
                    
                    System.out.println("Gewicht : leeg : " + eElement.getElementsByTagName("leeg").item(0).getTextContent() + " inhoud : " + eElement.getElementsByTagName("inhoud").item(0).getTextContent());
                    
                    System.out.println("Inhoud : ");
                    System.out.println("    Naam : " +eElement.getElementsByTagName("naam").item(1).getTextContent());
                    System.out.println("    soort: " +eElement.getElementsByTagName("soort").item(0).getTextContent());
                    System.out.println("    gevaar: " +eElement.getElementsByTagName("gevaar").item(0).getTextContent());
                 
                    System.out.println("ISO : " + eElement.getElementsByTagName("ISO").item(0).getTextContent());
                    
                    System.out.println("---------------------------------------------------------");
                 //dd
                }
            }
            
        }catch(Exception e){
             e.printStackTrace();
        }
    }
}
