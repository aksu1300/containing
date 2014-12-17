package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;
import java.util.ArrayList;

public class Wagon extends Node {
    
    private Vector3f location;
    private Spatial model;
    private Material material;
    private AssetManager assetmanager;
    private Container cargo;
    private float speed;
    private String id;
    
<<<<<<< HEAD
    public Wagon(String id,Vector3f location , AssetManager assetmanager) {
=======
    public Wagon(String id, Vector3f location , AssetManager assetmanager) {
>>>>>>> bf4730ffc9666bd3e40e5a056eeddfe96c2fb767
        this.id = id;
        this.location = location;
        this.assetmanager = assetmanager;
        this.model = this.assetmanager.loadModel("Models/high/train/wagon.j3o");
<<<<<<< HEAD
        this.material = new Material(assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");
        this.model.setMaterial(material);
        this.attachChild(this.model);
        
=======
        this.material = new Material(this.assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");
        this.model.setMaterial(material);
        this.attachChild(model);
>>>>>>> bf4730ffc9666bd3e40e5a056eeddfe96c2fb767
        
    }
    
    public void depart() {
        Vector3f station = new Vector3f(0, 6f, 14f);
        Vector3f exit = new Vector3f(0, 6f, 6f);
        MotionPath path = new MotionPath();
        path.addWayPoint(station);
        path.addWayPoint(exit);
    }
    
    public void arrive() {
        Vector3f entry = new Vector3f(0, 6f, 6f);
        Vector3f station = new Vector3f(0, 6f, 14f);
        MotionPath path = new MotionPath();
        path.addWayPoint(entry);
        path.addWayPoint(station);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get & Set">
    
<<<<<<< HEAD
    public void setCargo(Container cargo){
        if (this.cargo == null) {
=======
     public void setCargo(Container cargo){
>>>>>>> bf4730ffc9666bd3e40e5a056eeddfe96c2fb767
            this.cargo = cargo; 
            this.cargo.setLocalTranslation(0,0.6f,0);
            this.attachChild(this.cargo);
        
    }
    
    public void resetCargo() {
        this.cargo = null;
    }
    
    public void resetCargo() {
        this.cargo = null;
    }
    
    public Container getCargo(){
        return this.cargo;
    }
    
    public void updateSpeed(float uSpeed) {
        this.speed = uSpeed;
    }
    
    public Vector3f getLocation() {
        return this.location;
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
<<<<<<< HEAD
    // </editor-fold>
=======
    public String getId(){
        return this.id;
    }
    
>>>>>>> bf4730ffc9666bd3e40e5a056eeddfe96c2fb767
}
