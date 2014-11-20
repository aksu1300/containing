/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

/**
 *
 * @author Driving Ghost
 */
public class shipCrane extends Crane {
    
    shipCrane(String id, Material mat, AssetManager assetManager){
        super(id, mat, assetManager);
        crane = assetManager.loadModel("Models/high/crane/dockingcrane/crane.j3o");

        initModel(crane);
        
        
        
        
    }
    
    
    
}
