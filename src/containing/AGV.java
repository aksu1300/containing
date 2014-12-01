package containing;

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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Driving Ghost
 */
public class AGV extends Node {

    Vector3f loc;
    Material material;
    Spatial model;
    Container cargo; 
    float speed;
    String id;
    AssetManager assetManager;

    public AGV(String id, AssetManager assetManager) {
        this.id = id;
        this.assetManager = assetManager;
        initModel();
        initMaterial();
        this.attachChild(model);
    }

    public void updateSpeed(float uSpeed) {
        this.speed *= uSpeed; 
    }

    public void findPath() {
        
    }
    
    void initModel(){
        model = assetManager.loadModel("Models/high/agv/agv.j3o");
    }
    
    void initMaterial(){
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        model.setMaterial(material);
    }
    
    public Vector3f getCurrentloc(){
        return this.getLocalTranslation();
    }
     
    public float getSpeed(){
        if(this.cargo != null){
            return 0.125f;
        }
        else{
            return 0.25f;
        }
    }
    
    public void setContainer(Container cargo){
        this.cargo = cargo; 
        this.cargo.setLocalTranslation(0,1.5f,0);
        this.attachChild(this.cargo);
        
    }
    
    public void setLocation(Vector3f newloc){
        this.loc = newloc;
    }
    
    public Spatial getContainer(){
        return cargo;
    }
    
    public void removeContainer(){
        this.detachChild(cargo);
        this.cargo = null;
    }
    
    public void Move(MotionPath route, float speed){
        
        MotionEvent motionControl = new MotionEvent(this, route);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        
        motionControl.play();
    }
    
}
