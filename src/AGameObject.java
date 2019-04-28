import java.awt.*;

public abstract class AGameObject {
    protected double xPos;
    protected double yPos;
    protected int radius;
    protected Color color = new Color(96,96,255);

    public AGameObject(double xPos, double yPos, int radius){
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
    }

    public double getX(){ return this.xPos; }
    public double getY(){ return this.yPos; }
    public int getRadius(){ return this.radius; }
    public Color getColor(){ return this.color; }
}
