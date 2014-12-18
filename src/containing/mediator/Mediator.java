package containing.mediator;

import serialclass.Command;
import java.util.Stack;

public class Mediator {
    
    public static boolean writing = false;
    public static Stack commands = new Stack();

    public Mediator(){
    }
    
    public static void sendCommand(Command input) {
        commands.push(input);
            
        System.out.println("Stack size : " + commands.size());
    }
    
    public static Boolean getWriting(){
        return writing;
    }

    public static void setWriting(){
        writing = !writing;
    }

    public static Command getCommand() {
        return (Command)commands.peek();
    }
    
    public static void removeCommand(){
        commands.pop();
    }
}
