package utilities;

import java.awt.*;

public class DrawUtils
{
    public static int getTextCenterX(String text, Font font, Rectangle box)
    {
        int size = (int)(text.length()*font.getSize()/2.0);
        return (int)(box.getX()+(box.getWidth()/2.0 - size/2.0));
    }
}
