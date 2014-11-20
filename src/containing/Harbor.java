/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;

/**
 *
 * @author Jacco
 */
public class Harbor extends Node{
    
    //SimpleWaterProcessor waterProcessor;
    //Node sceneNode;
    //Node harborNode;
    //private BulletAppState bulletAppState;
    //private Spatial sky;
    //private FilterPostProcessor fpp;
    //private WaterFilter water;
    //private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f); 
    //private float initialWaterHeight = 0.8f;
    
    public Harbor(BulletAppState bulletAppState, AssetManager assetManager){
        
        // Platform
        Box b = new Box(150, 10, 350);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.LightGray);
        geom.setMaterial(mat);
        //geom.rotate(FastMath.HALF_PI, 0, 0);
        this.attachChild(geom);
        RigidBodyControl phyHarbor = new RigidBodyControl(0.0f);
        geom.addControl(phyHarbor);
        bulletAppState.getPhysicsSpace().add(phyHarbor);
        
        

        // load sky
        Spatial sky = SkyFactory.createSky(assetManager, "Scenes/Beach/FullskiesSunset0068.dds", false);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        this.attachChild(sky);
       
        
        
        

    }
    
}
