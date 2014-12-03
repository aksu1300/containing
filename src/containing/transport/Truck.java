package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;

public class Truck extends Node {
    String id;
    Vector3f loc;
    Spatial model;
    Material material;
    float speed;
    Container cargo;

    public Truck(String id, Vector3f loc, float speed, AssetManager assetManager) {
        this.id = id;
        this.loc = loc; 
        this.cargo = null;
        this.speed = speed;
        
        model = assetManager.loadModel("Models/high/truck.j3o");
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        model.setMaterial(material);
        model.scale(1);
        model.setLocalTranslation(loc);
        this.attachChild(model);
    }
    
    public void depart() {
        Vector3f pointA = new Vector3f(12f, 6f, -8f);
        Vector3f pointB = new Vector3f(12f, 6f, 14f);
        MotionPath path = new MotionPath();
        path.addWayPoint(pointA);
        path.addWayPoint(pointB);
    }
    
    public void arrive() {
        Vector3f entry = new Vector3f(12f, 6f, 8f);
        Vector3f station = new Vector3f(6f, 6f, 8f);
        MotionPath path = new MotionPath();
        path.addWayPoint(entry);
        path.addWayPoint(station);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    private void initPhysics() {
        
    }
    
    public void setContainer(Container cargo){
        this.cargo = cargo; 
        this.cargo.setLocalTranslation(loc.x, loc.y+1.2f, loc.z-0.35f);
        this.attachChild(this.cargo);
    }
    
    public Container getContainer(){return cargo;}
    
    // </editor-fold>
}
