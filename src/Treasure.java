import bagel.Image;

public class Treasure extends StationaryEntity{
    private final Image TREASURE_IMAGE = new Image("res/treasure.png");
    private final static int TAKEN = -1;


    public Treasure(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(TREASURE_IMAGE);
    }

    public void getEffect(Sailor sailor){
        super.changeStatus(TAKEN);
        sailor.treasurePickUp();
    }
}
