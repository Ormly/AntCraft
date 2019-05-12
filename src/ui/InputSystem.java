package ui;

import interfaces.IInputSystem;

public class InputSystem implements IInputSystem
{

    private UserInput userInput;

    @Override
    public UserInput getUserInput()
    {
        return this.userInput;
    }
}
