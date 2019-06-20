package ui;

import java.awt.*;

public class UserInput
{
    protected int mousePressedX;
    protected int mousePressedY;
    protected int mousePressedCode;

    protected int mouseMovedX;
    protected int mouseMovedY;

    protected int keyPressedCode;

    protected boolean isMousePressed;
    protected boolean isKeyPressed;
    protected boolean isMouseHeldDown;
    protected boolean isMouseDragged;

    protected Point endDrag, startDrag;
    protected int endDragX;
    protected int endDragY;
    protected int startDragX;
    protected int startDragY;

    public UserInput()
    {
        this.clear();
    }

    public void clear()
    {
        this.isKeyPressed = false;
        this.isMousePressed = false;
    }

    public int getMouseMovedX() { return this.mouseMovedX; };
    public int getMouseMovedY() { return this.mouseMovedY; };


    public int getMousePressedX()
    {
        return mousePressedX;
    }

    public int getEndDragX()
    {
        return endDragX;
    }

    public int getEndDragY()
    {
        return endDragY;
    }

    public int getStartDragX()
    {
        return startDragX;
    }

    public int getStartDragY()
    {
        return startDragY;
    }

    public int getMousePressedY()
    {
        return mousePressedY;
    }

    public int getMousePressedCode()
    {
        return mousePressedCode;
    }
    public int getKeyPressedCode()
    {
        return this.keyPressedCode;
    }

    public boolean isMousePressed()
    {
        return isMousePressed;
    }
    public boolean isMouseHeldDown()
    {
        return this.isMouseHeldDown;
    }
    public boolean isKeyPressed()
    {
        return isKeyPressed;
    }
    public boolean isMouseDragged()
    {
        return isMouseDragged;
    }
}

