package containing;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
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
    Spatial cargo; 
    float speed;
    String id;

    public AGV(String id, Material material, AssetManager assetManager, Vector3f loc) {
        this.id = id;
        this.material = material;
        this.loc = loc; 
        model = assetManager.loadModel("Models/high/agv/agv.j3o");
        model.setMaterial(material);
        model.scale(0.5f);
        this.attachChild(model);
    }

    public void updateSpeed(float uSpeed) {
        this.speed *= uSpeed; 
    }

    public void findPath() {
        
    }
    
    public void setContainer(Spatial cargo){
        this.cargo = cargo; 
        this.cargo.setLocalTranslation(0,0.6f,0);
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
    
}
