/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class AGVController {

    WayPoints wp;
    
    AGVController() {
        
        wp = new WayPoints();
    }

    MotionPath moveCommand(ArrayList<String> path) {
        MotionPaths navigation = new MotionPaths("AGVmovepath");
        for (String s : path) {
            navigation.addWayPoint(new Vector3f(wp.getPoint(s)));
        }
        navigation.setCurveTension(0);
        return navigation;
    }

    void updateRooster() {
    }
}
