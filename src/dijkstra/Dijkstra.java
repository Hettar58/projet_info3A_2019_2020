package dijkstra;

import dijkstra.UI.CommandUI;
import dijkstra.UI.RenderPanel;
import dijkstra.datastructures.Heap;
import dijkstra.geometry.Obstacle;
import dijkstra.datastructures.Sommet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Dijkstra {
    public static Dijkstra instance;

    // PARAMETRES PRINCIPAUX
    public static int nbPoints = 1500;
    public static int nbObstacles = 0;
    public static int R = 50;   //distance max entre 2 points
    public static int r = 20;   //rayon max dans lequel des nouveaux points sont générés.
    public static int nbPointsIteration = 20;       //nombre de points crées pour 1 point du PCC
    public static double seuilSauvegarde = 1.01;    //seuil de différence pour lequel un point est contacté.

    public static final int INFINI = 9999;

    public static ArrayList<Obstacle> obstacles;
    public static Heap<Double, Sommet> graphe_current;
    public static ArrayList<Sommet> graphe_old;
    public static ArrayList<Sommet> PCC;

    public static Sommet origine;
    public static Sommet arrivee;
    int compteurIteration;


    RenderPanel UI;
    CommandUI commandes;

    public Dijkstra()
    {
        instance = this;
        this.compteurIteration = 0;
        graphe_current = new Heap(nbPoints);
        PCC = new ArrayList<Sommet>();
        obstacles = new ArrayList<Obstacle>();
        graphe_old = new ArrayList<Sommet>();
    }

    public void applyDijkstra()
    {
        System.out.println();
        handleIterations();
        PCC = dijkstra(origine, arrivee, graphe_current, graphe_old);
        System.out.println("Applied Dijkstra on " + graphe_old.size() + " points | PCC size: "+ PCC.size() + " | remaining : " + graphe_current.getSize());
        Main.rw.repaint();
        compteurIteration++;
    }

    public ArrayList<Sommet> dijkstra(Sommet debut, Sommet fin, Heap<Double, Sommet> graphe_current, ArrayList<Sommet> graphe_old)
    {
        ArrayList<Sommet> out = new ArrayList<Sommet>();

        Sommet tmp = debut;
        do {
            //System.out.println("--- GRAPHE ---");
            //System.out.println(graphe_current.toString());
            //System.out.println("--- FIN GRAPHE ---");

            tmp = graphe_current.getRootValue();
            //System.out.println();
            if (tmp != null)
            {
                //System.out.println(tmp.getNombreVoisins());
                Iterator it = tmp.getVoisins().entrySet().iterator();
                while(it.hasNext())
                {
                    Map.Entry<Sommet, Double> voisin = (Map.Entry)it.next();
                    Sommet s = voisin.getKey();
                    double distance = voisin.getValue();

                    if (s.distance > tmp.distance + distance)
                    {
                        s.distance = tmp.distance + distance;
                        s.previous = tmp;
                        graphe_current.updateKeyFromValue(s, s.distance);
                    }
                }

                //pas sûr de l'utilité.
                tmp.distance_origine = Sommet.Distance(origine, tmp);
                tmp.distance_arrivee = Sommet.Distance(arrivee, tmp);

                graphe_old.add(tmp);
                graphe_current.removeRoot();
            }
        }while (tmp != fin);

        tmp = fin;
        while(tmp != null)
        {
            out.add(tmp);
            tmp = tmp.previous;
        }

        return out;
    }

    private void handleIterations()
    {
        if (compteurIteration != 0)
        {
            switch (compteurIteration)
            {
                case 1: seuilSauvegarde = 1.01; break;
                case 2: seuilSauvegarde = 1.001; break;
                case 3: seuilSauvegarde = 1.0001; break;
                case 4: seuilSauvegarde = 1.0; break;
            }
            R = R - (R/10);
            r = r - (r / 10);
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
        //seuilSauvegarde = 1.01;

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
