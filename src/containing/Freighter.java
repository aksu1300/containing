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
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Driving Ghost
 */
public class Freighter extends Node {
    
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    private float size;
    private boolean docked = false;
    private Material material;
    private AssetManager assetmanager;
    private Spatial shipSpatial;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    //public RigidBodyControl ballPhy;
    // A ship has a list of containers 
    //public List<Container> containers;
    private ArrayList<ArrayList<Stack<Container>>> lengtharray;
    private Vector3f loc;
    
    public Freighter(AssetManager assetmanager) {
        this.assetmanager = assetmanager;
        this.lengtharray = new ArrayList<ArrayList<Stack<Container>>>();
        // Init ship 
        initShip();

        // Init material of the ship
        initMaterial();
        
        //initHold
        initHold();

        // Attach the model to the 
        this.attachChild(shipSpatial);
    }

    //  Init ship
    public void initShip() {
        shipSpatial = assetmanager.loadModel("Models/high/ship/seaship.j3o");
        shipSpatial.rotate(0, -(FastMath.PI / 2), 0);
        shipSpatial.scale(0.5f);
    }

    public void initMaterial() {
        // Load the container
        material = new Material(assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");
        shipSpatial.setMaterial(material);
    }

    /**
     * initHold zet de containers op hun plaats op het deck. 
     */
    public void initHold() {
        /**
         * @lengtharray is the ammount of containers in the length of the ship.
         * @lengtharray gets a new arraylist of stacks for the ammount of width
         * of the containers on the ship.
         */
        for(int length = 0; length < 9; length++){
            this.lengtharray.add(new ArrayList<Stack<Container>>());
            for(int width = 0; width < 5; width++){
                this.lengtharray.get(length).add(new Stack<Container>());
                for(int height = 0; height < 3; height++){
                    Container x = new Container(assetmanager, 1.5f);
                    x.rotate(0, -(FastMath.PI / 2), 0);
                    x.setLocalTranslation(60-(length*24), size / 2 + 0.1f + (height*4.25f), -10 + (width * 5));
                    this.attachChild(x);
                    this.lengtharray.get(length).get(width).push(x);
                }
            }
        }
    }
    
    public ArrayList<ArrayList<Stack<Container>>> getContainerlist(){ return this.lengtharray; } 
    
    // Get a container from the boot
    public Container giveContainer(int length, int width) {
        // first deattach the container from the ship
        Container x = lengtharray.get(length).get(width).pop();
        this.detachChild(x);
        // Get the container from the list of containers of the ship
        // Give the deattached container from the ship back to the caller of the 
        // Method 
        return x;
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
        setLoc(route.getWayPoint(route.getNbWayPoints() - 1));
        motionControl.play();
        route.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (route.getNbWayPoints() == wayPointIndex + 1) {
                    setDocked();
                }
            }
        });
    }
    public void setLoc(Vector3f l){
        this.loc = l;
    }
    
    public Vector3f getLoc(){
        return this.loc;
    }
  
    
    public boolean getDocked() {
        return this.docked;
    }

    public void setDocked() {
        this.docked = !docked;
    }
}
