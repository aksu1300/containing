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
public class shipCrane extends Node {
    
    Vector3f loc;
    Container container;
    Material material;
    Spatial crane;
    Spatial grabbingGear;
    Spatial grabbingGearHolder;
    Spatial hookLeft;
    Spatial hookRight;
    AssetManager assetManager;
    Vector3f location;
    float speed;
    String id;
    float size;
    boolean status = false; // flase is up, true is down
    

public shipCrane(AssetManager _assetManager, float _size, Vector3f location) {
        this.assetManager = _assetManager;
        this.size = _size;
        this.location = location;

        //Init Sphere
        initContainer();
        
        // Init material of the container
        initMaterials();
       
        // Attach to the root node
        this.attachChild(crane);
        
        this.attachChild(grabbingGear);
        this.attachChild(grabbingGearHolder);
        this.attachChild(hookLeft);
        this.attachChild(hookRight);
        
    }

    // init Materials
    private void initMaterials() {
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        crane.setMaterial(material);
        
        grabbingGear.setMaterial(material);
        
        grabbingGearHolder.setMaterial(material);
        
        hookLeft.setMaterial(material);
        
        hookRight.setMaterial(material);
        
    }

    // init Container 
    private void initContainer() {
        // Load the container
        crane = assetManager.loadModel("Models/high/crane/dockingcrane/crane.j3o");
        crane.scale(size);
        
        grabbingGear = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGear.j3o");
        grabbingGear.scale(size);
        
        grabbingGearHolder = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGearHolder.j3o");
        grabbingGearHolder.scale(size);
        
        hookLeft = assetManager.loadModel("Models/high/crane/dockingcrane/hookLeft.j3o");
        hookLeft.scale(size);
        
        hookRight = assetManager.loadModel("Models/high/crane/dockingcrane/hookRight.j3o");
        hookRight.scale(size);
        
    }
    
     /**
     * Grabbing a container
     */
    public Vector3f getLocation(){
        return this.location;
    }
    
    public boolean pullGrabber(float tpf){  
        if(this.getChild(1).getLocalTranslation().y <= 9.5f)
        {
            System.out.print("FUCK JACCO");
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++)
               this.getChild(i).move(0, tpf, 0);
            return false; // Grabber is still moving
        }
        else
            return true; // Grabber reached top  
    }
    
    public boolean inGrabber(float tpf){
        if (this.getChild(1).getLocalTranslation().x < 20) 
        {
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++)
               this.getChild(i).move(tpf, 0, 0);
            return false; // Still moving grabber in.
        }
        else
            return true; // Max in,
        
        
    }
    
         /**
     * Taking a container
     */
    public boolean pushGrabber(float tpf){
        if(this.getChild(1).getLocalTranslation().y >= -10f)
        {
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++) 
               this.getChild(i).move(0,  tpf *- 1, 0);
            return false; // still pushing.
        }
        else
            return true; // max pushed.
        
        
    }
    /**
     * Grabbing a container
     */
    public void grabContainer(){
        this.getChild(1).move(0, size, size);
    }
    
    
    
    
}
