package main;

import core.ResourceManager;
import ui.Frame;
import utilities.Constants;
import utilities.logging.Logger;
import utilities.logging.ConsoleLoggerFactory;
import utilities.logging.Logging;
import utilities.logging.QuiteLoggerFactory;

public class Main
{
    public static void main(String[] args)
    {
        Logging.setLoggerFactory(new ConsoleLoggerFactory());
        Logger logger = Logging.getLogger(Main.class.getName());

        logger.info("Starting Game");

        ResourceManager.init();

        Frame frame = new Frame(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.display();

        GameWorld game = new GameWorld();

        game.setGraphicsSystem(frame.getPanel());
        game.init();
        game.run();

        logger.info("Ending Game");
    }
}
