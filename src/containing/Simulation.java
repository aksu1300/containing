/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import serialclass.Command;
import containing.transport.Train;
import containing.transport.Truck;
import containing.transport.Boat;
import containing.database.containerDbHandler;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.cinematic.MotionPath;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;
import containing.mediator.ClientSocket;
import containing.mediator.Mediator;
import containing.transport.TrainCrane;
import containing.transport.Wagon;
import de.lessvoid.nifty.Nifty;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Simulation extends SimpleApplication {

    // The client socket
    private static Socket clientSocket = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private BulletAppState bulletAppState;
    private float initialWaterHeight = 0f;
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);
    Spatial cargo;
    ArrayList<ShipCrane> shCrane;
    Truck t;
    Train train;
    Truck truck;
    Wagon wagon;
    ArrayList<TrainCrane> tCranes = new ArrayList<TrainCrane>();
    Freighter freighter;
    Boat boat;
    Harbor harbor;
    MotionPath motionPath;
    MotionPath motionPath1;
    AGVController agvc;
    ArrayList<String> path;
    Mediator m;
    int counter = 0;
    //Guid
    private Nifty nifty;
    private containerDbHandler cdb;

    public void startSimulation() {
        if (clientSocket == null) {
            new Thread(new ClientSocket()).start();
            this.start();
            System.out.println("Cannot start up the application without server connection.\r\nPlease start up the Server first. ");

        } else {
            this.start();
        }
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        harbor = new Harbor(bulletAppState, assetManager);
        agvc = new AGVController();

        //cam settings
        flyCam.setEnabled(true);
        //flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(300);
        cam.setLocation(new Vector3f(30, 100, 30));
        cam.setFrustumFar(9000);
        cam.onFrameChange();

        // load water
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        WaterFilter water = new WaterFilter(rootNode, lightDir);
        water.setWaterHeight(initialWaterHeight);

        fpp.addFilter(water);
        viewPort.addProcessor(fpp);

        viewPort.setBackgroundColor(ColorRGBA.Blue);

        //Attach Platform to rootnode
        rootNode.attachChild(harbor);


        shCrane = harbor.shCranes;
        //this works 
        //shCrane.get(0).moveCrane(new Vector3f(0,0,4));
        tCranes = harbor.trainCranes;
        train = harbor.train;


        //System.out.println(freighter.containers.get(4).get(4).peek().getLocalTranslation());
        //System.out.println(freighter.getDocked());


        //test to see if the database connection works properly
        //cdb = new containerDbHandler();
        //cdb.addOne(1234567);
        for (Wagon w : train.getWagons()) {
            w.setCargo(new Container(assetManager, 1.0f));
        }

        //tCranes.get(3).doMove(train.getWagons().get(5),train.getLocation());
        //tCranes.get(2).doMove(train.getWagons().get(4),train);


        int i = 0;
        for (Truck tc : harbor.trucks) {
            tc.truckArrive(harbor.truckCranes.get(i));
            harbor.truckCranes.get(i).truck = tc;
            i++;
        }

        for (TruckCrane tc : harbor.truckCranes) {
            if (tc.container != null) {
                tc.craneUp();

            }
        }

    }

    @Override
    public void simpleUpdate(float tpf) {
        Command command = Mediator.getCommand();
        if (command != null) {
            parseCommand(command);
        }
    }

    public void initKeys() {
        inputManager.addMapping("Show", new KeyTrigger(KeyInput.KEY_F10));
        inputManager.addMapping("Hide", new KeyTrigger(KeyInput.KEY_F9));
    }

//    private void updateCommands() {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            Command c = (Command) ois.readObject();
//            // send recieved command to the parser
//            parseCommand(c);
//        } catch (IOException ex) {
//            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private void initSockets() {
//        try {
//            socket = new Socket("141.252.219.27", 5400);
//            path = new ArrayList<String>();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * The parseCommand method will parse Commands sent to the simulator
     *
     * @param cmd
     */
    private void parseCommand(Command cmd) {

        /*
         * The create commands.
         */
        if (cmd.getCommand().equals("createShip")) {
            //Adding freighter to the harbor
            freighter = new Freighter(assetManager);
            freighter.setName(cmd.getIdentifier());
            rootNode.attachChild(freighter);
            System.out.println("New ship created");
        }

        if (cmd.getCommand().equals("createFreighter")) {
            //Adding freighter to the harbor
            freighter = new Freighter(assetManager);
            rootNode.attachChild(freighter);
            System.out.println("New freighter created");
            //freighter.Move(harbor.getDockingroute(), 1.2f);
            //System.out.println("Freighter docked.");
        }

        if (cmd.getCommand().equals("createTrain")) {
            Train train = new Train(new Vector3f(3, 4, 5), assetManager);
            train.setContainers(cmd.getContents());
            train.setName(cmd.getIdentifier());
            rootNode.attachChild(train);
            System.out.println("New train created.");
        }

        // 
        if (cmd.getCommand().equals("createTruck")) {
            Truck truck = new Truck(cmd.getIdentifier(), new Vector3f(0, 0, 0), 2, assetManager);
            truck.setContainer(cmd.getContents().get(0));
            rootNode.attachChild(truck);
            System.out.println("New truck created!");
        }


        /*
         * The move commands.
         */
        if (cmd.getCommand().equals("moveShip")) {
            Boat ship = (Boat) rootNode.getChild(cmd.getIdentifier());
            Vector3f locA = ship.getLocation();
            ship.Move(harbor.getDockingroute(), speed);
            System.out.println("Moved boat from (" + locA + ") to (" + ship.getLocation() + ").");
        }

        if (cmd.getCommand().equals("moveFreighter")) {
            freighter.Move(harbor.getDockingroute(), speed);
            System.out.println("A freighter docked at the harbor.");
        }

        if (cmd.getCommand().equals("moveTrain")) {
            Train train = (Train) rootNode.getChild(cmd.getIdentifier());
            if (train.getDocked()) {
                train.depart();
                System.out.println("A train departed from the station and left the map.");
            } else {
                train.arrive();
                System.out.println("A train arrived at the station.");
            }
        }

        if (cmd.getCommand().equals("moveTruck")) {
            Truck truck = (Truck) rootNode.getChild(cmd.getIdentifier());
            Vector3f locA = truck.getLocation();
            truck.move(lightDir);
            System.out.println("A truck moved from (" + locA + ") to (" + truck.getLocation() + ").");
        }

        /*
         * Misc commands
         */
        if (cmd.getCommand().equals("getContainer")) {
            ShipCrane shipCrane = (ShipCrane) rootNode.getChild(cmd.getIdentifier());
            shipCrane.procesCrane(cmd.getContent());
            System.out.println("A shipcrane got a container of a freighter.");
        }
    }

    /**
     *
     *
     * @param rm
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
