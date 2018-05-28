/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import platforms.WaterPlatform;

/**
 *
 * @author David
 */
public class BulletEnemy extends BasicEnemy {

    public BulletEnemy(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, true);
        setColor(new Color(220, 220, 220));
        isSolid = false;
        health = 2;
        if (xV < 0) {
            canMoveInRightDir = false;
            canMoveInLeftDir = true;
            setImage("images/character/Enemies_Bullet_1.png");
        } else {
            canMoveInRightDir = true;
            canMoveInLeftDir = false;
            setImage("images/character/Enemies_Bullet_2.png");
        }

    }

    public BulletEnemy(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV, true);
        isSolid = false;
        health = 2;
        if (xV < 0) {
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


        /*if (!isOnGround || reactors.size() <= 0) {
         verMove += gravity;
         isOnGround = false;

         } else {
         verMove = 0;
         }*/

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

            /*if ((e instanceof Player) && e.getHitBoxesArray()[3].intersects(hitboxes[0])) {
             e.setVerMove(-5);
             e.setIsOnGround(false);
             hurt();
             }*/

            if ((e instanceof WaterPlatform)) {
            }

            if (e.isSolid()) {

                if (hitboxes[1].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() + (int) e.getWidth();
                    kill();
                    /*canMoveInRightDir = true;
                     canMoveInLeftDir = false;
                     horMove = 0;*/

                }
                if (hitboxes[2].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() - width;
                    kill();
                    /*canMoveInRightDir = false;
                     canMoveInLeftDir = true;
                     horMove = 0;*/

                }

            }
        }
        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }
}
