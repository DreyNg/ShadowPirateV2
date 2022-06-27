import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;


public abstract class GameCharacter extends GameEntity{
    private int healthPoint;
    private int maxHealth;
    private double stepSize;

    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private final static int RED_BOUNDARY = 35;
    private final static int ORANGE_BOUNDARY = 65;


    protected GameCharacter(double xPos, double yPos){
        super(xPos,yPos);
        COLOUR.setBlendColour(GREEN);
    }

    protected abstract void checkBorder(double[] border);

    protected void setStepSize(double stepSize){
        this.stepSize = stepSize;
    }

    protected double getStepSize(){
        return this.stepSize;
    }

    protected double getHealth(){
        return this.healthPoint;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void increaseHealth(int health) {
        this.healthPoint += health;
    }

    protected void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
        this.healthPoint = maxHealth;
    }

    protected void getAttacked(int damage){
        this.healthPoint -= damage;
        if(healthPoint <= 0){
            healthPoint = 0;
        }
    }

    protected void relocate(double xPos, double yPos){
        super.setX(xPos);
        super.setY(yPos);
        super.getImage().drawFromTopLeft(xPos, yPos);
    }

    public void renderHealthPoints(double healthX, double healthY, Font font){
        double percentageHP = (this.getHealth()/this.maxHealth) * 100;
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
        } else{
            COLOUR.setBlendColour(GREEN);
        }
        font.drawString(Math.round(percentageHP) + "%", healthX, healthY, COLOUR);
    }




}
