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
    AGV a;
    AssetManager assetManager;
    Vector3f location;
    float speed;
    String id;
    float size;
    boolean status = false; // flase is up, true is down
    BoundingVolume boundGrab;
    public boolean moving = true;
    Vector3f c_loc;
    boolean idle = true;
    boolean done = false;
    boolean movetoAVG = false;// is true after it has a container, this is so the motionpath has something to work with
    boolean needscontainer = false;
    Freighter f;
    Node Hook;

    public ShipCrane(AssetManager _assetManager, float _size, Vector3f location) {
        this.assetManager = _assetManager;
        this.size = _size;
        this.location = location;

        //Init Sphere
        initContainer();

        // Init material of the container
        initMaterials();

        // Attach to the root node
        this.attachChild(crane);
        
        Hook = new Node("hook");
        Hook.attachChild(grabbingGear);
        Hook.attachChild(grabbingGearHolder);
        Hook.attachChild(hookLeft);
        Hook.attachChild(hookRight);
        this.attachChild(Hook);

      
        initBounding();

    }

    public void procesCrane(Container container , Freighter freighter, AGV agv) {
        
            this.f = freighter;
            this.container = container;
            this.a = agv;
            location = container.getLocalTranslation();
            c_loc = location;
            idle = false;
           
            moveCrane(location);
        
    }

    public void moveCrane(Vector3f location) {

        ArrayList<MotionEvent> craneMotion = new ArrayList<MotionEvent>();


        final MotionPath craneroute = new MotionPath();
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, location.x  + 1.1f));

        MotionEvent motionControl = new MotionEvent(this, craneroute);
        craneMotion.add(motionControl);
        this.moving = false;




        for (MotionEvent me : craneMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        grabberMoveForward();
                    }
                }
            });
        }

    }

    public void grabberMoveForward() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {

            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x + c_loc.z - 10.1f, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.Hook, trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(2);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        grabberDown();

                    }
                }
            });
        }
    }

    public void grabberMoveBackward() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {

            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x + 20.5f, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.Hook, trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(2);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        grabberDownAgain();
                        

                        
                    }
                }
            });
        }
    }

    public void grabberUp() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y + 7, this.Hook.getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(this.Hook, trainroute);
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
                        if (needscontainer == false) {
                          
                            grabberMoveBackward();
                            
                        }
                    }
                }
            });
        }
    }
    
    public void grabberDownAgain() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            
//              nroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x , this.Hook.getLocalTranslation().y - c_loc.z , this.Hook.getLocalTranslation().z ));
            
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y - 5f, this.Hook.getLocalTranslation().z));
            
            MotionEvent motionControl = new MotionEvent(this.Hook, trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(2);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        
                        a.setContainer(container);
                    }
                }
            });
        }
    }

    public void grabberDown() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            if(movetoAVG == false){
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x , this.Hook.getLocalTranslation().y - c_loc.z , this.Hook.getLocalTranslation().z ));
            }else{
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
                trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y - 5f, this.Hook.getLocalTranslation().z));
            }
            MotionEvent motionControl = new MotionEvent(this.Hook, trainroute);
            grabMotion.add(motionControl);
        }

        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(2);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        setContainer(f.getContainer(1, 1));
                        
                        if (movetoAVG == false) {
                            if (needscontainer == false) {
                                
//                                grabberMoveBackward();
                                grabberUp();
                            }
                            
                        }else{
                            //this is were we unload it on the agv
                            releaseContainer();
                            movetoAVG = false;
                        }
                        
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

    public Vector3f getLocation() {
        return this.location;
    }

    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();

    }

    public void setContainer(Container container) {
        this.container = container;
        Hook.attachChild(container);
        this.container.setLocalTranslation(this.Hook.getLocalTranslation().x + 1 , this.Hook.getLocalTranslation().y + 20 , this.Hook.getLocalTranslation().z);
        this.container.rotate(0f, FastMath.PI / 2, 0f);
        
        
        setNeedsContainer(false);
        
    }
    
    

    private void releaseContainer() {
        this.detachChild(container);
        
    }

    public void setNeedsContainer(boolean inneed) {
        this.needscontainer = inneed;
        
    }

    public boolean getNeedsContainer() {
        return this.needscontainer;
        
    }
}
