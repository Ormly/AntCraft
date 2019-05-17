package main;

import gameobjects.Nest;
import gameobjects.GameObject;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameWorld
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
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
        this.updateObjects(this.lastFrameDuration);
        // collision detection
        this.redrawObjects();
    }

    private void calcFrameDuration()
    {
        long now = System.currentTimeMillis();
        this.lastFrameDuration = (now - this.msSinceLastFrame) / 1000.0;
        this.msSinceLastFrame = now;
    }

    private void updateObjects(double elapsed)
    {
        //ask timeline for next
        //TimeLineEvent event = timerline.getNextEvent()
        //createNewObjects(evet.getObjects())
        // traverse all game objects and update their position
        // should maybe happen in userInputCheck

    }

    private void createNewObject(ArrayList<GameObject> objects){

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
                logger.debug("Left Mouse button pressed at coordinates X: " + userInput.getMousePressedX() + "|Y: " + userInput.getMousePressedY());
            }
            if(mouseCode == MouseEvent.BUTTON3)
            {
                logger.debug("Right Mouse button pressed at coordinates X: "+userInput.getMousePressedX()+"|Y: "+userInput.getMousePressedY());
            }
        }

        if(keyPressed)
        {
            keyCode = userInput.getKeyPressedCode();
            logger.debug("Key Pressed(code "+keyCode+")");
            if(keyCode == KeyEvent.VK_A)
                logger.debug("'a' has been pressed ("+keyCode+")");
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
