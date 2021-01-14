package dijkstra.geometry;

import dijkstra.datastructures.Sommet;

import java.awt.*;

public abstract class Obstacle {
    public abstract void afficher(Graphics g);
    public abstract boolean collision(Sommet s);
}
