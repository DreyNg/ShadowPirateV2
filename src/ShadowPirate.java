import bagel.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * @HungLongNguyen
 */
public class ShadowPirate extends AbstractGame {
        public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    // magic variables
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    private final Image BACKGROUND_IMAGE_LEVEL0 = new Image("res/background0.png");
    private final Image BACKGROUND_IMAGE_LEVEL1 = new Image("res/background1.png");
    private final static int LADDER_X = 990;
    private final static int LADDER_Y = 630;
    private final Font MESSAGE = new Font("res/wheaton.otf", 55);
    private final static String WIN_MSG = "CONGRATULATION!";
    private final static String LOSE_MSG = "GAME OVER";
    private final static String START_TEXT = "PRESS SPACE TO START";
    private final static String LEVEL_COMPLETE = "LEVEL COMPLETE!";
    private final static String INSTRUCTION_TEXT = "PRESS S TO ATTACK";
    private final static String LEVEL0_PURPOSE_TEXT = "USE ARROW KEYS TO FIND LADDER";
    private final static String LEVEL1_PURPOSE_TEXT = "FIND THE TREASURE";
    private final static int HAS_LOST = 3;
    private final static int HAS_NOT_STARTED = -1;
    private final static int LEVEL0 = 0;
    private final static int LEVEL0_COMPLETE = -2;
    private final static int LEVEL1_START_SCREEN = -3;
    private final static int LEVEL1 = 1;
    private final static int HAS_WON = 2;
    private final static int MIN_HEALTH = 0;
    private final static int TEXT_Y = 402;
    private final static int NEXT = 70;
    private final static int REFRESH_RATE = 60;
    private final static int THREE_SECOND = 3;
    private final static int LEFT = 0;
    private final static int TOP = 1;
    private final static int RIGHT = 2;
    private final static int BOTTOM = 3;

    // helper variables
    private int timer;
    private int readHelper = 0;
    private int gameStage = -1;

    // sailors
    private Sailor sailorLevel0;
    private Sailor sailorLevel1;

    // other game entities for level 0
    private ArrayList<StationaryEntity> stationaryEntityLevel0 = new ArrayList<StationaryEntity>();
    private ArrayList<NPC> gameCharacterLevel0 = new ArrayList<NPC>();
    private ArrayList<Projectile> projectilesLevel0 = new ArrayList<Projectile>();
    private double[] borderLevel0 = new double[5];

