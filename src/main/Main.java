package main;

import ui.Frame;
import utilities.Constants;

public class Main
{
    public static void main(String[] args)
    {

        ILogger logger = new FileLogger(Main.class.getName());
        logger.info("Starting Game");
        Frame frame = new Frame(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.display();

        GameWorld game = new GameWorld();

        game.setGraphicsSystem(frame.getPanel());
        game.init();
        game.run();
        logger.info("Ending Game");

    }
}
