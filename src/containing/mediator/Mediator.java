package containing.mediator;

public class Mediator {
    private static Protocol protocol;
    
    public Mediator() {
        
    }
    
    private static void parse() {
        switch (protocol) {
            case TEST: System.out.println("TEST");
                break;
        }
    
    }
}

// poort 5400