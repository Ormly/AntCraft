package main;

import core.PhysicsSystem;
import gameobjects.Ant;
import gameobjects.Bug;
import gameobjects.GameObject;
import gameobjects.Nest;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;
import utilities.*;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameWorld
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private GraphicsSystem graphicsSystem;
    private PhysicsSystem physicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;

    private boolean isRunning;
    private long timestampLast;
    private double frameDuration;
    private Timeline timeline;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> gameObjectsToCreate;
    private Nest nest;
    private int numOfAnts = 6;

    public GameWorld()
    {
        this.gameObjects = new ArrayList<>();
        this.gameObjectsToCreate = new ArrayList<>();
    }

    public void init()
    {
        this.physicsSystem = new PhysicsSystem(this);
        GameObject.setGameWorld(this);
        setInputSystem(graphicsSystem.getInputSystem());
        this.nest = new Nest(400, 300, 50);

        this.initializeTimeline();

        nest = new Nest(400, 300, 50);
        gameObjects.add(nest);
    }

    public ArrayList<GameObject> getGameObjects()
    {
        return gameObjects;
    }

    public void run()
    {
        this.isRunning = true;
        this.timeline.start();
        this.timestampLast = System.currentTimeMillis();

        while(true)
            gameLoop();
    }

    public void gameLoop()
    {
        calcFrameDuration();

        checkUserInput();

        updateObjects();

        redrawObjects();
    }

    private void calcFrameDuration()
    {
        long now = System.currentTimeMillis();
        this.frameDuration = (now - this.timestampLast) / 1000.0;
        this.timestampLast = now;
    }

    private void updateObjects()
    {
        if(!isRunning)
            return;

        if(this.timeline.hasEvents()){
            TimelineEvent event = timeline.getNextEvent();

            if(event.isGameOverEvent()){
                gameOver(); // end the game
            }

            ArrayList<GameObject> newObjects = event.getObjects();

            if(newObjects != null)
                this.gameObjectsToCreate.addAll(event.getObjects());
        }

        createNewObjects();

        ArrayList<GameObject> toRemove = new ArrayList<>();

        // update all game objects and remove dead ones
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isDead())
                toRemove.add(gameObject);
            else
                gameObject.update(this.frameDuration);
        }

        this.gameObjects.removeAll(toRemove);
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
                //TODO make numOfAnts make sense
                if(numOfAnts > 0)
                {
                    Ant ant = new Ant(Constants.NEST_X_POS, Constants.NEST_Y_POS);
                    ant.setDestination(userInput.getMousePressedX(), userInput.getMousePressedY());
                    gameObjectsToCreate.add(ant);
                    numOfAnts--;
                }
            }
        }

        userInput.clear();
    }

    private void initializeTimeline()
    {
        ArrayList<GameObject> bugs = new ArrayList();
        bugs.add(new Bug(100,100,10,20));
        bugs.add(new Bug(400,700,10,20));
        this.timeline = new Timeline();
//        this.timeline.addEvent(new GameOverEvent(20 * 1000));
        this.timeline.addEvent(new SpawnEvent(bugs, 1 * 1000));
    }

    private void createNewObjects()
    {
        if(!this.gameObjectsToCreate.isEmpty())
        {
            gameObjects.addAll(this.gameObjectsToCreate);
            this.gameObjectsToCreate.clear();
        }
    }

    private void gameOver()
    {
        this.isRunning = false;
        logger.info("Game is over!");
    }

    private void setInputSystem(InputSystem inputSystem)
    {
        this.inputSystem = inputSystem;
    }

    public void setGraphicsSystem(GraphicsSystem graphicsSystem)
    {
        this.graphicsSystem = graphicsSystem;
    }

    public ArrayList<GameObject> getCollisions(GameObject object)
    {
        return this.physicsSystem.getCollisions(object);
    }
}
