package ui;

import interfaces.IFrame;

import javax.swing.*;

public class Frame extends JFrame implements IFrame
{
    private GraphicsSystem graphicsSystem;

    public Frame(int width, int height)
    {
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setAlwaysOnTop(true);
        //this.setUndecorated(true);

        this.setResizable(false);

        this.graphicsSystem = new GraphicsSystem(width, height);

        graphicsSystem.setFocusable(true);
        graphicsSystem.requestFocusInWindow();

        this.setContentPane(graphicsSystem);
    }

    public void display()
    {
        this.setVisible(true);
    }

    public GraphicsSystem getPanel() { return this.graphicsSystem; }

}
