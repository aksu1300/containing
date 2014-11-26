/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.storage;

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
import containing.Container;
import java.util.List;

/**
 *
 * @author Driving Ghost
 */
public class Storage extends Node{
    
    private AssetManager assetManager;
    private List<Container> cargo;
    private StorageCrane crane;
    private Vector3f loc;
    private BulletAppState bulletAppState;
    
    public Storage(AssetManager assetManager, StorageCrane cr, Vector3f l, BulletAppState bAS){
        this.assetManager = assetManager; 
        this.loc = l;
        this.bulletAppState = bAS;
        this.crane = cr;
        
        Initline();
        this.crane.rotate(0, FastMath.HALF_PI, 0);
        this.attachChild(this.crane);
    }
    
    public void Initline(){
        Box containerlines = new Box(50, 0, 4.5f);
        Geometry containerlines_geom = new Geometry("Box", containerlines);
        Material containerlines_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        containerlines_mat.setColor("Color", ColorRGBA.Gray);
        containerlines_geom.setMaterial(containerlines_mat);
        containerlines_geom.setLocalTranslation(this.loc);
        this.attachChild(containerlines_geom);
        RigidBodyControl containerlines_phy = new RigidBodyControl(0.0f);
        containerlines_geom.addControl(containerlines_phy);
        bulletAppState.getPhysicsSpace().add(containerlines_phy);
    }
    
    public void Storecargo (Container c){
        this.cargo.add(c);
    }
    
    public Container Unstorecargo (int id){
        Container c = this.cargo.get(id);
        this.cargo.remove(id);
        return c;   
    }
    
    public StorageCrane Getcranes(){return this.crane;}
    
    
    
}
