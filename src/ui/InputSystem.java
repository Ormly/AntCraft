package ui;

import interfaces.IInputSystem;

import java.awt.*;
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
        this.userInput.isKeyPressed = true;
        this.userInput.keyPressedCode = keyEvent.getKeyCode();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        this.userInput.isMousePressed = true;
        this.userInput.isMouseHeldDown = true;

        this.userInput.mousePressedX = mouseEvent.getX();
        this.userInput.mousePressedY = mouseEvent.getY();

        this.userInput.mousePressedCode = mouseEvent.getButton();

        this.userInput.startDragX = mouseEvent.getX();
        this.userInput.startDragY = mouseEvent.getY();
        this.userInput.endDragX = this.userInput.startDragX;
        this.userInput.endDragY = this.userInput.startDragY;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        this.userInput.isMouseDragged = false;
        this.userInput.endDragX = mouseEvent.getX();
        this.userInput.endDragY = mouseEvent.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        this.userInput.isMouseDragged = true;
        this.userInput.endDragX = mouseEvent.getX();
        this.userInput.endDragY = mouseEvent.getY();

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {

        this.userInput.mouseMovedX = mouseEvent.getX();
        this.userInput.mouseMovedY = mouseEvent.getY();
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
