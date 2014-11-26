/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HUD;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Kenneth
 */
public class MyHUD extends AbstractAppState implements ScreenController {

    
    private Nifty nifty;
    private Application app;
    private Screen screen;
    
    public MyHUD(){
        
    }
    
    public void switchScreen(String nextScreen) {
        nifty.gotoScreen(nextScreen);  //switch screens
      
    }

    public void startSimulatie(){
        
    }
    
    public void stopSimulatie(){
        
    }
  
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
  
    }

    public void onStartScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
