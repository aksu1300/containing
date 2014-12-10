/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import containing.storage.StorageCrane;
import containing.storage.Storage;
import containing.transport.TrainCrane;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.util.SkyFactory;
import containing.transport.Train;
import containing.transport.Truck;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ProjectGroep
 */
public class Harbor extends Node {

    List<Vector3f> storagesloc;
    List<Vector3f> cranesloc;
    List<Storage> storagelines;
    public ArrayList<ShipCrane> shCranes;
    public ArrayList<ShipCrane> boatCranes;
    public ArrayList<TruckCrane> truckCranes;
    public ArrayList<TrainCrane> trainCranes;
    public ArrayList<AGV> agvRoosterA;
    public ArrayList<AGV> agvRoosterB;
    private AssetManager assetmanager;
    public ArrayList<Truck> trucks;

    public Harbor(BulletAppState bulletAppState, AssetManager assetManager) {
        shCranes = new ArrayList<ShipCrane>();
        trainCranes = new ArrayList<TrainCrane>();
        storagelines = new ArrayList<Storage>();
        truckCranes = new ArrayList<TruckCrane>();
        shCranes = new ArrayList<ShipCrane>();
        boatCranes = new ArrayList<ShipCrane>();
        storagelines = new ArrayList<Storage>();
        truckCranes = new ArrayList<TruckCrane>();
        storagesloc = new ArrayList<Vector3f>();
        cranesloc = new ArrayList<Vector3f>();
        trucks = new ArrayList<Truck>();

        agvRoosterA = new ArrayList<AGV>();
        agvRoosterB = new ArrayList<AGV>();

        this.assetmanager = assetManager;

        initPlatform(bulletAppState);
        initSky();
        initStorage();
        initShipcranes();
        
        initTruckcranes();
        initTrucks();
        initRails();
        initTrainCranes();
        initTrain();
    }

    public void initTreinRails() {
    }

    /**
     * *
     * Initaliseert de weg van en naar punten. Ligt onder de waypoints.
     */
    public void initRoad() {
    }

    //FreightCranes
    public void initBoatCranes() {
        // Adding a BoatCrane to the harbor
        for (int i = 0; i < 8; i++) {
            ShipCrane crane = new ShipCrane(assetmanager, 1f, new Vector3f(40 - (i * 30), 14, -450));
            crane.rotate(0, -FastMath.PI / 2, 0);
            crane.setLocalTranslation(crane.getLocation());
            shCranes.add(crane);
            this.attachChild(crane);
        }
    }
    
    public void initRails() {
        Line line = new Line(new Vector3f(-100, 10, 500), new Vector3f(-650, 10, 500));
        line.setLineWidth(4);
        Geometry geometry = new Geometry("Bullet", line);
        Material orange = new Material(assetmanager, "Common/MatDefs/Misc/Unshaded.j3md");
        orange.setColor("Color", ColorRGBA.Blue);
        geometry.setMaterial(orange);                  
        this.attachChild(geometry);
    }
    
    public void initTrainCranes() {
        for (int i = 0; i < 4; i++)
        {
            TrainCrane crane = new TrainCrane(assetmanager, 1f, new Vector3f(-100 + (i * 20), 10, 500));
            crane.rotate(0, FastMath.PI * 1.5f, 0);
            crane.setLocalTranslation(crane.getLocation());
            trainCranes.add(crane);
            this.attachChild(crane);
        }
    }
    
    public void initTrain() {
        //Train train = new Train(new Vector3f(-100, 10, 250), assetmanager, 2);
        Train train = new Train(new Vector3f(-100, 10.5f, 500), assetmanager);
        train.rotate(0, FastMath.PI * 1.5f, 0);
        train.setLocalTranslation(train.getLocation());
        this.attachChild(train);
    }

