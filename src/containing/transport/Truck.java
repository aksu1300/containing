package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
        model.rotate(0, -FastMath.PI/2, 0);
        
        this.attachChild(model);
    }
    
    public void move(final MotionPath route, float speed){
        MotionEvent motionControl = new MotionEvent(this, route);
        //motionControl.setDirectionType(MotionEvent.Direction.Path);
        //motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();

        route.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (route.getNbWayPoints() == wayPointIndex + 1) {
                    System.out.println("Point reached!");
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
    
    // </editor-fold>
}
