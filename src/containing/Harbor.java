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

/**
 *
 * @author Jacco
 */
public class Harbor extends Node {

    private int amountlines = 40;
    public ArrayList<shipCrane> shCranes;
    private AssetManager assetmanager;

    public Harbor(BulletAppState bulletAppState, AssetManager assetManager) {
        shCranes = new ArrayList<shipCrane>();
        this.assetmanager = assetManager;
        initCranes();
        initPlatform(assetManager, bulletAppState);
        initSky(assetManager);
        initContainerlines(assetManager, bulletAppState, amountlines);
    }

    public void initCranes() {

        for (int i = 0; i < 6; i++) {
            this.shCranes.add(new shipCrane(assetmanager, 1));
        }
        for (int i = 0; i < shCranes.size(); i++) {
            shipCrane sc = shCranes.get(i);
            sc.rotate(0, FastMath.PI, 0);
            sc.setLocalTranslation(160, 10.5f, -120 + (i * 30));
            this.attachChild(sc);
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

    public void initContainerlines(AssetManager assetManager, BulletAppState bulletAppState, int ammountlines) {
        for (int i = 0; i < amountlines; i++) {
            Box containerlines = new Box(50, 0, 4);
            Geometry containerlines_geom = new Geometry("Box", containerlines);
            Material containerlines_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            containerlines_mat.setColor("Color", ColorRGBA.Gray);
            containerlines_geom.setMaterial(containerlines_mat);
            containerlines_geom.setLocalTranslation(0, 10.01f, (i * 10 + 1) - 200);
            this.attachChild(containerlines_geom);
            RigidBodyControl containerlines_phy = new RigidBodyControl(0.0f);
            containerlines_geom.addControl(containerlines_phy);
            bulletAppState.getPhysicsSpace().add(containerlines_phy);
        }
    }
}
