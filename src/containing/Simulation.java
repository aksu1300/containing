/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
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
    int counter = 0;
    //Guid
    private Nifty nifty;

    public Simulation() {
        initSockets();
    }

    public void startSimulation() {
        if (clientSocket == null) {
            System.out.println("Cannot start up the application without server connection.\r\nPlease start up the Server first.");
        } else {
            this.start();
        }
    }

    @Override
    public void simpleInitApp() {
        initHud();
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        harbor = new Harbor(bulletAppState, assetManager);
        agvc = new AGVController();

        //right camera position
        //cam.setLocation(new Vector3f(200, 150, 150));
        //cam.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_Y);
        flyCam.setEnabled(true);
//        flyCam.setDragToRotate(true);
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

        // Adding a ship to the scene
        boat = new Boat(assetManager);
        boat.Move(harbor.getFreighterDock(), 0.3f);
        rootNode.attachChild(boat);
        //Adding freighter to the harbor
        freighter = new Freighter(assetManager);
        freighter.Move(harbor.getDockingroute(), 1.2f);

        rootNode.attachChild(freighter);
        for (TruckCrane tc : harbor.truckCranes) {
            Container xxx = new Container(assetManager, 1);
            tc.setAGV(new AGV("AAA", assetManager));
            tc.agv.setContainer(xxx);
            tc.craneDown();
        }

        shCrane = harbor.shCranes;
        //this works 
//        shCrane.get(0).moveCrane(new Vector3f(0,0,4));
        tCranes = harbor.trainCranes;
        train = harbor.train;
        
        
        //for loop loopt door alles heen tot ddat die de juiste container heeft
//        System.out.println(freighter.containers.get(4).get(4).peek().getLocalTranslation());
//        System.out.println(freighter.getDocked());
        
            
            
        
        for (Wagon w : train.getWagons()) {
            w.setCargo(new Container(assetManager, 1.0f));         
        }
        //loopt die door alle cranes heen loopt en vervolgens 
        //door de wagons om te zien welke containjer hij moet hebben
//            tCranes.get(3).doMove(train.getWagons().get(5),train);
//            tCranes.get(2).doMove(train.getWagons().get(4),train);
     
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
        if(shCrane.get(0).moving == true){
            if (freighter.getDocked()) {
                    shCrane.get(0).moveCrane(freighter.containers.get(20).get(3).peek().getLocalTranslation());   
            }
        }
//        System.out.println(cam.getLocation().x);
//        System.out.println(cam.getLocation().y);
//        System.out.println(cam.getLocation().z);
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

    private void initSockets() {
        /*
         * Open a socket on a given host and port. Open input and output streams.
         */
        try {
            clientSocket = new Socket("localhost", 5400);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            is = new DataInputStream(clientSocket.getInputStream());
            is.close();
            inputLine.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "localhost");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + "localhost");
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
