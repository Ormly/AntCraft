package ui;

import interfaces.IFrame;

import javax.swing.*;

public class Frame extends JFrame implements IFrame
{
    private GraphicsSystem gm;

    public Frame(int w, int h)
    {
        this.setSize(w, h);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.gm = new GraphicsSystem(w, h);
        this.setContentPane(gm);
    }

    public void display()
    {
        this.setVisible(true);
    }

    public GraphicsSystem getPanel() { return this.gm; }

}
