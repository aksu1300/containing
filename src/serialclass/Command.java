package serialclass;

import containing.Container;
import java.io.Serializable;
import java.util.ArrayList;

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
     * For createing freighter, boats, trains
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
     * For createing AVGs, Trucks. 
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
     * @param command What to be done with the object in question.
     * @param identifier ID of the object in question. 
     * @param target the target parameter is used to identify the point or object to move to.
     */
    public Command(String command, String identifier, String target){ //class is incorect!
        
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
     
<<<<<<< HEAD
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
    
=======
     /**
      * The toString method allows you to print all the information this object holds. 
      * @return Object information, such as the command, identifier, orx, ory and orz value in a single string. 
      */
    @Override
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
     public String toString() {
        String x = command + ":" + identifier  + ":(" + orx + ":" + ory + ":"+ orz + ")";
        for (int i = 0; i < contents.size(); i++){
            x += contents.get(i);
        }
        return x;
    }

<<<<<<< HEAD
    
=======
    //gets & sets
    
    public ArrayList<Container> getContents() {
        return contents;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setContents(ArrayList<Container> contents) {
        this.contents = contents;
    }

<<<<<<< HEAD
=======
    public Container getContent() {
        return content;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setContent(Container content) {
        this.content = content;
    }

<<<<<<< HEAD
=======
    public float getOrx() {
        return orx;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setOrx(float orx) {
        this.orx = orx;
    }

<<<<<<< HEAD
=======
    public float getOry() {
        return ory;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setOry(float ory) {
        this.ory = ory;
    }

<<<<<<< HEAD
=======
    public float getOrz() {
        return orz;
    }
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472

    public void setOrz(float orz) {
        this.orz = orz;
    }

<<<<<<< HEAD
=======
    public float getDex() {
        return dex;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setDex(float dex) {
        this.dex = dex;
    }

<<<<<<< HEAD
=======
    public float getDey() {
        return dey;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setDey(float dey) {
        this.dey = dey;
    }

<<<<<<< HEAD
=======
    public float getDez() {
        return dez;
    }

>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    public void setDez(float dez) {
        this.dez = dez;
    }
    
    
}
