/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.FlyingEnemy;
import entities.TalkableCharacter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PlacementTalkableCharacterPlatform extends PlacementPlayerPlatform {
    
    String[] thoughts;

    public PlacementTalkableCharacterPlatform(int xP, int yP, int w, int h, int xV, int yV, String[] msgs) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.BLUE);
        isSolid = false;
        placedEntity = false;
        thoughts = msgs;
    }

    public PlacementTalkableCharacterPlatform(int xP, int yP, String imgName, int xV, int yV, String[] msgs) {
        super(xP, yP, imgName, xV, yV);
        setColor(Color.BLUE);
        isSolid = false;
        placedEntity = false;
        thoughts = msgs;
    }

    public void placeEntity(ArrayList<List<Entity>> entities) {
        entities.get(2).add(new TalkableCharacter((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() + (getHeight() / 8)), (int) (getWidth() / 2), (int) (getHeight() - (getHeight() / 3)), 1, 5, thoughts));
    }
}
