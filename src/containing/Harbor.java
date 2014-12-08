/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import containing.storage.StorageCrane;
import containing.storage.Storage;
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
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jacco
 */
public class Harbor extends Node {

    //List with locations of cranes;
    //List with locations of storageslines;
    List<Vector3f> storagesloc;
    List<Vector3f> cranesloc;
    List<Storage> storagelines;
    public ArrayList<ShipCrane> shCranes;
    public ArrayList<AGV> agvRoosterA;
    public ArrayList<AGV> agvRoosterB;
    private AssetManager assetmanager;

    public Harbor(BulletAppState bulletAppState, AssetManager assetManager) {
        shCranes = new ArrayList<ShipCrane>();
        storagelines = new ArrayList<Storage>();

        storagesloc = new ArrayList<Vector3f>();
        cranesloc = new ArrayList<Vector3f>();

        agvRoosterA = new ArrayList<AGV>();
        agvRoosterB = new ArrayList<AGV>();

        this.assetmanager = assetManager;
        initPlatform(bulletAppState);
        initSky();
        initStorage(bulletAppState);
        initShipcranes();
        initAGV();
    }
   
    /***
     * initAGV initialises the AGV's on their parking spots. 
     * 
     */
    public void initAGV() {
        for (Storage s : storagelines) { //STORAGE S IS USED! But foreach looks better than a for loop B^) 
            for (int i = 0; i < 4; i++) { //BOT4 (A kant)
                agvRoosterA.add(new AGV(("SAV" + i), assetmanager));
            }
            for (int i = 0; i < 4; i++) { //TOP4 (B kant)
                agvRoosterB.add(new AGV(("SBV" + i), assetmanager));
            }
        }
        float zval = 106; //beginnend Z waarde. 
        for (int i = 0; i < agvRoosterA.size(); i++) {
            if(i % 4 == 0){
                zval -= 24.5f; //na elke 4 agvs wordt de z waarde met 24.5 verlaagt. 
            }
            agvRoosterA.get(i).setLocalTranslation(75,10.5f,zval-(i*5)); 
            agvRoosterA.get(i).rotate(0, -(FastMath.PI / 2), 0); //goedom zetten.
            this.attachChild(agvRoosterA.get(i)); //attach to world!
        }
        zval = 106;
        for (int i = 0; i < agvRoosterB.size(); i++) { //onderstaande code werkt hetzelfde als voor roosterA. le copy paste!
            if(i % 4 == 0){
                zval -= 24.5f;
            }
            agvRoosterB.get(i).setLocalTranslation(-122,10.5f,zval-(i*5));
            agvRoosterB.get(i).rotate(0, (FastMath.PI / 2), 0);
            this.attachChild(agvRoosterB.get(i));
        }
    }

    public void initStorage(BulletAppState bulletAppState) {
        for (float i = -2.5f; i < 6.5f; i += 1.5f) { //aantal lines
            Vector3f locminus = new Vector3f(-25, 10.5f, i * - 30);
            Vector3f locminus2 = new Vector3f(-25, 10.5f, i * - 30);
            storagelines.add(new Storage(assetmanager, new StorageCrane(assetmanager, 0.5f, locminus2), locminus, bulletAppState)); // beide kanten op.
        }
        for (Storage sl : storagelines) {
            this.attachChild(sl);
        }
    }

    //LATER VERWIJDEREN VOOR AGV CONTROLEUR!
    public MotionPath testMotionPaths() {
        MotionPaths platTest = new MotionPaths("AGVtestpath");
        platTest.addWayPoint(WayPoints.PA.coords);
        platTest.addWayPoint(WayPoints.PG.coords);
        platTest.addWayPoint(WayPoints.PF.coords);
        platTest.addWayPoint(WayPoints.PE.coords);

        platTest.addWayPoint(WayPoints.PC.coords);
        platTest.addWayPoint(WayPoints.PB.coords);
        platTest.addWayPoint(WayPoints.PA.coords);
        platTest.addWayPoint(WayPoints.CA.coords);
        platTest.addWayPoint(WayPoints.CB.coords);
        platTest.addWayPoint(WayPoints.PG.coords);

        platTest.setCurveTension(0.0f);
        return platTest;
    }

    public void initShipcranes() {
        // Adding a ShipCrane to the harbor
        for (int i = 0; i < 6; i++) {
            ShipCrane crane = new ShipCrane(assetmanager, 1f, new Vector3f(160, 10.5f, 320 + (-i * 30)), i);
            crane.rotate(0, FastMath.PI, 0);
            crane.setLocalTranslation(crane.getLocation());
//        initTocranemotionpath(crane, i);
//        initFromcranemotionpath(crane, i);
            shCranes.add(crane);
            this.attachChild(crane);
        }
    }

    public MotionPath getDockingroute() {
        final MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(170, 9, -650));
        dockingroute.addWayPoint(new Vector3f(170, 9, 0));
        return dockingroute;
    }

    public MotionPath getUndockingroute() {
        MotionPath undockingroute = new MotionPath();
        undockingroute.addWayPoint(new Vector3f(170, 9, 0));
        undockingroute.addWayPoint(new Vector3f(170, 9, 650));
        return undockingroute;
    }

    
    //EIGENLIJKS BOAT!
    public MotionPath getFreighterDock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(1500, 3, -360));
        dockingroute.addWayPoint(new Vector3f(0, 3, -360));
        return dockingroute;
    }

    //BOAT!
    public MotionPath getFreighterUndock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(0, 9, -360));
        dockingroute.addWayPoint(new Vector3f(-1700, 9, 1500));
        return dockingroute;
    }

    public void initPlatform(BulletAppState bulletAppState) {
        Box platform = new Box(400, 10, 350);
        Geometry platform_geom = new Geometry("Box", platform);
        Material platform_mat = new Material(assetmanager, "Common/MatDefs/Misc/Unshaded.j3md");
        platform_mat.setColor("Color", ColorRGBA.LightGray);
        platform_geom.setMaterial(platform_mat);
        platform_geom.setLocalTranslation(-250, 0, 0);
        this.attachChild(platform_geom);
        RigidBodyControl phyHarbor = new RigidBodyControl(0.0f);
        platform_geom.addControl(phyHarbor);
        bulletAppState.getPhysicsSpace().add(phyHarbor);
    }

    public void initSky() {
        Spatial sky = SkyFactory.createSky(assetmanager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        this.attachChild(sky);
    }
}
//    public void initFromcranemotionpath(ShipCrane crane, int i){
//        // Paths from the cranes
//        MotionPaths cranepath = new MotionPaths("TestCrane1");
//        cranepath.addWayPoint(new Vector3f(140, 10, crane.getLocation().getZ()));
//        cranepath.addWayPoint(new Vector3f(140, 10, 200));
//        cranepath.addWayPoint(new Vector3f(100, 10, 200));
//        cranepath.setCurveTension(0.0f);
//        fromcranepaths.add(cranepath); 
//    }
//    public void initTocranemotionpath(ShipCrane crane, int i){
//        //Paths to the cranes
//        MotionPaths cranepath = new MotionPaths("TestCrane1");
//        cranepath.addWayPoint(new Vector3f(100, 10, -150));
//        cranepath.addWayPoint(new Vector3f(140, 10, -150));
//        cranepath.addWayPoint(new Vector3f(140, 10, crane.getLocation().getZ()));
//        cranepath.setCurveTension(0.0f);
//        tocranepaths.add(cranepath);   
//    }

