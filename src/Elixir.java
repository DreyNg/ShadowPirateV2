import bagel.Image;

public class Elixir extends StationaryEntity{
    private final Image ELIXIR_IMAGE = new Image("res/items/elixir.png");
    private final Image ELIXIR_ICON = new Image("res/items/elixirIcon.png");
    private final static String LOG_MSG = "Sailor finds Elixir. Sailor’s current health: ";
    private final static String DASH = "/";
    private final static int ELIXIR_POINT = 35;
    private final static int TAKEN = -1;


    public Elixir(double xPos, double yPos){
        super(xPos, yPos);
        super.setImage(ELIXIR_IMAGE);
    }

    public void getEffect(Sailor sailor){
        sailor.setMaxHealth(sailor.getMaxHealth() + ELIXIR_POINT);
        super.changeStatus(TAKEN);
        sailor.pickUp(ELIXIR_ICON);
        String LOG_MESSAGE = String.format(LOG_MSG + (int)sailor.getHealth() + DASH + sailor.getMaxHealth());
        System.out.println(LOG_MESSAGE);
    }


}
