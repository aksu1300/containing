/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author account
 */
public class Container extends Node{

    private String id;
    private AssetManager assetManager;
    private Spatial container;
    private float size;
    private Material material;

    public BoundingVolume geometry;

    
    public Container(AssetManager _assetManager, float _size) {
        this.assetManager = _assetManager;
        this.size = _size;

        //Init Sphere
        initContainer();
        
        // Init material of the container
        initMaterials();
        
        initBounding();
       
        // Attach to the root node
        this.attachChild(container);
    }

    // init Materials
    private void initMaterials() {
        material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        container.setMaterial(material);
    }

    // init Container 
    private void initContainer() {
        // Load the container
        container = assetManager.loadModel("Models/high/container/container.j3o");
        container.scale(size);
    }
    
    public String getId() {return this.id;}
   
    private void initBounding(){
        geometry = this.container.getWorldBound();
    }
    
}
