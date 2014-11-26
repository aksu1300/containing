/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

/**
 *
 * @author Driving Ghost
 */
public class Simulation {
    
    public Simulation(){
    }
        Game G = new Game();
    /**
     * startSimulation methode start de 3D simulatie van de haven.
     * 
     */
    public void startSimulation(){
        
        G.start();  
    }
    
    /**
     * de updateConfig methode update de Config files waarmee de game wordt gerund. 
     * Dit is onder andere de snelheid
     */
    public void updateConfig(){
        
    }
    
    /**
     * calcPath bepaalt welke motionpath het best geschikt is voor de AGVs.
     * 
     */
    public void calcPath(){
            
    }
    
    /**
     * de stopSimulation methode stopt de simulatie. 
     */
    public void stopSimulation(){
        
    }
    
    /**
     * updateDataBase stuurt de informatie naar de DataBase toe voor de webApp en algemene informatie. 
     */
    public void updateDataBase(){
        
    }
    
}
