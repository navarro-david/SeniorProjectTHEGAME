/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BulletEnemy;
import entities.Entity;
import entities.Player;
import entities.PowerUp;
import entities.PowerUpHealth;
import entities.PowerUpProjectile;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class SpawnerBulletPlatform extends SpawnerPlatform {

    int minTime = 2, maxTime = 4;
    String[] sndFlNms = {"audio/sounds/shot1.wav"};
    SoundDriverV2 sounds;

    public SpawnerBulletPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(105, 105, 105));
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        rnd = new Random();
        //setImage("images/Tile_SpawnerBullet.png");
        spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        hurtTimer = new TimerDriverV2(1000, false);
        setHealth(3);
        spawnDistance = 500;
    }

    public SpawnerBulletPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        rnd = new Random();
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        setHealth(3);
        spawnDistance = 500;
    }
    
        public void drawEntity(Graphics2D win) {
        if (charImage != null) {
            win.drawImage(charImage, (int) getXPos(), (int) getYPos(), null);
        } else {
            win.setColor(color);
            win.fill(hitbox);
            win.setColor(Color.BLACK);
            for (Line2D.Double s : sides) {
                win.draw(s);
            }

            /*
             * win.setColor(Color.RED); win.fill(hitboxes[0]);
             * win.setColor(Color.BLUE); win.fill(hitboxes[1]);
             * win.setColor(Color.GREEN); win.fill(hitboxes[2]);
             * win.setColor(Color.YELLOW);
             win.fill(hitboxes[3]);
             */
        }
        if (playSound) {
            sounds.play(soundIndex);
            playSound = false;
        }

        /*if (talkTimer.getCount() > messages.length) {
         talkTimer.resetCount();
         talkTimer.stop();
         } else if (talkTimer.isRunning()) {
         if (talkTimer.getCount() < messages.length) {
                
         for (String s : messages) { System.out.println(s);
         }
                 
         win.setColor(new Color(255, 255, 255, 128));
         int strWidth = win.getFontMetrics().stringWidth(messages[talkTimer.getCount()]), strHeight = win.getFontMetrics().getHeight();
         win.fillRect((int) ((getXPos() + width / 2) - (strWidth / 2)), (int) getYPos() - 20, strWidth, strHeight);
         win.setColor(Color.BLACK);
         win.drawString(messages[talkTimer.getCount()], (int) ((getXPos() + width / 2) - (strWidth / 2)), (int) (getYPos() - 22 + strHeight));
         }

         }*/
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);

        if (spawnTimer.getTimeReached()) {
            spawnTimer.setTimeReached(false);
            spawnTimer.stop();
            spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
            spawnEntities(entities);
        }


        for (Entity e : reactors) {

            if (e instanceof Player) {
                if (!spawnTimer.isRunning()) {
                    spawnTimer.start();
                }
            }
        }
    }

    public void spawnEntities(ArrayList<List<Entity>> entities) {
        Entity bullet = null;
        //System.out.println(entities.get(0).get(0));
        if (entities.get(2).get(0).getXPos() > getXPos()) {

            bullet = new BulletEnemy((int) (getXPos() + getWidth()), (int) (getYPos() + (getHeight() / 4)), (int) (getWidth() / 2), (int) (getHeight() / 2), 1, 1);
        } else {
            bullet = new BulletEnemy((int) (getXPos() - (getWidth() / 2)), (int) (getYPos() + (getHeight() / 4)), (int) (getWidth() / 2), (int) (getHeight() / 2), -1, 1);
        }
        
        soundIndex = 0;
        playSound = true;
        entities.get(1).add(bullet);

    }
}
