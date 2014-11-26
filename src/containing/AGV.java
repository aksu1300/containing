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

    public AGV(String id, Material material, AssetManager assetManager) {
        this.id = id;
        this.material = material;
        model = assetManager.loadModel("Models/high/agv/agv.j3o");
        model.setMaterial(material);
        model.scale(1.5f);
        this.attachChild(model);
    }

    public void updateSpeed(float uSpeed) {
        this.speed *= uSpeed; 
    }

    public void findPath() {
        
    }
    
    public Vector3f getCurrentloc(){
        return this.getLocalTranslation();
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
    
    public void Move(final MotionPath route, float speed){
        MotionEvent motionControl = new MotionEvent(this, route);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();
    }
    
}
