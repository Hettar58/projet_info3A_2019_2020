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
    public static final int OBSTACLES = 0;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 20;
    public static final int INFINI = 9999;
    public static final int R = 50;//rayon pour prendre en compte un point dans les calculs
    public static final int r = 20;
    public static final double SAVE_THRESOLD = 1.05;   //seuil de sauvegarde
    
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Sommet> graphe;            //graphe tampon
    ArrayList<Sommet> graphe_arrivee;    //graphe avec les sommets orientés vers l'arrivee
    ArrayList<Sommet> graphe_origine;    //grapghe avec les sommets orientés vers l'arrivée
    public static ArrayList<Sommet> graphe_copy;
    public static ArrayList<Sommet> PCC;
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
        graphe_copy = ALCopy(graphe);   // sauvegarde du graphe
        
        //calcul des OS
        Sommet s1 = origine;
        do{
            s1 = find(graphe, 0);
            if (s1 != null){
                for (int i = 0; i <= s1.voisins.size() - 1; i++){
                    double d = s1.getArc(i);
                    Sommet s = s1.getVoisin(i);
                    if (s.distance > s1.distance + d){
                        s.distance = s1.getDistance() + d;
                        s.pred = s1;
                    }
                }
                graphe_origine.add(s1);
                graphe.remove(s1);
                
            }
        }while(s1 != arrivee && s1 != null);

        Sommet s = arrivee;
        while (s != null){
            PCC.add(s);
            s = s.pred;
        }
        
        
        for(int i=0; i<= PCC.size()-1; i++){
            Point x =PCC.get(i).pos;
            graphe.add(new Sommet(generatePoint(2, x)));
            
        }
        
        
        //graphe = ALCopy(graphe_origine);   //copie du graphe avec les distances à l'origine déja définies
        
        //calcul des SA
        /*s1 = arrivee;
        do{
            s1 = find(graphe, 1);
            if (s1 != null){
                for (int i = 0; i <= s1.voisins.size() - 1; i++){
                    double d = s1.getArc(i);
                    s = s1.getVoisin(i);
                    if (s.distance > s1.distance + d){
                        s.distance = s1.getDistance() + d;
                        s.pred = s1;
                    }
                    //System.out.println(d);
                }
                
                graphe.remove(s1);
                graphe_arrivee.add(s1);
            }
        }while(s1 != origine && s1 != null);

        s = origine;
        while (s != null){
            PCC.add(s);
            s = s.pred;
        }
        
        graphe = ALCopy(graphe_copy);
        
        System.out.println(graphe.size());
        System.out.println(graphe_origine.size());
        System.out.println(graphe_arrivee.size());
        System.out.println(PCC.size());
        
        for(int i = 0; i < graphe.size(); i++){
            double d = (graphe_origine.get(i).distance + graphe_arrivee.get(i).distance) / Sommet.Distance(origine, arrivee);
            if (d <= SAVE_THRESOLD && graphe.get(i).distance <= R){
                graphe.remove(i);       
            }
            System.out.println(d);
        }*/
        
        
        System.out.println(graphe.size());
        UI.graphe = graphe;
        UI.graphe_test = graphe_copy;
    }
    
    public ArrayList<Sommet> dijkstra(Sommet debut, Sommet fin, ArrayList<Sommet> pgraphe){
        ArrayList<Sommet> output = new ArrayList<Sommet>();
       //A REMPLIR
        return output;
    }
    
    public void init(){
        //graphe = new Heap(MAX_POINTS);
        graphe = new ArrayList<Sommet>();
        PCC = new ArrayList<Sommet>();
        obstacles = new ArrayList<Obstacle>();
        graphe_copy = new ArrayList<Sommet>();
        graphe_origine = new ArrayList<Sommet>();
        graphe_arrivee = new ArrayList<Sommet>();
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
        //generateStaticPoints();
        
        //arrivee = graphe.get(MAX_POINTS - 1);
        Point p_arrivee = new Point(1019, 730);
        arrivee = new Sommet(p_arrivee);
        arrivee.distance = INFINI;
        graphe.add(arrivee);
        
        for (Sommet s : graphe){
            for (Sommet s2 : graphe){
                double d = Sommet.Distance(s, s2);
                if (d <= R){
                    s.addVoisin(s2, d);
                    s2.addVoisin(s, d);
                    
                }
            }
        }
        UI = new RenderPanel();
        UI.graphe = graphe;
        UI.PCC = PCC;
        this.add(UI);
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
