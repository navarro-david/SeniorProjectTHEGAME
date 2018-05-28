/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author David
 */
public class NoPlayerPlatform extends SolidPlatform {

    public NoPlayerPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(216, 191, 216, 128));
        setIsSolid(false);
        //setImage("images/Tile_NoPlayer.png");

    }

    public NoPlayerPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setIsSolid(false);

    }

    public void drawEntity(Graphics2D win) {
        if (charImage != null) {
            win.drawImage(charImage, xPos, yPos, null);
            win.setColor(color);
            win.fill(hitbox);
        } else {
            win.setColor(color);
            win.fill(hitbox);
            win.setColor(Color.BLACK);
            for (Line2D.Double s : sides) {
                win.draw(s);
            }

        }

        if (playSound && sounds != null) {
            sounds.play(soundIndex);
            playSound = false;
        }
    }
}
