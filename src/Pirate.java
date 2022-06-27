import bagel.Image;

public class Pirate extends NPC{
    private final static int MAX_HEALTH = 45;
    private final static int ATTACK_ZONE = 100 ;
    private final static int DAMAGE_POINT = 10;
    private final static int COOL_DOWN = 3000;
    private final static String LOG_MSG1 = "Sailor inflicts ";
    private final static String LOG_MSG2 = " damage points on Pirate. Sailor’s current health: ";


    private final static Image[] PIRATE_IMAGE = {   new Image("res/pirate/pirateLeft.png"),
                                                    new Image("res/pirate/pirateRight.png"),
                                                    new Image("res/pirate/pirateHitLeft.png"),
                                                    new Image("res/pirate/pirateHitRight.png"),
                                                    new Image("res/pirate/pirateProjectile.png") };

    public Pirate(double xPos, double yPos){
        super(xPos, yPos, PIRATE_IMAGE, MAX_HEALTH, ATTACK_ZONE, DAMAGE_POINT, COOL_DOWN, LOG_MSG1, LOG_MSG2);
    }
}
