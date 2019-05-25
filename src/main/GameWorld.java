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
    private MouseAreaSelection mouseAreaSelection;

    private Nest nest;

    private int numOfAnts = 6;

    public GameWorld()
    {
        this.gameObjects = new ArrayList<>();
        this.gameObjectsToCreate = new ArrayList<>();
        this.gameObjectsSelected = new ArrayList<>();
    }

    public void init()
    {
        this.physicsSystem = new PhysicsSystem(this);
        GameObject.setGameWorld(this);
        setInputSystem(graphicsSystem.getInputSystem());
        this.nest = new Nest(400, 300, 50);

        this.mouseAreaSelection = new MouseAreaSelection();

        this.initializeTimeline();

        nest = new Nest(400, 300, 50);
        gameObjects.add(nest);

        gameObjects.add(new Ant(100,400));
        gameObjects.add(new Ant(100,450));
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

        if(mouseAreaSelection.isVisible())
            graphicsSystem.draw(mouseAreaSelection);

        graphicsSystem.draw(nest);

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

        boolean nestSelected;

        if(mousePressed)
        {
            if(mouseCode == MouseEvent.BUTTON1)
            {
                GameObject object = getClickSelectedObject(mouseX, mouseY);
                gameObjectsSelected.clear();
                if(object != null)
                {
                    logger.debug("Object at ("+mouseX+"|"+mouseY+") clicked.");
                    gameObjectsSelected.add(object);
                }
                else
                    logger.debug("Background clicked.");

                /*
                //TODO make numOfAnts make sense
                if(numOfAnts > 0)
                {
                    Ant ant = new Ant(Constants.NEST_X_POS, Constants.NEST_Y_POS);
                    ant.setDestination(userInput.getMousePressedX(), userInput.getMousePressedY());
                    gameObjectsToCreate.add(ant);
                    numOfAnts--;
                }
                */
            }

            if(mouseCode == MouseEvent.BUTTON3)
            {
                if(!gameObjectsSelected.isEmpty())
                {
                    for(GameObject gameObject : gameObjectsSelected)
                    {
                        if(gameObject instanceof Ant)
                            gameObject.setDestination(mouseX, mouseY);
                    }
                }
            }
        }

        if(mouseDragged && (mouseCode != MouseEvent.BUTTON3))
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

            ArrayList<GameObject> areaSelectedObjects = getAreaSelectedObjects(topLeftX,topLeftY,bottomRightX,bottomRightY);
            gameObjectsSelected.clear();

            if(!areaSelectedObjects.isEmpty())
                gameObjectsSelected.addAll(areaSelectedObjects);

        } else this.mouseAreaSelection.setIsVisible(false);

        userInput.clear();
    }

    private GameObject getClickSelectedObject(int mouseX, int mouseY)
    {
        double distanceX;
        double distanceY;
        double radius;

        for(GameObject gameObject : gameObjects)
        {
            distanceX = Math.abs(mouseX - gameObject.getXPos());
            distanceY = Math.abs(mouseY - gameObject.getYPos());
            radius = gameObject.getRadius();

            if((distanceX + distanceY <= radius) || (Math.pow(distanceX,2) + Math.pow(distanceY,2)) <= Math.pow(radius,2))
                return gameObject;
        }
        return null;
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
}
