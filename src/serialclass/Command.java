/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serialclass;

import containing.Container;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * PROJECT IS SIMULATION!!
 * @author Driving Ghost
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    
    final String command;
    final String identifier;
    ArrayList<Container> contents = new ArrayList<Container>();
    Container content;
    float orx;
    float ory;
    float orz;
    float dex;
    float dey;
    float dez;

    /**
     * For creating freighter, boats, trains
     * @param command Use the create string to use this class correctly.
     * @param identifier Class name of the item you wish to create. Such as boat, freighter or train.
     * @param contents List of Containers. It holds all the containers in it's cargo hold.
     */
    public Command(String command, String identifier, ArrayList<Container> contents){
        
        this.command = command;
        this.identifier = identifier;
        this.contents = contents;
    }
    
    /**
     * for creating trucks! 
     * @param command use the create String to use this class correctly.
     * @param identifier class name of the object you wish to create.
     * @param content A single container. 
     */
    public Command(String command, String identifier, Container content){
        this.command = command;
        this.identifier = identifier;
        this.content = content;
    }
    
    /**
     * For moving units!
     * @param command Use the MOVE command to use this correctly.
     * @param identifier the identifier parameter is used to grab the desired object. Such as an AGV or a crane. 
     * @param target the target parameter is used to identify the point or object to move to
     */
    public Command(String command, String identifier, String target){
        
        this.command = command;
        this.identifier = identifier;
        
    }
    
    /**
     * This command is used to move targets to a vector desired by the user.
     * @param command use the MOVE command to use this correctly
     * @param identifier the identifier parameter is used to grab the desired object. This can be an AGV or a crane.
     * @param dex Destination vector x value.
     * @param dey Destination vector y value.
     * @param dez Destination vector z value. 
     */
     public Command(String command, String identifier, float dex, float dey, float dez) {

        this.command = command;
        this.identifier = identifier;
        
        this.dex = dex;
        this.dey = dey;
        this.dez = dez;
    }
    
     /**
      * The toString method allows you to print all the information this object holds. 
      * @return Object information, such as the command, identifier, orx, ory and orz value in a single string. 
      */
     public String toString() {
        String x = command + ":" + identifier  + ":(" + orx + ":" + ory + ":"+ orz + ")";
        for (int i = 0; i < contents.size(); i++){
            x += contents.get(i);
        }
        return x;
    }
}
