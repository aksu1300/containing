/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Driving Ghost
 */
public class Crane extends Node {
    
    Vector3f loc;
    Material material;
    Spatial model;
    AssetManager assetManager;
    float speed;
    String id;
    
    public Crane(String id, Material strmaterial, AssetManager assetManager){
        this.id = id;
        this.material = strmaterial;
        this.assetManager = assetManager;
        
    }
    
    public void updateSpeed(float uSpeed){
        this.speed *= uSpeed;
    }
    
    public void oppakken(){
        
    }
    
    public void neerleggen(){
        
    }
    
    public void move(){
        
    }
    
}