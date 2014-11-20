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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author account
 */
public class Ship extends Node {
    
    private AssetManager assetManager;
    private Spatial shipSpatial;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    private float size;
    private Material material;
    private Vector3f loc;
    
    //public RigidBodyControl ballPhy;
    private Vector3f lastLocation;
   
    // A ship has a list of containers 
    private List<Container> containers = new ArrayList<Container>();
    
    
    public Ship(AssetManager assetManager, float size){
        this.assetManager = assetManager;
        this.size = size;
        
        // Init ship 
        initShip();
        
        // Init material of the ship
        initMaterial();
      
        // Attach the model to the 
        this.attachChild(shipSpatial);
    }
    
    
    //  Init container
    public void initShip(){
        shipSpatial = assetManager.loadModel("Models/high/ship/seaship.j3o");
        shipSpatial.scale(size);
    }
    
    public void initMaterial(){
        // Load the container
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        shipSpatial.setMaterial(material);
    }
    
    
    // Add a container to the ship
    /**
     * 
     * @param container 
     */
    public void addContainer(Container container){
        // Adding a container to a ship
        this.containers.add(container);

        // Now move the new container to a specific place 
        // But first check how many there are and use this to for the place
        // on the ship
        // DEBUGGGING1
        int size = this.containers.size();
        container.setLocalTranslation(0, (size/2)+0.1f, 0);
        
                
        // Atach the container to the ship node
        this.attachChild(container);
        
    }
    
    // Get a container from the boot
    public Container getContainer(int id){
        // first deattach the container from the ship
        this.detachChild(this.containers.get(id));
        
        // Get the container from the list of containers of the ship
        // Give the deattached container from the ship back to the caller of the 
        // Method 
        return this.containers.get(id);
          
    }
    
    // Get the spatial
    public Spatial getSpatial(){
        return this.shipSpatial;
    }

    
}
