/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import utilities.SoundDriverV2;

/**
 *
 * @author David
 */
public class SolidPlatform extends Entity {
    
    SoundDriverV2 sounds = null;

    public SolidPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, new Color(238, 232, 170));
        //setImage("images/Tile_Solid.png");
    }

    public SolidPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
    }

    public void drawEntity(Graphics2D win) {
        if (charImage != null) {
            win.drawImage(charImage, xPos, yPos, null);
        } else {
            win.setColor(color);
            win.fill(hitbox);
            win.setColor(Color.BLACK);
            for (Line2D.Double s : sides) {
                win.draw(s);
            }
            win.setColor(Color.RED);
            win.fill(hitboxes[0]);
            win.setColor(Color.BLUE);
            win.fill(hitboxes[1]);
            win.setColor(Color.GREEN);
            win.fill(hitboxes[2]);
            win.setColor(Color.YELLOW);
            win.fill(hitboxes[3]);
        }

        if (playSound && sounds != null) {
            sounds.play(soundIndex);
            playSound = false;
        }
    }

    public void act(ArrayList<List<Entity>> entities) {
        return;
    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle(0, 0, 0, 0);
        hitboxes[1] = new Rectangle(0, 0, 0, 0);
        hitboxes[2] = new Rectangle(0, 0, 0, 0);
        hitboxes[3] = new Rectangle(0, 0, 0, 0);
    }
}