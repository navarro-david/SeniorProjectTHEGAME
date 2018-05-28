/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import platforms.WaterPlatform;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class FlyingEnemy extends BasicEnemy {

    TimerDriverV2 flyTimer, flyDelay;
    Random rnd;
    int frameCount = 0, frameDelay = 25;
    int minTime = 0, maxTime = 3;
    boolean canFly, frameSwitch;
    String[] imagesName = new String[2];

    public FlyingEnemy(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, false);
        setColor(new Color(176, 224, 230));
        rnd = new Random();
        isSolid = false;
        flyTimer = new TimerDriverV2(1500, true);
        flyDelay = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        flyDelay.start();
        canFly = true;
        imagesName[0] = "images/character/Enemies_Flying_1.png";
        imagesName[1] = "images/character/Enemies_Flying_2.png";

    }

    public FlyingEnemy(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV, false);
        isSolid = false;
        setColor(new Color(176, 224, 230));
        rnd = new Random();
        isSolid = false;
        flyTimer = new TimerDriverV2(1500, true);
        flyDelay = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        flyDelay.start();
        canFly = true;

    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();

        if (flyDelay.getTimeReached()) {
            flyDelay.stop();
            flyTimer.start();
        }

        if (flyTimer.isRunning() && canFly) {
            if (flyTimer.getCount() % 2 == 0) {
                verMove = -getYVel();
            } else {
                verMove = getYVel();
            }
        }
        
       frameCount++;
       if(frameCount > frameDelay){
           frameCount = 0;
           int val = frameSwitch ? 1 : 0;
           frameSwitch = !frameSwitch;
           setImage(imagesName[val]);
       } 

        canFly = !canFly;
        isOnGround = false;
        isInWater = false;

        for (Entity e : reactors) {

            if ((e instanceof Player) && e.getHitBoxesArray()[3].intersects(hitboxes[0])) {
                e.setVerMove(-5);
                e.setIsOnGround(false);
                hurt();
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
        moveEntity((int) 0, (int) verMove);
    }
}
