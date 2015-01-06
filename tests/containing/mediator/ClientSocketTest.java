/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing.mediator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author markz_000
 */
public class ClientSocketTest {
    
    public ClientSocketTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /*
     * assertTrue(), assertFalse(), assertEquals(), or assert().
     */
    
    /**
     * Test of run method, of class ClientSocket.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        ClientSocket instance = new ClientSocket();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendTest method, of class ClientSocket.
     */
    @Test
    public void testSendTest() {
        System.out.println("sendTest");
        ClientSocket instance = new ClientSocket();
        instance.sendTest();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readTest method, of class ClientSocket.
     */
    @Test
    public void testReadTest() {
        System.out.println("readTest");
        ClientSocket instance = new ClientSocket();
        instance.readTest();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}