package main;

import core.Level;
import core.PhysicsSystem;
import gameobjects.*;
import ui.*;
import utilities.*;
import utilities.logging.Logger;
import utilities.logging.Logging;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameWorld
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private GraphicsSystem graphicsSystem;
    private PhysicsSystem physicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;
    private GUIMenu currentMenu;
    private Menu mainMenu;
    private LevelMenu levelMenu;

    private long timestampLast;
    private double frameDuration;

    private ArrayList<Level> levels;
    private Level currentLevel;
    private Timeline timeline;

    private boolean isRunning;
    private boolean isGameOver;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> gameObjectsToCreate;
    private ArrayList<GameObject> gameObjectsSelected;
    private Queue<Ant> antsInNest;
    private MouseAreaSelection mouseAreaSelection;
    private AntStockIndicator antStockIndicator;
    private BugQueue bugQueue;
    private ArrayList<AttackIndicator> attackIndicators;
    private ArrayList<Impact> impactEffects;

    private Nest nest;

    private int numOfAnts;

    public GameWorld()
    {
        this.resetGame();
    }

    private void resetGame()
    {
        this.isGameOver = false;
        this.mainMenu = new Menu();
        this.physicsSystem = new PhysicsSystem(this);
        this.gameObjects = new ArrayList<>();
        this.gameObjectsToCreate = new ArrayList<>();
        this.gameObjectsSelected = new ArrayList<>();
        this.antsInNest = new LinkedList<>();
        this.antStockIndicator = new AntStockIndicator(this);
        this.currentLevel = null;
	    this.attackIndicators = new ArrayList<>();
	    this.impactEffects = new ArrayList<>();
    }

    public void pauseGame()
    {
        this.isRunning = false;
        this.timeline.freeze();
    }

    public void unPauseGame()
    {
        if(this.isGameOver)
        {
            this.resetGame();
            this.init();
            return;
        }

        this.isRunning = true;
        if(!this.timeline.isAlive())
            this.timeline.start();
        else
            this.timeline.unFreeze();
    }

    public void init()
    {
        setInputSystem(graphicsSystem.getInputSystem());
        GameObject.setGameWorld(this);
        this.mouseAreaSelection = new MouseAreaSelection();
        nest = new Nest(Constants.NEST_X_POS, Constants.NEST_Y_POS, 65);
        gameObjects.add(nest);

        this.setCurrentMenu(this.mainMenu);
        GUIMenu.setGameWorld(this);
        GUIMenu.setInputSystem(graphicsSystem.getInputSystem());
        this.loadLevels();
        this.initAntsInNest();

        this.bugQueue = new BugQueue(this);
    }

    private void loadLevels()
    {
        this.levels = Level.readFromFile();
        if(this.levels.isEmpty())
        {
            logger.error("No levels found!");
            System.exit(1);
        }
        this.levelMenu = new LevelMenu();

        if(this.currentLevel == null)
            this.setCurrentLevel(this.levels.get(0));
    }

    private void setCurrentLevel(Level level)
    {
        this.currentLevel = level;
        this.timeline = this.currentLevel.getTimeline();
        this.numOfAnts = this.currentLevel.getNumOfAnts();
    }


    private void initAntsInNest()
    {
        for(int i=0; i<this.numOfAnts; ++i)
        {
            this.antsInNest.add(new Ant());
        }
        this.gameObjects.addAll(this.antsInNest);
    }

    public ArrayList<GameObject> getGameObjects()
    {
        return gameObjects;
    }

    public void run()
    {
        this.isRunning = false;
        this.timeline.start();
        this.timestampLast = System.currentTimeMillis();

        this.pauseGame(); // start the game paused

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

        if(this.nest.isDead())
            gameOver();

	    bugQueue.update();

	    antStockIndicator.update();

        if(this.timeline.hasEvents()){
            TimelineEvent event = timeline.getNextEvent();

            if(event.isGameOverEvent())
	    {
                gameOver(); // end the game
            }

            ArrayList<GameObject> newObjects = event.getObjects();

            if(newObjects != null)
                this.gameObjectsToCreate.addAll(event.getObjects());
        }

        createNewObjects();

        removeTheDead();

        // update all game objects
        attackIndicators.clear();
        for(GameObject gameObject : gameObjects)
        {
            // is ant back to nest?
            if(gameObject instanceof Ant && ((Ant)gameObject).isInNest() && !this.antsInNest.contains(gameObject))
            {
                this.antsInNest.add((Ant)gameObject);
                this.gameObjectsSelected.remove(gameObject);
            }

            gameObject.update(this.frameDuration);
            if(gameObject instanceof Bug)
            {
                if(!this.physicsSystem.isObjectOnScreen(gameObject))
                {
                    AttackIndicator indicator = new AttackIndicator(this);
                    indicator.init((Bug) gameObject);
                    attackIndicators.add(indicator);
                }
            }
        }

        if(!attackIndicators.isEmpty())
        {
            for(AttackIndicator attackIndicator : attackIndicators)
                attackIndicator.updateIsVisible();
        }

        for(int i=0; i < this.impactEffects.size(); ++i)
        {
            this.impactEffects.get(0).update(this.frameDuration);

            if(this.impactEffects.get(i).isDead())
                this.impactEffects.remove(this.impactEffects.get(i));
        }

    }

    private void removeTheDead()
    {
        ArrayList<GameObject> toRemove = new ArrayList<>();
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject.isDead())
                toRemove.add(gameObject);

        }

        this.gameObjects.removeAll(toRemove);
        this.gameObjectsSelected.removeAll(toRemove);
    }

    private void redrawObjects()
    {
        graphicsSystem.clear();

        if(!this.isRunning)
            this.graphicsSystem.draw(this.currentMenu);
        else
        {
            for(GameObject gameObject : gameObjects)
            {
                if(gameObject.isVisible()) // only draw visible objects
                    graphicsSystem.draw(gameObject);
            }

            graphicsSystem.draw(gameObjectsSelected);

            graphicsSystem.draw(nest);

            if(mouseAreaSelection.isVisible())
                graphicsSystem.draw(mouseAreaSelection);

            if(antStockIndicator.isVisible())
                graphicsSystem.draw(antStockIndicator);

            if(bugQueue.isVisible())
                graphicsSystem.draw(bugQueue);


            ArrayList<AttackIndicator> toRemove = new ArrayList<>();
            if(!attackIndicators.isEmpty())
            {
                for(AttackIndicator attackIndicator : attackIndicators)
                {
                    if(attackIndicator.isVisible())
                        graphicsSystem.draw(attackIndicator);
                    else
                        toRemove.add(attackIndicator);
                }
            }
            attackIndicators.removeAll(toRemove);

            for(Impact i:this.impactEffects)
                this.graphicsSystem.draw(i);
        }

        graphicsSystem.swapBuffers();
    }

    private void checkUserInput()
    {
        userInput = inputSystem.getUserInput();

        if(userInput.isKeyPressed())
            if(userInput.getKeyPressedCode() == KeyEvent.VK_ESCAPE)
            {
                this.currentMenu = this.mainMenu;
                if(this.isRunning)
                    this.pauseGame();
                else
                    this.unPauseGame();
            }

        if(!this.isRunning)
        {
            this.currentMenu.processInput(userInput);
            userInput.clear();
            return;
        }

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
                                Ant ant = this.antsInNest.remove();
                                ant.setDestination(userInput.getMousePressedX(), userInput.getMousePressedY());
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
            gameObjectsSelected.addAll(this.physicsSystem.getAreaSelectedObjects(topLeftX,topLeftY,bottomRightX,bottomRightY));

        } else this.mouseAreaSelection.setIsVisible(false);

        userInput.clear();
    }

    private void setCurrentMenu(GUIMenu menu)
    {
        this.currentMenu = menu;
    }

    public void showLevelMenu()
    {
        setCurrentMenu(this.levelMenu);
    }

    private void createNewObjects()
    {
        if(!this.gameObjectsToCreate.isEmpty())
        {
            gameObjects.addAll(this.gameObjectsToCreate);
            this.gameObjectsToCreate.clear();
        }
    }

    public Nest getNest()
    {
        return this.nest;
    }

    private void gameOver()
    {
        this.isRunning = false;
        this.isGameOver = true;
        this.setCurrentMenu(new GameOverMenu(this.nest.getHealthStatus()>0));
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

    public int getNumOfAnts()
    {
        return this.antsInNest.size();
    }

    public Timeline getTimeline()
    {
        return this.timeline;
    }

    public PhysicsSystem getPhysicsSystem()
    {
        return this.physicsSystem;
    }

    public LinkedList<String> getLevelNames()
    {
        LinkedList<String> names = new LinkedList<>();

        for(Level l:this.levels)
        {
            names.add(l.getName());
        }

        return names;
    }

    public void startLevelWithName(String name)
    {
        this.resetGame();

        for(Level l:this.levels)
        {
            if(l.getName().compareTo(name) == 0)
            {
                this.setCurrentLevel(l);
            }
        }

        this.init();

        this.setCurrentMenu(this.mainMenu);

        this.unPauseGame();
    }

    public void spawnImpactEffect(double x, double y)
    {
        this.impactEffects.add(new Impact(x,y));
    }
}
