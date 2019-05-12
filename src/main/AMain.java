package main;

import ui.BFrame;
import utilities.AConstants;

public class AMain
{
    public static void main(String[] args)
    {
        BFrame frame = new BFrame(AConstants.SCREEN_WIDTH, AConstants.SCREEN_HEIGHT);
        frame.display();

        GameWorld game = new GameWorld();
        game.init();
        game.setGraphicsManager(frame.getPanel());
        game.run();
    }
}
