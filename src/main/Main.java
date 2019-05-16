package main;

import ui.Frame;
import utilities.Constants;
import utilities.logging.AbstractLogger;
import utilities.logging.ConsoleLoggerFactory;
import utilities.logging.FileLoggerFactory;
import utilities.logging.Logging;

public class Main
{
    public static void main(String[] args)
    {
        Logging.setLoggerFactory(new FileLoggerFactory(Constants.LOG_FILE_PATH));
        AbstractLogger logger = Logging.getLogger(Main.class.getName());
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
