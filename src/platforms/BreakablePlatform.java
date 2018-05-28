/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import utilities.SoundDriverV2;

/**
 *
 * @author David
 */
public class BreakablePlatform extends SolidPlatform {

    String[] sndFlNms = {"audio/sounds/hurt6.wav"};

    public BreakablePlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(189, 183, 107));
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        //setImage("images/Tile_Breakable.png");

    }

    public BreakablePlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);

    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);


        for (Entity e : reactors) {

            /*if((e instanceof Projectile)){
             hurt();
             e.kill();
             }*/

            if (hitboxes[3].intersects(e.getHitBox()) && (e instanceof Player)) {
                hurt();
                e.setVerMove(0);
            }

        }


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
        hitboxes[0] = new Rectangle((int) getXPos() + width / 8, (int) getYPos(), 0, 0);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, 0, 0);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, 0, 0);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, width / 2, height / 4);
    }
}
