package containing.storage;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.AGV;
import containing.Container;
import java.util.ArrayList;

public class StorageCrane extends Node {

    Vector3f loc;
    Container container;
    Material material;
    Spatial crane;
    Spatial grabbingGear;
    Spatial grabbingGearHolder;
    Spatial hookLeft;
    Spatial hookRight;
    Storage s;
    AssetManager assetManager;
    Vector3f location;
    Vector3f c_location;
    Vector3f s_location;
    AGV agv;
    float speed;
    String id;
    float size;
    BoundingVolume boundGrab;
    boolean bringtostorage = false;
    boolean takefromstorage = false;
    public boolean needcontainer = false;
    Node Hook;

    public StorageCrane(AssetManager _assetManager, float _size, Vector3f location) {
        this.assetManager = _assetManager;
        this.size = _size;
        this.location = location;

        //Init Sphere
        initGrabber();
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

        this.setLocalTranslation(this.location);
        initBounding();

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
    private void initGrabber() {
        // Load the container
        crane = assetManager.loadModel("Models/high/crane/storagecrane/crane.j3o");
        crane.scale(size);

        grabbingGear = assetManager.loadModel("Models/high/crane/storagecrane/grabbingGear.j3o");
        grabbingGear.scale(size);

        grabbingGearHolder = assetManager.loadModel("Models/high/crane/storagecrane/grabbingGearHolder.j3o");
        grabbingGearHolder.scale(size);

        hookLeft = assetManager.loadModel("Models/high/crane/storagecrane/hookLeft.j3o");
        hookLeft.scale(size);

        hookRight = assetManager.loadModel("Models/high/crane/storagecrane/hookRight.j3o");
        hookRight.scale(size);
    }

    public void bringToStorage(Storage storage, AGV agv) {
        this.bringtostorage = true;
        this.agv = agv;
        this.s = storage;
        this.c_location = agv.getLocalTranslation();
        moveCrane(c_location);

    }

    public void takeFromStorage(Storage storage, Container container) {
        this.takefromstorage = true;
        this.s = storage;
        this.container = container;
        this.s_location = container.getLocalTranslation();
        moveCrane(s_location);
        //container locatie heen gaan.
    }

    public void procesStorageCrane(Storage storage, Vector3f location) {
        this.location = location;
        this.container = new Container(assetManager, 1f);
        this.s = storage;
//        moveCrane(location);
        craneDown(location);
    }

    public void moveCrane(final Vector3f location) {

        ArrayList<MotionEvent> craneMotion = new ArrayList<MotionEvent>();


        final MotionPath craneroute = new MotionPath();
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, location.z));

        MotionEvent motionControl = new MotionEvent(this, craneroute);
        craneMotion.add(motionControl);





        for (MotionEvent me : craneMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        craneRight(location);
                    }
                }
            });
        }

    }

    public void moveCraneAgain(final Vector3f location) {

        ArrayList<MotionEvent> craneMotion = new ArrayList<MotionEvent>();


        final MotionPath craneroute = new MotionPath();
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
        craneroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, 240));

        MotionEvent motionControl = new MotionEvent(this, craneroute);
        craneMotion.add(motionControl);





        for (MotionEvent me : craneMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        craneDownAgain(location);
                    }
                }
            });
        }

    }

    public void craneRight(final Vector3f location) {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x - 5, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));

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
                        craneDown(location);
                    }
                }
            });
        }
    }

    public void craneLeft() {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x - 5, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));

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

    public void craneUp(final Vector3f location) {
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, 0f, this.getChild(i).getLocalTranslation().z));

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
                        moveCraneAgain(location);
                    }
                }
            });
        }
    }

    public void craneDown(final Vector3f location) {
        //check for container has to happen 
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {


            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, -location.y, this.Hook.getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(Hook, trainroute);
            grabMotion.add(motionControl);
        }


        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        if (bringtostorage == true) {
                            //get the agv container here
                            setContainer(agv.getContainer());
                            //need a boolean here that tells it what it is doing

                            craneUp(location);
                        } else if (takefromstorage == true) {
                        }


//                        craneUp(location);
                    }

                }
            });
        }
    }

    public void craneDownAgain(final Vector3f location) {
        //check for container has to happen 
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {


            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, this.Hook.getLocalTranslation().y, this.Hook.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.Hook.getLocalTranslation().x, -location.y, this.Hook.getLocalTranslation().z));

            MotionEvent motionControl = new MotionEvent(Hook, trainroute);
            grabMotion.add(motionControl);
        }


        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        if (bringtostorage == true) {
                            //get the agv container here
                            setContainer(agv.getContainer());
                            //need a boolean here that tells it what it is doing

                            craneUp(location);
                        } else if (takefromstorage == true) {
                        }

                        s.addContainer(container);
//                        craneUp(location);
                    }

                }
            });
        }
    }

    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();
    }

    public void setContainer(Container c) {
        this.container = c;
        Hook.attachChild(container);
        this.container.setLocalTranslation(0f, 25f, 0f);

    }

    public void releaseContainer() {
        this.detachChild(container);
    }
}
