import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;


public abstract class Projectile extends GameEntity{
    private final double STEP_SIZE;
    private final int DAMAGE;
    private double radians;
    private double directionX;
    private double directionY;
    private final String LOG_MSG;
    private final static String DASH = "/";
    private final static int WIDTH = 20;
    private final static int HEIGHT = 20;
    private final static int USED = -1;
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;


    public Projectile(double x, double y, Image image, Sailor sailor, int damage, double STEP_SIZE, String LOG_MSG) {
        super(x, y);
        setImage(image);
        this.setDirectionAndRotation(sailor);
        this.DAMAGE = damage;
        this.STEP_SIZE = STEP_SIZE;
        this.LOG_MSG = LOG_MSG;

    }

    protected void setDirectionAndRotation(Sailor sailor){
        Point sailorPoint = new Point(sailor.getX(), sailor.getY());
        double length = new Point(getX(), getY()).distanceTo(sailorPoint);

        // CALCULATING THE VECTOR DIRECTION
        directionX = (sailorPoint.x - getX()) / length;
        directionY = (sailorPoint.y - getY()) / length;

        // CALCULATING THE RADIAN
        radians = Math.atan2(sailor.getY() - getY(), sailor.getX() - getX());
    }

    protected void relocate(double xPos, double yPos){
        super.setX(xPos);
        super.setY(yPos);
        DrawOptions rotation = new DrawOptions().setRotation(this.radians);
        super.getImage().drawFromTopLeft(xPos, yPos, rotation);
    }

    public void checkCollision(Sailor sailor){
        if(Math.abs(getX() - sailor.getX()) <= WIDTH
                && Math.abs(getY() - sailor.getY()) <= HEIGHT){
           sailor.getAttacked(this.DAMAGE);
            String LOG_MESSAGE = String.format(LOG_MSG + (int)sailor.getHealth() + DASH + sailor.getMaxHealth());
            System.out.println(LOG_MESSAGE);
           super.changeStatus(USED);
        }
    }

    protected void checkBorder(double[] border){
        if (super.getX() < border[LEFT] || border[RIGHT] < super.getX()
                || super.getY() < border[TOP] || border[BOTTOM] < super.getY()){
            super.changeStatus(USED);
        }
    }

    public void update(){
        double x = super.getX();
        double y = super.getY();

        x += directionX * STEP_SIZE;
        y += directionY * STEP_SIZE;
        this.relocate(x,y);

    }
}
