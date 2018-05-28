/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import utilities.PowerUpType;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class PowerUp extends Entity {

    TimerDriverV2 lifeTimer;

    public PowerUp(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, new Color(245, 245, 220));
        isSolid = false;
        powerUp = PowerUpType.NONE;
        canMoveInRightDir = true;
        canMoveInLeftDir = false;

        lifeTimer = new TimerDriverV2(60000, false);
        lifeTimer.start();
    }

    public PowerUp(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        isSolid = false;
        powerUp = PowerUpType.NONE;
        canMoveInRightDir = true;
        canMoveInLeftDir = false;

        lifeTimer = new TimerDriverV2(60000, false);
        lifeTimer.start();
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();
        
        if(lifeTimer.getTimeReached()){
            kill();
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

        for (Entity e : reactors) {

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
}
