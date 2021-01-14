package dijkstra.geometry;

import dijkstra.datastructures.Sommet;
import static dijkstra.datastructures.Sommet.Distance;
import java.awt.Graphics;

public class Disque extends Obstacle {
    Point centre;
    int rayon;

    public Disque(Point c, int r){
        centre = c;
        rayon = r;
    }

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }


    public boolean collision(Sommet s){
        if (Distance(s, new Sommet(centre)) <= rayon){
            return true;
        }
        else{
            return false;
        }
    }

    public void afficher(Graphics g){
        g.fillOval(centre.getX() - rayon, centre.getY() - rayon, 2 * rayon, 2 * rayon);
    }
}
