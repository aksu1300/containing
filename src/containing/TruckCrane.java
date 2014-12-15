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
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.transport.Truck;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class TruckCrane extends Node {

    Vector3f loc;
    Container container;
    AGV agv;
    Truck truck;
    Material material;
    Spatial crane;
    Spatial grabbingGear;
    Spatial hookLeft;
    Spatial hookRight;
    AssetManager assetManager;
    Vector3f location;
    float speed;
    String id;
    boolean status = false; // flase is up, true is down
    BoundingVolume boundGrab;
    boolean done = false;
    int limit = 4;

    
    public void startProcedure(Truck t, AGV a){
        setAGV(a);
        setTruck(t);
        craneDown();
        
    }
    
    
    public TruckCrane(AssetManager _assetManager, Vector3f location) {

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

    }

    public void resetTC() {
        final MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(this.getLocalTranslation());
        dockingroute.addWayPoint(this.location);

        MotionEvent motionControl = new MotionEvent(this, dockingroute);

        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2);
        motionControl.play();


        craneUp();

    }

    public void moveTC() {

        final MotionPath dockingroute = new MotionPath();
        if (!done) {
            dockingroute.addWayPoint(this.getLocalTranslation());
            dockingroute.addWayPoint(new Vector3f(this.getLocalTranslation().x - 15, this.getLocalTranslation().y, this.getLocalTranslation().z));
        } else {
            dockingroute.addWayPoint(this.getLocalTranslation());
            dockingroute.addWayPoint(new Vector3f(this.getLocalTranslation().x + 15, this.getLocalTranslation().y, this.getLocalTranslation().z));
        }


        MotionEvent motionControl = new MotionEvent(this, dockingroute);

        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2);
        motionControl.play();

        dockingroute.addListener(new MotionPathListener() {
            public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (dockingroute.getNbWayPoints() == wayPointIndex + 1) {
                    setDoneTrue();
                    craneDown();
                }
            }
        });
    }

    public void craneUp() {

        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath dockingroute = new MotionPath();
            dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y + 5, this.getChild(i).getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.getChild(i), dockingroute);
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
                        if (!getDone() && getContainer() != null) {
                            moveTC();
                        }
                    }
                }
            });
        }
    }

    public void craneDown() {

        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        if (this.container != null) {
            for (int i = 1; i < this.children.size(); i++) {

                final MotionPath dockingroute = new MotionPath();
                dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
                dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y - 5, this.getChild(i).getLocalTranslation().z));

                MotionEvent motionControl = new MotionEvent(this.getChild(i), dockingroute);
                grabMotion.add(motionControl);
            }
        } else {
            for (int i = 1; i < this.children.size() - 1; i++) {

                final MotionPath dockingroute = new MotionPath();
                dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
                dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y - 5, this.getChild(i).getLocalTranslation().z));

                MotionEvent motionControl = new MotionEvent(this.getChild(i), dockingroute);
                grabMotion.add(motionControl);
            }
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {

                        if (getDone()) {
                            releaseContainer();
                            resetTC();
                        } else {
                            setContainer(agv.cargo);
                            releaseAGV();
                            craneUp();

                        }

                    }
                }
            });
        }
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

    public void setDoneTrue() {

        this.done = true;
    }

    public void setDoneFalse() {

        this.done = false;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setAGV(AGV a) {
        this.agv = a;
        this.attachChild(agv);
        this.agv.setLocalTranslation(0, 0, 0);

    }

    public AGV releaseAGV() {
        this.detachChild(agv);
        return this.agv;
    }

    public void releaseContainer() {
        this.detachChild(container);
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container c) {
        this.container = c;
        this.attachChild(this.container);
        this.container.setLocalTranslation(this.getChild(1).getLocalTranslation().x, this.getChild(1).getLocalTranslation().y + 6.2f, this.getChild(1).getLocalTranslation().z);
    }

    public void setTruck(Truck t) {
        this.truck = t;
    }
}
