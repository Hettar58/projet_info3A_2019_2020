/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author yt646712
 */
public class Main extends JFrame{
    public static Main main;    //lien pour les commandes
    
    public static int nbPoints = 1500;
    public static int nbObstacles = 0;
    
    final int largeurFenetre = 800;
    int hauteurFenetre = 600;
    
    public static final int INFINI = 9999;
    public static int R = 60;                     //distance maximale entre 2 points
    double seuilSauvegarde = 1.01;    //seuil de différence pour lequel un point est éclaté.
    double r = 20;                  //rayon dans lequel des nouveaux points sont générés
    int nbPointsIteration = 5;           //nb de points crées pour 1 pt du PCC dans le raffinement.
    
    public static final int MAX_RAYON = 40;
    public static final int MIN_RAYON = 15;
    public static final int MAX_DELTA = 80;
    public static final int MIN_DELTA = 60;
    
    public static ArrayList<Obstacle> obstacles;
    public static Heap graphe;
    public static ArrayList<Sommet> graphe_copy;
    public static ArrayList<Sommet> PCC;
    
    Sommet origine;
    Sommet arrivee;
    int iterationCounter;
    
    RenderPanel UI;
    CommandUI InterfaceCommande;

    
    public Main(){
        main = this;
        this.setTitle("Dijkstra");
        this.setSize(largeurFenetre, hauteurFenetre);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        iterationCounter = 0;
        
        //graphe = new ArrayList<Sommet>();
        graphe = new Heap(nbPoints);
        PCC = new ArrayList<Sommet>();
        obstacles = new ArrayList<Obstacle>();
        graphe_copy = new ArrayList<Sommet>();
        
        UI = new RenderPanel();
        this.add(UI);
        
        init();
        
        InterfaceCommande = new CommandUI(this, false);
        InterfaceCommande.setVisible(true);
    }
    
    
    public void applyDijktra(){
        if (iterationCounter < 5){
            System.out.println();
            if (iterationCounter != 0){
                switch(iterationCounter){
                    case 1: seuilSauvegarde = 1.01; break;
                    case 2: seuilSauvegarde = 1.001; break;
                    case 3: seuilSauvegarde = 1.0001; break;
                    case 4: seuilSauvegarde = 1.0; break;
                }
                
                graphe.clear();
                generateGraphe(1);
                graphe_copy.clear();
                generateVoisins(graphe);
                
            }
            
            PCC = dijkstra(origine, arrivee, graphe, graphe_copy);
            
            System.out.println("Applied Dijkstra on " + graphe_copy.size() + " points / PCC size: "+ PCC.size());
            UI.repaint();

            iterationCounter++;
        }
    }
    
    
    public void reset(){
        iterationCounter = 0;
        R = 75;
        seuilSauvegarde = 1.01;
        graphe.clear();
        PCC.clear();
        graphe_copy.clear();
        obstacles.clear();
        UI.repaint();
    }
    
    public void quit(){
        InterfaceCommande.setVisible(false);
        InterfaceCommande.dispose();
        System.exit(0);
    }
    
    public ArrayList<Sommet> dijkstra(Sommet debut, Sommet fin, Heap g, ArrayList<Sommet> g_copy){
        ArrayList<Sommet> output = new ArrayList<Sommet>();
        Sommet s1 = debut;
        Object tmp;
        do{
            //System.out.println();
            //System.out.println(g.toString());
            tmp = g.getRoot();
            if (tmp != null){
                s1 = (Sommet)(tmp);
                for (int i = 0; i <= s1.voisins.size() - 1; i++){
                    double d = s1.getArc(i);
                    Sommet s = s1.getVoisin(i);
                    if (s.distance > s1.distance + d){
                        s.distance = s1.getDistance() + d;
                        s.pred = s1;
                        g.updateKeyFromValue(s, s.distance);
                    }
                }
                
                s1.distance_origine = Sommet.Distance(origine, s1);
                s1.distance_arrivee = Sommet.Distance(arrivee, s1);
                
                g_copy.add(s1);
                g.removeRoot();
                g.refresh();
            }
        }while(s1 != fin && tmp != null);

        Sommet s = arrivee;
        while (s != null){
            output.add(s);
            s = s.pred;
        }
        return output;
    }
    
    public void init(){
        
        graphe.clear();
        graphe_copy.clear();
        graphe = new Heap(nbPoints);
        PCC.clear();
        obstacles.clear();
        
        
        generateObstacles();
        generateGraphe(0);
        generateVoisins(graphe);
        
        UI.repaint();
    }
    
