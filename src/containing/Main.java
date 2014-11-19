package containing;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author Umit Aksu
 * Umit Test shit
 * Jacco test shit
 * Umit 
 */
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;
    
    Ship ship;
        
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // Create a new ship 
        ship = new Ship(assetManager,0.01f);
        
        // Adding a container to a ship
        Container container = new Container(assetManager,0.01f);
        ship.addContainer(container);
        ship.addContainer(new Container(assetManager,0.01f));
        ship.addContainer(new Container(assetManager,0.01f));
        
        ship.setLocalTranslation(0, 0, 6f);
        
        // Add the ship to the rootNode
        rootNode.attachChild(ship);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        rootNode.getChild(ship.getSpatial().getName()).setLocalTranslation(0, tpf, 2*tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
