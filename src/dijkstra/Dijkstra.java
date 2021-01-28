package dijkstra;

import static dijkstra.CONFIG.*;
import dijkstra.datastructures.Heap;
import dijkstra.geometry.Obstacle;
import dijkstra.datastructures.Sommet;

import java.util.ArrayList;

public class Dijkstra {
    public static Dijkstra instance;
    int compteurIteration;

    public Dijkstra()
    {
        instance = this;
        this.compteurIteration = 0;
        graphe_current = new Heap<Double, Sommet>(nbPoints);
        PCC = new ArrayList<Sommet>();
        obstacles = new ArrayList<Obstacle>();
        graphe_old = new ArrayList<Sommet>();
    }

    public void applyDijkstra()
    {
        System.out.println();
        handleIterations();

        long beginTime = System.currentTimeMillis();
        PCC = dijkstra(origine, fin, graphe_current, graphe_old);
        long endTime = System.currentTimeMillis();
        System.out.println("Dijkstra:" + graphe_old.size() + " points | PCC size: "+ PCC.size() + " | remaining : " + graphe_current.getSize() + " | " + (endTime - beginTime) + " ms");
        Main.rw.repaint();
        compteurIteration++;
    }

    public ArrayList<Sommet> dijkstra(Sommet debut, Sommet fin, Heap<Double, Sommet> graphe_current, ArrayList<Sommet> graphe_old)
    {
        ArrayList<Sommet> out = new ArrayList<Sommet>();

        Sommet tmp;
        do {
            tmp = graphe_current.getRootValue();
            if (tmp != null)
            {
                for (int i = 0; i < tmp.getNombreVoisins(); i++) {
                    Sommet s = tmp.getSommetVoisin(i);
                    double arc = tmp.getArcVoisin(i);
                    double distance_tmp = tmp.getDistance();

                    if (s.getDistance() > distance_tmp + arc) {
                        s.setDistance(distance_tmp + arc);
                        s.setPrevious(tmp);
                        graphe_current.updateKeyFromValue(s, s.getDistance());
                    }
                }

                graphe_old.add(tmp);
                graphe_current.removeRoot();
            }
        }while (tmp != fin);

        tmp = fin;
        while(tmp != null)
        {
            out.add(tmp);
            tmp = tmp.getPrevious();
        }

        return out;
    }

    private void handleIterations()
    {
        if (compteurIteration != 0)
        {
            R = R - (R / 8);
            r = r - (r / 8);
            System.out.println("dMax search (R) = " + R + " | dMax generator (r) = " + r);
            graphe_current.clear();
            Generator.generateGraphe(1);
            graphe_old.clear();     //on le clear après car nécessaire pour la génération du nouveau graphe.
            Generator.generateVoisins(graphe_current);
        }
    }

    public void reset()
    {
        compteurIteration = 0;
        R = 75;

        graphe_current.clear();
        graphe_old.clear();
        PCC.clear();
        obstacles.clear();

        Main.rw.repaint();
    }

    public void init()
    {
        graphe_current.clear();
        graphe_old.clear();
        PCC.clear();
        obstacles.clear();

        graphe_current = new Heap<Double, Sommet>(nbPoints);

        Generator.generateObstacles();
        Generator.generateGraphe(0);
        Generator.generateVoisins(graphe_current);

        Main.rp.repaint();
    }
}
