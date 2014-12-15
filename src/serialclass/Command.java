/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serialclass;

import containing.Container;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class Command implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    
    String command;
    String identifier;
    ArrayList<Container> contents = new ArrayList<Container>();
    Container content;
    float orx;
    float ory;
    float orz;
    float dex;
    float dey;
    float dez;

    /**
     * For creating ships, boats, trains
     * @param command
     * @param identifier
     * @param contents 
     */
    public Command(String command, String identifier, ArrayList<Container> contents){
        
        this.command = command;
        this.identifier = identifier;
        this.contents = contents;
    }
    
    /**
     * 
     * @param command
     * @param identifier
     * @param content 
     */
    public Command(String command, String identifier, Container content){
        this.command = command;
        this.identifier = identifier;
        this.content = content;
    }
    
    /**
     * 
     * @param command
     * @param identifier 
     */
    public Command(String command, String identifier){
        
        this.command = command;
        this.identifier = identifier;
        
    }
    
    /**
     * 
     * @param command
     * @param identifier
     * @param orgin
     * @param destin 
     */
     public Command(String command, String identifier, float orx, float ory, float orz, float dex, float dey, float dez) {

        this.command = command;
        this.identifier = identifier;
        this.orx = orx;
        this.ory = ory;
        this.orz = orz;

        this.dex = dex;
        this.dey = dey;
        this.dez = dez;
    }
     
    public String getCommand() {
        return command;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<Container> getContents() {
        return contents;
    }

    public Container getContent() {
        return content;
    }

    public float getOrx() {
        return orx;
    }

    public float getOry() {
        return ory;
    }

    public float getOrz() {
        return orz;
    }

    public float getDex() {
        return dex;
    }

    public float getDey() {
        return dey;
    }

    public float getDez() {
        return dez;
    }
    
     public String toString() {
        return command + ":" + identifier  + ":(" + orx + ":" + ory + ":"+ orz + "):";
    }
}
