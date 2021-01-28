package dijkstra;

import dijkstra.datastructures.Heap;
import dijkstra.datastructures.Sommet;
import dijkstra.geometry.Obstacle;

import java.util.ArrayList;

public class CONFIG {
    public static int nbPoints = 4000;
    public static int nbObstacles = 0;
    public static int R = 20;
    public static int r = 20;
    public static int nbPointsIteration = 30;
    public static final int INFINI = 9999;

    //tableaux globaux
    public static ArrayList<Obstacle> obstacles;
    public static Heap<Double, Sommet> graphe_current;
    public static ArrayList<Sommet> graphe_old;
    public static ArrayList<Sommet> PCC;

    //sommets globaux
    public static Sommet origine;
    public static Sommet fin;
}
