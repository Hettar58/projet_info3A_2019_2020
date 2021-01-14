package dijkstra.geometry;

import dijkstra.datastructures.Sommet;

import java.awt.*;

public class Rectangle extends Obstacle {

    private Point ext1;
    private Point ext2;

    public Rectangle(Point HG, Point BD){
        this.ext1 = HG;
        this.ext2 = BD;
    }

    @Override
    public boolean collision(Sommet s){
        return( ext1.getX() < s.position.getX()
                && ext2.getX() > s.position.getX()
                && ext1.getY() < s.position.getY()
                && ext2.getY() > s.position.getY()
                );
    }

    @Override
    public void afficher(Graphics g) {
        g.fillRect(ext1.getX(), ext1.getY(), ext2.getX() - ext1.getX(), ext2.getY() - ext1.getY());
    }
}
