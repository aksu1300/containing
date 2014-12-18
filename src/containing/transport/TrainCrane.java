package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cinematic.MotionPath;
<<<<<<< HEAD
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import containing.AGV;
import containing.Container;
import containing.Harbor;
import java.util.ArrayList;

public class TrainCrane extends Node {

=======
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.AGV;
import containing.Container;
import java.util.ArrayList;

public class TrainCrane extends Node {
    Vector3f loc;
    Container container;
    Container cargo;
    Material material;
    Wagon wagon;
    Train train;
    AGV agv;
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    Spatial crane;
    Spatial grabbingGear;
    Spatial grabbingGearHolder;
    Spatial hookLeft;
    Spatial hookRight;
    Material material;
    AssetManager assetmanager;
    BoundingVolume boundGrab;

    Vector3f location;
    float speed;
<<<<<<< HEAD
	
    Container container;
    Harbor harbor;
	
    boolean idle;
    boolean status = false; // false is up, true is down
    boolean up = true;
    boolean in = false;
    boolean done = false;

    public TrainCrane(AssetManager assetManager, Vector3f location, Harbor harbor) {
        this.assetmanager = assetManager;
=======
    String id;
    float size;
    boolean idle = true;// its not doing anything, so it can be used
    boolean done = true;
    boolean motion1 = false;
    BoundingVolume boundGrab;


    
    public TrainCrane(AssetManager assetManager, float size, Vector3f location) {
        this.assetManager = assetManager;
        this.size = size;
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
        this.location = location;
        this.harbor = harbor;

        initModels();
        initMaterials();

        this.attachChild(crane);
        this.attachChild(grabbingGear);
        this.attachChild(grabbingGearHolder);
        this.attachChild(hookLeft);
        this.attachChild(hookRight);

        initBounding();
<<<<<<< HEAD
        this.idle = true;
=======
        
        //muhahah this works
//        this.wagon.setCargo(new Container(this.assetManager,1.0f));
        //init this in the simulation
//        craneDown();
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    }

    private void initMaterials() {
        this.material = new Material(assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");

        crane.setMaterial(material);
        grabbingGear.setMaterial(material);
        grabbingGearHolder.setMaterial(material);
        hookLeft.setMaterial(material);
        hookRight.setMaterial(material);

    }

    private void initModels() {
        crane = assetmanager.loadModel("Models/high/crane/traincrane/crane.j3o");
        grabbingGear = assetmanager.loadModel("Models/high/crane/traincrane/grabbingGear.j3o");
        grabbingGearHolder = assetmanager.loadModel("Models/high/crane/traincrane/grabbingGearHolder.j3o");
        hookLeft = assetmanager.loadModel("Models/high/crane/traincrane/hookLeft.j3o");
        hookRight = assetmanager.loadModel("Models/high/crane/traincrane/hookRight.j3o");
    }

    private void initBounding() {
        this.boundGrab = this.getChild(1).getWorldBound();
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public boolean getstate() {
        return this.idle;
    }

    //nieuw
    public void giveContainer(AGV agv, Container container, Wagon Wagon) {
        this.idle = false;
        Wagon wagon;
        wagon = calcWagon(container);
        moveTo(wagon);
        takeContainer(wagon);
        placeContainer(agv);
    }
<<<<<<< HEAD

    private Wagon calcWagon(Container container) {
	Train train = this.harbor.train;

        for (Wagon wagon : train.getWagons()) {
            if (wagon.getCargo() == container) {
                return wagon;
            }
        }
        return null;
    }

    private void moveTo(Wagon wagon) {
        this.move(wagon.getLocation());
    }

    private void takeContainer(Wagon wagon) {
        TakeT();
        this.container = wagon.getCargo();
        this.container.rotate(0, (FastMath.PI * 0.5f), 0);
        this.container.setLocalTranslation(this.getChild(1).getLocalTranslation().x, this.getChild(1).getLocalTranslation().y + 2, this.getChild(1).getLocalTranslation().z);
        wagon.resetCargo();
        this.attachChild(container);
        retractT();
    }

    private void TakeT() {
        MotionPath path = new MotionPath();
        path.addWayPoint(location);
	
        Move(path);
    }

    private void retractT() {
    }

    private void placeContainer(AGV agv) {
        grabA();
        agv.setContainer(this.container);
        this.container = null;
        pullA();
=======
    public void doMove(Wagon wagon,Train train){
            
            setWagon(wagon);
//            System.out.println(wagon.getLocation());
//            System.out.println(wagon.getLocalTranslation());
            moveCrane(wagon.getLocation(),train.getLocation());
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    }
    
    public void moveCrane(Vector3f location , Vector3f trainloc){
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(trainloc.x - location.z , this.getLocalTranslation().y , this.getLocalTranslation().z ));

<<<<<<< HEAD
    private void grabA() {
    }

    private void pullA() {
    }

    public void Move(MotionPath path) {

        MotionEvent motionControl = new MotionEvent(this, path);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(this.speed);

        motionControl.play();
    }

    //oud.
    public void inGrabber(float tpf) {
        if (this.getChild(1).getLocalTranslation().x < 20) {
            this.in = false;
            tpf = tpf * 4;
            for (int i = 1; i < this.children.size(); i++) {
                this.getChild(i).move(tpf, 0, 0);
            }
        } else {
            this.in = true;
        }
    }

    public void pullGrabber(float tpf) {
        if (this.getChild(1).getLocalTranslation().y <= 9) {
            this.getChild(1).move(0, tpf, 0);
            this.getChild(2).move(0, tpf, 0);
            this.getChild(3).move(0, tpf, 0);
            this.getChild(4).move(0, tpf, 0);
            if (this.container != null) {
                //this.getChild(5).move(0, tpf, 0);
            }
        } else {
            this.up = true;
        }
    }

    public void pushGrabber(float tpf) {
        this.up = false;
        if (this.getChild(1).getLocalTranslation().y >= -10f) {
            for (int i = 1; i < this.children.size(); i++) {
                this.getChild(i).move(0, tpf * -1, 0);
            }
=======
            MotionEvent motionControl = new MotionEvent(this, trainroute);
            grabMotion.add(motionControl);
        
        
        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();

            mp.addListener(new MotionPathListener() {
                public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                    if (mp.getNbWayPoints() == wayPointIndex + 1) {
                        //crane down when has a container so that it sits on an agv
                        craneDown();
                        setIdle(false);
                    }
                }
            });
        }
    }
    
    //make some bools to see if the crane did a certain movement andneeds to do something else
    public void craneRight(){
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x +5, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z ));

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
                        if(idle == false){
                            setIdle(true);
                        }
                        
                    }
                }
            });
        }
    }
    public void craneLeft(){
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x -5, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z ));
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472

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
                        //crane down when has a container so that it sits on an agv
                       setDone(true);
                        
                    }
                }
            });
        }
    }
