package containing.mediator;

import serialclass.Command;
import java.util.Stack;

public class Mediator {
    
<<<<<<< HEAD
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
=======
    static boolean writing = false;
    static Stack commands = new Stack();

    static void sendCommand(Command input) {
        commands.add(input);
    }
    
    static Boolean getWriting(){
        return writing;
    }

    static void setWriting(){
        writing = !writing;
    }

    static Command getCommand() {
        return (Command)commands.peek();
    }
    
    static void removeCommand(){
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
        commands.pop();
    }
}
