import bagel.Image;

public class Sword extends StationaryEntity{
    private final Image SWORD_IMAGE = new Image("res/items/sword.png");
    private final Image SWORD_ICON = new Image("res/items/swordIcon.png");
    private final static String LOG_MSG = "Sailor finds Sword. Sailor’s current damage points increased to ";
    private final static int DAMAGE_INCREASE = 15;
    private final static int TAKEN = -1;

    public Sword(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(SWORD_IMAGE);
    }

    public void getEffect(Sailor sailor){
        sailor.setDamagePoint(sailor.getDamagePoint() + DAMAGE_INCREASE);
        super.changeStatus(TAKEN);
        sailor.pickUp(SWORD_ICON);
        String LOG_MESSAGE = String.format(LOG_MSG + (int)sailor.getDamagePoint());
        System.out.println(LOG_MESSAGE);
    }


}
