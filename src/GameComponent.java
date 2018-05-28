
import utilities.GameDriverV3;
import entities.Entity;
import entities.FlyingEnemy;
import entities.Player;
import utilities.LevelDriver;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import platforms.DisappearPlatform;
import platforms.LevelChangerPlatform;
import platforms.PlacementPlayerPlatform;
import platforms.SpawnerPlatform;
import utilities.FileDriverV2;
import utilities.SoundDriverV2;


/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author David
 */
public class GameComponent extends GameDriverV3 {

    private int frameWidth, frameHeight, levelWidth, levelHeight, bufferZone, plyrSrtXPos, plyrSrtYPos;
    //private int levelIndex;
    private Rectangle bg;
    private Rectangle[] healthBars, memoryBars;
    private Random rnd;
    private LevelDriver currentLevel;
    private FileDriverV2 saveFile;
    private String[] musicNames;
    private SoundDriverV2 levelMusic;
    private boolean[] progress;
    private int[] levelIndex = new int[1];
    private Player player;
    private ArrayList<List<Entity>> allEntities;
    private ArrayList<Entity> playerEntities;
    private ArrayList<Entity> enemyEntities;
    private ArrayList<Entity> platforms;
    
    BufferedImage backgroundTest, backgroundImage;

    public GameComponent(int fW, int fH) {
        frameWidth = fW;
        frameHeight = fH;
        bufferZone = 150;
        saveFile = new FileDriverV2("files/SaveFile.txt");
        progress = saveFile.parseBooleanArray(saveFile.readFile());
        musicNames = new String[5];
        for (int i = 0; i < musicNames.length; i++) {
            musicNames[i] = "audio/music/Music_Level_" + i + ".wav";
        }
        levelMusic = new SoundDriverV2(musicNames);

        for (boolean b : progress) {
            System.out.println(b);
        }


        levelIndex[0] = 0;

        bg = new Rectangle(0, 0, frameWidth, frameHeight);
        healthBars = new Rectangle[4];
        for (int i = 0; i < healthBars.length; i++) {
            healthBars[i] = new Rectangle(40 * i + 20, 20, 20, 40);
        }
        memoryBars = new Rectangle[2];
        for (int i = 0; i < memoryBars.length; i++) {
            memoryBars[i] = new Rectangle(40 * i + 20, 90, 20, 40);
        }
        rnd = new Random();
        
        backgroundTest = addImage("images/LevelGrid.png");
        backgroundImage = addImage("images/Background_0.png");

        setLevel(levelIndex[0], null);


    }

    public void setLevel(int index, Graphics2D win) {
        //try {
        if (allEntities != null) {
            allEntities.clear();
        }
        for (int i = 0; i < musicNames.length; i++) {
            levelMusic.stop(i);
        }
        System.gc();
        currentLevel = null;
        currentLevel = new LevelDriver(frameWidth, frameHeight, "files/Level_" + index + ".txt", "files/Messages_Level_" + index + ".txt", index);

        if (currentLevel != null) {
            plyrSrtXPos = currentLevel.getPlyrSrtXPos();
            plyrSrtYPos = currentLevel.getPlyrSrtYPos();
            levelWidth = currentLevel.getLevelWidth();
            levelHeight = currentLevel.getLevelHeight();
            allEntities = currentLevel.getAllEntities();
            player = currentLevel.getPlayer();
            backgroundImage = addImage("images/Background_" + index + ".png");
            levelMusic.loop(index);
            if (index > 0) {
                progress[(index * 2) - 2] = false;
                progress[(index * 2) - 1] = false;
            }
        }
        
        /*System.out.println("Level Width: " + levelWidth);
        System.out.println("Level Height: " + levelHeight);
        System.out.println("Level Picture Width: " + (levelWidth + frameWidth));
        System.out.println("Level Picture Height: " + (levelHeight + frameHeight));*/

    }

    public void draw(Graphics2D win) {
        //win.setColor(Color.BLACK);
        //win.fill(bg);
        win.drawImage(backgroundImage, 0, 0, null);
        //drawBackground(win);
        executeAct(win);
        cameraPosition();       

    }