    public void initPlatform(BulletAppState bulletAppState) {
        Box platform = new Box(1500, 10, 600);
        Box TruckPlatform = new Box(500, 10, 600);
        Geometry platform_geom = new Geometry("Box", platform);
        Geometry truckplatform_geom = new Geometry("Box", TruckPlatform);
        Material platform_mat = new Material(assetmanager, "Common/MatDefs/Misc/Unshaded.j3md");
        platform_mat.setColor("Color", ColorRGBA.LightGray);
        platform_geom.setMaterial(platform_mat);
        truckplatform_geom.setMaterial(platform_mat);
        platform_geom.setLocalTranslation(-1350, 0, 150);
        truckplatform_geom.setLocalTranslation(-1100, 0, -50);
        this.attachChild(platform_geom);
        this.attachChild(truckplatform_geom);
        RigidBodyControl phyHarbor = new RigidBodyControl(0.0f);
        platform_geom.addControl(phyHarbor);
        bulletAppState.getPhysicsSpace().add(phyHarbor);
    }

    public void initSky() {
        Spatial sky = SkyFactory.createSky(assetmanager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        this.attachChild(sky);
    }

    public void initStorage() {

        int amount = 3;
        int asciivalue = 48;

        for (float i = -2.5f; i < 31f; i += 1.5f) { //aantal lines
            if (storagelines.size() == 4) {
                amount = 2;
            }

            Vector3f locminus = new Vector3f(-25 - (i * 30), 10.5f, 30);
            Vector3f locminus2 = new Vector3f(-25 - (i * 30), 10.5f, 30);
            storagelines.add(new Storage(assetmanager, new StorageCrane(assetmanager, 0.5f, locminus2), locminus, amount, asciivalue)); // beide kanten op.
            asciivalue++;
        }
        
        for (Storage sl : storagelines) {
            this.attachChild(sl);
        }

    }

    public void initTrucks() {
        for (int i = 0; i < 20; i++) {
            Truck t = new Truck("T" + (i + 1), new Vector3f(-1200, 11, -350 - (i * 13)), 1, assetmanager);
            trucks.add(t);
            this.attachChild(t);
        }
    }

    public void initTruckcranes() {
        for (int i = 0; i < 20; i++) {
            TruckCrane trCrane = new TruckCrane(assetmanager, new Vector3f(-740, 11, -350 - (i * 13)));
            trCrane.rotate(0, FastMath.PI / 2, 0);
            
            truckCranes.add(trCrane);
        }
        for (TruckCrane tr : truckCranes) {
            this.attachChild(tr);
        }
    }

    public void initShipcranes() {
        int ruimte = 30;
        // Adding a ShipCrane to the harbor
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                ruimte *= 2;
            } else {
                ruimte = 30;
            }
            ShipCrane crane = new ShipCrane(assetmanager, 1f, new Vector3f(160, 10.5f, -120 + (i * ruimte)));
            crane.rotate(0, FastMath.PI, 0);
            crane.setLocalTranslation(crane.getLocation());
//        initTocranemotionpath(crane, i);
//        initFromcranemotionpath(crane, i);
            shCranes.add(crane);
            this.attachChild(crane);
        }
    }

    //Freighter!
    public MotionPath getDockingroute() {
        final MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(170, 9, -650));
        dockingroute.addWayPoint(new Vector3f(170, 9, 0));
        return dockingroute;
    }

    //FREIGHTER!!
    public MotionPath getUndockingroute() {
        MotionPath undockingroute = new MotionPath();
        undockingroute.addWayPoint(new Vector3f(170, 9, 0));
        undockingroute.addWayPoint(new Vector3f(170, 9, 650));
        return undockingroute;
    }

    //EIGENLIJKS BOAT!
    public MotionPath getFreighterDock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(1500, 3, -460));
        dockingroute.addWayPoint(new Vector3f(0, 3, -460));
        return dockingroute;
    }

    //BOAT!
    public MotionPath getFreighterUndock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(0, 9, -460));
        dockingroute.addWayPoint(new Vector3f(-1700, 9, 1600));
        return dockingroute;
    }
}
