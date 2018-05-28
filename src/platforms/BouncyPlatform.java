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
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import utilities.SoundDriverV2;

/**
 *
 * @author David
 */
public class BouncyPlatform extends SolidPlatform {

    String[] sndFlNms = {"audio/sounds/jump5.wav"};
    //SoundDriverV2 sounds;
    int jumpVel;

    public BouncyPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(204, 229, 255));
        sounds = new SoundDriverV2(sndFlNms);
        //setImage("images/Tile_Bouncy.png");
        //setSoundNames(sndFlNms);
        //setSoundNames(sndFlNms);
        jumpVel = 9;
    }

    public BouncyPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        sounds = new SoundDriverV2(sndFlNms);
        jumpVel = 9;
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

            win.setColor(new Color(100,149,237));
            win.fill(hitboxes[0]);
            //win.setColor(Color.YELLOW);
            win.fill(hitboxes[3]);

        }
        if (playSound) {
            sounds.play(soundIndex);
            playSound = false;
        }

    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);


        for (Entity e : reactors) {


            if (hitboxes[3].intersects(e.getHitBox())) {
            }
            if (hitboxes[0].intersects(e.getHitBox())) {
                if (/*(e instanceof Player) ||*/!(e instanceof Projectile) && !e.isSolid()) {
                    e.setIsOnGround(false);
                    e.setVerMove(-jumpVel);
                    soundIndex = 0;
                    playSound = true;
                }
            }
        }


    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + width / 8, (int) getYPos(), width - (width / 4), height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, 0, 0);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, 0, 0);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, width / 2, height / 4);
    }
}
