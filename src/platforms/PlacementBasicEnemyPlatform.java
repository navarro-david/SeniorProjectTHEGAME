/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.BasicEnemy;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PlacementBasicEnemyPlatform extends PlacementPlayerPlatform {

    public PlacementBasicEnemyPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.RED);
        isSolid = false;
        placedEntity = false;
    }

    public PlacementBasicEnemyPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        setColor(Color.RED);
        isSolid = false;
        placedEntity = false;
    }

    public void placeEntity(ArrayList<List<Entity>> entities) {
        entities.get(1).add(new BasicEnemy((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() + (getHeight() / 8)), (int) (getWidth() / 2), (int) (getHeight() / 2), -1, 1, false));
    }
}
