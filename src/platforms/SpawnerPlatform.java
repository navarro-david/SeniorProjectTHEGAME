/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BasicEnemy;
import entities.Entity;
import entities.Player;
import entities.Projectile;
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
public class SpawnerPlatform extends SolidPlatform {

    Random rnd;
    TimerDriverV2 spawnTimer, hurtTimer;
    int minTime = 3, maxTime = 7;
    int spawnDistance;
    String[] sndFlNms = {"audio/sounds/pickup8.wav"};
    //SoundDriverV2 sounds;

    public SpawnerPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(178, 34, 34));
        rnd = new Random();
        //
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        hurtTimer = new TimerDriverV2(1000, false);
        setHealth(3);
        spawnDistance = 500;
    }

    public SpawnerPlatform(int xP, int yP, String imgName, int xV, int yV) {
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

    }

    public ArrayList<Entity> processEntities(ArrayList<List<Entity>> entities) {
        ArrayList<Entity> reactors = new ArrayList<Entity>();
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(i).size(); j++) {
                if (entities.get(i).get(j).equals(this)) {
                    continue;
                }
                if (Math.abs(entities.get(i).get(j).getXPos() - getXPos()) < spawnDistance
                        && Math.abs(entities.get(i).get(j).getYPos() - getYPos()) < spawnDistance
                        && ((entities.get(i).get(j) instanceof Player) || (entities.get(i).get(j) instanceof Projectile))) {
                    reactors.add(entities.get(i).get(j));
                }
            }
        }
        return reactors;
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
            
            if ((e instanceof Projectile) && hitbox.intersects(e.getHitBox())) {
                hurt();
                e.kill();
            }
        }


    }

    public void spawnEntities(ArrayList<List<Entity>> entities) {
        int xV = 1;
        if (!rnd.nextBoolean()) {
            xV = -1;
        }
        soundIndex = 0;
        playSound = true;
        entities.get(1).add(new BasicEnemy((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() - (getHeight() / 2)), (int) (getWidth() / 2), (int) (getHeight() / 2), xV, 1, true));
        entities.get(1).get(entities.get(1).size() - 1).setVerMove(-5);
    }

    public void hurt() {
        if (!hurtTimer.isRunning()) {
            hurtTimer.start();
            health--;
            setColor(getColor().darker());
        }
        if (health <= 0) {
            kill();
        }
    }

    public int getSpawnDistance() {
        return spawnDistance;
    }

    public void setSpawnDistance(int value) {
        spawnDistance = value;
    }
}
