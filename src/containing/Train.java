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
    
    public Train(String id, Vector3f loc,float speed , AssetManager assetManager, ArrayList wagons) {
        this.id = id;
        this.loc = loc; 
        this.cargo = null;
        this.speed = speed;
        model = assetManager.loadModel("Models/high/train/train.j3o");
        model.scale(0.5f);
        this.attachChild(model);
        this.wagons = wagons;
    }
    
    public void depart() {
        Vector3f pointA = new Vector3f(0, 6f, 6f);
        Vector3f pointB = new Vector3f(0, 6f, 14f);
        MotionPath path = new MotionPath();
        path.addWayPoint(pointA);
        path.addWayPoint(pointB);
    }
    
    public void arrive() {
        //moveEvent to het station.
    }
    
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
