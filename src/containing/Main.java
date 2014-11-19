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
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Ship ship = new Ship(assetManager,0.01f);
        rootNode.attachChild(ship);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
