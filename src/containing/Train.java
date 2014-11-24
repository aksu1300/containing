package containing;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

public class Train extends Node {
    
    private Vector3f loc;
    private Spatial model;
    private ArrayList cargo; 
    private float speed;
    private String id;
    private ArrayList carts;
    
    public Train(String id, Vector3f loc,float speed , AssetManager assetManager, ArrayList carts) {
        this.id = id;
        this.loc = loc; 
        this.cargo = null;
        this.speed = speed;
        model = assetManager.loadModel("Models/high/train/train.j3o");
        model.scale(0.5f);
        this.attachChild(model);
        this.carts = carts;
    }
    
    public void depart() {
        //moveEvent to edge of map.
    }
    
    public void arrive() {
        //moveEvent to het station.
    }
    
    private void initPhysics() {
        //adds gravity effects.
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    public ArrayList getContainers(){return this.cargo;}
    
    public int getCartCount() {return this.carts.size();}
    
    public ArrayList getCarts() {return this.carts;}
    
    public void setCargo(Container container, Wagon wagon) {wagon.setContainer(container);}
    
    public void updateSpeed(float uSpeed) {this.speed = uSpeed;}
    
    // </editor-fold>
}
