package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;

public class Wagon extends Node {
    private Vector3f location;
    private Spatial model;
    private Material material;
    private AssetManager assetmanager;
    private Container cargo;
    private float speed;
    private String id;
    
    public Wagon(String id,Vector3f location , AssetManager assetManager) {
        this.id = id;
        this.location = location;
        this.model = assetManager.loadModel("Models/high/train/wagon.j3o");
        this.attachChild(model);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get & Set">
    
    public void setCargo(Container cargo){
        if (cargo == null) {
            this.cargo = cargo; 
            this.cargo.setLocalTranslation(0,0.6f,0);
            this.attachChild(this.cargo);
        }
    }
    
    public Container getCargo(){
        return cargo;
    }
    
    public void updateSpeed(float uSpeed) {
        this.speed = uSpeed;
    }
    
    // </editor-fold>
}
