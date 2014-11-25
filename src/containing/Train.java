package containing;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

public class Train extends Node {
    
    private String id;
    private Vector3f loc;
    private Spatial model;
    private float speed;
    private ArrayList cargo;
    private ArrayList wagons;
    
    /**
     * Train that gets the containers out of the harbor.
     * @param id String ex:C10064
     * @param loc Vector3f location ex:new Vector3f(0, 6f, 0)
     * @param speed float Speed ex:6f
     * @param assetManager AssetManager for the train ex:new AssetManager
     * @param wagons Wagons that can be added to the train ex:new Wagon
     */
    public Train(String id, Vector3f loc,float speed , AssetManager assetManager, ArrayList wagons) {
        this.id = id;
        this.loc = loc; 
        this.cargo = null;
        this.speed = speed;
        model = assetManager.loadModel("Models/high/train/train.j3o");
        model.scale(1.5f);
        this.attachChild(model);
        this.wagons = wagons;
    }
    
    /**
     * Train leaves the station and goes out of the harbor area.
     */
    public void depart() {
        Vector3f station = new Vector3f(0, 6f, 14f);
        Vector3f exit = new Vector3f(0, 6f, 6f);
        MotionPath path = new MotionPath();
        path.addWayPoint(station);
        path.addWayPoint(exit);
    }
    
    /**
     * Train enters the harbor area and stops at the station. 
     */
    public void arrive() {
        Vector3f entry = new Vector3f(0, 6f, 6f);
        Vector3f station = new Vector3f(0, 6f, 14f);
        MotionPath path = new MotionPath();
        path.addWayPoint(entry);
        path.addWayPoint(station);
    }
    
    /**
     * Add physics (gravity) to the train.
     */
    private void initPhysics() {
        //adds gravity effects.
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    public ArrayList getCargo(){return this.cargo;}
    
    public int getWagonCount() {return this.wagons.size();}
    
    public ArrayList getCarts() {return this.wagons;}
    
    public void setCargo(Container container, Wagon wagon) {wagon.setCargo(container);}
    
    public void updateSpeed(float uSpeed) {this.speed = uSpeed;}
    
    // </editor-fold>
}
