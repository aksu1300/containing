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
    float speed;
    String id;
    float size;

public shipCrane(AssetManager _assetManager, float _size) {
        this.assetManager = _assetManager;
        this.size = _size;

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
    public void pullGrabber(float tpf){
        System.out.println("Y  : " + this.getChild(1).getLocalTranslation().y);
        
        if(this.getChild(1).getLocalTranslation().y <= 9.5f)
        {
            this.getChild(1).move(0,tpf,0);
            this.getChild(2).move(0,tpf,0);
            this.getChild(3).move(0,tpf,0);
            this.getChild(4).move(0,tpf,0);
            this.getChild(5).move(0,tpf, 0);
        }
    }
    
         /**
     * Taking a container
     */
    public void pushGrabber(float tpf){
        System.out.println("Y  : " + this.getChild(1).getLocalTranslation().y);
        if(this.getChild(1).getLocalTranslation().y >= -10f)
        {
            this.getChild(1).move(0,tpf*-1,0);
            this.getChild(2).move(0,tpf*-1,0);
            this.getChild(3).move(0,tpf*-1,0);
            this.getChild(4).move(0,tpf*-1,0);
        }
        else{
            this.container = new Container(assetManager, 1);
            container.setLocalTranslation(new Vector3f(this.getChild(3).getLocalTranslation()).setY(this.getChild(3).getLocalTranslation().getY()+11.5f));
            this.attachChild(container);
        }
        
    }
    
    /**
     * Grabbing a container
     */
    public void grabContainer(){
        this.getChild(1).move(0, size, size);
    }
    
    
    
    
}
