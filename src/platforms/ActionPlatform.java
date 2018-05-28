/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import java.awt.Color;
import java.awt.Graphics2D;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class ActionPlatform extends SolidPlatform {

    String[] sndFlNms = {"audio/sounds/talk2.wav"};
    //SoundDriverV2 sounds;
    int timerIndex = 0;
    TimerDriverV2 talkTimer;

    public ActionPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.YELLOW);
        setIsSolid(false);
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
        talkTimer = new TimerDriverV2(4000, true);

    }

    public ActionPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setIsSolid(false);
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
        talkTimer = new TimerDriverV2(4000, true);
    }

    public void drawEntity(Graphics2D win) {
        if (charImage != null) {
            win.drawImage(charImage, (int) getXPos(), (int) getYPos(), null);
        } else {
            win.setColor(color);
            win.draw(hitbox);
            win.setColor(new Color(0,0,0, 128));
            win.fill(hitbox);
        }

        if (playSound) {
            sounds.play(soundIndex);
            playSound = false;
        }

        if (talkTimer.getCount() > messages.length) {
            talkTimer.resetCount();
            talkTimer.stop();
            timerIndex = 0;
        } else if (talkTimer.isRunning()) {
            if (talkTimer.getCount() < messages.length) {

                if (talkTimer.getCount() >= timerIndex) {
                    timerIndex++;
                    soundIndex = 0;
                    playSound = true;
                }

                win.setColor(new Color(255, 255, 255, 128));
                int strWidth = win.getFontMetrics().stringWidth(messages[talkTimer.getCount()]), strHeight = win.getFontMetrics().getHeight();
                win.fillRect((int) ((getXPos() + width / 2) - (strWidth / 2)), (int) getYPos() - 15, strWidth, strHeight);
                win.setColor(Color.BLACK);
                win.drawString(messages[talkTimer.getCount()], (int) ((getXPos() + width / 2) - (strWidth / 2)), (int) (getYPos() - 20 + strHeight));
            }
        }

    }

    public void talk() {
        if (!talkTimer.isRunning()) {
            talkTimer.start();
        }
    }
}
