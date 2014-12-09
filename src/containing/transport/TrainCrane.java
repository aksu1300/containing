package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.cinematic.MotionPath;
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
    Spatial crane;
    Spatial grabbingGear;
    Spatial grabbingGearHolder;
    Spatial hookLeft;
    Spatial hookRight;
    Material material;
    AssetManager assetmanager;
    BoundingVolume boundGrab;
    
    String id;
    Vector3f location;
    float speed;
    
    Container container;
    Harbor harbor;
    Train train;
    boolean idle;
    
    
    boolean status = false; // false is up, true is down
    boolean up = true;
    boolean in = false;
    boolean done = false;
    
    public TrainCrane(AssetManager assetManager, Vector3f location, Harbor harbor) {
        this.assetmanager = assetManager;
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
        this.idle = true;
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
        this.train = harbor.train;
        Wagon wagon;
        wagon = calcWagon(container);
        moveTo(wagon);
        takeContainer(wagon);
        placeContainer(agv);
    }
    
    private Wagon calcWagon(Container container) {
        for (Wagon wagon : this.train.getWagons())
            if (wagon.getCargo() == container)
                return wagon;
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
    }
    private void grabA() {
        
    }
    
    private void pullA() {
        
    }
    
    public void Move(MotionPath path){
        
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

        } else {
            this.done = true;
        }

    }
    
}