    public void cameraPosition() {
        if (allEntities == null) {
            return;
        }
  
        int horOffset = plyrSrtXPos - (int) player.getXPos();
        int verOffset = plyrSrtYPos - (int) player.getYPos();

        for (int i = 0; i < allEntities.size(); i++) {
            for (int j = 0; j < allEntities.get(i).size(); j++) {
                Entity currentEntity = allEntities.get(i).get(j);
                currentEntity.setHitboxLocation((int) currentEntity.getXPos() + horOffset, (int) currentEntity.getYPos() + verOffset);
            }
        }
      
    }
    
    public void drawBackground(Graphics2D win){
        if(levelIndex[0] > 0){
            int bgXPos = (int)allEntities.get(0).get(0).getXPos() - frameWidth / 2;
            int bgYPos = (int)allEntities.get(0).get(0).getYPos() - frameHeight / 2;
            //win.drawImage(backgroundTest2, 0, 0, null);
            win.drawImage(backgroundTest.getSubimage(Math.abs(bgXPos), Math.abs(bgYPos), frameWidth, frameHeight), 0, 0, null);
            //win.drawImage(backgroundTest.getSubimage(Math.abs(bgXPos), Math.abs(bgYPos), frameWidth, frameHeight), 0, 0, null);
            //win.drawImage(backgroundTest.getSubimage(Math.abs(bgXPos), Math.abs(bgYPos), frameWidth, frameHeight), 0, 0, null);
            //win.drawImage(addImage("images/test.png"), 0, 0, null);
            //win.drawImage(backgroundTest.getSubimage(Math.abs(bgXPos), Math.abs(bgYPos), frameWidth, frameHeight), 4, 4, null);
        }
    }

    public void executeAct(Graphics2D win) {
        if (allEntities == null) {
            return;
        }
        for (int i = 0; i < allEntities.size(); i++) {
            for (int j = 0; j < allEntities.get(i).size(); j++) {
                Entity currentEntity = allEntities.get(i).get(j);
                if (currentEntity instanceof Player) {
                    player.act(keys, progress, levelIndex, allEntities);
                    player.drawEntity(win);
                    if (player.isInvincible() && levelMusic.isPlaying(levelIndex[0])) {
                        levelMusic.pause(levelIndex[0]);
                        levelMusic.play(4);
                    } else if (!player.isInvincible() && levelMusic.isPlaying(4)) {
                        levelMusic.resumeLoop(levelIndex[0]);
                        levelMusic.stop(4);
                    }
                    continue;
                } else if (currentEntity instanceof LevelChangerPlatform) {
                    if (((LevelChangerPlatform) currentEntity).getLevelCompleted()) {
                        saveFile.writeFile(progress, false);
                        setLevel(levelIndex[0], win);                        
                    }
                }
                boolean withinHor = (currentEntity.getXPos() > -bufferZone) && (currentEntity.getXPos() < (frameWidth + bufferZone));
                boolean withinVer = (currentEntity.getYPos() > -bufferZone) && (currentEntity.getYPos() < (frameHeight + bufferZone));
                if ((withinHor && withinVer) || (currentEntity instanceof DisappearPlatform)
                        || (currentEntity instanceof PlacementPlayerPlatform) || (currentEntity instanceof SpawnerPlatform)
                        || (currentEntity instanceof FlyingEnemy)) {
                    currentEntity.drawEntity(win);
                    currentEntity.act(allEntities);
                }
            }
        }
        //System.out.println(levelMusic.isPlaying(levelIndex[0]));
        for (int i = 0; i < allEntities.size(); i++) {
            for (int j = 0; j < allEntities.get(i).size(); j++) {
                Entity currentEntity = allEntities.get(i).get(j);
                if (!(currentEntity instanceof Player)) {
                    if (!currentEntity.isAlive()) {
                        allEntities.get(i).remove(j);
                        j--;
                    }
                } else {
                    if (!currentEntity.isAlive()) {
                        setLevel(levelIndex[0], win);

                    } else {
                        for (int k = 0; k < currentEntity.getHealth(); k++) {
                            win.setColor(new Color(255, 0, 0, 128));
                            win.fill(healthBars[k]);
                        }
                        if (levelIndex[0] > 0) {
                            for (int k = 0; k < 2; k++) {
                                if (progress[(levelIndex[0] * 2) - (k + 1)]) {
                                    win.setColor(new Color(0, 0, 255, 128));
                                    win.fill(memoryBars[k]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
