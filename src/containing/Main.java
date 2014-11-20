package containing;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;

/**
 * test
 *
 * @author Umit Aksu Umit Test shit Jacco test shit Umit branch test AGV
 */
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;
    private float initialWaterHeight = 0f; 
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);

    AGV agv;
    Spatial cargo;
    shipCrane shCrane;

    shipCrane crane;
    
    Ship ship;
        
    
    MotionPath motionPath;
    MotionPath motionPath1;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
        Harbor harbor = new Harbor(bulletAppState, assetManager);
        
//        //right camera position
        cam.setLocation(new Vector3f(200, 150, 150));
        cam.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_Y);
        
        // load water
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        WaterFilter water = new WaterFilter(rootNode, lightDir);
        water.setWaterHeight(initialWaterHeight);
    
        fpp.addFilter(water);
        viewPort.addProcessor(fpp);
        
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        
        //Attach Platform to rootnode
        rootNode.attachChild(harbor);
        
        
        // Adding a ship to the scene
        ship = new Ship(assetManager,0.5f);
        ship.setLocalTranslation(150, 0, 4);
        
        ship.addContainer(new Container(assetManager,1f));
        
        
        // The ship will follow the path 
        motionPath = new MotionPath();
        motionPath.addWayPoint(new Vector3f(300,9,1));
        motionPath.addWayPoint(new Vector3f(250,9,100));
        motionPath.addWayPoint(new Vector3f(200,9,200));
        motionPath.addWayPoint(new Vector3f(170,9,300));
        
        // The ship will follow the path 1
        motionPath1 = new MotionPath();
        motionPath1.addWayPoint(new Vector3f(170,9,300));
        motionPath1.addWayPoint(new Vector3f(200,9,200));
        motionPath1.addWayPoint(new Vector3f(250,9,100));
        motionPath1.addWayPoint(new Vector3f(700,9,1));
        
        
        MotionEvent motionControl = new MotionEvent(ship,motionPath);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);
        
        
       motionControl.play();
        
        
        motionPath.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                if(motionPath.getNbWayPoints() == wayPointIndex + 1)
                {
                    System.out.println("Finished");
                    motionControl = new MotionEvent(ship,motionPath1);
                    motionControl.play();
                }
            }
        });
        
        
        motionPath1.addListener(new MotionPathListener() {

            public void onWayPointReach(MotionEvent motionControl, int wayPointIndex) {
                if(motionPath1.getNbWayPoints() == wayPointIndex + 1)
                {
                    System.out.println("Finished");
                    motionControl = new MotionEvent(ship,motionPath);
                    motionControl.play();
                }
            }
            
        });
        
        
        
        rootNode.attachChild(ship);
        
        
        // Adding a shipCrane to the harbor
        crane = new shipCrane(assetManager,1.5f);
        crane.rotate(0, FastMath.PI, 0);
        crane.setLocalTranslation(speed, speed, speed);
        rootNode.attachChild(crane);
        
        
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //ship.move(0, 0, (tpf*50) *-1);
        crane.pullGrabber(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
