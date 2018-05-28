/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PlacementPlayerPlatform extends SolidPlatform {

    boolean placedEntity;

    public PlacementPlayerPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.GREEN);
        isSolid = false;
        placedEntity = false;
    }

    public PlacementPlayerPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setColor(Color.GREEN);
        isSolid = false;
        placedEntity = false;
    }

    public void drawEntity(Graphics2D win) {

    }

    public void act(ArrayList<List<Entity>> entities) {
        if (placedEntity) {
            return;
        } else {
            placeEntity(entities);
            placedEntity = true;
        }
    }
    
    public void placeEntity(ArrayList<List<Entity>> entities){
        entities.get(2).get(0).setHitboxLocation((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() + (getHeight() / 8)));
    }
}
