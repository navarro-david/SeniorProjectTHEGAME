/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.PowerUpInvincible;
import entities.PowerUpProjectile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PowerUpInvinciblePlatform extends PowerUpHealthPlatform {

    public PowerUpInvinciblePlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);

    }

    public PowerUpInvinciblePlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);


    }

    public void spawnEntities(ArrayList<List<Entity>> entities) {

        Entity powerUp = new PowerUpInvincible((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() - (getHeight() / 2)), (int) (getWidth() / 2), (int) (getHeight() / 2), 1, 1);
        powerUp.setVerMove(-5);
        entities.get(0).add(powerUp);

    }
}
