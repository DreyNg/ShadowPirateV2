import bagel.Image;

public class Potion extends StationaryEntity{
    private final Image POTION_IMAGE = new Image("res/items/potion.png");
    private final Image POTION_ICON = new Image("res/items/potionIcon.png");
    private final static String LOG_MSG = "Sailor finds Potion. Sailor’s current health: ";
    private final static String DASH = "/";
    private final static int TAKEN = -1;
    private final static int HEALTH_INCREASE = 25;



    public Potion(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(POTION_IMAGE);
    }

    public void getEffect(Sailor sailor){
        if(sailor.getHealth() + HEALTH_INCREASE >= sailor.getMaxHealth()){
            sailor.setMaxHealth(sailor.getMaxHealth());
        }
        else{
            sailor.increaseHealth(HEALTH_INCREASE);
        }
        String LOG_MESSAGE = String.format(LOG_MSG + (int)sailor.getHealth() + DASH + sailor.getMaxHealth());
        System.out.println(LOG_MESSAGE);
        super.changeStatus(TAKEN);
        sailor.pickUp(POTION_ICON);


    }

}
