/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class Simulation {

    Game G;
    ArrayList<String> config;

    public Simulation() {
        G = new Game();
        config = new ArrayList<String>();
    }

    /**
     * startSimulation methode start de 3D simulatie van de haven.
     *
     */
    public void startSimulation() {
        G.config = initConfig();
        G.start();
        while (true) {
            updateConfig();
        }
    }

    public ArrayList<String> initConfig() {
        ArrayList<String> firstConfig = new ArrayList<String>();
        firstConfig.add("init");
        firstConfig.add("newboat");
        firstConfig.add("cranes");
        firstConfig.add("storage");

        return firstConfig;
    }

    /**
     * de updateConfig methode update de Config files waarmee de game wordt
     * gerund. Dit is onder andere de snelheid
     */
    public void updateConfig() { 
        G.config = this.config;
        if (!config.isEmpty()){
            config.remove(0);
        }
        else{
            config.add("No Command");
        }
    }

    /**
     * calcPath bepaalt welke motionpath het best geschikt is voor de AGVs.
     *
     */
    public static MotionPath calcPath(String type, Vector3f start, Vector3f end) {
        return null;
    }

    /**
     * de stopSimulation methode stopt de simulatie.
     */
    public void stopSimulation() {
    }

    /**
     * updateDataBase stuurt de informatie naar de DataBase toe voor de webApp
     * en algemene informatie.
     */
    public void updateDataBase() {
    }
}
