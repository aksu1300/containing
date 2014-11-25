package containing;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Wagon extends Node {
    private Vector3f loc;
    private Spatial model;
    private Container cargo;
    private float speed;
    private String id;
    
    public Wagon(String id,Vector3f loc ,float speed , AssetManager assetManager) {
        this.id = id;
        this.loc = loc;
        this.cargo = null;
        this.speed = speed;
        model = assetManager.loadModel("Models/high/train/wagon.j3o");
        model.scale(0.5f);
        this.attachChild(model);
    }
    
    private void initPhysics() {
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get & Set">
    
    public void setCargo(Container cargo){
        if (cargo == null) {
            this.cargo = cargo; 
            this.cargo.setLocalTranslation(0,0.6f,0);
            this.attachChild(this.cargo);
        }
    }
    
    public Container getContainer(){return cargo;}
    
    public void updateSpeed(float uSpeed){this.speed = uSpeed;}
    
}
    
    // </editor-fold>

