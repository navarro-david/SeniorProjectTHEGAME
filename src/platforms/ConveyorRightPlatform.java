/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import java.awt.Color;

/**
 *
 * @author David
 */
public class ConveyorRightPlatform extends ConveyorLeftPlatform {
    
    public ConveyorRightPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(112,128,144));
        conveyorSpeed = 1;
        //setImage("images/Tile_ConveyorRight.png");
    }

    public ConveyorRightPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        conveyorSpeed = 1;
    }
    
}
