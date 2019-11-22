package dijkstra;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yann
 */
public class Sommet {
    public Point pos;
    public ArrayList<Sommet> voisins;
    public ArrayList<Double> arcs;
    
    public Sommet(Point p){
        this.pos = p;
        voisins = new ArrayList<Sommet>();
        arcs = new ArrayList<Double>();
    }
    
    public void addVoisin(Sommet s, double d){
        voisins.add(s);
        arcs.add(d);
    }
    
    public Sommet getVoisin(int index){
        return voisins.get(index);
    }
    
    public double getDistance(int index){
        return arcs.get(i);
    }
}
