/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.storage;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;

/**
 *
 * @author Driving Ghost
 */
public class StorageCrane extends Node {
    
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
    
    public StorageCrane(AssetManager _assetManager, float _size, Vector3f location){
         this.assetManager = _assetManager;
        
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
        
        this.setLocalTranslation(this.location);
        
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
        crane = assetManager.loadModel("Models/high/crane/storagecrane/crane.j3o");
        //crane.scale(1, 0.5f, 1);
        
        grabbingGear = assetManager.loadModel("Models/high/crane/storagecrane/grabbingGear.j3o");
        grabbingGear.scale(size);
        
        grabbingGearHolder = assetManager.loadModel("Models/high/crane/storagecrane/grabbingGearHolder.j3o");
        grabbingGearHolder.scale(size);
        
        hookLeft = assetManager.loadModel("Models/high/crane/storagecrane/hookLeft.j3o");
        hookLeft.scale(size);
        
        hookRight = assetManager.loadModel("Models/high/crane/storagecrane/hookRight.j3o");
        hookRight.scale(size);   
    }
    
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
    
    public boolean pullGrabber(float tpf){  
        if(this.getChild(1).getLocalTranslation().y <= 9.5f)
        {
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++)
               this.getChild(i).move(0, tpf, 0);
            return false; // Grabber is still moving
        }
        else
            return true; // Grabber reached top  
    }
    
    public void moveOut(float tpf, int i){
        if(this.getChild(1).getLocalTranslation().z <= (55 + (i * 1.5))){
            for (int j = 0; j < this.children.size(); j++)
               this.getChild(j).move(0, 0, tpf * 20);
        }
        
    }
    
    
}
