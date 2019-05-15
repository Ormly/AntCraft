package main;

import gameobjects.Nest;
import gameobjects.GameObject;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameWorld
{
    private GraphicsSystem graphicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;

    private long msSinceLastFrame;
    private double lastFrameDuration;
    private ArrayList<GameObject> objects;
    private Nest nest;

    public GameWorld()
    {
        this.objects = new ArrayList<>();
        this.lastFrameDuration = System.currentTimeMillis();
    }

    public void init()
    {
        setInputSystem(graphicsSystem.getInputSystem());
        this.nest = new Nest(400, 300, 50);
    }

    public void run()
    {
        this.msSinceLastFrame = System.currentTimeMillis();

        while(true)
        {
            this.gameLoop();
        }
    }

    public void gameLoop()
    {
        calcFrameDuration();
        this.checkUserInput();
        // move stuff
        // collision detection
        this.redrawObjects();
    }

    private void calcFrameDuration()
    {
        long now = System.currentTimeMillis();
        this.lastFrameDuration = (now - this.msSinceLastFrame) / 1000.0;
        this.msSinceLastFrame = now;
    }

    private void updateObjects(long elapsed)
    {
        // traverse all game objects and update their position
        // should maybe happen in userInputCheck

    }

    private void redrawObjects()
    {
        // iterate over all objects and redraw them
//        this.logger.debug("Drawing element!");
        this.graphicsSystem.clear();
//        this.graphicsManager.draw();
        this.graphicsSystem.draw(this.nest);
        this.graphicsSystem.swapBuffers();
    }

    private void checkUserInput()
    {
        userInput = inputSystem.getUserInput();

        int mouseCode;
        int keyCode;

        boolean mousePressed = userInput.isMousePressed();
        boolean mouseHeldDown = userInput.isMouseHeldDown();
        boolean keyPressed = userInput.isKeyPressed();

        if(mousePressed)
        {
            mouseCode = userInput.getMousePressedCode();
            if(mouseCode == MouseEvent.BUTTON1)
            {
                System.out.println("Left Mouse button pressed at coordinates X: "+userInput.getMousePressedX()+"|Y: "+userInput.getMousePressedY());
            }
            if(mouseCode == MouseEvent.BUTTON3)
            {
                System.out.println("Right Mouse button pressed at coordinates X: "+userInput.getMousePressedX()+"|Y: "+userInput.getMousePressedY());
            }
        }

        if(keyPressed)
        {
            keyCode = userInput.getKeyPressedCode();
            System.out.println("Key Pressed(code "+keyCode+")");
            if(keyCode == KeyEvent.VK_A)
                System.out.println("'a' has been pressed ("+keyCode+")");
        }

        userInput.clear();
    }

    private void createNewObjects()
    {

    }

    private void gameOver()
    {

    }

    private void setInputSystem(InputSystem inputSystem)
    {
        this.inputSystem = inputSystem;
    }

    public void setGraphicsSystem(GraphicsSystem graphicsSystem)
    {
        this.graphicsSystem = graphicsSystem;
    }
}
