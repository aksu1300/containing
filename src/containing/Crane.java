/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Driving Ghost
 */
public class Crane extends Node {

    Vector3f loc;
    Material material;
    Spatial crane;
    Spatial grabbingGear;
    Spatial grabbingGearHolder;
    Spatial hookLeft;
    Spatial hookRight;
    AssetManager assetManager;
    float speed;
    String id;
    float size;

   public Crane(String id, AssetManager assetManager) {
        this.id = id;
        this.assetManager = assetManager;
       
        // Attach to the root node
        this.attachChild(crane);
    }

}
