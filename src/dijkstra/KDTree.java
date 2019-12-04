/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;

/**
 *
 * @author Yann
 */
public class KDTree {
    ArrayList<KDNode> tree;
    int profondeur;
    
    public KDTree(ArrayList<Sommet> graphe, int profondeur){
        
    }
    
    public void addObject(){}
    
    public void removeObject(){}
    
    public void nearestNeihbour(){}
    
    public void updateNode(){}
    
    public void clear(){}
    
    private class KDNode{
        Sommet position;
        KDTree gauche;
        KDTree droite;
    }
}
