/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Driving Ghost
 */
public class Freighter extends Node {
    
    private String id;
    private Vector3f loc;
    private float speed;
    private AssetManager assetmanager;
    
    public Freighter(AssetManager assetmanager){
        this.assetmanager = assetmanager;//hardcode the scale!
        this.id = "F01";
    }
}
