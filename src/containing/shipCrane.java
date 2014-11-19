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
        grabGear = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGear.j3o");
        grabGearHolder = assetManager.loadModel("Models/high/crane/dockingcrane/grabbingGearHolder.j3o");
        hookLeft = assetManager.loadModel("Models/high/crane/dockingcrane/hookLeft.j3o");
        hookRight = assetManager.loadModel("Models/high/crane/dockingcrane/hookRight.j3o");
        initModel(crane);
        
        
        
        
    }
    
    
    
}
