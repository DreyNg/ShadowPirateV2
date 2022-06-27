import bagel.Image;

public class PirateProjectile extends Projectile{
    private final static Image PROJECTILE_IMAGE = new Image("res/pirate/pirateProjectile.png");
    private final static double STEP_SIZE = 0.4;
    private final static int DAMAGE = 10;
    private final static String LOG_MSG = "Pirate inflicts 10 damage points on Sailor. Sailor’s current health: ";


    public PirateProjectile(double x, double y, Sailor sailor){
        super(x, y, PROJECTILE_IMAGE, sailor, DAMAGE,STEP_SIZE, LOG_MSG);
    }
}
