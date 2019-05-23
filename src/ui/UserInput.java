package ui;

import java.awt.*;

public class UserInput
{
    protected int mousePressedX;
    protected int mousePressedY;
    protected int mousePressedCode;

    protected int mouseMovedX;
    protected int mouseMovedY;

    //implemented with codes instead of characters, as it is the more robust approach
    protected int keyPressedCode;

    protected boolean isMousePressed;
    protected boolean isKeyPressed;
    protected boolean isMouseHeldDown;
    protected boolean isMouseDragged;

    protected Point endDrag, startDrag;

    public UserInput()
    {
        this.clear();
    }

    public void clear()
    {
        this.isKeyPressed = false;
        this.isMousePressed = false;
    }

    public int getMousePressedX()
    {
        return mousePressedX;
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

    public Point getEndDrag()
    {
        return this.endDrag;
    }

    public Point getStartDrag()
    {
        return this.startDrag;
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

    public void clearDragData()
    {
        this.isMouseDragged = false;
    }
}

