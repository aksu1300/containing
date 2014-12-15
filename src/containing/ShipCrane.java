/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Umit
 */
public class ShipCrane extends Node {

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
    BoundingVolume boundGrab;
    boolean up = true;
    boolean in = false;
    boolean done = false;

    public ShipCrane(AssetManager _assetManager, Vector3f location) {
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
        initBounding();

    }
    
    public void moveCranes(Freighter f) {
        
        final MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(this.getLocalTranslation());
        dockingroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, f.getLocalTranslation().z -(230)));

        MotionEvent motionControl = new MotionEvent(this, dockingroute);

        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2);
        motionControl.play();
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
        grabbingGear = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGear.j3o");
        grabbingGearHolder = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGearHolder.j3o");
        hookLeft = assetManager.loadModel("Models/high/crane/dockingcrane/hookLeft.j3o");
        hookRight = assetManager.loadModel("Models/high/crane/dockingcrane/hookRight.j3o");
    }
    
    /**
     * Grabbing a container
     */
    public Vector3f getLocation() {
        return this.location;
    }

    
    /**
     * Grabbing a container
     */
    public void grabContainer(Container grabbed) {
        this.container = grabbed;
        this.container.rotate(0, (FastMath.PI / 2), 0);
        this.container.setLocalTranslation(this.getChild(1).getLocalTranslation().x, this.getChild(1).getLocalTranslation().y + 10, this.getChild(1).getLocalTranslation().z);
        this.attachChild(container);
    }

    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();
    }
}
