package main;

import ui.Frame;
import utilities.Constants;

public class Main
{
    public static void main(String[] args)
    {
        Frame frame = new Frame(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.display();

        GameWorld game = new GameWorld();
        game.init();
        game.setGraphicsManager(frame.getPanel());
        game.run();
    }
}