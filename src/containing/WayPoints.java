/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author Driving Ghost
 */
public class WayPoints {

    private ArrayList<Point> wpList;
    Point S1A;
    Point S2A;
    Point S3A;
    Point S4A;
    Point S5A;
    Point S6A;
    Point S1B;
    Point S2B;
    Point S3B;
    Point S4B;
    Point S5B;
    Point S6B;
    //Platform Corners
    Point PA;
    Point PB;
    Point PC;
    Point PD;
    Point PE;
    Point PF;
    Point PG;
    Point PH;
    //Freighter Crane Corners
    Point CA;
    Point CB;
    //Cranes Freighter
    Point C1;
    Point C2;
    Point C3;
    Point C4;
    Point C5;
    Point C6;
    //Boat Crane Corners
    Point FA;
    Point FB;
    //Cranes Boat
    Point F1;
    Point F2;
    Point F3;
    Point F4;
    //Trains
    Point T1;
    Point T2;
    Point T3;
    Point T4;
    Point T5;
    //Trucks
    Point E1;
    Point E2;
    Point E3;
    Point E4;
    Point E5;

    WayPoints() {
        wpList = new ArrayList<Point>();
        initPoints();
        initList();


    }

    
    
    private void initPoints() {
        S1A = new Point("S1A", new Vector3f(95, 12, 73));
        S2A = new Point("S2A", new Vector3f(95, 12, 28));
        S3A = new Point("S3A", new Vector3f(95, 12, -17));
        S4A = new Point("S4A", new Vector3f(95, 12, -62));
        S5A = new Point("S5A", new Vector3f(95, 12, -107));
        S6A = new Point("S6A", new Vector3f(95, 12, -152));
        S1B = new Point("S1B", new Vector3f(-142, 12, 73));
        S2B = new Point("S2B", new Vector3f(-142, 12, 28));
        S3B = new Point("S3B", new Vector3f(-142, 12, -17));
        S4B = new Point("S4B", new Vector3f(-142, 12, -62));
        S5B = new Point("S5B", new Vector3f(-142, 12, -107));
        S6B = new Point("S6B", new Vector3f(-142, 12, -152));
        //Platform Corners
        PA = new Point("PA", new Vector3f(95, 12, 110));
        PB = new Point("PB", new Vector3f(-142, 12, 110));
        PC = new Point("PC", new Vector3f(-182, 12, 110));
        PD = new Point("PD", new Vector3f(-107, 12, 110));
        PE = new Point("PE", new Vector3f(-182, 12, -190));
        PF = new Point("PF", new Vector3f(-142, 12, -190));
        PG = new Point("PG", new Vector3f(95, 12, -190));
        PH = new Point("PH", new Vector3f(0, 0, 0));
        //Freighter Crane Corners
        CA = new Point("CA", new Vector3f(135, 12, 110));
        CB = new Point("CB", new Vector3f(135, 12, -190));
        //Cranes Freighter
        C1 = new Point("C1", new Vector3f(0, 0, 0));
        C2 = new Point("C2", new Vector3f(0, 0, 0));
        C3 = new Point("C3", new Vector3f(0, 0, 0));
        C4 = new Point("C4", new Vector3f(0, 0, 0));
        C5 = new Point("C5", new Vector3f(0, 0, 0));
        C6 = new Point("C6", new Vector3f(0, 0, 0));
        //Boat Crane Corners
        FA = new Point("FA", new Vector3f(0, 0, 0));
        FB = new Point("FB", new Vector3f(0, 0, 0));
        //Cranes Boat
        F1 = new Point("F1", new Vector3f(0, 0, 0));
        F2 = new Point("F2", new Vector3f(0, 0, 0));
        F3 = new Point("F3", new Vector3f(0, 0, 0));
        F4 = new Point("F4", new Vector3f(0, 0, 0));
        //Trains
        T1 = new Point("T1", new Vector3f(0, 0, 0));
        T2 = new Point("T2", new Vector3f(0, 0, 0));
        T3 = new Point("T3", new Vector3f(0, 0, 0));
        T4 = new Point("T4", new Vector3f(0, 0, 0));
        T5 = new Point("T5", new Vector3f(0, 0, 0));
        //Trucks
        E1 = new Point("E1", new Vector3f(0, 0, 0));
        E2 = new Point("E2", new Vector3f(0, 0, 0));
        E3 = new Point("E3", new Vector3f(0, 0, 0));
        E4 = new Point("E4", new Vector3f(0, 0, 0));
        E5 = new Point("E5", new Vector3f(0, 0, 0));
    }

    private void initList() {

        wpList.add(S1A);
        wpList.add(S2A);
        wpList.add(S3A);
        wpList.add(S4A);
        wpList.add(S5A);
        wpList.add(S6A);

        wpList.add(S1B);
        wpList.add(S2B);
        wpList.add(S3B);
        wpList.add(S4B);
        wpList.add(S5B);
        wpList.add(S6B);

        wpList.add(PA);
        wpList.add(PB);
        wpList.add(PC);
        wpList.add(PD);
        wpList.add(PE);
        wpList.add(PF);
        wpList.add(PG);
        wpList.add(PH);

        wpList.add(CA);
        wpList.add(CB);

        wpList.add(C1);
        wpList.add(C2);
        wpList.add(C3);
        wpList.add(C4);
        wpList.add(C5);
        wpList.add(C6);

        wpList.add(FA);
        wpList.add(FB);

        wpList.add(F1);
        wpList.add(F2);
        wpList.add(F3);
        wpList.add(F4);

        wpList.add(T1);
        wpList.add(T2);
        wpList.add(T3);
        wpList.add(T4);
        wpList.add(T5);

        wpList.add(E1);
        wpList.add(E2);
        wpList.add(E3);
        wpList.add(E4);
        wpList.add(E5);
    }

    
    
    //Storage
    public Vector3f getPoint(String pointid) {

        for (Point p : wpList) {
            if (p.id.equals(pointid)) {
                //System.out.println(p.id + "  " + p.coords);
                return p.coords;
            }
        }
        return null;
    }
}
