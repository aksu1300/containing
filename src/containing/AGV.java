package containing;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
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

    Integer X;
    Integer Y;
    Material material;
    Spatial model;
    float speed;
    String ID;

    public AGV(String ID, Material material, AssetManager assetManager) {
        this.ID = ID;
        this.material = material;
        
        model = assetManager.loadModel("Models/agv.j3o");
        model.setMaterial(material);
        
        this.attachChild(model);
    }

    public void updateSpeed(double modifier) {
        this.speed *= modifier; 
    }

    public void findPath() {
    }
    
    
}
