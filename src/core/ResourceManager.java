package core;

import utilities.logging.Logger;
import utilities.logging.Logging;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ResourceManager
{
    private Logger logger = Logging.getLogger(this.getClass().getName());
    private static ResourceManager instance;
    private HashMap<String, BufferedImage> images;

    //singleton
    private ResourceManager()
    {
        this.images = new HashMap<>();
        this.loadAllResources();
    }

    public static ResourceManager getInstance(){
        return ResourceManager.instance;
    }

    public static void init()
    {
        if(ResourceManager.instance == null)
        {
            ResourceManager.instance = new ResourceManager();
        }
    }

    private void loadAllResources()
    {
        File res = new File("res/");
        for(File file: res.listFiles())
        {
            try
            {
                // strip suffix
                String name = file.getName().substring(0,file.getName().indexOf('.'));
                this.images.put(name, ImageIO.read(file));
            }
            catch(IOException e)
            {
                this.logger.error(e.getMessage());
            }
        }
    }

    public BufferedImage getImage(String name)
    {
        return this.images.get(name);
    }
}
