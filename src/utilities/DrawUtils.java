package utilities;

import gameobjects.Bug;
import gameobjects.GameObject;

import java.awt.*;
import java.util.*;
import java.util.List;
import static java.util.Collections.reverseOrder;

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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
