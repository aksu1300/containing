/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.transport.Truck;

/**
 *
 * @author Driving Ghost
 */
public class TruckCrane extends Node {
    
    Vector3f loc;
    Container container;
    AGV agv;
    Material material;
    Spatial crane;
    Spatial grabbingGear;
    Spatial truck;
    Spatial hookLeft;
    Spatial hookRight;
    AssetManager assetManager;
    Vector3f location;
    float speed;
    
    String id;
    boolean status = false; // flase is up, true is down
    BoundingVolume boundGrab;
    boolean up = true;
    boolean in = false;
    boolean done = false;
    
    public TruckCrane(AssetManager _assetManager, Vector3f location){
        
        this.assetManager = _assetManager;
        this.location = location;

        //Init Sphere
        initCrane();

        // Init material of the container
        initMaterials();

        // Attach to the root node
        this.attachChild(crane);

        this.attachChild(grabbingGear);
        this.attachChild(hookLeft);
        this.attachChild(hookRight);
        
        this.setLocalTranslation(location);
        
        initBounding();
        
    }
    
    private void initMaterials() {
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        crane.setMaterial(material);
       
        grabbingGear.setMaterial(material);

        hookLeft.setMaterial(material);

        hookRight.setMaterial(material);
    }
    
    private void initCrane() {
        // Load the container
        crane = assetManager.loadModel("Models/high/crane/truckcrane/crane.j3o");
        
        grabbingGear = assetManager.loadModel("Models/high/crane/truckcrane/grabbingGear.j3o");
        
        hookLeft = assetManager.loadModel("Models/high/crane/truckcrane/hookLeft.j3o");
        
        hookRight = assetManager.loadModel("Models/high/crane/truckcrane/hookRight.j3o");

    }
 
    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();

    }
    
    public void setAGV(AGV a){
        this.agv = a;
    }
    
    public AGV releaseAGV(){
        return this.agv;
    }
    
    public void setContainer(Container c){
        this.container = c;
        c.setLocalTranslation(this.getLocalTranslation().x-1, this.getLocalTranslation().y+2, this.getLocalTranslation().z);
    }
    
    public void setTruck(Truck t){
        this.truck = t;
    }
    
}
