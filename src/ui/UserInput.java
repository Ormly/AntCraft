package ui;

public class UserInput
{
    private int mousePressedX;
    private int mousePressedY;
    private int mousePressedValue;

    private int mouseMovedX;
    private int mouseMovedY;

    private char keyPressedValue;

    private boolean isMousePressed;
    private boolean isKeyPressed;
    private boolean isMouseHeldDown;

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

    public void setMousePressedX(int mousePressedX)
    {
        this.mousePressedX = mousePressedX;
    }

    public int getMousePressedY()
    {
        return mousePressedY;
    }

    public void setMousePressedY(int mousePressedY)
    {
        this.mousePressedY = mousePressedY;
    }

    public int getMouseMovedX()
    {
        return mouseMovedX;
    }

    public void setMouseMovedX(int mouseMovedX)
    {
        this.mouseMovedX = mouseMovedX;
    }

    public int getMouseMovedY()
    {
        return mouseMovedY;
    }

    public void setMouseMovedY(int mouseMovedY)
    {
        this.mouseMovedY = mouseMovedY;
    }

    public int getMousePressedValue() { return mousePressedValue; }

    public void setMousePressedValue(int mousePressedValue)
    {
        this.mousePressedValue = mousePressedValue;
    }

    public boolean isMousePressed() { return isMousePressed; }

    public void setMousePressed(boolean isMousePressed) { this.isMousePressed = isMousePressed; }

    public boolean isMouseHeldDown() { return this.isMouseHeldDown; }

    public void setMouseHeldDown(boolean isMouseHeldDown) { this.isMouseHeldDown = isMouseHeldDown; }

    public int getKeyPressedValue()
    {
        return this.keyPressedValue;
    }

    public void setKeyPressedValue(char keyPressedValue)
    {
        this.keyPressedValue = keyPressedValue;
    }

    public boolean isKeyPressed()
    {
        return isKeyPressed;
    }

    public void setIsKeyPressed(boolean isKeyPressed)
    {
        this.isKeyPressed = isKeyPressed;
    }
}

