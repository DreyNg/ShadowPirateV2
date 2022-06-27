import bagel.Image;

public class Blackbeard extends NPC{
    private final static int MAX_HEALTH = 90;
    private final static int ATTACK_ZONE = 200 ;
    private final static int DAMAGE_POINT = 20;
    private final static int COOL_DOWN = 1500;
    private final static String LOG_MSG1 = "Sailor inflicts ";
    private final static String LOG_MSG2 = " damage points on Blackbeard. Sailor’s current health: ";
    private final static Image[] BB_IMAGE = {   new Image("res/blackbeard/blackbeardLeft.png"),
            new Image("res/blackbeard/blackbeardRight.png"),
            new Image("res/blackbeard/blackbeardHitLeft.png"),
            new Image("res/blackbeard/blackbeardHitRight.png"),
            new Image("res/blackbeard/blackbeardProjectile.png") };


    public Blackbeard(double xPos, double yPos){
        super(xPos, yPos, BB_IMAGE, MAX_HEALTH, ATTACK_ZONE, DAMAGE_POINT, COOL_DOWN, LOG_MSG1, LOG_MSG2);
    }
}
