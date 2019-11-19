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
public class Dijkstra extends JFrame{
    
    public static final int MAX_RAYON = 50;
    public static final int MIN_RAYON = 9;
    public static final int POINTS = 50;
    public static final int OBSTACLES = 0;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 20;
    public static final int INFINI = 9999;
    public static final int R = 350;        //rayon pour prendre en compte un point dans les calculs
    public static final int R_VISION = 50;  //rayon pour la génération des points en mode itératif
    
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Point> graphe;
    public static ArrayList<Point> PCC;
    Point origine;
    Point arrivee;
    //public static Heap graphe;
    RenderPanel UI;

    
    public Dijkstra(){
        this.setTitle("Dijkstra");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        init();
        Point s1 = origine;
        do{
            s1 = find_min(graphe, s1);
            //double d = PCC.get(PCC.size() -1).getDistance() + Point.calcDistance(s1, PCC.get(PCC.size() -1));
            if (s1 != null){
                PCC.add(s1);
                graphe.remove(s1);
                for (Point p : graphe){
                    p.setDistance(Point.calcDistance(s1, p));
                }
                System.out.println(s1.toString());
            }

        }while(s1 != arrivee && s1 != null);
        
        graphe.clear();
        graphe = PCC;
        
        for (int i = 0; i < POINTS - graphe.size(); i++){
            graphe.add(generatePoint(3));
            System.out.println("regen des points");
        }
    }
    
    public void init(){
        //graphe = new Heap(MAX_POINTS);
        graphe = new ArrayList<Point>();
        PCC = new ArrayList<Point>();
        obstacles = new ArrayList<Obstacle>();
        generateObstacles();
        
        /*origine = new Point(5, 5);
        graphe.add(origine);
        for (int i = 0; i < MAX_POINTS - 2; i++){
            Point p = generatePoint(true);
            p.setDistance(Point.calcDistance(p, origine));
            //graphe.addObject(p, i);
            graphe.add(p);
        }*/
        generateStaticPoints();
        
        //arrivee = graphe.get(MAX_POINTS - 1);
        //arrivee = new Point(800, 600);
        //graphe.add(arrivee);
        UI = new RenderPanel();
        this.add(UI);
    }
    
    public Point find_min(ArrayList<Point> Q, Point origine){
        double mini = R;
        Point output = null;
        for (Point p : Q){
            double distance = Point.calcDistance(origine, p);
            if (distance < mini){
                output = p;
                mini = p.getDistance();
            }
        }
        return output;
    }
    
    public void generateObstacles(){
        for (int i = 0; i < OBSTACLES; i++){
            int type = (int)(2*Math.random());
            if (type == 0){ //disque
                int r = (int)(MIN_RAYON + (MAX_RAYON - MIN_RAYON) * Math.random());
                Point c = generatePoint(0);
                Disque d = new Disque(c, r);
                obstacles.add(d);
            }
            else{   //rectangle
                Point ex1  = generatePoint(0);
                Point ex2 = null;
                
                //pour faciliter les calculs. implique que ext1 = HG et ext2 = BD
                do{
                    ex2 = generatePoint(0);
                }while (ex2.getX() <= ex1.getX() || ex2.getY() <= ex1.getY());
                
                Rectangle r = new Rectangle(ex1, ex2);
                obstacles.add(r);
            }
        }
    }
    
