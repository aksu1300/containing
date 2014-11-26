/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
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
public class Harbor extends Node{
    
    //List with locations of cranes;
    List<Vector3f> cranesloc = new ArrayList<Vector3f>();
    //List with locations of storageslines;
    List<Vector3f> storagesloc = new ArrayList<Vector3f>();
    List<MotionPaths> tocranepaths = new ArrayList<MotionPaths>();
    List<MotionPaths> fromcranepaths = new ArrayList<MotionPaths>();
    List<Storage> storagelines = new ArrayList<Storage>();
    public ArrayList<shipCrane> shCranes;
    private AssetManager assetmanager;
    

    public Harbor(BulletAppState bulletAppState, AssetManager assetManager) {
        shCranes = new ArrayList<shipCrane>();
        this.assetmanager = assetManager;
        initPlatform(assetManager, bulletAppState);
        initSky(assetManager);
        initStorage(assetManager, bulletAppState);        
        initShipcranes(assetManager);
    }
    
    public void initStorage(AssetManager assetManager, BulletAppState bulletAppState){
        for (int i = 0; i < 20; i++) { //aantal lines
            Vector3f locplus = new Vector3f(0, 10.5f, i * +11);
            Vector3f locminus = new Vector3f(0, 10.5f, i * -11);
            Vector3f locplus2 = new Vector3f(0, 11f, i * + 11);
            Vector3f locminus2 = new Vector3f(0, 11f, i * -11);
            storagelines.add(new Storage(assetManager, new storageCrane(assetManager, 0.5f, locminus2), locminus, bulletAppState)); // beide kanten op.
            storagelines.add(new Storage(assetManager, new storageCrane(assetManager, 0.5f, locplus2), locplus, bulletAppState));
        }
        for(Storage sl : storagelines){
            this.attachChild(sl);
        }
    }
    
    public void initTocranemotionpath(shipCrane crane, int i){
        //Paths to the cranes
        MotionPaths cranepath = new MotionPaths("TestCrane1");
        cranepath.addWayPoint(new Vector3f(100, 10, -150));
        cranepath.addWayPoint(new Vector3f(140, 10, -150));
        cranepath.addWayPoint(new Vector3f(140, 10, crane.getLocation().getZ()));
        cranepath.setCurveTension(0.0f);
        tocranepaths.add(cranepath);   
    }
    
    public void initFromcranemotionpath(shipCrane crane, int i){
        // Paths from the cranes
        MotionPaths cranepath = new MotionPaths("TestCrane1");
        cranepath.addWayPoint(new Vector3f(140, 10, crane.getLocation().getZ()));
        cranepath.addWayPoint(new Vector3f(140, 10, 200));
        cranepath.addWayPoint(new Vector3f(100, 10, 200));
        cranepath.setCurveTension(0.0f);
        fromcranepaths.add(cranepath); 
    }
    
    public void initTothestorage(List<Storage> storagelines, int i){
        MotionPaths storagepath = new MotionPaths("TestStore1");
        //storagepath.addWayPoint();
        //storagepath.addWayPoint();
        storagepath.addWayPoint(new Vector3f());
    }
    
    
    public void initShipcranes(AssetManager assetManager){
     // Adding a shipCrane to the harbor
        for (int i = 0; i < 6; i++){
        shipCrane crane = new shipCrane(assetManager, 1f, new Vector3f(160, 10.5f, -120 +(i*30)));
        crane.rotate(0, FastMath.PI, 0);
        crane.setLocalTranslation(crane.getLocation());
        initTocranemotionpath(crane, i);
        initFromcranemotionpath(crane, i);
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

    public MotionPath getFreighterDock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(1500, 3, -360));
        dockingroute.addWayPoint(new Vector3f(46, 3, -360));
        return dockingroute;
    }

    public MotionPath getFreighterUndock() {
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(170, 9, -650));
        dockingroute.addWayPoint(new Vector3f(170, 9, 0));
        return dockingroute;
    }

    public void initPlatform(AssetManager assetManager, BulletAppState bulletAppState) {
        Box platform = new Box(150, 10, 350);
        Geometry platform_geom = new Geometry("Box", platform);
        Material platform_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        platform_mat.setColor("Color", ColorRGBA.LightGray);
        platform_geom.setMaterial(platform_mat);
        this.attachChild(platform_geom);
        RigidBodyControl phyHarbor = new RigidBodyControl(0.0f);
        platform_geom.addControl(phyHarbor);
        bulletAppState.getPhysicsSpace().add(phyHarbor);
    }

    public void initSky(AssetManager assetManager) {
        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        this.attachChild(sky);
    }
}
