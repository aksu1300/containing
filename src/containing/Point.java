/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.math.Vector3f;

/**
 *
 * @author Driving Ghost
 */
public class Point {

    public Vector3f coords;
    public String id;

    Point(String id, Vector3f coords) {
        this.coords = coords;
        this.id = id;
    }
}