    //mode == 0:    pas de vérification de collision.
    //mode == 1:    vérification de collision
    //mode == 2:    vérification de collision ET inclusion dans le rayon.
    public Point generatePoint(int mode){
        if (mode == 1){
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
                    if (obstacles.get(i).collision(p) == true){
                        collide = true;
                    }
                    else if (mode == 3){    //vérification d'inclusion.
                        for (int j = 0; j < graphe.size() -1; j++){
                            if (Point.calcDistance(graphe.get(i), p) > R){
                                collide = true;
                            }
                        }
                    }
                }
            }while (collide == true);
            return p;
        }
    }
    
    public void generateStaticPoints(){
        origine = new Point(5, 5);
        graphe.add(origine);
        Point p1 = new Point(94, 377);
        graphe.add(p1);
        Point p2 = new Point(479, 233);
        graphe.add(p2);
        Point p3 = new Point(1002, 747);
        graphe.add(p3);
        Point p4 = new Point(563, 17);
        graphe.add(p4);
        Point p5 = new Point(36, 380);
        graphe.add(p5);
        Point p6 = new Point(906, 309);
        graphe.add(p6);
        Point p7 = new Point(36, 407);
        graphe.add(p7);
        Point p8 = new Point(135, 392);
        graphe.add(p8);
        Point p9 = new Point(312, 213);
        graphe.add(p9);
        Point p10 = new Point(33, 66);
        graphe.add(p10);
        Point p11 = new Point(441, 466);
        graphe.add(p11);
        Point p12 = new Point(506, 106);
        graphe.add(p12);
        Point p13 = new Point(21, 378);
        graphe.add(p13);
        Point p14 = new Point(199, 161);
        graphe.add(p14);
        Point p15 = new Point(995, 187);
        graphe.add(p15);
        Point p16 = new Point(747, 354);
        graphe.add(p16);
        Point p17 = new Point(190, 333);
        graphe.add(p17);
        Point p18 = new Point(320, 245);
        graphe.add(p18);
        Point p19 = new Point(710, 436);
        graphe.add(p19);
        Point p20 = new Point(827, 111);
        graphe.add(p20);
        Point p21 = new Point(131, 416);
        graphe.add(p21);
        Point p22= new Point(820, 646);
        graphe.add(p22);
        Point p23= new Point(167, 587);
        graphe.add(p23);
        Point p24= new Point(485, 209);
        graphe.add(p24);
        Point p25= new Point(305, 312);
        graphe.add(p25);
        Point p26= new Point(358, 648);
        graphe.add(p26);
        Point p27= new Point(99, 364);
        graphe.add(p27);
        Point p28= new Point(200, 211);
        graphe.add(p28);
        Point p29= new Point(804, 53);
        graphe.add(p29);
        Point p30= new Point(610, 6);
        graphe.add(p30);
        Point p31= new Point(453, 729);
        graphe.add(p31);
        Point p32= new Point(403, 512);
        graphe.add(p32);
        Point p33= new Point(503, 687);
        graphe.add(p33);
        Point p34= new Point(860, 599);
        graphe.add(p34);
        Point p35= new Point(219, 205);
        graphe.add(p35);
        Point p36= new Point(64, 586);
        graphe.add(p36);
        Point p37= new Point(380, 278);
        graphe.add(p37);
        Point p38= new Point(896, 268);
        graphe.add(p38);
        Point p39= new Point(1001, 165);
        graphe.add(p39);
        Point p40= new Point(819, 259);
        graphe.add(p40);
        Point p41= new Point(620, 368);
        graphe.add(p41);
        Point p42= new Point(775, 673);
        graphe.add(p42);
        Point p43= new Point(554, 433);
        graphe.add(p43);
        Point p44= new Point(17, 411);
        graphe.add(p44);
        Point p45= new Point(10, 402);
        graphe.add(p45);
        Point p46= new Point(432, 254);
        graphe.add(p46);
        Point p47= new Point(819, 582);
        graphe.add(p47);
        Point p48= new Point(800, 554);
        graphe.add(p48);
        Point arrivee= new Point(800, 600);
        graphe.add(arrivee);
        
        for (int i = 1; i < graphe.size() - 1; i++){
            graphe.get(i).setDistance(Point.calcDistance(origine, graphe.get(i)));
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.setProperty("file.encoding", "UTF-8");
        EventQueue.invokeLater(() -> {
            Dijkstra app = new Dijkstra();
        });
    }   
}
