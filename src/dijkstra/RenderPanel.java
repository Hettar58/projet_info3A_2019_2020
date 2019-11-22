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

    ArrayList<Sommet> points;                 //a voir avec le HEAP
    ArrayList<Sommet> PCC;
    ArrayList<Sommet> graphe_purge;

    
    public RenderPanel(){
        //appels de new a sortir du constructeur
        obstacles = Dijkstra.obstacles;
        points = Dijkstra.graphe;
        PCC = Dijkstra.PCC;

        graphe_purge = Dijkstra.graphe;
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        for (Obstacle o : obstacles){
            o.afficher(g);
        }
        
        g.setColor(Color.RED);
        for (Sommet s : graphe_purge){
            s.pos.afficher(g);
            for (Sommet s2 : s.voisins){
                g.drawLine(s.pos.getX(), s.pos.getY(), s2.pos.getX(), s2.pos.getY());
                s2.pos.afficher(g);
            }
        }
        
        g.setColor(Color.BLUE);

        for (int i = 0; i < PCC.size() - 2; i++){
            g.drawLine(PCC.get(i).pos.getX(), PCC.get(i).pos.getY(), PCC.get(i + 1).pos.getX(), PCC.get(i + 1).pos.getY());
            g.fillOval(PCC.get(i).pos.getX(), PCC.get(i).pos.getY(), 4, 4);
            g.fillOval(PCC.get(i+1).pos.getX(), PCC.get(i+1).pos.getY(), 4, 4);
        }
        
        g.setColor(Color.BLACK);
        origine.afficher(g);
        arrivee.afficher(g);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}
