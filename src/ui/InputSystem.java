package ui;

import interfaces.IInputSystem;

import java.awt.event.*;

public class InputSystem implements IInputSystem, KeyListener, MouseListener, MouseMotionListener
{
    private UserInput userInput = new UserInput();

    @Override
    public UserInput getUserInput()
    {
        return this.userInput;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        this.userInput.setIsKeyPressed(true);

        if(keyEvent.getID() == KeyEvent.KEY_TYPED)
            this.userInput.setKeyPressedValue(keyEvent.getKeyChar());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        this.userInput.setMousePressed(true);
        this.userInput.setMouseHeldDown(true);

        this.userInput.setMousePressedX(mouseEvent.getX());
        this.userInput.setMousePressedY(mouseEvent.getY());

        this.userInput.setMousePressedValue(mouseEvent.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        this.userInput.setMouseHeldDown(false);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        /*TODO figure out how this works for purpose of selecting multiple ants*/
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        this.userInput.setMouseMovedX(mouseEvent.getX());
        this.userInput.setMouseMovedY(mouseEvent.getY());
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
