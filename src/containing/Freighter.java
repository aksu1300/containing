/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Driving Ghost
 */
public class Freighter extends Ship {
    
    String id;
    
    public Freighter(AssetManager assetmanager, float size){
        super(assetmanager, size/2);
        this.id = "F01";
    }
}
