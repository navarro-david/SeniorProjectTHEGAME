/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utilities.PowerUpType;

/**
 *
 * @author bhsrobotics
 */
public class PowerUpHealth extends PowerUp {
    
    //BufferedImage[] pics;

    public PowerUpHealth(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(255,182,193));
        isSolid = false;
        setColor(new Color(255, 204, 204));
        setPowerUp(PowerUpType.HEALTH);
    }

    public PowerUpHealth(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        //setColor(new Color(255,182,193));
        isSolid = false;
        setColor(new Color(255, 204, 204));
        setPowerUp(PowerUpType.HEALTH);
    }
}
