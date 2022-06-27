import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

import java.util.ArrayList;

public abstract class NPC extends GameCharacter{
    /*
    *        Stands for non-player characters
    * */


    // Attribute variables
    private final double STEP_SIZE = (Math.random() * ((0.7 - 0.2) + 1)) + 0.2;;
    private final int ATTACK_ZONE ;
    private final Image IMAGE_LEFT;
    private final Image IMAGE_RIGHT;
    private final Image IMAGE_HIT_LEFT;
    private final Image IMAGE_HIT_RIGHT;
    private final Image PROJECTILE_IMAGE;
    private final int DAMAGE_POINT;
    private final int COOL_DOWN;

    // Magic number
    private final static int MOVE_RIGHT = 1;
    private final static int MOVE_LEFT = -1;
    private final static int MOVE_UP = 2;
    private final static int MOVE_DOWN = -2;
    private final static Font FONT = new Font("res/wheaton.otf", 15);
    private final static int ONE_SECOND = 1000;
    private final static int ONE_POINT_FIVE_SECOND = 1500;
    private final static int TIMER_MAX = 299999999;
    private final static int TIMER_RESET = 0;
    private final static String DASH = "/";
    private final String LOG_MSG1;
    private final String LOG_MSG2;
    private final int WIDTH = 40;
    private final int HEIGHT = 40;
    private final int MIN_HEALTH = 0;
    private final int DEAD = -1;
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;
    private final int PIRATE_ATTACK_ZONE = 100;
    private final int BB_ATTACK_ZONE = 200;
    private final int REFRESH_RATE = 60;
    private final int Y_UPPER = -6;

    // Helper Variables
    private int direction = (new Random().nextDouble() > 0.18) ? 1 : 2;;
    private int changeDirection = -1;
    private boolean playerInZone = false;
    private int timer = 0;
    private int invincibleStartTime;
    private boolean isCoolDown = false;
    private boolean isInvincible = false;


    // Constructor
    public NPC(double xPos, double yPos, Image[] image, int MAX_HEALTH, int ATTACK_ZONE,
               int DAMAGE_POINT, int COOL_DOWN, String LOG_MSG1, String LOG_MSG2) {
        super(xPos, yPos);
        this.ATTACK_ZONE = ATTACK_ZONE;
        this.IMAGE_LEFT = image[0];
        this.IMAGE_RIGHT = image[1];
        this.IMAGE_HIT_LEFT = image[2];
        this.IMAGE_HIT_RIGHT = image[3];
        this.PROJECTILE_IMAGE = image[4];
        this.DAMAGE_POINT = DAMAGE_POINT;
        this.COOL_DOWN = COOL_DOWN;
        this.LOG_MSG1 = LOG_MSG1;
        this.LOG_MSG2 = LOG_MSG2;

        super.setImage(IMAGE_RIGHT);
        super.setMaxHealth(MAX_HEALTH);
        super.setStepSize(STEP_SIZE);
    }

    // Logic of invincible
    private void invincibleHandler(){
        if(this.isInvincible){
            if (getImage().equals(IMAGE_LEFT)){
                setImage(IMAGE_HIT_LEFT);
            } else if (getImage().equals(IMAGE_RIGHT)){
                setImage(IMAGE_HIT_RIGHT);
            }
        } else{
            if (getImage().equals(IMAGE_HIT_LEFT)){
                setImage(IMAGE_LEFT);
            } else if (getImage().equals(IMAGE_HIT_RIGHT)){
                setImage(IMAGE_RIGHT);
            }
        }
    }

    // Handle the logic of getting attacked
    public void getAttackHandler(Sailor sailor){
        // if invincible then do nothing
        if(!sailor.isAttacking() || isInvincible){return;}

        // checking collision with the player
        if(Math.abs(getX() - sailor.getX()) <= WIDTH
                && Math.abs(getY() - sailor.getY()) <= HEIGHT){
            super.getAttacked(sailor.attack());
            String LOG_MESSAGE = String.format(LOG_MSG1 + sailor.getDamagePoint()
                    + LOG_MSG2 + (int)super.getHealth() + DASH + super.getMaxHealth());
            System.out.println(LOG_MESSAGE);
            this.invincibleStartTime = timer;
            isInvincible = true;
        }

        // remove character if dead
        if(super.getHealth() <= MIN_HEALTH ){
            super.changeStatus(DEAD);
        }
    }

