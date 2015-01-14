package containing.transport;

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

public class TrainCrane extends Node {
    Vector3f loc;
    Container container;
    Container cargo;
    Material material;
    Wagon wagon;
    AGV agv;
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
    boolean idle = true;// its not doing anything, so it can be used
    boolean done = true;
    boolean movetoAGV = false;
    BoundingVolume boundGrab;


    
    public TrainCrane(AssetManager assetManager, float size, Vector3f location) {
        this.assetManager = assetManager;
        this.size = size;
        this.location = location;
    
        initContainer();
        initMaterials();
        this.attachChild(crane);

        this.attachChild(grabbingGear);
        this.attachChild(grabbingGearHolder);
        this.attachChild(hookLeft);
        this.attachChild(hookRight);
        initBounding();
        
        //muhahah this works
//        this.wagon.setCargo(new Container(this.assetManager,1.0f));
        //init this in the simulation
//        craneDown();
    }
    
    private void initMaterials() {
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        crane.setMaterial(material);

        grabbingGear.setMaterial(material);

        grabbingGearHolder.setMaterial(material);

        hookLeft.setMaterial(material);

        hookRight.setMaterial(material);

    }
    
    private void initContainer() {
        // Load the container
        crane = assetManager.loadModel("Models/high/crane/traincrane/crane.j3o");
        crane.scale(size);

        grabbingGear = assetManager.loadModel("Models/high/crane/traincrane/grabbingGear.j3o");
        grabbingGear.scale(size);

        grabbingGearHolder = assetManager.loadModel("Models/high/crane/traincrane/grabbingGearHolder.j3o");
        grabbingGearHolder.scale(size);

        hookLeft = assetManager.loadModel("Models/high/crane/traincrane/hookLeft.j3o");
        hookLeft.scale(size);

        hookRight = assetManager.loadModel("Models/high/crane/traincrane/hookRight.j3o");
        hookRight.scale(size);

    }
    //should probarly be called procesCrane
    public void doMove(Wagon wagon,Vector3f location, AGV agv){
            // drawn an agv when the crane has a container. 
        //drop the container on an AGV
            this.agv = agv;
            setWagon(wagon);
            moveCrane(wagon.getLocation(),location);
    }
    
    public void moveCrane(Vector3f location , Vector3f trainloc){
        ArrayList<MotionEvent> grabMotion = new ArrayList<MotionEvent>();
        
            final MotionPath trainroute = new MotionPath();
            trainroute.addWayPoint(new Vector3f(this.getLocalTranslation().x, this.getLocalTranslation().y, this.getLocalTranslation().z));
            trainroute.addWayPoint(new Vector3f(trainloc.x - location.z , this.getLocalTranslation().y , this.getLocalTranslation().z ));

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
                        movetoAGV = true;
                        if(movetoAGV == true){
                            craneDown();
                        }
                       
                        
                    }
                }
            });
        }
    }
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
                      
                        if(movetoAGV == true){
                            
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
                if(movetoAGV == true){ 
                        setAGV(agv);
                        agv.setContainer(cargo);
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
        this.idle = idle;
    }
    
    public boolean getIdel(){
        return this.idle;
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
}
