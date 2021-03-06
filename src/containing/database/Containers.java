package containing.database;
// Generated Dec 18, 2014 11:10:12 AM by Hibernate Tools 3.2.1.GA



/**
 * Containers generated by hbm2java
 */
public class Containers  implements java.io.Serializable {


     private Integer id;
     private int containerId;
     private String aankomstDatum;
     private String aankomstTijd;
     private String vertrekVervoer;
     private String inhoudNaam;
     private String inhoudSoort;
     private String gevaar;
     private String vetrekDatum;
     private String vertrekTijd;
     private String eigenaar;
     private int isoNr;
     private int storageLot;

    public Containers() {
    }

    public Containers(int containerId) {
        this.containerId = containerId;
    }
    
    public Containers(int containerId, String aankomstDatum, String aankomstTijd, String vertrekVervoer, String inhoudNaam, String inhoudSoort, String gevaar, String vetrekDatum, String vertrekTijd, String eigenaar, int isoNr, int storageLot) {
       this.containerId = containerId;
       this.aankomstDatum = aankomstDatum;
       this.aankomstTijd = aankomstTijd;
       this.vertrekVervoer = vertrekVervoer;
       this.inhoudNaam = inhoudNaam;
       this.inhoudSoort = inhoudSoort;
       this.gevaar = gevaar;
       this.vetrekDatum = vetrekDatum;
       this.vertrekTijd = vertrekTijd;
       this.eigenaar = eigenaar;
       this.isoNr = isoNr;
       this.storageLot = storageLot;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getContainerId() {
        return this.containerId;
    }
    
    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }
    public String getAankomstDatum() {
        return this.aankomstDatum;
    }
    
    public void setAankomstDatum(String aankomstDatum) {
        this.aankomstDatum = aankomstDatum;
    }
    public String getAankomstTijd() {
        return this.aankomstTijd;
    }
    
    public void setAankomstTijd(String aankomstTijd) {
        this.aankomstTijd = aankomstTijd;
    }
    public String getVertrekVervoer() {
        return this.vertrekVervoer;
    }
    
    public void setVertrekVervoer(String vertrekVervoer) {
        this.vertrekVervoer = vertrekVervoer;
    }
    public String getInhoudNaam() {
        return this.inhoudNaam;
    }
    
    public void setInhoudNaam(String inhoudNaam) {
        this.inhoudNaam = inhoudNaam;
    }
    public String getInhoudSoort() {
        return this.inhoudSoort;
    }
    
    public void setInhoudSoort(String inhoudSoort) {
        this.inhoudSoort = inhoudSoort;
    }
    public String getGevaar() {
        return this.gevaar;
    }
    
    public void setGevaar(String gevaar) {
        this.gevaar = gevaar;
    }
    public String getVetrekDatum() {
        return this.vetrekDatum;
    }
    
    public void setVetrekDatum(String vetrekDatum) {
        this.vetrekDatum = vetrekDatum;
    }
    public String getVertrekTijd() {
        return this.vertrekTijd;
    }
    
    public void setVertrekTijd(String vertrekTijd) {
        this.vertrekTijd = vertrekTijd;
    }
    public String getEigenaar() {
        return this.eigenaar;
    }
    
    public void setEigenaar(String eigenaar) {
        this.eigenaar = eigenaar;
    }
    public int getIsoNr() {
        return this.isoNr;
    }
    
    public void setIsoNr(int isoNr) {
        this.isoNr = isoNr;
    }
    public int getStorageLot() {
        return this.storageLot;
    }
    
    public void setStorageLot(int storageLot) {
        this.storageLot = storageLot;
    }




}


