/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import platforms.WaterPlatform;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class Projectile extends Entity {

    TimerDriverV2 lifeTimer;

    public Projectile(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, new Color(255, 69, 0));
        isSolid = false;
        lifeTimer = new TimerDriverV2(2000, false);
        lifeTimer.start();

        if (xV < 0) {
            canMoveInRightDir = false;
            canMoveInLeftDir = true;
        } else {
            canMoveInRightDir = true;
            canMoveInLeftDir = false;
        }
    }

    public Projectile(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        isSolid = false;
        lifeTimer = new TimerDriverV2(2000, false);
        lifeTimer.start();

        if (xV > 0) {
            canMoveInRightDir = false;
            canMoveInLeftDir = true;
        } else {
            canMoveInRightDir = true;
            canMoveInLeftDir = false;
        }
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();

        if (lifeTimer.getTimeReached()) {
            kill();
            //System.out.println("killed projectile");
        }

        if (!isOnGround || reactors.size() <= 0) {
            verMove += gravity;
            isOnGround = false;

        } else {
            verMove = 0;
        }

        //Right and Left Control

        double xV = getXVel();
        if (isInWater) {
            xV /= 2;
        }

        if (canMoveInRightDir) {
            horMove = Math.abs(xV);
        } else if (canMoveInLeftDir) {
            horMove = -Math.abs(xV);
        } else {
            horMove = 0;
        }
        //canMoveInRightDir = true;
        //canMoveInLeftDir = true;
        isOnGround = false;
        isInWater = false;

        for (Entity e : reactors) {

            //<editor-fold defaultstate="collapsed" desc="Enemy Actions">
            if ((e instanceof BasicEnemy)) {
                e.hurt();
                kill();
            }

            if ((e instanceof WaterPlatform)) {
                setIsInWater(true);
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Block Actions">
            if (e.isSolid() /*|| (e instanceof Projectile)*/) {
                if (hitboxes[3].intersects(e.getHitBox())) {

                    isOnGround = true;
                    newY = (int) e.getYPos() - height + 1;

                }
                if (hitboxes[1].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() + (int) e.getWidth();
                    canMoveInRightDir = true;
                    canMoveInLeftDir = false;

                }
                if (hitboxes[2].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() - width;
                    canMoveInRightDir = false;
                    canMoveInLeftDir = true;

                }
                if (hitboxes[0].intersects(e.getHitBox())) {

                    newY = (int) e.getYPos() + (int) e.getHeight();
                    verMove = 0;

                }
            }
            //</editor-fold>
        }

        //Jumping Control
        if (isOnGround) {
            isOnGround = false;
            //isJumping = true;
            verMove = -(verMove - verMove / 8);
        }




        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }
}
