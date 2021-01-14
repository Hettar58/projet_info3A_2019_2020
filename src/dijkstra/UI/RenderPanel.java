package dijkstra.UI;

import dijkstra.Dijkstra;
import dijkstra.datastructures.Heap;
import dijkstra.datastructures.Sommet;
import dijkstra.geometry.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderPanel extends JPanel {
    public RenderPanel()
    {
        super();
    }

    public void draw(Graphics g)
    {
        ArrayList<Obstacle> obstacles = Dijkstra.obstacles;
        ArrayList<Sommet> PCC = Dijkstra.PCC;
        Heap<Double, Sommet> graphe_current = Dijkstra.graphe_current;
        ArrayList<Sommet> graphe_old = Dijkstra.graphe_old;



        g.setColor(Color.ORANGE);
        for (int i = 0; i < graphe_old.size(); i++){
            Sommet s = graphe_old.get(i);
            s.position.afficher(g);
            for (int j = 0; j < s.getNombreVoisins(); j++){
                Sommet s2 = s.getSommetVoisin(j);
                g.drawLine(s.position.getX(), s.position.getY(), s2.position.getX(), s2.position.getY());
                s2.position.afficher(g);
            }
        }

        g.setColor(Color.RED);
        for (int i = 0; i < graphe_current.getSize(); i++){
            Sommet s = (Sommet)(graphe_current.getValueAt(i));
            s.position.afficher(g);
            for (int j = 0; j < s.getNombreVoisins(); j++){
                Sommet s2 = s.getSommetVoisin(j);
                g.drawLine(s.position.getX(), s.position.getY(), s2.position.getX(), s2.position.getY());
                s2.position.afficher(g);
            }
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < PCC.size() - 1; i++){
            g.drawLine(PCC.get(i).position.getX(), PCC.get(i).position.getY(), PCC.get(i + 1).position.getX(), PCC.get(i + 1).position.getY());
            g.fillOval(PCC.get(i).position.getX(), PCC.get(i).position.getY(), 4, 4);
            g.fillOval(PCC.get(i+1).position.getX(), PCC.get(i+1).position.getY(), 4, 4);
        }

        g.setColor(Color.GRAY);
        for(Obstacle o : obstacles)
        {
            o.afficher(g);
        }

        g.setColor(Color.CYAN);
        g.fillOval(Dijkstra.origine.position.getX(), Dijkstra.origine.position.getY(), 5, 5);
        g.fillOval(Dijkstra.arrivee.position.getX(), Dijkstra.arrivee.position.getY(), 5, 5);

        System.out.println("Render: " + graphe_current.getSize() + " pts (graphe) | " + graphe_old.size() + " pts (copy) | "+ obstacles.size() + " obstacles | "+ PCC.size() + " pts (PCC).");
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}
