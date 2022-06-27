import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class GameEntity {
    private double xPos;
    private double yPos;
    private Image EntityImage;
    private int status = 0;


    protected GameEntity(double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    protected double getX(){
        return this.xPos;
    }

    protected double getY(){
        return this.yPos;
    }

    protected void setX(double xPos){
        this.xPos = xPos;
    }

    protected void setY(double yPos){
        this.yPos = yPos;
    }

    protected void setImage(Image characterImage){
        this.EntityImage = characterImage;
    }

    protected Image getImage(){
        return this.EntityImage;
    }





    public void update(){
        EntityImage.drawFromTopLeft(this.xPos, this.yPos);
    }



    protected Rectangle getBoundingBox(){
        return this.EntityImage.getBoundingBoxAt(new Point(this.xPos, this.yPos));
    }

    protected void changeStatus(int n){
        this.status = n;
    }

    public int getStatus(){
        return this.status;
    }


}
