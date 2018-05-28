/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class DisappearPlatform extends SolidPlatform {

    Color col1, col2;
    TimerDriverV2 disappearTimer;
    //SoundDriverV2 sounds;
    String[] sndFlNms = {"audio/sounds/beep4.wav", "audio/sounds/beep3.wav"};
    boolean soundPlayed, soundPlayed2;

    public DisappearPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        col1 = new Color(245, 245, 220);
        col2 = new Color(248, 248, 255, 128);
        disappearTimer = new TimerDriverV2(1000, true);
        setColor(col1);
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        soundPlayed = false;
        soundPlayed2 = false;
        //setImage("images/Tile_Disappear_1.png");
        //setIsSolid(false);

    }

    public DisappearPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        col1 = new Color(245, 245, 220);
        col2 = new Color(248, 248, 255, 128);
        disappearTimer = new TimerDriverV2(1000, true);
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        soundPlayed = false;
        soundPlayed2 = false;
        //setIsSolid(false);

    }

    public void act(ArrayList<List<Entity>> entities) {
        ArrayList<Entity> reactors = processEntities(entities);
        for (Entity e : reactors) {
            if (e instanceof Player) {
                if (!disappearTimer.isRunning()) {
                    disappearTimer.start();
                }
            }
        }

        //System.out.println(disappearTimer.getCount());

        if (disappearTimer.getCount() == 1) {
            setIsSolid(false);
            setColor(col2);
            //setImage("images/Tile_Disappear_2.png");
            if (!soundPlayed) {
                soundIndex = 0;
                playSound = true;
                soundPlayed = true;
                soundPlayed2 = false;
            }

        } else if (disappearTimer.getCount() == 8) {
            disappearTimer.resetCount();
            //setImage("images/Tile_Disappear_1.png");
            disappearTimer.stop();
            if (!soundPlayed2) {
                soundIndex = 1;
                playSound = true;
                soundPlayed = false;
                soundPlayed2 = true;
            }

            setIsSolid(true);
            setColor(col1);
        }
    }
}
