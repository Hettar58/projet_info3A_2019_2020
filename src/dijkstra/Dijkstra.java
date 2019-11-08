/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author yt646712
 */
public class Dijkstra extends JFrame{
    RenderPanel UI;
    
    public Dijkstra(){
        this.setTitle("Dijkstra");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        UI = new RenderPanel();
       
        this.add(UI);
        
        Point p1 = new Point(200, 400);
        Point p2 = new Point(350, 350);
        Disque d1 = new Disque(p1, 40);
        Point p3 = new Point(10, 10);
        Point p4 = new Point(110, 110);
        Rectangle r1 = new Rectangle(p3, p4);
        UI.addObstacle(d1);
        UI.addObstacle(r1);
        UI.addPoint(p1);
        UI.addPoint(p2);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.setProperty("file.encoding", "UTF-8");
        EventQueue.invokeLater(() -> {
            Dijkstra app = new Dijkstra();
        });
    }   
}
