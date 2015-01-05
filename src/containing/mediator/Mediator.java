package containing.mediator;

import java.util.ArrayList;
import serialclass.Command;
import java.util.Stack;

public class Mediator {

    /*<<<<<<< HEAD
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
     =======*/
    private static boolean writing = false;
    public static ArrayList commands = new ArrayList();

    public static void sendCommand(Command input) {
        commands.add(input);
    }

    public static Boolean getWriting() {
        return writing;
    }

    public static void setWriting() {
        writing = !writing;
    }

    public static Command getCommand() {
        Command c = (Command) commands.get(0);
        commands.remove(0);
        return  c;
    }

}
