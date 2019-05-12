package ui;

import interfaces.AFrame;

import javax.swing.*;

public class BFrame extends JFrame implements AFrame
{
    private BGraphicsSystem gm;

    public BFrame(int w, int h)
    {
        this.setSize(w, h);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gm = new BGraphicsSystem(w, h);
        this.setContentPane(gm);
    }

    public void display()
    {
        this.setVisible(true);
    }

    public BGraphicsSystem getPanel() { return this.gm; }

}
