/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import HUD.Hud;
import HUD.MyHUD;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.cinematic.MotionPath;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

public class Game extends SimpleApplication {

    private BulletAppState bulletAppState;
    private float initialWaterHeight = 0f;
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);
    AGV agv;
    Spatial cargo;
    shipCrane shCrane;
    Train train;
    Truck truck;
    
   
    
    Freighter freighter;
    Boat ship;
    Harbor harbor;
    MotionPath motionPath;
    MotionPath motionPath1;
    
    boolean pushedtoship = false; //check if grabber is pushed to ship
    boolean pulledfromship = false; //check if grabber is pulled from ship
    boolean grabberin = false; // check if grabber is in
    boolean pushedtoagv = false; // check if grabber is pushed to agv
    boolean agvgo = false; // check if grabber is pulled from agv
    boolean pulledfromagv = false;
    boolean sequence = true; // true means incomplete.
    boolean agvatc = true;

    @Override
    public void simpleInitApp() {
        initHud();
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        harbor = new Harbor(bulletAppState, assetManager);

        //right camera position
        //cam.setLocation(new Vector3f(200, 150, 150));
        //cam.lookAt(Vector3f.UNIT_Y, Vector3f.UNIT_Y);
//        flyCam.setEnabled(true);
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(100);
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
        ship = new Boat(assetManager, 0.5f);
        //ship.addContainer(new Container(assetManager, 1f));
        ship.Move(harbor.getDockingroute(), 1.2f);
        rootNode.attachChild(ship);

        //Adding freighter to the harbor
        freighter = new Freighter(assetManager, 0.5f);
        //freighter.addContainer(new Container(assetManager,1f));
        freighter.Move(harbor.getFreighterDock(), 0.3f);
        rootNode.attachChild(freighter);

       
        
        // Adding a AGV to the harbor
        Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        agv = new AGV("1", material, assetManager);
        agv.rotate(0, FastMath.PI, 0);
        agv.setLocalTranslation(0, 10.5f, 0);
        rootNode.attachChild(agv);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(ship.getDocked()){
            for (shipCrane sc : harbor.shCranes){
                if(pushedtoship == false){ // Grabber not down
                   pushedtoship = sc.pushGrabber(tpf);}  // Gettting grabber down   

                if (pushedtoship && sc.container == null){ //adding a set of container
                   sc.container = new Container(assetManager, 1);
                   sc.container.setLocalTranslation(new Vector3f(sc.getChild(3).getLocalTranslation()).setY(sc.getChild(3).getLocalTranslation().getY()+11.5f));
                   sc.attachChild(sc.container); 
                }

                if(pulledfromship == false && pushedtoship){ // Grabber not up but down
                    pulledfromship = sc.pullGrabber(tpf);} // Getting grabber up

                if(pulledfromship && grabberin == false){ // Grabber not in
                    grabberin = sc.inGrabber(tpf);} // Bringing grabber in

                if(grabberin && pushedtoagv == false){ // Grabber not down to agv
                    pushedtoagv = sc.pushGrabber(tpf);} // Getting grabber down to agv

                if(pushedtoagv && agvgo == false){
                    agv.setContainer(sc.container); // the container is attached to the agv.
                    agvgo = true;}
                
                if(agvgo && pulledfromagv == false){
                    pulledfromagv = sc.pullGrabber(tpf);}
                    
                if(agvatc && agvgo && pulledfromagv){
                    agv.Move(harbor.fromcranepaths.get(0), 1f); // the agv will move away from the crane
                    agvatc = false;
                }
                    
            }
        }   
        System.out.println(cam.getLocation().x);
        System.out.println(cam.getLocation().y);
        System.out.println(cam.getLocation().z); 
    }

    private void initHud(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
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
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
