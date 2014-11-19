/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author account
 */
public class Ship extends Node{
    
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private Geometry geometry;
    private Material material;
    private float size;
    private RigidBodyControl shipPhy;
    public Vector3f lastLocation;
    
    // Default constructur to instantiate a Ship
    public Ship(AssetManager _assetManager, BulletAppState _bulletAppState, float _size) {
        this.assetManager = _assetManager;
        this.bulletAppState = _bulletAppState;
        this.size = _size;

        //Init Materials
        initMaterials();

        //Init Ship
        initShip();

        //Init Physics
        initPhysics();

        //Attach ball with physics
        this.attachChild(geometry);
    }
    
    // Init materials
    private void initMaterials(){
        // Init material
       this.material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
       ;
       TextureKey key = new TextureKey("Textures/bowlingball.png");
       
       
    }
    
    
    // Init ships
    private void initShip(){
        
    }
    
    
    // Init physics
    private void initPhysics(){
    
    }
   
}
