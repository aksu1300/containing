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

public class ClientSocket implements Runnable {
    
    private Socket socket = null;
    private int portNumber = 5400;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isConnected = false;

    @Override
    public void run() {
        while(!isConnected) {
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