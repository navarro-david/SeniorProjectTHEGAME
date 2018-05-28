/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import entities.Entity;
import entities.Player;
import java.util.ArrayList;
import java.util.List;
import platforms.*;

/**
 *
 * @author David
 */

/* Block Notes:
 * 
 * 0 - Air Block
 * 1 - Solid Block
 * 2 - Spike Block
 * 3 - Fall Away Block
 * 4 - Bouncy Block
 * 5 - Conveyor Left Block
 * 6 - Conveyor Right Block
 * 7 - Spawner Block
 * 8 - Spawner Power Up Block
 * 9 - Water Block
 * 10 - Disappear Block
 * 11 - No Player Block
 * 12 - Spawner Bullet Block
 * 13 - Breakable Block
 * 14 - Power Up Health Block
 * 15 - Power Up Projectile Block
 * 16 - Action Block
 * 17 - Placement Flying Enemy Block
 * 18 - Placement Talkable Character Block
 * 19 - Placement Memory Character Block
 * 20 - Placement Player Block
 * 21 - Level Changer Block
 * 22 - Placement Basic Enemy Block
 * 23 - Power Up Invincible Block
 * 
 */
public class LevelDriver {

    protected int frameWidth, frameHeight, levelWidth, levelHeight, blockSize, messageIndex, levelIndex;
    protected int plyrSrtXPos, plyrSrtYPos, plyrWidth, plyrHeight;
    protected String levelFileName, messagesFileName;
    protected FileDriverV2 file;
    protected int[][] levelData;
    protected String[][] levelMessages;
    protected Player player;
    protected ArrayList<List<Entity>> allEntities;
    protected ArrayList<Entity> playerEntities;
    protected ArrayList<Entity> enemyEntities;
    protected ArrayList<Entity> platformEntities;
    protected ArrayList<Entity> transparentPlatformEntities;

    public LevelDriver(int frmWid, int frmHght, String lvlFlNm, int lvlInx) {
        this(frmWid, frmHght, lvlFlNm, "No Message File Found", lvlInx);
    }

    public LevelDriver(int frmWid, int frmHght, String lvlFlNm, String msgFlNm, int lvlInx) {

        frameWidth = frmWid;
        frameHeight = frmHght;
        levelFileName = lvlFlNm;
        messagesFileName = msgFlNm;
        blockSize = 80;
        levelIndex = lvlInx;
        if (levelIndex > 0) {
            levelIndex = 0;
        } else {
            levelIndex = 1;
        }

        plyrWidth = blockSize / 2;
        plyrHeight = blockSize - (blockSize / 3);
        plyrSrtXPos = (frameWidth / 2) - (plyrWidth / 2);
        plyrSrtYPos = (frameHeight / 2) - (plyrHeight / 2);


        file = new FileDriverV2(levelFileName);
        levelData = file.parseInt2DArray(file.readFile());
        if (!messagesFileName.equals("No Message File Found")) {
            file.changeFile(messagesFileName);
            levelMessages = file.splitString(file.readFile());
        } else {
            levelMessages = new String[1][3];
            levelMessages[0][0] = "Cats are Cool!";
            levelMessages[0][1] = "SMH!";
            levelMessages[0][2] = "Why not RECURSION?!";
        }


        /*for(String[] x : levelMessages){
         for(String s: x){
         System.out.print(s + " : ");
         }
         System.out.print("\n");
         }*/

        allEntities = new ArrayList<List<Entity>>();
        platformEntities = new ArrayList<Entity>();
        enemyEntities = new ArrayList<Entity>();
        playerEntities = new ArrayList<Entity>();
        transparentPlatformEntities = new ArrayList<Entity>();

        fillPlatformList();

        levelWidth = levelData[0].length * blockSize;
        levelHeight = levelData.length * blockSize;

        //System.out.println(levelData[0].length + " " + levelWidth);
        //System.out.println(levelData.length + " " + levelHeight);

        playerEntities.add(player = new Player(plyrSrtXPos, plyrSrtYPos, plyrWidth, plyrHeight, 2, 6));

    }

    public Player getPlayer() {
        return player;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public int getPlyrSrtXPos() {
        return plyrSrtXPos;
    }

    public int getPlyrSrtYPos() {
        return plyrSrtYPos;
    }

    public ArrayList<List<Entity>> getAllEntities() {

        allEntities.clear();
        allEntities.add(platformEntities);
        allEntities.add(enemyEntities);
        allEntities.add(playerEntities);
        allEntities.add(transparentPlatformEntities);


        /*for (List<Entity> l : allEntities) {
         for (Entity e : l) {
         if (!(e instanceof Player)) {
         e.act(allEntities);
         }
         }
         }*/
        return allEntities;
    }

    public void fillPlatformList() {

        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length; j++) {
                switch (levelData[i][j]) {
                    case 0:
                        continue;
                    case 1:
                        platformEntities.add(new SolidPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 2:
                        platformEntities.add(new SpikePlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 3:
                        platformEntities.add(new FallAwayPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 4:
                        platformEntities.add(new BouncyPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 5:
                        platformEntities.add(new ConveyorLeftPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 6:
                        platformEntities.add(new ConveyorRightPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 7:
                        platformEntities.add(new SpawnerPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 8:
                        platformEntities.add(new SpawnerPowerUpPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 9:
                        transparentPlatformEntities.add(new WaterPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 10:
                        transparentPlatformEntities.add(new DisappearPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 11:
                        transparentPlatformEntities.add(new NoPlayerPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 12:
                        platformEntities.add(new SpawnerBulletPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 13:
                        platformEntities.add(new BreakablePlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 14:
                        platformEntities.add(new PowerUpHealthPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 15:
                        platformEntities.add(new PowerUpProjectilePlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 16:
                        platformEntities.add(new ActionPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 17:
                        platformEntities.add(new PlacementFlyingEnemyPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 18:
                        platformEntities.add(new PlacementTalkableCharacterPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0, levelMessages[messageIndex]));
                        messageIndex++;
                        if (messageIndex >= levelMessages.length) {
                            messageIndex = 0;
                        }
                        break;
                    case 19:
                        platformEntities.add(new PlacementMemoryCharacterPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0, levelMessages[messageIndex]));
                        messageIndex++;
                        if (messageIndex >= levelMessages.length) {
                            messageIndex = 0;
                        }
                        break;
                    case 20:
                        platformEntities.add(new PlacementPlayerPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 21:
                        platformEntities.add(new LevelChangerPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0, levelIndex));
                        levelIndex++;
                        break;

                    case 22:
                        platformEntities.add(new PlacementBasicEnemyPlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;
                    case 23:
                        platformEntities.add(new PowerUpInvinciblePlatform(j * blockSize, i * blockSize, blockSize, blockSize, 0, 0));
                        break;

                }
            }
        }

    }
}
