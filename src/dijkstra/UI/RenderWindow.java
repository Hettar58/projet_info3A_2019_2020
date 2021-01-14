package dijkstra.UI;

import javax.swing.*;

public class RenderWindow extends JFrame {
    public static RenderPanel UI;
    public RenderWindow()
    {
        super();
        this.setTitle("Dijkstra");
        this.setSize(850, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