    //mode == 0 -> graphe généré sans prise en compte du PCC
    //mode == 1 -> graphe généré avec prise en compte du seuil de sauvegarde
    public void generateGraphe(int mode){
        
        Point p_origine = new Point(5, 5);
        origine = new Sommet(p_origine);
        origine.distance = 0;
        //graphe.add(origine);
        graphe.addObject(origine, origine.distance);
        
        if (mode == 0){
            for (int i = 0; i < nbPoints - 2; i++){
                Point p = generatePoint(2,null);
                Sommet s = new Sommet(p);
                s.distance = INFINI;
                graphe.addObject(s, s.distance);
            }
        } 
        else if (mode == 1){
            for (int i = 0; i < graphe_copy.size() - 2; i++){
                Sommet s = (Sommet)(graphe_copy.get(i));
                double d = (s.distance_origine + s.distance_arrivee) / Sommet.Distance(origine, arrivee);
                if (d > seuilSauvegarde){
                    graphe_copy.remove(s);
                }
                else{
                    graphe.addObject(s, s.distance);
                    for (int j = 0; j < nbPointsIteration; j++){
                        Point p = generatePoint(2, s.pos);
                        Sommet s2 = new Sommet(p);
                        s2.distance = INFINI;
                        graphe.addObject(s2, s2.distance);
                    }
                }
                
            }
        }
        
        Point p_arrivee = new Point(780, 580);
        arrivee = new Sommet(p_arrivee);
        arrivee.distance = INFINI;
        graphe.addObject(arrivee, arrivee.distance);
        
        
    }
    
    public void generateVoisins(Heap g){
        for (int i = 0; i < g.getSize(); i++){
            Sommet s = (Sommet)(g.getValueAt(i));
            for (int j = 0; j < g.getSize(); j++){
                Object tmp = g.getValueAt(j);
                if (tmp != null){
                    Sommet s2 = (Sommet)(tmp);
                    double d = Sommet.Distance(s, s2);
                    if (d <= R){
                        s.addVoisin(s2, d);
                        s2.addVoisin(s, d);

                    }
                }
            }
        }
    }
    
    public void generateObstacles(){
        for (int i = 0; i < nbObstacles; i++){
            int type = (int)(2*Math.random());
            if (type == 0){ //disque
                int r = (int)(MIN_RAYON + (MAX_RAYON - MIN_RAYON) * Math.random());
                Point c = generatePoint(2,null);
                Disque d = new Disque(c, r);
                obstacles.add(d);
            }
            else{   //rectangle
                Point ex1  = generatePoint(0,null);
                Point ex2 = generatePoint(4, ex1);
                
                Rectangle r = new Rectangle(ex1, ex2);
                obstacles.add(r);
            }
        }
    }
    
    //mode == 0:    pas de vérification de collision.
    //mode == 1:    vérification de collision
    //mode == 2:    vérification de collision ET inclusion dans le rayon.
    //mode == 4:    spécifique au rectangle. force la création d'un pt inclus dans delta et > a la position de ref.
    public Point generatePoint(int mode, Point ref){
        if (mode == 0){
            int x = (int)(1+(largeurFenetre - 1) * Math.random());
            int y = (int)(1+(hauteurFenetre -1) * Math.random());
            Point p = new Point(x, y);
            return p;
        }
        else if(mode == 4){
            int x = (int)(ref.getX() + MIN_DELTA+ (MAX_DELTA + 1) * Math.random());
            int y = (int)(ref.getY() + MIN_DELTA+ (MAX_DELTA + 1) * Math.random());
            Point p = new Point(x, y);
            return p;
        }
        else{
            boolean collide = false;
            Point p = null;
            do{
                collide = false;
                p = null;
                int x = (int)(1+(largeurFenetre) * Math.random());
                int y = (int)(1+(hauteurFenetre) * Math.random());
                p = new Point(x, y);
                for (int i = 0; i < obstacles.size(); i++){
                    if (obstacles.get(i).collision(new Sommet(p)) == true){
                        collide = true;
                    }
                }
                if (mode == 2 && ref != null){    //vérification d'inclusion
                    if (Point.Distance(ref, p) > r){
                        collide = true;
                    }
                }
            }while (collide == true);
            return p;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.setProperty("file.encoding", "UTF-8");
        EventQueue.invokeLater(() -> {
            Main app = new Main();
        });
    }   
}
