/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Driving Ghost
 */
public class Freighter extends Node {
    
    private AssetManager assetManager;
    private Spatial shipSpatial;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    private float size;
    private boolean docked = false;
    private Material material;
    private Vector3f loc;
    //public RigidBodyControl ballPhy;
    private Vector3f lastLocation;
    // A ship has a list of containers 
    public List<Container> containers;
    
    public Freighter(AssetManager assetManager) {
        this.assetManager = assetManager;
        containers = new ArrayList<Container>();

        //initHold
        initHold();

        // Init ship 
        initShip();

        // Init material of the ship
        initMaterial();

        // Attach the model to the 
        this.attachChild(shipSpatial);
    }

    //  Init ship
    public void initShip() {
        shipSpatial = assetManager.loadModel("Models/high/ship/seaship.j3o");
        shipSpatial.rotate(0, -(FastMath.PI / 2), 0);
        shipSpatial.scale(0.5f);
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

        for (int i = 0; i < 9; i++) { //aantal decks
            for (int y = 0; y < 3; y++) { //aantal niveaus
                for (int z = 0; z < 5; z++) { //aantal per niveau
                    Container x = new Container(assetManager, 1.5f);
                    x.rotate(0, -(FastMath.PI / 2), 0);
                    x.setLocalTranslation(60-(i*24), size / 2 + 0.1f + (y*4.25f), -10 + (z * 5));
                    this.attachChild(x);
                    containers.add(x);
                }
            }
        }
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
