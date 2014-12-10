package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;
import containing.TruckCrane;

public class Truck extends Node {
    String id;
    Vector3f loc;
    Spatial model;
    Material material;
    float speed;
    Container cargo;
    boolean ready = false;

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
        model.rotate(0, -FastMath.PI/2, 0);
        
        this.attachChild(model);
    }
    
    public void truckArrive(TruckCrane tc) { //van ver weg naar TC
        final MotionPath path = new MotionPath();
        path.addWayPoint(new Vector3f(0, 0, 0));
        path.addWayPoint(new Vector3f(-tc.getLocalTranslation().x - 295, 0, 0));
        
        MotionEvent motionControl = new MotionEvent(this, path);
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();

        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                   
                }
            }
        });
        
    }
    
    public void truckDepart(TruckCrane tc){
        
        final MotionPath path = new MotionPath();
        path.addWayPoint(new Vector3f(-tc.getLocalTranslation().x - 295, 0, 0));
        path.addWayPoint(this.getLocalTranslation());
        
        
        
        MotionEvent motionControl = new MotionEvent(this, path);
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();

        path.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    setReady();
                }
            }
        });
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    private void initPhysics() {
        
    }
    
    public Vector3f getLoc(){
        return this.loc;
    }
    
    public void setContainer(Container cargo){
        this.cargo = cargo; 
        this.cargo.rotate(0, -FastMath.PI/2, 0);
        this.cargo.setLocalTranslation(loc.x, loc.y+1.2f, loc.z-0.345f);
        this.attachChild(this.cargo);
    }
    
    public Container getContainer(){return cargo;}
    
    public boolean getReady(){
        return this.ready;
    }
    
    public void setReady(){
        this.ready = !ready;
    }
    
    // </editor-fold>
}
