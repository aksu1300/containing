package HUD;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * test
 *
 * @author normenhansen
 */
public class Hud extends SimpleApplication {

    private static Hud app;

    public static void main(String[] args) {
        app = new Hud();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        app.setShowSettings(false); // splashscreen
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        app.setDisplayFps(false);
        app.setDisplayStatView(false);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        // <screen>
        nifty.addScreen("hud", new ScreenBuilder("hud") {
            {
                controller(new MyHUD());
                layer(new LayerBuilder("background") {
                    {
                        childLayoutVertical();
                        
                        image(new ImageBuilder() {
                            {
                                childLayoutVertical();
                                filename("Interface/top-left1.png");
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
                                        padding("0px,00px,74px,82px");

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
                                        padding("63px,00px,0px,12px");

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
                                        padding("0px,00px,5px,47px");

                                        image(new ImageBuilder() {
                                            {
                                                filename("Interface/stop-button.png");
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
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
