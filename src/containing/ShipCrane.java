/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

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
    public boolean moving = true;
    Vector3f c_loc;

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

    public void moveCrane(Vector3f location) {
        if (this.moving) {
            ArrayList<MotionEvent> craneMotion = new ArrayList<MotionEvent>();

           
            final MotionPath craneroute = new MotionPath();
            craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
            craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z - location.x));

            MotionEvent motionControl = new MotionEvent(this, craneroute);
            craneMotion.add(motionControl);
            this.moving = false;
            c_loc = location;
            System.out.println(c_loc);


            for (MotionEvent me : craneMotion) {
                final MotionPath mp = me.getPath();
                me.setInitialDuration(10f);
                me.setSpeed(1);
                me.play();

                mp.addListener(new MotionPathListener() {
                    public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                        if (mp.getNbWayPoints() == wayPointIndex + 1) {
                       grabberForward();
                        }
                    }
                });
            }
        }
    }

    public void grabberBackward() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x + 5, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.getChild(i), trainroute);
            grabMotion.add(motionControl);
            
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                    }
                }
            });
        }
    }

    public void grabberForward() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            System.out.println(c_loc.z);
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x + c_loc.z - c_loc.y, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.getChild(i), trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
//                        grabberBackward();

                    }
                }
            });
        }
    }

    public void grabberUp() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y + 5, this.getChild(i).getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.getChild(i), trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        grabberForward();
                    }
                }
            });
        }
    }

    public void grabberDown() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y - 5, this.getChild(i).getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.getChild(i), trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        grabberUp();

                    }
                }
            });
        }
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
