package containing;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 *
 * @author Umit Aksu Umit Test shit Jacco test shit Umit branch test AGV
 */
public class Main extends SimpleApplication {

    AGV agv;
    Spatial cargo;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        agv = new AGV("AAA", mat2, assetManager, new Vector3f(0, 0, 0));
        rootNode.attachChild(agv);

        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Blue);
        cargo = assetManager.loadModel("Models/high/container/container.j3o");
        cargo.setMaterial(mat);
        cargo.scale(0.5f);

    }

    @Override
    public void simpleUpdate(float tpf) {
        try {
            Thread.sleep(500);
            if (agv.cargo == null){
                agv.setContainer(cargo);
            }
            else 
            {
                agv.removeContainer();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
