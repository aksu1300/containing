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
import java.util.Stack;

/**
 *
 * @author Driving Ghost
 */
public class Freighter extends Node {
    
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;

    private boolean docked = false;
    private Material material;
    private AssetManager assetmanager;
    private Spatial shipSpatial;
    //private BulletAppState bulletAppState;
    //public Geometry ballGeo;
    //public RigidBodyControl ballPhy;
    // A ship has a list of containers 
    public ArrayList<ArrayList<Stack<Container>>> containers;
       
    public Freighter(AssetManager assetmanager) {
        this.assetmanager = assetmanager;
        containers = new ArrayList<ArrayList<Stack<Container>>>();
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
        shipSpatial.scale(0.8f);
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
        for (int x = 0; x < 21; x++){
            containers.add(new ArrayList<Stack<Container>>());
            for (int z = 0; z < 10; z++ ){
                containers.get(x).add(new Stack<Container>());
                for (int y = 0; y < 4; y++){
                    Container c = new Container(assetmanager, 1);
                    containers.get(x).get(z).push(c);
                    c.rotate(0,FastMath.PI/2,0);
                    this.attachChild(c);
                    c.setLocalTranslation(shipSpatial.getLocalTranslation().x+130-(x*18f), y*2.8f, shipSpatial.getLocalTranslation().z +11.5f-(z*2.5f));
                }
            }
        }
    }
    
    // Get a container from the boot
    public Container getContainer(int id) {
        return null;
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
                    setDocked(true);
                }
            }
        });
    }
    public boolean getDocked() {
        return this.docked;
    }

    public void setDocked(boolean docked) {
        this.docked = docked;
    }
}
