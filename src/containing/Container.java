/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author account
 */
public class Container extends Node{

    private AssetManager assetManager;
    private Spatial container;
    private float size;
    private Material material;
    
    public Geometry geometry;

    public Container(AssetManager _assetManager, float _size) {
        this.assetManager = _assetManager;
        this.size = _size;

        //Init Sphere
        initContainer();
        
        // Init material of the container
        initMaterials();
        

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
    }


}