<<<<<<< HEAD
=======
    public void craneUp(){
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y + 2.2f, this.getChild(i).getLocalTranslation().z));

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
                        if(motion1 == true){
                            releaseWagon();
                            craneRight();
                            
                            
                        }else{
                            craneLeft();
                        }
                        
                    }
                }
            });
        }
    }
    public void craneDown(){
        //check for container has to happen 
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        for (int i = 1; i < this.children.size(); i++) {
           

                final MotionPath trainroute = new MotionPath();
                trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y, this.getChild(i).getLocalTranslation().z));
                trainroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y - 2.2f, this.getChild(i).getLocalTranslation().z));

                MotionEvent motionControl = new MotionEvent(this.getChild(i), trainroute);
                grabMotion.add(motionControl);
        }
        
        
        for (MotionEvent me : grabMotion) {
            final MotionPath mp = me.getPath();
            me.setInitialDuration(10f);
            me.setSpeed(1);
            me.play();
            
            mp.addListener( new MotionPathListener() {
            public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                //crane up if picked up container
                if(motion1 == true){ 
                        releaseContainer();
                        craneUp();
                    
                }else{
                   setContainer(wagon.getCargo());
                   craneUp();
                }
            }
      });
            
        }
        
//        dockingroute.addWayPoint(new Vector3f(this.getChild(i).getLocalTranslation().x, this.getChild(i).getLocalTranslation().y + 5, this.getChild(i).getLocalTranslation().z));
    }
    
    public void setWagon(Wagon w){
        this.wagon = w;
    }
    public void releaseWagon(){
        this.wagon = null;
    }
    public Vector3f getLocation() {
        return this.location;
    }
    private void initBounding() {
        boundGrab = this.getChild(1).getWorldBound();
    }
    public void setAGV(AGV a) {
        this.agv = a;
        this.attachChild(agv);
        this.agv.setLocalTranslation(0, 0, 0);

    }
    public Container getContainer() {
        return this.container;
    }
    
    public void setIdle(boolean idle){
        this.idle =idle;
    }
    
    public void setDone(boolean done){
        this.done = done;
        this.idle = true;
    }

    public void setContainer(Container c) {
        this.container = c;
        this.container.setLocalTranslation(this.getChild(1).getLocalTranslation().x, this.getChild(1).getLocalTranslation().y +3.8f , this.getChild(1).getLocalTranslation().z);
        this.attachChild(this.container);
    }
    
    public void releaseContainer() {
        this.detachChild(container);
    }
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
}
