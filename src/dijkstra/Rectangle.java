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
public class Rectangle extends Obstacle{
    Point HG;
    Point BD;
    
    public Rectangle(Point HG, Point BD){
        this.HG = HG;
        this.BD = BD;
    }
    
    @Override
    public boolean collision(Point p){
        if (HG.getX() > p.getX() && BD.getX() < p.getX()){
            if (HG.getY() < p.getY() && BD.getY() > p.getY()){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    @Override
    public void afficher(Graphics g){
        g.fillRect(HG.getX(), HG.getY(), BD.getX() - HG.getX(), BD.getY() - HG.getY());
    }
}
