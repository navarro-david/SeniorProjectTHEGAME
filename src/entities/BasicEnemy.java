/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import platforms.WaterPlatform;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class BasicEnemy extends Entity {

    TimerDriverV2 lifeTimer;
    boolean spawner;
    String[] sndFlNms = {"audio/sounds/hurt6.wav"};
    SoundDriverV2 sounds;
    BufferedImage[] images = new BufferedImage[2];

    public BasicEnemy(int xP, int yP, int w, int h, int xV, int yV, boolean s) {
        super(xP, yP, w, h, xV, yV, Color.ORANGE);
        isSolid = false;
        health = 1;
        isAlive = true;
        spawner = s;
        sounds = new SoundDriverV2(sndFlNms);
        setImage("images/character/Enemies_Basic_1.png");
        if (xV < 0) {
            canMoveInRightDir = false;
            canMoveInLeftDir = true;
        } else {
            canMoveInRightDir = true;
            canMoveInLeftDir = false;
        }
        if (spawner) {
            lifeTimer = new TimerDriverV2(120000, false);
            lifeTimer.start();
        }


    }

    public BasicEnemy(int xP, int yP, String imgName, int xV, int yV, boolean s) {
        super(xP, yP, imgName, xV, yV);
        isSolid = false;
        health = 1;
        isAlive = true;
        spawner = s;
        sounds = new SoundDriverV2(sndFlNms);
        if (xV < 0) {
            canMoveInRightDir = false;
            canMoveInLeftDir = true;
        } else {
            canMoveInRightDir = true;
            canMoveInLeftDir = false;
        }
        if (spawner) {
            lifeTimer = new TimerDriverV2(120000, false);
            lifeTimer.start();
        }
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();

        if (spawner) {
            if (lifeTimer.getTimeReached()) {
                kill();
            }
        }

        if (!isOnGround || reactors.size() <= 0) {
            verMove += gravity;
            isOnGround = false;

        } else {
            verMove = 0;
        }

        if (canMoveInRightDir) {
            horMove = Math.abs(getXVel());
        } else if (canMoveInLeftDir) {
            horMove = -Math.abs(getXVel());
        } else {
            horMove = 0;
        }

        isOnGround = false;
        isInWater = false;

        for (Entity e : reactors) {

            if ((e instanceof Player) && e.getHitBoxesArray()[3].intersects(hitboxes[0])) {
                e.setVerMove(-5);
                e.setIsOnGround(false);
                hurt();
            }

            if ((e instanceof WaterPlatform)) {
            }

            if (e.isSolid()) {
                if (hitboxes[3].intersects(e.getHitBox())) {

                    isOnGround = true;
                    newY = (int) e.getYPos() - height + 1;

                }
                if (hitboxes[1].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() + (int) e.getWidth();
                    canMoveInRightDir = true;
                    canMoveInLeftDir = false;
                    horMove = 0;

                }
                if (hitboxes[2].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() - width;
                    canMoveInRightDir = false;
                    canMoveInLeftDir = true;
                    horMove = 0;

                }
                if (hitboxes[0].intersects(e.getHitBox())) {

                    newY = (int) e.getYPos() + (int) e.getHeight();
                    verMove = 0;

                }
            }
        }
        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }

    public void hurt() {
        health--;
        soundIndex = 0;
        sounds.play(soundIndex);
        if (health <= 0) {

            kill();
        }
    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + (width / 8), (int) getYPos(), width - (width / 4), height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 3, (int) getYPos() + height - height / 4, width / 3, height / 4);
    }
}
