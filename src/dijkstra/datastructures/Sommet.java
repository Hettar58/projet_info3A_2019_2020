package dijkstra.datastructures;

import dijkstra.geometry.Point;

import java.util.HashMap;

public class Sommet {
    public Point position;

    private HashMap<Sommet, Double> voisins;

    private Sommet previous;
    private double distance;

    public Sommet(Point p)
    {
        this.position = p;
        voisins = new HashMap<Sommet, Double>();
    }

    public void addVoisin(Sommet s, double d)
    {
        voisins.put(s, d);
    }

    public Sommet getSommetVoisin(int index)
    {
        return (Sommet) voisins.keySet().toArray()[index];
    }

    public double getArcVoisin(int index)
    {
        return (double) voisins.values().toArray()[index];
    }

    public int getNombreVoisins() { return voisins.size();}

    public static double Distance(Sommet s1, Sommet s2){
        return Math.sqrt(Math.pow(s2.position.getX() - s1.position.getX(), 2) + Math.pow(s2.position.getY() - s1.position.getY(), 2));
    }

    public void setPrevious(Sommet s)
    {
        this.previous = s;
    }

    public Sommet getPrevious(){ return previous;}

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
