
package containing.mediator;

public class Mediator {
    private Protocol protocol;
    
    public Mediator() {
        
    }
    
    private void parse() {
        switch (protocol) {
            case TEST: System.out.println("TEST");
                break;
        }
    
    }
}

// poort 5400