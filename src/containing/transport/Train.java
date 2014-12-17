package containing.transport;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import containing.Container;
import java.util.ArrayList;

public class Train extends Node {
    
    String id;
    private Vector3f location;
    private Spatial model;
    private Material material;
    private float speed;

    private ArrayList<Wagon> wagons = new ArrayList<Wagon>();
    private AssetManager assetmanager;
    
    public Train(Vector3f location, AssetManager assetmanager) {
        this.location = location;
        this.assetmanager = assetmanager;
        this.model = this.assetmanager.loadModel("Models/high/train/train.j3o");
        this.material = new Material(assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");
        this.model.setMaterial(material);
        this.attachChild(this.model);
    }
    
    /**
     * Train that gets the containers out of the harbor.
     * @param id String ex:C10064
     * @param loc Vector3f location ex:new Vector3f(0, 6f, 0)
     * @param speed float Speed ex:6f
     * @param assetmanager AssetManager for the train ex:new AssetManager
     * @param wagons Wagons that can be added to the train ex:new Wagon
     */
    public Train(String id, Vector3f location, AssetManager assetmanager, int nrWagons) {
        this.id = id;
        this.location = location;
        this.assetmanager = assetmanager;
        this.initWagons(nrWagons);
        this.model = this.assetmanager.loadModel("Models/high/train/train.j3o");
        this.material = new Material(assetmanager, "Common/MatDefs/Misc/ShowNormals.j3md");
        this.model.setMaterial(material);
        this.attachChild(this.model);
        this.wagons = new ArrayList<Wagon>();
        
    }
    
    private void initWagons(int nrWagons) {
        Wagon wagon1 = new Wagon("TW1", new Vector3f(0.06f, 0f, -13.1f), this.assetmanager);
        wagon1.rotate(0, FastMath.PI, 0);
        wagon1.setLocalTranslation(wagon1.getLocation());

//        wagon1.setCargo(new Container(this.assetmanager,1.0f));
        this.wagons.add(wagon1);
       this.attachChild(wagon1);
        
        float transloc = 5.35f;
        for (int i = 2; i < nrWagons + 2; i++) {
            Wagon wagon = new Wagon("TW" + i, new Vector3f(0.06f, 0, transloc + -18.45f * i), this.assetmanager);
            wagon.rotate(0, FastMath.PI, 0);
            
//          wagon.setCargo(new Container(this.assetmanager,1.0f));
            wagon.setLocalTranslation(wagon.getLocation());
            this.wagons.add(wagon);
            this.attachChild(wagon);
        }
    }
    
    /**
     * Train leaves the station and goes out of the harbor area.
     */
    public void depart() {
        Vector3f station = new Vector3f(0, 6f, 14f);
        Vector3f exit = new Vector3f(0, 6f, 6f);
        MotionPath path = new MotionPath();
        path.addWayPoint(station);
        path.addWayPoint(exit);
    }
    
    /**
     * Train enters the harbor area and stops at the station. 
     */
    public void arrive() {
        Vector3f entry = new Vector3f(0, 6f, 6f);
        Vector3f station = new Vector3f(0, 6f, 14f);
        MotionPath path = new MotionPath();
        path.addWayPoint(entry);
        path.addWayPoint(station);
    }
    
    /**
     * Add physics (gravity) to the train.
     */
    private void initPhysics() {
        //adds gravity effects.
    }
    
    /**
     * lololololol
     * @return the Cargo of all the wagons.
     */
    
    public String getId(){
        return this.id;
    }

    
    public int getWagonCount() {
        
        return this.wagons.size();
    }
    
    public ArrayList<Wagon> getWagons() {
        return this.wagons;
    }
    
    public void setCargo(Wagon wagon, Container container) {
        wagon.setCargo(container);
    }
    
    public void updateSpeed(float uSpeed) {
        this.speed = uSpeed;
    }
    
    public Vector3f getLocation() {
        return this.location;
    }
}
