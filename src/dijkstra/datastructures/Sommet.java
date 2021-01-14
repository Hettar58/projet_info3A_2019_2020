package dijkstra.datastructures;

import dijkstra.geometry.Point;

import java.util.HashMap;

public class Sommet {
    public Point position;

    public HashMap<Sommet, Double> voisins;

    public Sommet previous;

    public double distance;
    public double distance_origine;
    public double distance_arrivee;

    public Sommet(Point p)
    {
        this.position = p;
        voisins = new HashMap<Sommet, Double>();
    }

    public Sommet(Sommet s)
    {
        this.position = s.position;
        this.voisins = s.voisins;
        this.previous = s.previous;
        this.distance = s.distance;
        this.distance_origine = s.distance_origine;
        this.distance_arrivee = s.distance_arrivee;
    }

    public void addVoisin(Sommet s, double d)
    {
        voisins.put(s, d);
    }

    public void deleteVoisin(Sommet s)
    {
        voisins.remove(s);
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

    public HashMap<Sommet, Double> getVoisins() { return this.voisins;}

    public static double Distance(Sommet s1, Sommet s2){
        return Math.sqrt(Math.pow(s2.position.getX() - s1.position.getX(), 2) + Math.pow(s2.position.getY() - s1.position.getY(), 2));
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
