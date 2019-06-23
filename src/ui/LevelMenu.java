package ui;

import utilities.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class LevelMenu extends GUIMenu
{
    private final int BUTTON_WIDTH = 255;
    private final int BUTTON_HEIGHT = 60;
    private final int BUTTON_PADDING = 20;
    private final int WIDTH = 300;
    private final int HEIGHT = 500;

    public LevelMenu()
    {
        this.setPosition(Constants.SCREEN_WIDTH/2-WIDTH/2, 100);
        this.setSize(WIDTH,HEIGHT);

        LinkedList<String> levels = game.getLevelNames();

        int yPos = (int)this.getY()+BUTTON_PADDING;

        for(String level:levels)
        {
            MenuButton mb = new MenuButton((int)this.getX()+BUTTON_PADDING, yPos, BUTTON_WIDTH,BUTTON_HEIGHT);
            mb.setText(level);
            mb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    game.startLevelWithName(level);
                }
            });

            buttons.add(mb);

            yPos+= BUTTON_HEIGHT + BUTTON_PADDING;
        }
    }
}
