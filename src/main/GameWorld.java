package main;

import gameobjects.Ant;
import gameobjects.Bug;
import gameobjects.Nest;
import gameobjects.GameObject;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;
import utilities.Constants;
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
    private ArrayList<GameObject> gameObjectsToCreate;
    private Nest nest;

    public GameWorld()
    {
        this.gameObjects = new ArrayList<>();
        this.gameObjectsToCreate = new ArrayList<>();
        this.lastFrameDuration = System.currentTimeMillis();
    }

    public void init()
    {
        setInputSystem(graphicsSystem.getInputSystem());

        nest = new Nest(400, 300, 50);
        gameObjects.add(nest);
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
        long now = System.currentTimeMillis();
        lastFrameDuration = (now - msSinceLastFrame) / 1000.0;
        msSinceLastFrame = now;
    }

    private void updateObjects(long elapsed)
    {
        createNewObjects(gameObjectsToCreate);

        for(GameObject gameObject : gameObjects)
            gameObject.update(lastFrameDuration);
    }

    private void redrawObjects()
    {
        graphicsSystem.clear();
        for(GameObject gameObject : gameObjects)
            graphicsSystem.draw(gameObject);
        graphicsSystem.draw(nest);

        graphicsSystem.swapBuffers();
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
                //TODO check for remaining ants
                Ant ant = new Ant(Constants.NEST_X_POS,Constants.NEST_Y_POS);
                ant.setDestination(userInput.getMousePressedX(),userInput.getMousePressedY());
                gameObjectsToCreate.add(ant);
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

    private void createNewObjects(ArrayList<GameObject> newGameObjects)
    {
        gameObjects.addAll(newGameObjects);
    }

    private void gameOver() {}

    private void setInputSystem(InputSystem inputSystem) { this.inputSystem = inputSystem; }
    public void setGraphicsSystem(GraphicsSystem graphicsSystem) { this.graphicsSystem = graphicsSystem; }
}
