/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BulletEnemy;
import entities.Entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class WaterPlatform extends SolidPlatform {

    int effectCount, effectDelay;

    public WaterPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(127, 255, 212, 128));
        setIsSolid(false);
        effectCount = 0;
        effectDelay = 3;

    }

    public WaterPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setIsSolid(false);
        effectCount = 0;
        effectDelay = 10;

    }

    public void drawEntity(Graphics2D win) {
            win.setColor(color);
            win.fill(hitbox);        
    }

    public void act(ArrayList<List<Entity>> entities) {
        ArrayList<Entity> reactors = processEntities(entities);

        effectCount++;

        for (Entity e : reactors) {
            if (!e.isSolid() && !(e instanceof BulletEnemy)) {
                e.setIsInWater(true);
                if (!e.isOnGround()) {
                    if (effectCount > effectDelay) {
                        effectCount = 0;
                        e.moveEntity(0, 1);
                    }

                }
            }
        }
    }
}
