import bagel.*;
import java.util.ArrayList;

public class Sailor extends GameCharacter {
    private int maxHealth = 100;
    private int stepSize = 1;
    private final static Image SAILOR_IMAGE_LEFT = new Image("res/sailor/sailorLeft.png");
    private final static Image SAILOR_IMAGE_RIGHT = new Image("res/sailor/sailorRight.png");
    private final static Image SAILOR_IMAGE_HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");
    private final static Image SAILOR_IMAGE_HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private final static Font FONT = new Font("res/wheaton.otf", 30);
    private final static int HEALTH_X = 10;
    private final static int HEALTH_Y = 25;
    private int damagePoint = 15;
    private boolean takenTreasure = false;
    private final static int INVENTORY_X = 10;
    private final static int INVENTORY_Y = 50;
    private final static int TIMER_MAX = 299999999 ;
    private final static int TIMER_RESET = 0;
    private final static int REFRESH_RATE = 60;
    private final static int THREE_SECOND = 3000;
    private final static int ONE_SECOND = 1000;
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;

    // HELPER VARIABLE
    private int timer = 0;
    private int attackStartTime;
    private boolean isCoolDown = false;
    private boolean isAttacking = false;
    private ArrayList<Image> inventory = new ArrayList<Image>();
    private double oldX;
    private double oldY;


    public Sailor(double xPos, double yPos) {
        super(xPos, yPos);
        super.setImage(SAILOR_IMAGE_RIGHT);
        super.setMaxHealth(this.maxHealth);
        super.setStepSize(this.stepSize);
    }

    public void pickUp(Image item){
        this.inventory.add(item);
    }

    public void treasurePickUp(){
        this.takenTreasure = true;
    }

    public boolean hasTakenTreasure() {
        return takenTreasure;
    }

    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }

    public int getDamagePoint() {
        return damagePoint;
    }

    public boolean isAttacking(){
        return this.isAttacking;
    }

    public int attack(){
        return this.damagePoint;
    }

    private void setOldPoints() {
        oldX = super.getX();
        oldY = super.getY();
    }

    public void moveBack() {
        setX(oldX);
        setY(oldY);
    }

    // DISPLAY PICKED ITEMS ON TOP LEFT
    public void inventoryHandler(){
        double inventoryX = INVENTORY_X;
        double inventoryY = INVENTORY_Y;
        for(int i = 0; i < inventory.size(); ++i){
            inventory.get(i).drawFromTopLeft(inventoryX, inventoryY);
            inventoryY += INVENTORY_Y;
        }
    }

    public void update(Input input, ArrayList<StationaryEntity> stationaryEntity, double[] border) {
        double nextX = super.getX();
        double nextY = super.getY();

        // check input and update to screen
        if (input.isDown(Keys.UP)) {
            setOldPoints();
            nextY -= super.getStepSize();
        }
        else if (input.isDown(Keys.DOWN)) {
            setOldPoints();
            nextY += super.getStepSize();
        }
        else if (input.isDown(Keys.LEFT)) {
            setOldPoints();
            nextX -= super.getStepSize();
            if(isAttacking){
                setImage(SAILOR_IMAGE_HIT_LEFT);
            }
            else{
                super.setImage(SAILOR_IMAGE_LEFT);
            }
        }
        else if (input.isDown(Keys.RIGHT)) {
            setOldPoints();
            nextX += super.getStepSize();
            if(isAttacking){
                setImage(SAILOR_IMAGE_HIT_RIGHT);
            }
            else {
            super.setImage(SAILOR_IMAGE_RIGHT);
            }
        }
        else if (input.wasPressed(Keys.S) && !this.isCoolDown) {
            this.isAttacking = true;
            this.attackStartTime = timer;
            this.isCoolDown = true;
            if(getImage().equals(SAILOR_IMAGE_LEFT)){
                setImage(SAILOR_IMAGE_HIT_LEFT);
            }
            else if(getImage().equals(SAILOR_IMAGE_RIGHT)){
                setImage(SAILOR_IMAGE_HIT_RIGHT);
            }
        }

        // handling sailor behavior
        super.relocate(nextX, nextY);
        super.renderHealthPoints(HEALTH_X, HEALTH_Y, FONT);
        checkBorder(border);
        this.timerHandler(REFRESH_RATE);
        this.attackHandler();
        this.inventoryHandler();

        // resetting timer if needed
        if(timer == TIMER_MAX){
            timer = TIMER_RESET;
        }
        timer++;
    }

    // handling attacking logic
    public void attackHandler(){
        if(this.isAttacking){
            if (getImage().equals(SAILOR_IMAGE_LEFT)){
                setImage(SAILOR_IMAGE_HIT_LEFT);
            }
            else if (getImage().equals(SAILOR_IMAGE_RIGHT)){
                setImage(SAILOR_IMAGE_HIT_RIGHT);
            }
        }
        else{
            if (getImage().equals(SAILOR_IMAGE_HIT_LEFT)){
                setImage(SAILOR_IMAGE_LEFT);
            }
            else if (getImage().equals(SAILOR_IMAGE_HIT_RIGHT)){
                setImage(SAILOR_IMAGE_RIGHT);
            }

        }
    }


    // handling timer / counting logic
    private void timerHandler(double refreshRate){
        if(attackStartTime + REFRESH_RATE == timer){
            isAttacking = false;
        }
        if(attackStartTime + THREE_SECOND/ONE_SECOND * REFRESH_RATE == timer){
            isCoolDown = false;
        }
    }

    // check border
    protected void checkBorder(double[] border){
        if (super.getX() < border[LEFT] || border[RIGHT] < super.getX()
                || super.getY() < border[TOP] || border[BOTTOM] < super.getY()){
                moveBack();
        }
    }
}
