package dijkstra;

import dijkstra.datastructures.Heap;
import dijkstra.datastructures.Sommet;
import dijkstra.geometry.Disque;
import dijkstra.geometry.Point;
import dijkstra.geometry.Rectangle;
import static dijkstra.Dijkstra.*;

public class Generator {

    public static final int MAX_RAYON = 40;
    public static final int MIN_RAYON = 15;
    public static final int MAX_DELTA = 80;
    public static final int MIN_DELTA = 60;

    public static void generateObstacles()
    {
        for (int i = 0; i < Dijkstra.nbObstacles; i++)
        {
            int type = (int)(2*Math.random());

            if (type == 0)
            {
                int r = (int)(MIN_RAYON + (MAX_RAYON - MIN_RAYON) * Math.random());
                Point c = generatePoint(2, null);
                Disque d = new Disque(c, r);
                Dijkstra.obstacles.add(d);
            }
            else if (type == 1)
            {
                Point ex1 = generatePoint(0, null);
                Point ex2 = generatePoint(4, ex1);

                Rectangle r = new Rectangle(ex1, ex2);
                obstacles.add(r);
            }
        }
    }

    //mode == 0 -> graphe généré sans prise en compte du PCC
    //mode == 1 -> graphe généré avec prise en compte du PCC
    public static void generateGraphe(int mode)
    {
        Point p_origine = new Point(5, 5);
        origine = new Sommet(p_origine);
        origine.distance = 0;

        graphe_current.add(origine.distance, origine);

        if (mode == 0)
        {
            for (int i = 0; i < Dijkstra.nbPoints - 2; i++)
            {
                Point p = generatePoint(2, null);
                Sommet s = new Sommet(p);
                s.distance = INFINI;
                graphe_current.add(s.distance, s);
            }
        }
        else if (mode == 1)
        {
            //TODO: pas sur du code ici. a aucun moment il y a prise en compte du pcc
            /*
            for (int i = 0; i < graphe_old.size() - 2; i++)

            {
                Sommet s = graphe_old.get(i);
                double d = (s.distance_origine + s.distance_arrivee) / Sommet.Distance(origine, arrivee);

                if (d > seuilSauvegarde){
                    graphe_old.remove(s);
                }
                else{
                    graphe_current.add(s.distance, s);
                    for (int j = 0; j < nbPointsIteration; j++){
                        Point p = generatePoint(2, s.position);
                        Sommet s2 = new Sommet(p);
                        s2.distance = INFINI;
                        graphe_current.add(s2.distance, s2);
                    }
                }
            }
            */
            for (int i = 0; i < PCC.size(); i++)
            {
                graphe_current.add(PCC.get(i).distance, PCC.get(i));
                for (int j = 0; j < nbPointsIteration; j++)
                {
                    Point p = generatePoint(2, PCC.get(i).position);
                    Sommet s = new Sommet(p);
                    s.distance = INFINI;
                    graphe_current.add(s.distance, s);
                }
            }
            System.out.println("size graphe | " + graphe_current.getSize());
        }

        Point p_arrivee = new Point (780, 580);
        arrivee = new Sommet(p_arrivee);
        arrivee.distance = INFINI;
        graphe_current.add(arrivee.distance, arrivee);
    }

    public static void generateVoisins(Heap<Double, Sommet> g)
    {
        System.out.println("size: " + g.getSize());
        for (int i = 0; i < g.getSize(); i++)
        {
            Sommet s = g.getValueAt(i);
            for (int j = 0; j < g.getSize(); j++)
            {
                Sommet tmp = g.getValueAt(j);
                double d = Sommet.Distance(s, tmp);

                if (d <= R)
                {
                    s.addVoisin(tmp, d);
                    tmp.addVoisin(s, d);
                }
            }
        }
    }

    //mode == 0 -> pas de vérification de collision
    //mode == 1 -> vérification de collision
    //mode == 2 -> vérification de collision et inclusion dans le rayon.
    //mode == 4 -> spécifique au rectangle: force la création d'un pt inclus dans le delta et > à la position de ref.
    public static Point generatePoint(int mode, Point ref)
    {
        if (mode == 0)
        {
            int x = (int)(1 + (800 - 1) * Math.random());
            int y = (int)(1 + (600 -1) * Math.random());
            Point p = new Point(x, y);
            return p;
        }
        else if (mode == 4)
        {
            int x = (int)(ref.getX() + MIN_DELTA+ (MAX_DELTA + 1) * Math.random());
            int y = (int)(ref.getY() + MIN_DELTA+ (MAX_DELTA + 1) * Math.random());
            Point p = new Point(x, y);
            return p;
        }
        else if (mode == 2 || mode == 3)
        {
            boolean collide = false;
            Point p = null;
            do {
                collide = false;
                p = null;
                int x = (int)(1 + (800 - 1) * Math.random());
                int y = (int)(1 + (600 -1) * Math.random());
                p = new Point(x, y);

                for (int i = 0; i < obstacles.size(); i++)
                {
                    if (obstacles.get(i).collision(new Sommet(p)) == true)
                        collide = true;
                }

                if (mode == 2 && ref != null)
                {
                    if (Point.Distance(ref, p) > r)
                        collide = true;
                }
            }while(collide == true);
            return p;
        }
        return null;
    }
}
