/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.mediator;

import containing.WayPoints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
<<<<<<< HEAD
import serialclass.Command;
=======
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472

public class ClientSocket implements Runnable {
    
    private Socket socket = null;
<<<<<<< HEAD
    private int portNumber = 6200;
=======
    private int portNumber = 5400;
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isConnected = false;

    @Override
    public void run() {
        while(!isConnected) {
<<<<<<< HEAD
            try {            
                socket = new Socket("127.0.0.1", portNumber);
                System.out.println("Connection established.");  
                isConnected = true;
=======
            try {
                socket = new Socket("localHost", portNumber);
                System.out.println("Connection established.");
                isConnected = true;
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
            } catch (UnknownHostException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
<<<<<<< HEAD
            
            
        }
        try{
            outputStream = new ObjectOutputStream(socket.getOutputStream());
                
        }
        catch(IOException ex){
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("SHEIZE WEIZE");
                Command c = (Command) inputStream.readObject();
                if (c != null){
                    Mediator.sendCommand(c);
                }
                else{
                    System.out.println("whats up with you nigga");
                }
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("SHEIZE WEIZE end of reading while!");
        }
        
=======
        }
>>>>>>> bcd73466b92e4a90b6286217767bfb1f79ced472
    }
    
    public void sendTest() {
        try {
            WayPoints obj = new WayPoints();
            outputStream.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readTest() {
        try {
            Object obj = (Object) inputStream.readObject();
            System.out.println("Object Received");
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(Object obj) {
        
    }
    
    public Object read() {
        return null;
    }
}