package ui;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Icon
{
    private BufferedImage image;
    private AffineTransform transform;
    private double translateXY;
    private double scale;

    public Icon(BufferedImage image, double translateXY, double scale)
    {
        this.image = image;
        this.translateXY = translateXY;
        this.scale = scale;
        this.transform = new AffineTransform();
    }

    public void update(double posX, double posY, double rotate)
    {
        this.transform.setToTranslation(posX,posY);
        this.transform.translate(this.translateXY,this.translateXY);
        this.transform.scale(this.scale,this.scale);
        this.transform.rotate(rotate+Math.PI/2.0,
                              this.image.getWidth()/2,
                              this.image.getHeight()/2);
    }

    public BufferedImage getImage()
    {
        return this.image;
    }

    public AffineTransform getTransform()
    {
        return this.transform;
    }
}
