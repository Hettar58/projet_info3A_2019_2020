package dijkstra;

import dijkstra.UI.CommandUI;
import dijkstra.UI.RenderPanel;
import dijkstra.UI.RenderWindow;

public class Main {
    public static RenderWindow rw;
    public static CommandUI UI;
    public static RenderPanel rp;

    public static void main(String[] args)
    {
        Dijkstra dj = new Dijkstra();
        rw = new RenderWindow();
        rp = new RenderPanel();
        UI = new CommandUI(rw, false);
        UI.setVisible(true);
        rp.setPreferredSize(rw.getSize());
        rp.setVisible(true);
        rw.add(rp);
        rw.revalidate();
        dj.init();
    }

    public static void quit()
    {
        UI.setVisible(false);
        UI.dispose();
        System.exit(0);
    }
}
