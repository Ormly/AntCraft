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
        //this.userInput.isMouseDragged = true;

        this.userInput.mousePressedX = mouseEvent.getX();
        this.userInput.mousePressedY = mouseEvent.getY();

        this.userInput.mousePressedCode = mouseEvent.getButton();

        this.userInput.startDrag = mouseEvent.getPoint();
        this.userInput.endDrag = this.userInput.startDrag;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        this.userInput.isMouseDragged = false;
        this.userInput.endDrag = mouseEvent.getPoint();

        //(this.userInput.startDrag != null && this.userInput.endDrag != null)
            //this.userInput.isMouseDragged = true;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent)
    {
        this.userInput.isMouseDragged = true;
        this.userInput.endDrag = mouseEvent.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        this.userInput.mouseMovedX = mouseEvent.getX();
        this.userInput.mouseMovedY = mouseEvent.getY();
    }


    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        /* KEY TESTS
        System.out.println("entered KeyTyped");
        displayInfo(keyEvent);
        */
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        /* KEY TESTS
        System.out.println("entered KeyReleased");
        displayInfo(keyEvent);
        */
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    /* KEY TESTS
    private void displayInfo(KeyEvent keyEvent)
    {
        if(keyEvent.getID() == KeyEvent.KEY_TYPED)
        {
            char c = keyEvent.getKeyChar();
            System.out.println("Typed char ["+c+"]");
        }
        else
        {
            int code = keyEvent.getKeyCode();
            System.out.println("Keycode "+code+"("+keyEvent.getKeyText(code)+")");
        }
    }
    */
}
