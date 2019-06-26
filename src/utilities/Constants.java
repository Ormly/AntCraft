package utilities;

import java.awt.*;

public final class Constants
{
    public final static int SCREEN_WIDTH = 1024;
    public final static int SCREEN_HEIGHT = 768;

    public final static int MENU_HEIGHT = (int)(((double)SCREEN_HEIGHT)*0.65);
    public final static int MENU_WIDTH = (int)(((double)SCREEN_WIDTH)*0.35);
    public final static int MENU_X_POS = (int)(SCREEN_WIDTH/2.0 - MENU_WIDTH/2.0);
    public final static int MENU_Y_POS = (int)(SCREEN_HEIGHT/2.0 - MENU_HEIGHT/2.0);

    public final static double NEST_X_POS = SCREEN_WIDTH/2.0;
    public final static double NEST_Y_POS = SCREEN_HEIGHT/2.0;

    public final static int ANT_RADIUS = 15;
    public final static int LADYBUG_RADIUS = 22;
    public final static int SPIDER_RADIUS = 17;
    public final static int DONG_RADIUS = 22;
    public final static int POWERUP_RADIUS = 15;

    public final static double MAX_ANT_HEALTH = 80.0;
    public final static double MAX_NEST_HEALTH = 5000.0;
    public final static double MAX_LADYBUG_HEALTH = 350.0;
    public final static double MAX_SPIDER_HEALTH = 650.0;
    public final static double MAX_DONG_HEALTH = 1500.0;

    public final static double ANT_SPEED = 100.0;
    public final static double LADYBUG_SPEED = 27.0;
    public final static double SPIDER_SPEED = 35.0;
    public final static double DONG_SPEED = 15.0;

    public final static double ANT_DAMAGE_FACTOR = 20.0;
    public final static double LADYBUG_DAMAGE_FACTOR = 27.0;
    public final static double SPIDER_DAMAGE_FACTOR = 40.0;
    public final static double DONG_DAMAGE_FACTOR = 50.0;
    public final static double POWERUP_DAMAGE_FACTOR = 5.0;
    public final static double POWERUP_DAMAGE_DURATION = 10.0;

    public final static double ANT_HEALING_FACTOR = 20.0;
    public final static double POWERUP_HEALING_FACTOR = 100.0;
    public final static double ANT_HEALING_FREQ = 1.0;

    public final static double ANT_ATTACK_FREQ = 1.0;
    public final static double LADYBUG_ATTACK_FREQ = 1.0;
    public final static double SPIDER_ATTACK_FREQ = 2.0;
    public final static double DONG_ATTACK_FREQ = 3.0;

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
    public final static String LEVEL_FILE_PATH = "levels.json";

    public final static Color MENU_COLOR = new Color(188,123,95);
    public final static Color BUTTON_COLOR = new Color( 225, 164, 110);
    public final static Color TEXT_COLOR = new Color(33,62, 74);
}
