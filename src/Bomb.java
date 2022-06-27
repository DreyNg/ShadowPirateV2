import bagel.Image;

public class Bomb extends StationaryEntity{
    private final Image BOMB_IMAGE = new Image("res/bomb.png");
    private final Image EXPLODE_IMAGE = new Image("res/explosion.png");
    private final static String LOG_MSG = "Bomb inflicts 10 damage points on Sailor. Sailor’s current health: ";
    private final static String DASH = "/";
    private final static int ONE_POINT_FIVE_SECOND = 1500;
    private final static int ONE_SECOND = 1000;
    private final static int REFRESH_RATE = 60;
    private final static int EXPLODED = -1;
    private final static int NORMAL = 0;
    private final static int EXPLODING = 1;
    private final static int BOMB_DAMAGE = 10;

    //helper variable
    private int timer = 0;
    private int explodeTime;


    public Bomb(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(BOMB_IMAGE);
    }

    private void explodeHandler(Sailor sailor){
        if(super.getStatus() == NORMAL){
            explodeTime = timer;    // store the time bomb explode
            sailor.getAttacked(BOMB_DAMAGE);
            setImage(EXPLODE_IMAGE);
            super.changeStatus(EXPLODING);

            // console log
            String LOG_MESSAGE = String.format(LOG_MSG + (int)sailor.getHealth() + DASH + sailor.getMaxHealth());
            System.out.println(LOG_MESSAGE);
        }
        sailor.moveBack();
    }

    @Override
    public void update(){
        super.getImage().drawFromTopLeft(getX(), getY());
        timer++;
        if(super.getStatus() == EXPLODING){
            // EXPLODING EFFECT HAPPENS FOR 1.5 SECONDS OR 30 FRAMES
            if(explodeTime + ONE_POINT_FIVE_SECOND/ONE_SECOND * REFRESH_RATE == timer) {
                super.changeStatus(EXPLODED);
            }
        }
    }

    public void getEffect(Sailor sailor){
        explodeHandler(sailor);
    }



}
