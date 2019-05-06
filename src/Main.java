public class Main
{
    public static void main(String[] args)
    {

        ILogger logger = new FileLogger(Main.class.getName());
        logger.info("Starting Game");
        Frame frame = new Frame(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.display();

        Game game = new Game();
        game.init();
        game.setGraphicsManager(frame.getPanel());
        game.run();
        logger.info("Ending Game");

    }
}
