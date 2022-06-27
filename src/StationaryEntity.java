import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class StationaryEntity extends GameEntity{
    private final static int WIDTH = 40;
    private final static int HEIGHT = 40;

    protected StationaryEntity(double xPos, double yPos){
        super(xPos,yPos);
    }

    public void checkCollision(Sailor sailor) {
        if (Math.abs(getX() - sailor.getX()) <= WIDTH
                && Math.abs(getY() - sailor.getY()) <= HEIGHT) {
            this.getEffect(sailor);
        }
    }
    public abstract void getEffect(Sailor sailor);


}
