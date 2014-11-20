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
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;

/**
 *
 * @author Jacco
 */
public class Harbor extends Node{
    
    private int amountlines = 40;
    
    public Harbor(BulletAppState bulletAppState, AssetManager assetManager){
        Initplatform(assetManager, bulletAppState);
        Initsky(assetManager);
        Initcontainerlines(assetManager, bulletAppState, amountlines);
    }
    
    public MotionPath Getdockingroute(){
        MotionPath dockingroute = new MotionPath();
        dockingroute.addWayPoint(new Vector3f(170,9,-650));
        dockingroute.addWayPoint(new Vector3f(170,9,0));
        return dockingroute;  
    }
    
    public MotionPath Getundockingroute(){
        MotionPath undockingroute = new MotionPath();
        undockingroute.addWayPoint(new Vector3f(170, 9, 0));
        undockingroute.addWayPoint(new Vector3f(170, 9, 650));
        return undockingroute;
    }
        
    public void Initplatform(AssetManager assetManager, BulletAppState bulletAppState){
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
    
    public void Initsky(AssetManager assetManager){
        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        this.attachChild(sky);
    }
    
    public void Initcontainerlines(AssetManager assetManager, BulletAppState bulletAppState, int ammountlines){
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
