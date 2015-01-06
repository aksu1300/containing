/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Kenneth 
 */
public class containerDbHandler {
    
    private static SessionFactory factory; 
    
    /**
     * Database handler for the containers table in the containing database
     */
    public containerDbHandler(){
        try{
         factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
        }
    }
    
    /**
     * Add the full list of data to the container table
     * 
     * @param containerId
     * @param aankomstDatum
     * @param aankomstTijd
     * @param vertrekVervoer
     * @param inhoudNaam
     * @param inhoudSoort
     * @param gevaar
     * @param vetrekDatum
     * @param vertrekTijd
     * @param eigenaar
     * @param isoNr
     * @param storageLot
     * @return the inserted id
     */
    public Integer addToContainers(int containerId, String aankomstDatum, String aankomstTijd, String vertrekVervoer, String inhoudNaam, String inhoudSoort, String gevaar, String vetrekDatum, String vertrekTijd, String eigenaar, int isoNr, int storageLot){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer containerID = null;
        try{
           tx = session.beginTransaction();
           Containers containers = new Containers(containerId, aankomstDatum,aankomstTijd, vertrekVervoer, inhoudNaam, inhoudSoort, gevaar, vetrekDatum,  vertrekTijd, eigenaar,  isoNr,  storageLot);
           containerID = (Integer) session.save(containers); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return containerID;
              
    }
    
    //let op dat als je een single item wil toevoegen de mapping not-null niet op true meot staan. Dat gezegd, dit geld ook voor de database
    /**
     * Add a containerID to the database
     * 
     * @param containerId
     * @return the added id
     */
    public Integer addOne(int containerId){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer containerID = null;
        try{
           tx = session.beginTransaction();
           Containers containers = new Containers(containerId);
           containerID = (Integer) session.save(containers); 
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           System.out.println("It whent wrong here");
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
        return containerID;
    }
    
    //update all method. Don't think we wil need this.
    /**
     *  Update everything in the containers table
     * 
     * @param containerId
     * @param aankomstDatum
     * @param aankomstTijd
     * @param vertrekVervoer
     * @param inhoudNaam
     * @param inhoudSoort
     * @param gevaar
     * @param vetrekDatum
     * @param vertrekTijd
     * @param eigenaar
     * @param isoNr
     * @param storageLot
     */
    public void updateContainers(int containerId, String aankomstDatum, String aankomstTijd, String vertrekVervoer, String inhoudNaam, String inhoudSoort, String gevaar, String vetrekDatum, String vertrekTijd, String eigenaar, int isoNr, int storageLot){
        //working based on the containers numbers that are given in the xml files
        Session session = factory.openSession();
        Transaction tx = null;
        try{
         tx = session.beginTransaction();
         Containers containers = 
                    (Containers)session.get(Containers.class, containerId); 
//         containers.setSpeed(speed);
         containers.setAankomstDatum(aankomstDatum);
         containers.setAankomstTijd(aankomstTijd);
         containers.setVertrekVervoer(vertrekVervoer);
         containers.setInhoudNaam(inhoudNaam);
         containers.setInhoudSoort(inhoudSoort);
         containers.setGevaar(gevaar);
         containers.setVertrekTijd(vertrekTijd);
         containers.setVetrekDatum(vetrekDatum);
         containers.setIsoNr(isoNr);
         containers.setStorageLot(storageLot);
         containers.setEigenaar(eigenaar);
		 session.update(containers); 
         tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
    }
    
    //make an update method for the storage lots where the storage gets updated only
    
    //make a delete update so we can delete the stuff from the database, 
    //which probarly should not happen.
    
}
