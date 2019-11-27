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
    
    public static final int MAX_RAYON = 50;
    public static final int MIN_RAYON = 9;

    public static final int POINTS = 1500;
    public static final int OBSTACLES = 10;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 20;
    public static final int INFINI = 9999;
    public static final int R = 50;//rayon pour prendre en compte un point dans les calculs
    public static final int r = 30;
    public static final double SAVE_THRESOLD = 1.05;   //seuil de sauvegarde
    
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Sommet> graphe;            //graphe tampon
    public static ArrayList<Sommet> graphe_copy;       //copie du graphe d'origine
    public static ArrayList<Sommet> PCC;                //PCCX
    Sommet origine;
    Sommet arrivee;

    //public static Heap graphe;
    RenderPanel UI;

    
    public Main(){
        this.setTitle("Dijkstra");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        init();
        
        PCC = dijkstra(origine, arrivee, graphe, graphe_copy);
        
        graphe.clear();
        origine = new Sommet(new Point(5, 5));
        origine.distance = 0;
        graphe.add(origine);
        
        for(int i=0; i<= PCC.size()-1; i++){
            for(int j=0; j<= 100;j++){
                Point x =PCC.get(i).pos;
                Sommet s = new Sommet(generatePoint(2, x));
                s.distance = INFINI;
                graphe.add(s);
            }   
        }
        
        arrivee = new Sommet(new Point(1019, 730));
        arrivee.distance = INFINI;
        graphe.add(arrivee);
        generateVoisins(graphe);
        
        System.out.println(graphe.size());
        
        graphe_copy.clear();
        ArrayList<Sommet> PCC_copy = ALCopy(PCC);
        UI.PCC_copy = PCC_copy;
        
        PCC = dijkstra(origine, arrivee, graphe, graphe_copy);
        
        System.out.println(graphe.size());
        System.out.println(graphe_copy.size());
        
        UI.graphe_test = graphe_copy;
        UI.graphe = graphe;
        UI.PCC = PCC;
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
        graphe = new ArrayList<Sommet>();
        PCC = new ArrayList<Sommet>();
        obstacles = new ArrayList<Obstacle>();
        graphe_copy = new ArrayList<Sommet>();
        generateObstacles();
        

        Point p_origine = new Point(5, 5);
        origine = new Sommet(p_origine);
        graphe.add(origine);
        for (int i = 0; i < POINTS - 2; i++){
            Point p = generatePoint(2,null);
            //graphe.addObject(s, i);
            Sommet s = new Sommet(p);
            s.distance = INFINI;
            graphe.add(s);
        }
        
        Point p_arrivee = new Point(1019, 730);
        arrivee = new Sommet(p_arrivee);
        arrivee.distance = INFINI;
        graphe.add(arrivee);
        
        generateVoisins(graphe);
        
        UI = new RenderPanel();
        this.add(UI);
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
                Point ex2 = null;
                
                //pour faciliter les calculs. implique que ext1 = HG et ext2 = BD
                do{
                    ex2 = generatePoint(0,null);
                }while (ex2.getX() <= ex1.getX() || ex2.getY() <= ex1.getY());
                
                Rectangle r = new Rectangle(ex1, ex2);
                obstacles.add(r);
            }
        }
    }
    
    //mode == 0:    pas de vérification de collision.
    //mode == 1:    vérification de collision
    //mode == 2:    vérification de collision ET inclusion dans le rayon.
    public Point generatePoint(int mode, Point ref){
        if (mode == 0){
            int x = (int)(1+(WIDTH - 1) * Math.random());
            int y = (int)(1+(HEIGHT -1) * Math.random());
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
                    if (Point.Distance(ref, p) >= r){
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
