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
    private Point ext1;
    private Point ext2;
    
    public Rectangle(Point HG, Point BD){
        this.ext1 = HG;
        this.ext2 = BD;
    }
    
    @Override
    public boolean collision(Sommet s){
        if (ext1.getX() < s.pos.getX() && ext2.getX() > s.pos.getX() && ext1.getY() < s.pos.getY() && ext2.getY() > s.pos.getY()){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public void afficher(Graphics g){
        /*if (ext1.getX() < ext2.getX() && ext1.getY() < ext2.getY()){
            g.fillRect(ext1.getX(), ext1.getY(), ext2.getX() - ext1.getX(), ext2.getY() - ext1.getY());
        }
        else if(ext1.getX() > ext2.getX() && ext1.getY() > ext2.getY()){
            g.fillRect(ext2.getX(), ext2.getY(), ext1.getX() - ext2.getX(), ext1.getY() - ext1.getY());
        }
        else if(ext1.getX() > ext2.getX() && ext1.getY() < ext2.getY()){
            g.fillRect(ext2.getX(), ext1.getY(), ext1.getX() - ext2.getX(), ext2.getY() - ext1.getY());
        }
        else if (ext1.getX() < ext1.getX() && ext1.getY() > ext2.getY()){
            g.fillRect(ext1.getX(), ext2.getY(), ext2.getX() - ext1.getX(), ext1.getY() - ext2.getY());
        }*/
        g.fillRect(ext1.getX(), ext1.getY(), ext2.getX() - ext1.getX(), ext2.getY() - ext1.getY());
    }
}
