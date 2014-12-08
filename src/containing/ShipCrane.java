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
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Driving Ghost
 */
public class ShipCrane extends Node {

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
    int id;
    float size;
    boolean status = false; // flase is up, true is down
    BoundingVolume boundGrab;
    boolean up = true;
    boolean in = false;
    boolean done = false;

    public ShipCrane(AssetManager _assetManager, float _size, Vector3f _location, int _id) {
        this.assetManager = _assetManager;
        this.size = _size;
        this.location = _location;
        this.id = _id;

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

    // init Materials
    private void initMaterials() {
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        crane.setMaterial(material);

        grabbingGear.setMaterial(material);
        grabbingGear.getLocalTranslation().y = grabbingGear.getLocalTranslation().y + 8 ;
        grabbingGear.getLocalTranslation().x = grabbingGear.getLocalTranslation().x + 12 ;

        grabbingGearHolder.setMaterial(material);
        grabbingGearHolder.getLocalTranslation().y = grabbingGearHolder.getLocalTranslation().y + 8 ;
        grabbingGearHolder.getLocalTranslation().x = grabbingGearHolder.getLocalTranslation().x + 12 ;
        
        hookLeft.setMaterial(material);
        hookLeft.getLocalTranslation().y = hookLeft.getLocalTranslation().y + 8 ;
        hookLeft.getLocalTranslation().x = hookLeft.getLocalTranslation().x + 12 ;
        
        hookRight.setMaterial(material);
        hookRight.getLocalTranslation().y = hookRight.getLocalTranslation().y + 8 ;
        hookRight.getLocalTranslation().x = hookRight.getLocalTranslation().x + 12 ;

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
    public Vector3f getLocation() {
        return this.location;
    }

    public void inGrabber(float tpf) {
        if (this.getChild(1).getLocalTranslation().x < 20) {
            this.in = false;
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++) {
                this.getChild(i).move(tpf, 0, 0);
            }
        } 
        else {
            this.in = true;
        }
    }
    
    public void processShip(Freighter fr, float speed){
        
        
        MotionEvent motionControl = new MotionEvent(this, );
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);        
        motionControl.play();   
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
    
    /**
     * Moving to a container
     */
    public void moveTo(float speed,Freighter fr, int deck){
        final MotionPath route = new MotionPath();
        route.addWayPoint(this.location);
        
        route.addWayPoint(new Vector3f(this.location.x, this.location.y, 60 - 24*deck));
        this.location.z = 60 - 24*deck;
        //(fr.getContainerlist().get(deck).get(0).peek().getLocalTranslation().z)
        MotionEvent motionControl = new MotionEvent(this, route);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();
    }

    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();
    }


}
