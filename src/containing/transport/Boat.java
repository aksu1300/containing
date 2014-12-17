/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;
import java.util.ArrayList;
import java.util.List;

public class Boat extends Node{
    
    private AssetManager assetManager;
    private Spatial shipSpatial;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    private boolean docked = false;
    private Material material;
    private Vector3f loc;
    //public RigidBodyControl ballPhy;
    private Vector3f lastLocation;
    // A ship has a list of containers 
    private List<Container> containers;

    

    // Get the list of the containers
    public List<Container> getContainers() {
        return containers;
    }

    // Set a list of container to the ship container list
    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public Boat(AssetManager assetManager) {
        this.assetManager = assetManager;
        containers = new ArrayList<Container>();
        initHold();
        initShip();
        initMaterial();
        this.attachChild(shipSpatial);
    }
    
    public Boat(String id, Vector3f vector3f,AssetManager assetManager) {
        this.assetManager = assetManager;
        this.setName(id);
        containers = new ArrayList<Container>();
        initHold();
        initShip();
        initMaterial();
        this.attachChild(shipSpatial);
    }   
    
    //  Init ship
    public void initShip() {
        shipSpatial = assetManager.loadModel("Models/high/ship/seaship.j3o");
        shipSpatial.rotate(0, -(FastMath.PI / 2), 0);
        shipSpatial.scale(0.25f);
    }

    public void initMaterial() {
        // Load the container
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        shipSpatial.setMaterial(material);
    }

    /**
     * initHold zet de containers op hun plaats op het deck. 
     */
    public void initHold() {

    }

    
    // Get a container from the boot
    public Container getContainer(int id) {
        // first deattach the container from the ship
        this.detachChild(this.containers.get(id));

        // Get the container from the list of containers of the ship
        // Give the deattached container from the ship back to the caller of the 
        // Method 
        return this.containers.get(id);
    }

    // Get the spatial
    public Spatial getSpatial() {
        return this.shipSpatial;
    }

    public void Move(final MotionPath route, float speed) {
        MotionEvent motionControl = new MotionEvent(this, route);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(speed);
        motionControl.play();

        route.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (route.getNbWayPoints() == wayPointIndex + 1) {
                    setDocked();
                } else {
                    System.out.println("nope");
                }
            }
        });
    }
    public boolean getDocked() {
        return this.docked;
    }

    public void setDocked() {
        this.docked = !docked;
    }

}


