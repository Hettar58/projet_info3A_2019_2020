/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import static dijkstra.Point.calcDistance;
import java.awt.Graphics;

/**
 *
 * @author yt646712
 */
public class Disque extends Obstacle{
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
    
    
    @Override
    public boolean collision(Point p){
        if (calcDistance(centre, p) <= rayon){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public void afficher(Graphics g){
        g.fillOval(centre.getX() - rayon, centre.getY() - rayon, 2 * rayon, 2 * rayon);
    }
}
