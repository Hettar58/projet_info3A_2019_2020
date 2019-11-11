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
    public static final int MAX_POINTS = 5000;
    public static final int MAX_OBSTACLES = 10;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int MARGIN = 20;
    
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Point> graphe;
    //public static Heap graphe;
    RenderPanel UI;

    
    public Dijkstra(){
        this.setTitle("Dijkstra");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        //graphe = new Heap(MAX_POINTS);
        graphe = new ArrayList<Point>();
        obstacles = new ArrayList<Obstacle>();
        generateObstacles();
        
        for (int i = 0; i < MAX_POINTS; i++){
            Point p = generatePoint(true);
            //graphe.addObject(p, i);
            graphe.add(p);
        }
        
        UI = new RenderPanel();
        this.add(UI);
    }
    
    public void generateObstacles(){
        for (int i = 0; i < MAX_OBSTACLES; i++){
            int type = (int)(2*Math.random());
            if (type == 0){ //disque
                int r = (int)(MIN_RAYON + (MAX_RAYON - MIN_RAYON) * Math.random());
                Point c = generatePoint(false);
                Disque d = new Disque(c, r);
                obstacles.add(d);
            }
            else{   //rectangle
                Point ex1  = generatePoint(false);
                Point ex2 = null;
                
                //pour faciliter les calculs. implique que ext1 = HG et ext2 = BD
                do{
                    ex2 = generatePoint(false);
                }while (ex2.getX() <= ex1.getX() || ex2.getY() <= ex1.getY());
                
                Rectangle r = new Rectangle(ex1, ex2);
                obstacles.add(r);
            }
        }
    }
    public Point generatePoint(boolean colCheck){
        if (colCheck == false){
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
            Dijkstra app = new Dijkstra();
        });
    }   
}
