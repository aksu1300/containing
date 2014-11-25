/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Driving Ghost
 */
public class Storage extends Node{
    
    AssetManager assetManager;
    
    public Storage(AssetManager assetManager){
        
        this.assetManager = assetManager; 
    }
    
}
