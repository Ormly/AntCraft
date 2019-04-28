import javax.swing.*;

public class Frame extends JFrame
{
    private GraphicsManager gm;

    public Frame(int w, int h)
    {
        this.setSize(w, h);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gm = new GraphicsManager(w, h);
        this.setContentPane(gm);
    }

    public void display()
    {
        this.setVisible(true);
    }

    public GraphicsManager getPanel() { return this.gm; }

}
