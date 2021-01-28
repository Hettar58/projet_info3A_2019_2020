package dijkstra.UI;

import static dijkstra.CONFIG.*;
import dijkstra.datastructures.Sommet;
import dijkstra.geometry.Obstacle;

import javax.swing.*;
import java.awt.*;

//TODO: optimisation du rendu en prenant en compte les arcs déja dessinés.
public class RenderPanel extends JPanel {
    public RenderPanel()
    {
        super();
    }

    private void printSommet(Graphics g, Sommet s)
    {
        s.position.afficher(g);
        for (int i = 0; i < s.getNombreVoisins(); i++)
        {
            Sommet s2 = s.getSommetVoisin(i);
            g.drawLine(s.position.getX(), s.position.getY(), s2.position.getX(), s2.position.getY());
            //s2.position.afficher(g);
        }
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.ORANGE);
        for (int i = 0; i < graphe_old.size(); i++){
            Sommet s = graphe_old.get(i);
            printSommet(g, s);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < graphe_current.getSize(); i++){
            Sommet s = (Sommet)(graphe_current.getValueAt(i));
            printSommet(g, s);
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
        g.fillOval(origine.position.getX(), origine.position.getY(), 5, 5);
        g.fillOval(fin.position.getX(), fin.position.getY(), 5, 5);

        System.out.println("Render: " + graphe_current.getSize() + " pts (graphe) | " + graphe_old.size() + " pts (copy) | "+ obstacles.size() + " obstacles | "+ PCC.size() + " pts (PCC).");
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
}