    // other game entities for level 1
    private ArrayList<StationaryEntity> stationaryEntityLevel1 = new ArrayList<StationaryEntity>();
    private ArrayList<NPC> gameCharacterLevel1 = new ArrayList<NPC>();
    private ArrayList<Projectile> projectilesLevel1 = new ArrayList<Projectile>();
    private double[] borderLevel1 = new double[5];



    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.CSVHandler();
        game.run();
    }
    /**
     * Method used to read file and create objects
     */

    private void CSVHandler(){
        this.readCSV("res/level0.csv", stationaryEntityLevel0, gameCharacterLevel0, borderLevel0);
        this.readCSV("res/level1.csv", stationaryEntityLevel1, gameCharacterLevel1, borderLevel1);
    }

    private void readCSV(String fileName, ArrayList<StationaryEntity> stationaryEntity,
                         ArrayList<NPC> gameCharacter, double[] border){
        String inputLine;
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while((inputLine = br.readLine()) != null){
                String[] inputValues = inputLine.split(",");
                if(inputValues[0].equals("Sailor") && readHelper == LEVEL0){
                    this.sailorLevel0 = new Sailor(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2]));
                }
                else if(inputValues[0].equals("Sailor") && readHelper == LEVEL1){
                    this.sailorLevel1 = new Sailor(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2]));
                }
                else if(inputValues[0].equals("Block") && readHelper == LEVEL0) {
                    stationaryEntity.add(new Block(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Block") && readHelper == LEVEL1) {
                    stationaryEntity.add(new Bomb(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }

                else if(inputValues[0].equals("Elixir")) {
                    stationaryEntity.add(new Elixir(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Treasure")) {
                    stationaryEntity.add(new Treasure(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Sword")) {
                    stationaryEntity.add(new Sword(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Potion")) {
                    stationaryEntity.add(new Potion(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Pirate")){
                    gameCharacter.add(new Pirate(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("Blackbeard")){
                    gameCharacter.add(new Blackbeard(Double.parseDouble(inputValues[1]),
                            Double.parseDouble(inputValues[2])));
                }
                else if(inputValues[0].equals("TopLeft")){
                    border[LEFT] = Double.parseDouble(inputValues[1]);
                    border[TOP] = Double.parseDouble(inputValues[2]);
                }
                else if(inputValues[0].equals("BottomRight")){
                    border[RIGHT] = Double.parseDouble(inputValues[1]);
                    border[BOTTOM] = Double.parseDouble(inputValues[2]);
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        this.readHelper++;
    }

    public boolean passedLevel0(Sailor sailor){
        if(sailor.getX() >= LADDER_X && sailor.getY() > LADDER_Y){
            return true;
        }
        return false;
    }

    public int getMsgX(String text){
        return (int) ((WINDOW_WIDTH - MESSAGE.getWidth(text))/2);
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if(sailorLevel0.getHealth() <= MIN_HEALTH || sailorLevel1.getHealth() <= MIN_HEALTH){
            gameStage = HAS_LOST;
        }
        if (gameStage == HAS_NOT_STARTED) {
            MESSAGE.drawString(START_TEXT, getMsgX(START_TEXT), TEXT_Y);
            MESSAGE.drawString(INSTRUCTION_TEXT, getMsgX(INSTRUCTION_TEXT), TEXT_Y + NEXT);
            MESSAGE.drawString(LEVEL0_PURPOSE_TEXT, getMsgX(LEVEL0_PURPOSE_TEXT), TEXT_Y + NEXT + NEXT);

            if(input.wasPressed(Keys.SPACE)){
                this.gameStage = LEVEL0;
            }
        }
        else if(gameStage == LEVEL0) {
            GameLevel currentLevel = new GameLevel(BACKGROUND_IMAGE_LEVEL0, sailorLevel0,
                    stationaryEntityLevel0, gameCharacterLevel0, borderLevel0, projectilesLevel0);

            sailorLevel0.update(input, stationaryEntityLevel0, borderLevel0);
            if(this.passedLevel0(sailorLevel0)){
                this.gameStage = LEVEL0_COMPLETE;
            }
        }
        if (gameStage == LEVEL0_COMPLETE) {
            timer++;
            MESSAGE.drawString(LEVEL_COMPLETE, getMsgX(LEVEL_COMPLETE), TEXT_Y);
            if(timer >= THREE_SECOND*REFRESH_RATE){
                this.gameStage = LEVEL1_START_SCREEN;
            }
        }
            if (gameStage == LEVEL1_START_SCREEN) {
            MESSAGE.drawString(START_TEXT, getMsgX(START_TEXT), TEXT_Y);
            MESSAGE.drawString(INSTRUCTION_TEXT, getMsgX(INSTRUCTION_TEXT), TEXT_Y + NEXT);
            MESSAGE.drawString(LEVEL1_PURPOSE_TEXT, getMsgX(LEVEL1_PURPOSE_TEXT), TEXT_Y + NEXT + NEXT);

            if(input.wasPressed(Keys.SPACE)){
                this.gameStage = LEVEL1;
            }
        }
        else if(gameStage == LEVEL1) {
            GameLevel currentLevel = new GameLevel(BACKGROUND_IMAGE_LEVEL1,
                    sailorLevel1, stationaryEntityLevel1, gameCharacterLevel1, borderLevel1, projectilesLevel1);

            sailorLevel1.update(input, stationaryEntityLevel1, borderLevel1);
            if(sailorLevel1.hasTakenTreasure()){
                this.gameStage = HAS_WON;
            }
        }
        else if(gameStage == HAS_WON) {
            MESSAGE.drawString(WIN_MSG, getMsgX(WIN_MSG), TEXT_Y);
        }
        else if(gameStage == HAS_LOST) {
            MESSAGE.drawString(LOSE_MSG, getMsgX(LOSE_MSG), TEXT_Y);
        }

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }

}
