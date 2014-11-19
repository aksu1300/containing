package containing;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
        // PhysicsManager
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        // CamSetup
        Vector3f camVector = new Vector3f(0, 40, 0);
        cam.setLocation(camVector);
        // Background Color
        viewPort.setBackgroundColor(ColorRGBA.Blue);
        // Create platform
        Platform platform = new Platform(assetManager, bulletAppState);
        rootNode.attachChild(platform);
        
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