    // check collision with stationary entity and change direction
    public void checkCollision(ArrayList<StationaryEntity> stationaryEntity){
        Image characterImage = super.getImage();
        double xPos = getX();
        double yPos = getY();
        Rectangle characterBox = characterImage.getBoundingBoxAt(new Point(xPos, yPos));
        for(int i = 0; i < stationaryEntity.size(); ++i){
            StationaryEntity current = stationaryEntity.get(i);
            Rectangle blockBox = current.getBoundingBox();
            if(characterBox.intersects(blockBox)){
                this.direction *= changeDirection;
            }
        }
    }

    // check collision with border and change direction
    protected void checkBorder(double[] border){
        if (super.getX() < border[LEFT] || border[RIGHT] < super.getX()
                || super.getY() < border[TOP] || border[BOTTOM] < super.getY()){
            this.direction *= changeDirection;
        }
    }

    // check if player is in zone to attack
    protected void checkPlayerInZone(Sailor sailor){
        if(Math.abs(getX() - sailor.getX()) <= ATTACK_ZONE
                && Math.abs(getY() - sailor.getY()) <= ATTACK_ZONE){
            this.playerInZone = true;
            return;
        }
        this.playerInZone = false;
    }

    // Attack logic handler
    protected void attack(Sailor sailor, ArrayList<Projectile> projectiles){
        if(this.playerInZone && this.isCoolDown) {
            if(ATTACK_ZONE == PIRATE_ATTACK_ZONE){
                projectiles.add(new PirateProjectile(getX(), getY(), sailor));
            }
            else if(ATTACK_ZONE == BB_ATTACK_ZONE){
                projectiles.add(new BBProjectile(getX(), getY(), sailor));
            }
        }
    }

    // timer handler for cool down and invincible time
    private void timerHandler(double refreshRate){

        // timer variable count each screen frame rate -> if 60 frames counted -> one second for 60hz refresh rate
        // cool down ends in every cool down seconds
        if(timer/refreshRate * ONE_SECOND % COOL_DOWN == 0){
            isCoolDown = true;
        }
        else{
            isCoolDown = false;
        }

        // invincible happens for 1.5 seconds
        if( invincibleStartTime + ONE_POINT_FIVE_SECOND/ONE_SECOND * refreshRate == timer){
            isInvincible = false;
        }
    }

    // update the state of character and put on screen
    @Override
    public void update(){
        double pirateX = super.getX();
        double pirateY = super.getY();

        // handling directions
        if(this.direction == MOVE_RIGHT){
            pirateX += super.getStepSize();
            if(isInvincible) {
                setImage(IMAGE_HIT_RIGHT);
            }
            else {
                super.setImage(IMAGE_RIGHT);
            }
        }
        else if(this.direction == MOVE_LEFT){
            pirateX -= super.getStepSize();
            if(isInvincible) {
                setImage(IMAGE_HIT_LEFT);
            }
            else {
                super.setImage(IMAGE_LEFT);
            }
        }
        else if(this.direction == MOVE_UP){
            pirateY -= super.getStepSize();
        }
        else if(this.direction == MOVE_DOWN){
            pirateY += super.getStepSize();
        }

        // handling moving logic, rendering health, invincible logic and cool down logic
        super.relocate(pirateX,pirateY);
        super.renderHealthPoints(super.getX(), super.getY() + Y_UPPER, FONT);
        this.timerHandler(REFRESH_RATE);
        this.invincibleHandler();

        // reset timer
        if(timer == TIMER_MAX){
            timer = TIMER_RESET;
        }
        timer++;
    }
}

