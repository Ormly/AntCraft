package utilities;

import gameobjects.Bug;
import gameobjects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawUtils
{
    public static Random rand = new Random();

    public static int getTextCenterX(String text, Font font, Rectangle box)
    {
        int size = (int)(text.length()*font.getSize()/2.0);
        return (int)(box.getX()+(box.getWidth()/2.0 - size/2.0));
    }

    private static ArrayList<GameObject> generateBugs(int count, double radius)
    {
        Random rand = new Random();

        ArrayList<GameObject> bugs = new ArrayList();
        for(int i=0; i<count; ++i)
        {
            double theta = rand.nextDouble()*Math.PI*2;
            double x = Constants.NEST_X_POS + radius * Math.cos(theta);
            double y = Constants.NEST_Y_POS + radius * Math.sin(theta);
            //bugs.add(new Bug(x, y,10));
        }

        return bugs;
    }

    public static double getRandomDouble()
    {
        return rand.nextDouble();
    }

}
