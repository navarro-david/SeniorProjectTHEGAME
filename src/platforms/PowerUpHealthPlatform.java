/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.Player;
import entities.PowerUpHealth;
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
public class PowerUpHealthPlatform extends SolidPlatform {

    boolean ejectedPowerUp;
    String[] sndFlNms = {"audio/sounds/pickup1.wav"};
    
    //SoundDriverV2 sounds;

    public PowerUpHealthPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(255, 215, 0));
        sounds = new SoundDriverV2(sndFlNms);
        //setImage("images/Tile_PowerUpBlock_1.png");
        //setSoundNames(sndFlNms);
        ejectedPowerUp = false;
    }

    public PowerUpHealthPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
        ejectedPowerUp = false;

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

            win.setColor(Color.YELLOW);
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

            if (((hitboxes[3].intersects(e.getHitBox()) && (e instanceof Player)) || e instanceof Projectile) && !ejectedPowerUp) {
                spawnEntities(entities);
                ejectedPowerUp = true;
                //setImage("images/Tile_PowerUpBlock_2.png");
                setColor(color.darker());

                soundIndex = 0;
                playSound = true;
            }
        }
    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + width / 8, (int) getYPos(), 0, 0);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, 0, 0);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, 0, 0);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, width / 2, height / 4);
    }

    public void spawnEntities(ArrayList<List<Entity>> entities) {

        Entity powerUp = new PowerUpHealth((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() - (getHeight() / 2)), (int) (getWidth() / 2), (int) (getHeight() / 2), 1, 1);
        powerUp.setVerMove(-5);
        entities.get(0).add(powerUp);

    }
}
