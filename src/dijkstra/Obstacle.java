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
abstract class Obstacle {
    abstract void afficher(Graphics g);
    abstract boolean collision(Point p);
}
