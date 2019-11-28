/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author yt646712
 */
public class Main extends JFrame{
    public static Main main;    //lien pour les commandes
    
    public static int POINTS = 1500;
    public static int OBSTACLES = 0;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 20;
    public static final int INFINI = 9999;
    public static int R = 50;                     //distance maximale entre 2 points
    public static int r = 50;                     //distance maximale pour créer un point dans le raffinement.
    public static int POINTS_ITER = 10;           //nb de points crées pour 1 pt du PCC dans le raffinement.
    
    public static final int MAX_RAYON = 40;
    public static final int MIN_RAYON = 15;
    public static final int MAX_DELTA = 80;
    public static final int MIN_DELTA = 60;
    
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Sommet> graphe;            //graphe tampon
    public static ArrayList<Sommet> graphe_copy;       //copie du graphe d'origine
    public static ArrayList<Sommet> PCC;                //PCC
    Sommet origine;
    Sommet arrivee;
    int iterationCounter;
    //public static Heap graphe;
    RenderPanel UI;
    CommandUI InterfaceCommande;

    
    public Main(){
        main = this;
        this.setTitle("Dijkstra");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        iterationCounter = 0;
        
        graphe = new ArrayList<Sommet>();
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
        if (iterationCounter < 3){
            if (iterationCounter != 0){
                if (r - 10 > 0){
                    r = r - 10;
                }

                graphe.clear();
                generateGraphe(1);
                generateVoisins(graphe);
                graphe_copy.clear();
            }
            PCC = dijkstra(origine, arrivee, graphe, graphe_copy); 
            System.out.println("Applied Dijkstra on " + graphe_copy.size() + " points / PCC size: "+ PCC.size());

            UI.repaint();

            iterationCounter++;
        }
    }
    
    public void reset(){
        iterationCounter = 0;
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
    
    public ArrayList<Sommet> dijkstra(Sommet debut, Sommet fin, ArrayList<Sommet> g, ArrayList<Sommet> g_copy){
        ArrayList<Sommet> output = new ArrayList<Sommet>();
        Sommet s1 = debut;
        do{
            s1 = find(g, 0);
            if (s1 != null){
                for (int i = 0; i <= s1.voisins.size() - 1; i++){
                    double d = s1.getArc(i);
                    Sommet s = s1.getVoisin(i);
                    if (s.distance > s1.distance + d){
                        s.distance = s1.getDistance() + d;
                        s.pred = s1;
                    }
                }
                g_copy.add(s1);
                g.remove(s1);   
            }
        }while(s1 != fin && s1 != null);

        Sommet s = arrivee;
        while (s != null){
            output.add(s);
            s = s.pred;
        }
        return output;
    }
    
    public void init(){
        //graphe = new Heap(MAX_POINTS);
        
        graphe.clear();
        PCC.clear();
        obstacles.clear();
        graphe_copy.clear();
        
        generateObstacles();
        generateGraphe(0);
        generateVoisins(graphe);
        
        UI.repaint();
    }
    
    //mode == 0 -> graphe généré sans prise en compte du PCC
    //mode == 1 -> graphe généré avec prise en compte de r et du PCC.
    public void generateGraphe(int mode){
        
        Point p_origine = new Point(5, 5);
        origine = new Sommet(p_origine);
        origine.distance = 0;
        graphe.add(origine);
        
        if (mode == 0){
            for (int i = 0; i < POINTS - 2; i++){
                Point p = generatePoint(2,null);
                Sommet s = new Sommet(p);
                s.distance = INFINI;
                graphe.add(s);
            }
        } 
        else if (mode == 1){
            for(int i=0; i<= PCC.size()-1; i++){
                for(int j=0; j<= POINTS_ITER;j++){
                    Point x =PCC.get(i).pos;
                    Sommet s = new Sommet(generatePoint(2, x));
                    s.distance = INFINI;
                    graphe.add(s);
                }   
            }
        }
        
        Point p_arrivee = new Point(1019, 730);
        arrivee = new Sommet(p_arrivee);
        arrivee.distance = INFINI;
        graphe.add(arrivee);
        
    }
    
    public void generateVoisins(ArrayList<Sommet> g){
        for (Sommet s : g){
            for (Sommet s2 : g){
                double d = Sommet.Distance(s, s2);
                if (d <= R){
                    s.addVoisin(s2, d);
                    s2.addVoisin(s, d);
                    
                }
            }
        }
    }

    //mode == 0: cherche le minimum
    //mode == 1: cherche le maximum
    public Sommet find(ArrayList<Sommet> graphe, int mode){
        Sommet output = null;
        double ref = mode == 0 ? INFINI : 0;
        for (int i = 0; i <= graphe.size() -1; i++){
            if (graphe.get(i).getDistance() < ref && mode == 0){
                output = graphe.get(i);
                ref = output.getDistance();
            }
            else if(graphe.get(i).distance > ref && mode == 1){
                output = graphe.get(i);
                ref = output.getDistance();
            }
        }
        return output;
    }
    
    public void generateObstacles(){
        for (int i = 0; i < OBSTACLES; i++){
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
            int x = (int)(1+(WIDTH - 1) * Math.random());
            int y = (int)(1+(HEIGHT -1) * Math.random());
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
                int x = (int)(1+(WIDTH - MARGIN) * Math.random());
                int y = (int)(1+(HEIGHT - MARGIN) * Math.random());
                p = new Point(x, y);
                for (int i = 0; i < obstacles.size(); i++){
                    if (obstacles.get(i).collision(new Sommet(p)) == true){
                        collide = true;
                    }
                }
                if (mode == 2 && ref != null){    //vérification d'inclusion. A MODIFIER
                    if (Point.Distance(ref, p) > r){
                        collide = true;
                    }
                }
            }while (collide == true);
            return p;
        }
    }
    public ArrayList<Sommet> ALCopy (ArrayList<Sommet> al_origin){
        ArrayList<Sommet> output = new ArrayList<Sommet>();
        for (int i = 0; i <= al_origin.size() - 1; i++){
            output.add(new Sommet(al_origin.get(i)));
        }
        return output;
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
