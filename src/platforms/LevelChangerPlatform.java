/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import utilities.SoundDriverV2;

/**
 *
 * @author David
 */
public class LevelChangerPlatform extends ActionPlatform {

    int desiredLevelIndex;
    boolean levelCompleted;
    String[] sndFlNms = {"audio/sounds/talk2.wav", "audio/sounds/beep2.wav"};
    

    public LevelChangerPlatform(int xP, int yP, int w, int h, int xV, int yV, int lvlInx) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.GRAY);
        setIsSolid(false);
        desiredLevelIndex = lvlInx;
        levelCompleted = false;
        messages = new String[1];
        messages[0] = "You must find more memories to continue!";
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
    }

    public LevelChangerPlatform(int xP, int yP, String imgName, int xV, int yV, int lvlInx) {
        super(xP, yP, imgName, xV, yV);
        setIsSolid(false);
        desiredLevelIndex = lvlInx;
        levelCompleted = false;
        messages = new String[1];
        messages[0] = "You must find two memories to continue!";
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
    }
    

    public int getDesiredLevelIndex() {
        return desiredLevelIndex;
    }

    public void setDesiredLevelIndex(int value) {
        desiredLevelIndex = value;
    }

    public boolean getLevelCompleted() {
        return levelCompleted;
    }

    public void checkLevelComplete(boolean[] progress, int[] currentLevel) {
        if (currentLevel[0] == 0) {
            if (desiredLevelIndex == 1) {
                levelCompleted = true;
                currentLevel[0] = desiredLevelIndex;
                soundIndex = 1;
                playSound = true;
            } else if ((progress[(desiredLevelIndex - 1) * 2 - 2] && progress[(desiredLevelIndex - 1) * 2 - 1]) /*&& (!progress[(desiredLevelIndex * 2) - 2] && !progress[(desiredLevelIndex * 2) - 1])*/) {
                levelCompleted = true;
                currentLevel[0] = desiredLevelIndex;
                soundIndex = 1;
                playSound = true;
            } else {
                levelCompleted = false;
            }
        } else if (progress[(currentLevel[0] * 2) - 2] && progress[(currentLevel[0] * 2) - 1]) {
            levelCompleted = true;
            currentLevel[0] = desiredLevelIndex;
            soundIndex = 1;
            playSound = true;
        } else {
            levelCompleted = false;
        }
    }

    public void talk() {
        if (!talkTimer.isRunning() && !levelCompleted) {
            talkTimer.start();
        }
    }
}
