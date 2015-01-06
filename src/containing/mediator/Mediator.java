package containing.mediator;

import java.util.ArrayList;
import serialclass.Command;

public class Mediator {

    private static boolean writing = false; //Status of the Mediator.
    public static ArrayList commands = new ArrayList(); //inventory of the mediator.

    /**
     * Adds a command to the Mediator.
     * @param input The command you want to add to the Mediator. 
     */
    public static void sendCommand(Command input) {
        commands.add(input);
        System.out.println("Command added to Mediator");
    }

    /**
     * @return the status of the Mediator.  
     */
    public static Boolean getWriting() {
        return writing;
    }

    /**
     * Switches the status of the Mediator.
     */
    public static void setWriting() {
        writing = !writing;
    }
    
    /**
     * @return the 1st Command in the Mediator. 
     */
    public static Command getCommand() {
        Command c = (Command) commands.get(0);
        commands.remove(0);
        System.out.println("Forwarded command (" + c.getCommand() + " " + c.getIdentifier() + ").");
        return  c;
    }

}
