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

        this.userInput.setKeyPressedCode(keyEvent.getKeyCode());

        /* KEY TESTS
        System.out.println("entered KeyPressed");
        displayInfo(keyEvent);
        */
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        this.userInput.setMousePressed(true);
        this.userInput.setMouseHeldDown(true);

        this.userInput.setMousePressedX(mouseEvent.getX());
        this.userInput.setMousePressedY(mouseEvent.getY());

        this.userInput.setMousePressedCode(mouseEvent.getButton());
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
