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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jacco
 */
public class Platform extends Node {
    
    public Platform(AssetManager assetManager, BulletAppState bulletAppState){
        // Make the platform (flat box)
        Box platform = new Box(Vector3f.ZERO, 50, 50, 0);
        Geometry gplatform = new Geometry("Box", platform);
        Material mplatform = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mplatform.setColor("Color", ColorRGBA.LightGray);
        gplatform.setMaterial(mplatform);
        gplatform.rotate(FastMath.HALF_PI, 0, 0);
        this.attachChild(gplatform);
        // Make the platform physical
        RigidBodyControl phyplatform = new RigidBodyControl(0.0f);
        gplatform.addControl(phyplatform);
        bulletAppState.getPhysicsSpace().add(phyplatform);
        
        // Innerplatform NOG AANTEPASSEN VARIABLE NAMEN
        Box platform2 = new Box(Vector3f.ZERO, 25, 25, 0);
        Geometry gplatform2 = new Geometry("Box", platform2);
        Material mplatform2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mplatform2.setColor("Color", ColorRGBA.Gray);
        gplatform2.setLocalTranslation(0, 0.001f, 0);
        gplatform2.setMaterial(mplatform2);
        gplatform2.rotate(FastMath.HALF_PI, 0, 0);
        this.attachChild(gplatform2);
        // Make the innerplatform physical
        RigidBodyControl phyplatform2 = new RigidBodyControl(0.0f);
        gplatform.addControl(phyplatform2);
        bulletAppState.getPhysicsSpace().add(phyplatform2);
        
        // ContainerLines
        for (int i = 1; i < 6; i++) {
            Box containerline = new Box(Vector3f.ZERO, 3, 23, 0);
            Geometry gcontainerline = new Geometry("Box", containerline);
            Material mcontainerline = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mcontainerline.setColor("Color", ColorRGBA.Black);
            gcontainerline.setLocalTranslation((10 * i) - 30, 0.002f, 0);
            gcontainerline.setMaterial(mcontainerline);
            gcontainerline.rotate(FastMath.HALF_PI, 0, 0);
            this.attachChild(gcontainerline);
            // Make the containerline physical
            RigidBodyControl phycontainerline = new RigidBodyControl(0.0f);
            gcontainerline.addControl(phycontainerline);
            bulletAppState.getPhysicsSpace().add(phycontainerline);
        }
        
        
        
        
               
    }
   
}
