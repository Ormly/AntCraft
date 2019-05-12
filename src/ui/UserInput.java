package ui;

public class UserInput
{
    int mousePressedX;
    int mousePressedY;
    int mouseMovedX;
    int mouseMovedY;
    int mouseButton;

    boolean isMouseEvent;
    boolean isKeyEvent;
    boolean isMousePressed;

    public UserInput()
    {
        this.clear();
    }

    public void clear()
    {
        this.isKeyEvent = false;
        this.isMouseEvent = false;
        this.isMousePressed = false;
    }
}
