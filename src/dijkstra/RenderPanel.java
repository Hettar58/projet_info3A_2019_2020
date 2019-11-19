/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author yt646712
 */
public class RenderPanel extends JPanel{
    ArrayList<Obstacle> obstacles;
    ArrayList<Point> points;                 //a voir avec le HEAP
    ArrayList<Point> PCC;
    Point origine;
    Point arrivee;
    
    public RenderPanel(){
        //appels de new a sortir du constructeur
        obstacles = Dijkstra.obstacles;
        points = Dijkstra.graphe;
        PCC = Dijkstra.PCC;
        origine = Dijkstra.origine;
        arrivee = Dijkstra.arrivee;
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        for (Obstacle o : obstacles){
            o.afficher(g);
        }
        
        g.setColor(Color.RED);
        for (Point p : points){
            p.afficher(g);
        }
        
        g.setColor(Color.BLUE);
        for (int i = 0; i < PCC.size() - 1; i++){
            g.drawLine(PCC.get(i).getX(), PCC.get(i).getY(), PCC.get(i + 1).getX(), PCC.get(i + 1).getY());
            g.fillOval(PCC.get(i).getX(), PCC.get(i).getY(), 4, 4);
            g.fillOval(PCC.get(i+1).getX(), PCC.get(i+1).getY(), 4, 4);
        }
        
        g.setColor(Color.BLACK);
        origine.afficher(g);
        arrivee.afficher(g);
    }
    
    public void addObstacle(Obstacle o){
        obstacles.add(o);
    }
    
    public void addPoint(Point p){
        points.add(p);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}
