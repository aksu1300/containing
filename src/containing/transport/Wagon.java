package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

public class Wagon extends Node {
    Vector3f loc;
    Spatial model;
    Spatial cargo;
    float speed;
    String id;
    ArrayList carts;
    
    public Wagon(String id,Vector3f loc ,float speed , AssetManager assetManager) {
        this.id = id;
        this.loc = loc;
        this.cargo = null;
        this.speed = speed;
        model = assetManager.loadModel("Models/high/train/wagon.j3o");
        model.scale(0.5f); //NEEDS REWORK!!
        this.attachChild(model);
    }
    
    public void updateSpeed(float uSpeed) {this.speed = uSpeed;}
    
    private void initPhysics() {
        
    }
    
    public void setContainer(Spatial cargo){
        this.cargo = cargo; 
        this.cargo.setLocalTranslation(0,0.6f,0); //NEEDS WORK.
        this.attachChild(this.cargo);
    }
    
    public Spatial getContainer(){return cargo;}
}
