package utilities;

public final class Constants
{
    public final static int SCREEN_WIDTH = 1024;
    public final static int SCREEN_HEIGHT = 768;

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

    //HUD elements
    public final static double ANTSTOCKINDICATOR_X_POS = SCREEN_WIDTH - 120.0;
    public final static double ANTSTOCKINDICATOR_Y_POS = 15.0;
    public final static int ANTSTOCKINDICATOR_WIDTH = 100;
    public final static int ANTSTOCKINDICATOR_HEIGHT = 69;

    public final static double BUGQUEUE_X_POS = 20.0;
    public final static double BUGQUEUE_Y_POS = 15.0;
    public final static int BUGQUEUE_WIDTH = 320;
    public final static int BUGQUEUE_HEIGHT = 60;

    public final static String LOG_FILE_PATH = "antcraft.log";
}
