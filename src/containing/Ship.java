/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author account
 */
public class Ship extends Node {
    
    private AssetManager assetManager;
    private Spatial ship;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    private float size;
    private Material material;
    private Vector3f loc;
    
    //public RigidBodyControl ballPhy;
    public Vector3f lastLocation;
    
    
    public Ship(AssetManager assetManager, float size){
        this.assetManager = assetManager;
        this.size = size;
        
        // Init ship 
        initShip();
        
        // Init material of the ship
        initMaterial();
        
        
        // Attach the model to the 
        this.attachChild(ship);
    }
    
    
    //  Init container
    public void initShip(){
        ship = assetManager.loadModel("Models/high/ship/seaship.j3o");
        ship.scale(size);
    }
    
    public void initMaterial(){
        // Load the container
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        ship.setMaterial(material);
    }
}
