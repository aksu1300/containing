/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import serialclass.Command;
import containing.transport.Train;
import containing.transport.Truck;
import containing.transport.Boat;
import HUD.MyHUD;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.cinematic.MotionPath;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;
import containing.mediator.ClientSocket;
import containing.mediator.Mediator;
import containing.transport.TrainCrane;
import containing.transport.Wagon;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
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

        //right camera position
        //cam.setLocation(new Vector3f(200, 150, 150));
        //cam.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_Y);
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


        //for loop loopt door alles heen tot ddat die de juiste container heeft
        //System.out.println(freighter.containers.get(4).get(4).peek().getLocalTranslation());
        //System.out.println(freighter.getDocked());




        for (Wagon w : train.getWagons()) {
            w.setCargo(new Container(assetManager, 1.0f));
        }
        //loopt die door alle cranes heen loopt en vervolgens 
        //door de wagons om te zien welke containjer hij moet hebben
        //lets try this in the simpleupdate 
        //tCranes.get(3).doMove(train.getWagons().get(5),train);
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
        //
        if (!Mediator.commands.isEmpty() /* && !Mediator.getWriting()*/) {

            System.out.println("I've seen the light man! SHEIZE");
            parseCommand((Command) Mediator.getCommand());
            Mediator.removeCommand();
        }

        if (shCrane.get(0).moving == true) {
            if (freighter != null) {
                if (freighter.getDocked()) {
                    //need a for loop to check if crane is available and to loop trough all the stacks to find a container with the right id
                    shCrane.get(0).moveCrane(freighter.containers.get(2).get(3).peek().getLocalTranslation());
                }
            }
        }
    }

    public void initKeys() {
        inputManager.addMapping("Show", new KeyTrigger(KeyInput.KEY_F10));
        inputManager.addMapping("Hide", new KeyTrigger(KeyInput.KEY_F9));
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "Show", "Hide");

    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Show") && !keyPressed) {
                //show the gui
                initHud();
            }
            if (name.equals("Hide") && !keyPressed) {
                //and lets hide the gui
                nifty.exit();
            }
        }
    };

    private void initHud() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);


        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        // <screen>
        nifty.addScreen("hud", new ScreenBuilder("hud") {
            {
                controller(new MyHUD());
                layer(new LayerBuilder("background") {
                    {
                        childLayoutVertical();
                        padding("16px,0px,0px,0px");
                        image(new ImageBuilder() {
                            {
                                childLayoutVertical();
                                filename("Interface/top-left2.png");
                                alignLeft();
                            }
                        });
                    }
                });

                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutVertical();

                        panel(new PanelBuilder("panel_top") {
                            {
                                childLayoutCenter();
                                alignLeft();
                                height("120px");
                                width("160px");

                                panel(new PanelBuilder("panel_top_right1") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("16px,00px,58px,82px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/settings-button.png");
                                                valignCenter();
                                                interactOnClick("switchScreen(config)");
                                            }
                                        });
                                    }
                                });

                                panel(new PanelBuilder("panel_top_right2") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("93px,00px,0px,12px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/play-button.png");
                                                valignCenter();
                                                interactOnClick("startSimulatie()");
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder("panel_top_right3") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("28px,00px,0px,45px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/stop-button.png");
                                                valignCenter();
                                                interactOnClick("stopSimulatie()");
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder("panel_top_right5") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("220px,00px,0px,10px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/ffw1.png");
                                                valignCenter();
                                                interactOnClick("stopSimulatie()");
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder("panel_top_right6") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("330px,00px,0px,10px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/ffw2.png");
                                                valignCenter();
                                                interactOnClick("stopSimulatie()");
                                            }
                                        });
                                    }
                                });
                                panel(new PanelBuilder("panel_top_right7") {
                                    {
                                        childLayoutCenter();
                                        alignLeft();
                                        padding("440px,00px,0px,10px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/ffw3.png");
                                                valignCenter();
                                                interactOnClick("stopSimulatie()");
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
        nifty.gotoScreen("hud");

        nifty.addScreen("config", new ScreenBuilder("config") {
            {
                controller(new MyHUD());

                layer(new LayerBuilder("foreground") {
                    {
                        childLayoutCenter();

                        panel(new PanelBuilder("panel_top_right3") {
                            {
                                childLayoutCenter();

                                image(new ImageBuilder() {
                                    {
                                        filename("Interface/config.png");

//                                                interactOnClick("switchScreen(hud)");
                                    }
                                });

                                panel(new PanelBuilder("panel_top_right3") {
                                    {
                                        childLayoutCenter();
                                        alignRight();
                                        padding("0px,16px,480px,0px");

                                        control(new ButtonBuilder("StartButton", "X") {
                                            {
                                                alignRight();
                                                height("20px");
                                                width("50px");
                                                visibleToMouse(true);
                                                interactOnClick("switchScreen(hud)");
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.build(nifty));
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
        /**
         * *********************************
         */
        /**
         * * mag het een switch zijn aub? **
         */
        /**
         * *********************************
         */
        /*
         * The Create commands
         */
        if (cmd.getCommand().equals("createShip")) {
            //Adding freighter to the harbor
            freighter = new Freighter(assetManager);
            freighter.setName(cmd.getIdentifier());
            rootNode.attachChild(freighter);
        }

        if (cmd.getCommand().equals("createFreighter")) {
            //Adding freighter to the harbor
            freighter = new Freighter(assetManager);
            freighter.Move(harbor.getDockingroute(), 1.2f);

            rootNode.attachChild(freighter);

        }

        if (cmd.getCommand().equals("createTrain")) {
            Train train = new Train(new Vector3f(3, 4, 5), assetManager);
            train.setContainers(cmd.getContents());
            train.setName(cmd.getIdentifier());

            // Attach the train to the rootNode
            rootNode.attachChild(train);
        }

        // 
        if (cmd.getCommand().equals("createTruck")) {
            //Create truckt
            Truck truck = new Truck(cmd.getIdentifier(), new Vector3f(0, 0, 0), 2, assetManager);
            truck.setContainer(cmd.getContents().get(0));

            // Attach the truck to the rootNode
            rootNode.attachChild(truck);
        }


        /*
         * The moving commands
         */
        if (cmd.getCommand().equals("moveShip")) {
            // Get the ship 
            Boat ship = (Boat) rootNode.getChild(cmd.getIdentifier());
            ship.Move(harbor.getDockingroute(), speed);
        }

        if (cmd.getCommand().equals("moveFreighter")) {
            Freighter freighter = (Freighter) rootNode.getChild(cmd.getIdentifier());
            freighter.Move(harbor.getDockingroute(), speed);
        }

        if (cmd.getCommand().equals("moveTrain")) {
            Train train = (Train) rootNode.getChild(cmd.getIdentifier());
        }

        if (cmd.getCommand().equals("moveTruck")) {
            Truck truck = (Truck) rootNode.getChild(cmd.getIdentifier());
            truck.move(lightDir);
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
