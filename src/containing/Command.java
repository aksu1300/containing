/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.math.Vector3f;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class Command implements Serializable{
    
    String command;
    String identifier;
    
    ArrayList<Container> contents = new ArrayList<Container>();
    
    Vector3f orgin;
    Vector3f destin;
    
    public Command(String command, String identifier, ArrayList<Container> contents){
        
        this.command = command;
        this.identifier = identifier;
        this.contents = contents;
        
    }
    
    public Command(String command, String identifier){
        
        this.command = command;
        this.identifier = identifier;
        
    }
    
    
    public Command(String command, String identifier, Vector3f orgin, Vector3f destin){
        
        this.command = command;
        this.identifier = identifier;
        this.orgin = orgin;
        this.destin = destin;
    }
    
}
