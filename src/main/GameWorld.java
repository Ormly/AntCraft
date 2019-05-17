package main;

import gameobjects.Ant;
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

public class GameWorld
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private GraphicsSystem graphicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;

    private long msSinceLastFrame;
    private double lastFrameDuration;
    private ArrayList<GameObject> gameObjects;
    private Nest nest;

    public GameWorld()
    {
        this.gameObjects = new ArrayList<>();
        this.lastFrameDuration = System.currentTimeMillis();
    }

    public void init()
    {
        setInputSystem(graphicsSystem.getInputSystem());

        nest = new Nest(400, 300, 50);

        gameObjects.add(new Ant(100,100,10));
    }

    public void run()
    {
        msSinceLastFrame = System.currentTimeMillis();

        while(true)
            gameLoop();
    }

    public void gameLoop()
    {
        calcFrameDuration();
        checkUserInput();
        updateObjects(msSinceLastFrame);
        redrawObjects();
    }

    private void calcFrameDuration()
    {
        //this.logger.debug("Calculating Frame Duration...");

        long now = System.currentTimeMillis();
        lastFrameDuration = (now - msSinceLastFrame) / 1000.0;
        msSinceLastFrame = now;
    }

    private void updateObjects(long elapsed)
    {
        for(GameObject gameObject : gameObjects)
            gameObject.update(lastFrameDuration);
    }

    private void redrawObjects()
    {
        //this.logger.debug("Drawing elements...");

        graphicsSystem.clear();
        for(GameObject gameObject : gameObjects)
            graphicsSystem.draw(gameObject);
        graphicsSystem.draw(nest);

        graphicsSystem.swapBuffers();
    }

    private void checkUserInput()
    {
        //this.logger.debug("Checking User Input...");

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
                for(GameObject gameObject : gameObjects)
                    gameObject.setDestination(userInput.getMousePressedX(),userInput.getMousePressedY());
            }
        }

        /*TESTS
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
        */

        userInput.clear();
    }

    private void createNewObjects()
    {
        //this.logger.debug("Creating new Objects...");
    }

    private void gameOver() {}

    private void setInputSystem(InputSystem inputSystem) { this.inputSystem = inputSystem; }
    public void setGraphicsSystem(GraphicsSystem graphicsSystem) { this.graphicsSystem = graphicsSystem; }
}
