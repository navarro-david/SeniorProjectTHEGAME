/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import utilities.PowerUpType;

/**
 *
 * @author David
 */
public class PowerUpProjectile extends PowerUp{
    
        public PowerUpProjectile(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(176,196,222));
        isSolid = false;
        //setColor(new Color(255, 204, 204));
        setPowerUp(PowerUpType.PROJECTILE);
    }

    public PowerUpProjectile(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setColor(new Color(176,196,222));
        isSolid = false;
        //setColor(new Color(255, 204, 204));
        setPowerUp(PowerUpType.PROJECTILE);
    }
}
