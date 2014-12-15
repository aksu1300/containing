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
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;
import containing.storage.Storage;
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
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation extends SimpleApplication {

    // The client socket
    private static Socket clientSocket = null;
    private static DataInputStream is = null;
    private static BufferedReader inputLine = null;
    private BulletAppState bulletAppState;
    private float initialWaterHeight = 0f;
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);
    Spatial cargo;
    ShipCrane shCrane;
    Truck t;
    Train train;
    Truck truck;
    Freighter freighter;
    Boat boat;
    Harbor harbor;
    MotionPath motionPath;
    MotionPath motionPath1;
    AGVController agvc;
    Socket socket;
    ArrayList<String> path;

    public Simulation() {
        initSockets();
    }

    public void startSimulation() {
        if (clientSocket == null) {
            this.start();
            System.out.println("Cannot start up the application without server connection.\r\nPlease start up the Server first. ");
            
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
        //flyCam.setEnabled(true);
        flyCam.setDragToRotate(true);
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

        harbor.shCranes.get(0).moveCranes(freighter);

    }

    @Override
    public void simpleUpdate(float tpf) {
        ///Update inputlist
        
        updateCommands();
        
        ///Send outputlist
    }
    
    private void initHud() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
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
    
    private void updateCommands(){
        try {
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Command c = (Command) ois.readObject();
            // send recieved command to the parser
            parseCommand(c);
            System.out.println(c.toString());
            
        } catch (IOException ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initSockets() {
        try {
            socket = new Socket("127.0.0.1", 5400);
            path = new ArrayList<String>();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The parseCommand method will parse 
     * Commands sent to the simulator
     * @param cmd 
     */
    private void parseCommand(Command cmd){
        
       /*
        * The Create commands
        */
       if(cmd.getCommand().equals("createShip")){
           // The createShip command is recvied so create a ship
           Boat ship = new Boat(assetManager);
           ship.setContainers(cmd.getContents());
           ship.setId(cmd.getIdentifier());
           
           // attach the boat to the rootNode
           rootNode.attachChild(ship);        
           
           // 
           System.out.println("We recieved a createShip sheize :" + ship.getContainers().size());
       }
       
       if(cmd.getCommand() == "createFreighter"){
       }

       if(cmd.getCommand() == "createTrain"){
           Train train = new Train(new Vector3f(3,4,5), assetManager);
           train.setContainers(cmd.getContents());
           train.setId(cmd.getIdentifier());
           
           // Attach the train to the rootNode
           rootNode.attachChild(train);
       
       }
       
       if(cmd.getCommand() == "createTruck" ){
       
       } 
        
        
       /*
        * The moving commands
        */
       if(cmd.getCommand() == "moveShip"){
           
       }
       
       if(cmd.getCommand() == "moveFreighter"){
       
       }
       
       if(cmd.getCommand() == "moveShip"){
           
       }
       
       if(cmd.getCommand() == "moveTrain"){
       
       }
       
       if(cmd.getCommand() == "moveTruck" ){
       
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
