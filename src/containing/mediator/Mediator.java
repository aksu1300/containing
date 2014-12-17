package containing.mediator;

import serialclass.Command;
import java.util.Stack;

public class Mediator {
    
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
        commands.pop();
    }
}
