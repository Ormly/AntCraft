package utilities;

public final class Constants
{
    public final static int SCREEN_WIDTH = 1024;
    public final static int SCREEN_HEIGHT = 768;

    public final static int MENU_HEIGHT = (int)(((double)SCREEN_HEIGHT)*0.50);
    public final static int MENU_WIDTH = (int)(((double)SCREEN_WIDTH)*0.35);
    public final static int MENU_X_POS = (int)(SCREEN_WIDTH/2.0 - MENU_WIDTH/2.0);
    public final static int MENU_Y_POS = (int)(SCREEN_HEIGHT/2.0 - MENU_HEIGHT/2.0);

    public final static double NEST_X_POS = SCREEN_WIDTH/2.0;
    public final static double NEST_Y_POS = SCREEN_HEIGHT/2.0;

    public final static double MAX_ANT_HEALTH = 100.0;
    public final static double MAX_NEST_HEALTH = 5000.0;
    public final static double MAX_BUG_HEALTH = 1000.0;
    public final static double ANT_DAMAGE_FACTOR = 50.0;
    public final static double BUG_DAMAGE_FACTOR = 20.0;
    public final static double ANT_HEALING_FACTOR = 20.0;

    public final static double ANT_ATTACK_FREQ = 1.0;
    public final static double ANT_HEALING_FREQ = 1.0;
    public final static double BUG_ATTACK_FREQ = 2.0;

    public final static String LOG_FILE_PATH = "antcraft.log";
    public final static String LEVEL_FILE_PATH = "levels.json";
}
