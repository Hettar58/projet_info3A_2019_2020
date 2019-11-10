/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.awt.Graphics;

/**
 *
 * @author yt646712
 */
public class Point {
    private int x;
    private int y;
    private int distance;
    //boolean out;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.distance = 0;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public void afficher(Graphics g){
        int r = 2;
        g.fillOval(x-r, y-r, 2 * r, 2 * r);
    }
    
    public static double calcDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }
}
