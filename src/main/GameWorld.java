package main;

import core.PhysicsSystem;
import gameobjects.*;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;
import utilities.*;
import utilities.logging.AbstractLogger;
import utilities.logging.Logging;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GameWorld
{
    private AbstractLogger logger = Logging.getLogger(this.getClass().getName());
    private GraphicsSystem graphicsSystem;
    private PhysicsSystem physicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;

    private long timestampLast;
    private double frameDuration;

    private Timeline timeline;

    private boolean isRunning;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> gameObjectsToCreate;
    private ArrayList<GameObject> gameObjectsSelected;
    private Queue<Ant> antsInNest;
    private MouseAreaSelection mouseAreaSelection;


    private Nest nest;

    private int numOfAnts = 6;

    public GameWorld()
    {
        this.gameObjects = new ArrayList<>();
        this.gameObjectsToCreate = new ArrayList<>();
        this.gameObjectsSelected = new ArrayList<>();
        this.antsInNest = new LinkedList<>();
    }

    public void init()
    {
        this.physicsSystem = new PhysicsSystem(this);
        GameObject.setGameWorld(this);
        setInputSystem(graphicsSystem.getInputSystem());
        this.nest = new Nest(400, 300, 50);

        this.mouseAreaSelection = new MouseAreaSelection();

        this.initializeTimeline();
        this.initAntsInNest();

        nest = new Nest(Constants.NEST_X_POS, Constants.NEST_Y_POS, 50);
        gameObjects.add(nest);
    }

    private void initAntsInNest()
    {
        for(int i=0; i<this.numOfAnts; ++i)
        {
            this.antsInNest.add(new Ant());
        }
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
            if(gameObject instanceof Ant && ((Ant)gameObject).isInNest())
            {
                toRemove.add(gameObject);
                this.antsInNest.add((Ant)gameObject);
            }

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

        graphicsSystem.draw(gameObjectsSelected);

        graphicsSystem.draw(nest);

        if(mouseAreaSelection.isVisible())
            graphicsSystem.draw(mouseAreaSelection);

        graphicsSystem.swapBuffers();
    }

    private void checkUserInput()
    {
        userInput = inputSystem.getUserInput();

        int mouseCode = userInput.getMousePressedCode();
        int keyCode = userInput.getKeyPressedCode();
        int mouseX = userInput.getMousePressedX();
        int mouseY = userInput.getMousePressedY();

        boolean mousePressed = userInput.isMousePressed();
        boolean mouseHeldDown = userInput.isMouseHeldDown();
        boolean keyPressed = userInput.isKeyPressed();
        boolean mouseDragged = userInput.isMouseDragged();

        boolean nestSelected = false;
        boolean antSelected = false;

        if(mousePressed)
        {
            if(mouseCode == MouseEvent.BUTTON1)
            {
                GameObject object = getGameObjectAtCoordinate(mouseX, mouseY);

                gameObjectsSelected.clear();

                if(object != null)
                    gameObjectsSelected.add(object);

            }

            //TODO check if this is dumb/needs refactoring
            if(mouseCode == MouseEvent.BUTTON3)
            {
                if(!gameObjectsSelected.isEmpty())
                {
                    for(GameObject gameObject : gameObjectsSelected)
                    {
                        if(gameObject instanceof Nest) nestSelected = true;
                        if(gameObject instanceof Ant) antSelected = true;
                    }

                    if(nestSelected && antSelected)
                        gameObjectsSelected.remove(nest);

                    for(GameObject gameObject : gameObjectsSelected)
                    {
                        if(gameObject instanceof Ant)
                            gameObject.setDestination(mouseX, mouseY);

                        else if(gameObject instanceof Nest)
                        {
                            if(!this.antsInNest.isEmpty())
                            {
                                Ant ant = this.antsInNest.poll();
                                ant.setDestination(userInput.getMousePressedX(), userInput.getMousePressedY());
                                gameObjectsToCreate.add(ant);
                            }
                            else
                            {
                                logger.info("No more ants available in nest!");
                            }
                        }
                    }
                }
            }
        }

        if(mouseDragged && (mouseCode == MouseEvent.BUTTON1))
        {
            int endX = this.userInput.getEndDragX();
            int endY = this.userInput.getEndDragY();
            int startX = this.userInput.getStartDragX();
            int startY = this.userInput.getStartDragY();

            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);

            int topLeftX;
            int topLeftY;
            int bottomRightX;
            int bottomRightY;

            if(endX >= startX)
            {
                if(endY >= startY)
                {
                    topLeftX = startX;
                    topLeftY = startY;
                    bottomRightX = endX;
                    bottomRightY = endY;
                }
                else
                {
                    topLeftX = startX;
                    topLeftY = endY;
                    bottomRightX = endX;
                    bottomRightY = endY + height;
                }
            }
            else
            {
                if(endY > startY)
                {
                    topLeftX = endX;
                    topLeftY = startY;
                    bottomRightX = endX + width;
                    bottomRightY = endY;
                }
                else
                {
                    topLeftX = endX;
                    topLeftY = endY;
                    bottomRightX = endX + width;
                    bottomRightY = endY + height;
                }
            }
            this.mouseAreaSelection.update(topLeftX,topLeftY,width,height);

            gameObjectsSelected.clear();
            gameObjectsSelected.addAll(getAreaSelectedObjects(topLeftX,topLeftY,bottomRightX,bottomRightY));

        } else this.mouseAreaSelection.setIsVisible(false);

        userInput.clear();
    }

    private ArrayList<GameObject> getAreaSelectedObjects(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
    {
        double objectX;
        double objectY;
        ArrayList<GameObject> areaSelectedObjects = new ArrayList<>();

        for(GameObject gameObject : gameObjects)
        {
            objectX = gameObject.getXPos();
            objectY = gameObject.getYPos();

            if(objectX >= topLeftX && objectX <= bottomRightX && objectY >= topLeftY && objectY <= bottomRightY)
                areaSelectedObjects.add(gameObject);
        }
        return areaSelectedObjects;
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

    public GameObject getGameObjectAtCoordinate(int xPos, int yPos)
    {
        return this.physicsSystem.getGameObjectAtCoordinate(xPos,yPos);
    }

}
