package containing.mediator;

import containing.WayPoints;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialclass.Command;

public class ClientSocket implements Runnable {

    private Socket socket = null;
    private int portNumber = 5400;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isConnected = false;

    @Override
    public void run() {
        Command com = null;
        
        while (true) {
            while (!isConnected) {
                try {
                    socket = new Socket("localHost", portNumber);
                    System.out.println("Connection established.");
                    isConnected = true;
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                Command c = (Command) inputStream.readObject();
                System.out.println("Command recieved.");
                if (c != null && c != com) {
                    Mediator.sendCommand(c);
                    com = c;
                    System.out.println("Command forwarded.");
                } else {
                    System.out.println("error in ClientSocket:run, sendCommand");
                }
                //inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                //System.out.println(ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("SHEIZE WEIZE end of reading while!");
        }

    }

    public void sendTest() {
        try {
            WayPoints obj = new WayPoints();
            outputStream.writeObject(obj);


        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readTest() {
        try {
            Object obj = (Object) inputStream.readObject();
            System.out.println("Object Received");


        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}