import bagel.Image;

public class BBProjectile extends Projectile{
    private final static Image PROJECTILE_IMAGE = new Image("res/blackbeard/blackbeardProjectile.png");
    private final static double STEP_SIZE = 0.8;
    private final static int DAMAGE = 20;
    private final static String LOG_MSG = "Blackbeard inflicts 20 damage points on Sailor. Sailor’s current health: ";


    public BBProjectile(double x, double y, Sailor sailor){
        super( x, y, PROJECTILE_IMAGE, sailor, DAMAGE,STEP_SIZE, LOG_MSG);
    }
}
