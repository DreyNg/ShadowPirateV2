import bagel.Image;

public class Block extends StationaryEntity{
    private final Image BLOCK_IMAGE = new Image("res/block.png");


    public Block(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(BLOCK_IMAGE);
    }

    public void getEffect(Sailor sailor){
        sailor.moveBack();
    };




}
