/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.storage;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import containing.AGV;
import containing.Container;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Driving Ghost
 */
public class Storage extends Node {

    private AssetManager assetManager;
    public ArrayList<ArrayList<Stack<Container>>> containers;
    private StorageCrane crane;
    private Vector3f loc;
    public ArrayList<AGV> garageA;
    public ArrayList<AGV> garageB;
    Container container;

    public Storage(AssetManager assetManager, StorageCrane cr, Vector3f l, int amount, int value) {
        //this does not work xD
        containers = new ArrayList<ArrayList<Stack<Container>>>();
        this.garageA = new ArrayList<AGV>();
        this.garageB = new ArrayList<AGV>();
        this.assetManager = assetManager;
        this.loc = l;
        this.crane = cr;

        initHold();
        initLine();
        initAGV(amount, value);

        this.attachChild(this.crane);
    }

    public void initAGV(int amount, int value) {
        for (int i = 0; i < amount; i++) {
            AGV agv = new AGV("SA" + Character.toChars(48 + value) + "V" + i, assetManager);
            garageA.add(agv);
            agv.setLocalTranslation(loc.x + 8 - (i * 5), loc.y, 320);
            this.attachChild(agv);
        }
        for (int i = 0; i < amount; i++) {
            AGV agv = new AGV("SB" + Character.toChars(48 + value) + "V" + i, assetManager);
            garageB.add(agv);
            agv.setLocalTranslation(loc.x + 8 - (i * 5), loc.y, -260);
            agv.rotate(0, -FastMath.PI, 0);
            this.attachChild(agv);
        }
    }

    public void initLine() {
        Box containerlines = new Box(15, 0, 300);
        Geometry containerlines_geom = new Geometry("Box", containerlines);
        Material containerlines_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        containerlines_mat.setColor("Color", ColorRGBA.Gray);
        containerlines_geom.setMaterial(containerlines_mat);
        containerlines_geom.setLocalTranslation(this.loc);
        this.attachChild(containerlines_geom);
    }

//    public void Storecargo(Container c) {
//        this.containers.push(c);
//    }
//
//    public Container Unstorecargo(int id) {
//        Container c = this.containers.get(id);
//        this.containers.remove(id);
//        return c;
//    }
    public void addContainer(Container c) {
        for (int x = 0; x < 6; x++) {
            for (int z = 0; z < 28; z++) {
                for (int y = 0; y < 2; y++) {
                    if (this.containers.get(x).get(z).size() < 2) {
                        this.containers.get(x).get(z).push(c);
                        this.attachChild(c);
                        c.setLocalTranslation(this.loc.x - 6.5f + (2.8f * x) ,this.loc.y + (y * 3.8f) , this.loc.z + 250 -  (18.8f * z)); 
                        return;
                    }
                }
                
            }
            
        }
       
    }

    public void initHold() {
        // this doesnt really work ...

        for (int x = 0; x < 6; x++) {
            containers.add(new ArrayList<Stack<Container>>());
            for (int z = 0; z < 34; z++) {
                containers.get(x).add(new Stack<Container>());

            }
        }

    }

    public StorageCrane Getcranes() {
        return this.crane;
    }
}
