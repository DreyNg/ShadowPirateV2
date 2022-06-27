import bagel.Image;
import java.util.ArrayList;

public class GameLevel {
    private final Image BACKGROUND_IMAGE;
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int USED = -1;


    public GameLevel(Image BACKGROUND, Sailor sailor, ArrayList<StationaryEntity> StationaryEntity,
                     ArrayList<NPC> gameCharacter, double[] border,
                     ArrayList<Projectile> projectiles) {

        this.BACKGROUND_IMAGE = BACKGROUND;
        this.BACKGROUND_IMAGE.draw(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);

        //initialise Stationary Entities
        for(int j = 0; j< StationaryEntity.size(); ++j){
            StationaryEntity.get(j).update();
            StationaryEntity.get(j).checkCollision(sailor);
        }

        // remove used items
        for(int j = 0; j< StationaryEntity.size(); ++j) {
            if (StationaryEntity.get(j).getStatus() == USED) {
                StationaryEntity.remove(j);
            }
        }

        //initialise NPC and check their logic
        for(int j = 0; j < gameCharacter.size(); ++j) {
            gameCharacter.get(j).checkBorder(border);
            gameCharacter.get(j).checkPlayerInZone(sailor);
            gameCharacter.get(j).checkCollision(StationaryEntity);
            gameCharacter.get(j).attack(sailor, projectiles);
            gameCharacter.get(j).getAttackHandler(sailor);
            gameCharacter.get(j).update();
        }

        // remove dead characters
        for(int j = 0; j < gameCharacter.size(); ++j) {
            if (gameCharacter.get(j).getStatus() == USED) {
                gameCharacter.remove(j);
            }
        }

        // rendering projectile and handle their logic
        for(int i = 0; i < projectiles.size(); ++i){
            projectiles.get(i).update();
            projectiles.get(i).checkCollision(sailor);
            projectiles.get(i).checkBorder(border);
            if(projectiles.get(i).getStatus() == USED){
                projectiles.remove(i);
            }
        }
    }
}

