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
    
    public static final int MAX_RAYON = 20;
    public static final int MIN_RAYON = 9;
    public static final int MAX_POINTS = 10;
    public static final int MAX_OBSTACLES = 10;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    
    public static ArrayList<Obstacle> obstacles;
    
    RenderPanel UI;
    Heap graphe;
    
    public Dijkstra(){
        this.setTitle("Dijkstra");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        graphe = new Heap(MAX_POINTS);
        
        obstacles = new ArrayList<Obstacle>();
        generateObstacles();
        
        for (int i = 0; i < MAX_POINTS; i++){
            Point p = generatePoint(true);
            System.out.println(p.getX() + " " + p.getY());
            graphe.addObject(p, i);
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
                Point ex2 = generatePoint(false);
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
                int x = (int)(1+(WIDTH - 1) * Math.random());
                int y = (int)(1+(HEIGHT -1) * Math.random());
                p = new Point(x, y);
                for (int i = 0; i < MAX_OBSTACLES / 2; i++){
                    if (obstacles.get(i).collision(p) == true || obstacles.get(MAX_OBSTACLES - 1 - i).collision(p) == true){
                        collide = true;
                    }
                }
                System.out.println(collide);
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
