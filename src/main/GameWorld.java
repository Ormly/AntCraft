package main;

import gameobjects.Bug;
import gameobjects.Nest;
import gameobjects.GameObject;
import ui.GraphicsSystem;
import ui.InputSystem;
import ui.UserInput;
import utilities.GameOverEvent;
import utilities.SpawnEvent;
import utilities.Timeline;
import utilities.TimelineEvent;
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
    private Timeline timeline;
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

        ArrayList<GameObject> bugs = new ArrayList();
        bugs.add(new Bug(10,10,10));
        bugs.add(new Bug(10,10,10));
        bugs.add(new Bug(10,10,10));
        bugs.add(new Bug(10,10,10));
        this.timeline = new Timeline();
        this.timeline.addEvent(new GameOverEvent(20 * 1000));
        this.timeline.addEvent(new SpawnEvent(bugs, 5 * 1000));
    }

    public void run()
    {
        this.msSinceLastFrame = System.currentTimeMillis();
        this.timeline.start();

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
        TimelineEvent event = timeline.getNextEvent();

        // if no event has occurred, it'll come back as null!
        if(event != null){
            if(event.isGameOverEvent()){
                gameOver();
            }

            createNewObject(event.getObjects());
        }

        // is nest.health == 0 gameOver();

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

    // check why the game was over (win/lose)
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
