package containing;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.water.WaterFilter;

/**
 * test
 *
 * @author Umit Aksu Umit Test shit Jacco test shit Umit branch test AGV
 */
public class Main extends SimpleApplication {

    BulletAppState bulletAppState;
    private float initialWaterHeight = 0.8f; 
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
        Harbor harbor = new Harbor(bulletAppState, assetManager);
        
        //right camera position
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
        
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
