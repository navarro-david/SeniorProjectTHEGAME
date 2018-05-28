/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.MemoryCharacter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PlacementMemoryCharacterPlatform extends PlacementTalkableCharacterPlatform {

    public PlacementMemoryCharacterPlatform(int xP, int yP, int w, int h, int xV, int yV, String[] msgs) {
        super(xP, yP, w, h, xV, yV, msgs);
        setColor(Color.BLUE);
        isSolid = false;
        placedEntity = false;
    }

    public PlacementMemoryCharacterPlatform(int xP, int yP, String imgName, int xV, int yV, String[] msgs) {
        super(xP, yP, imgName, xV, yV, msgs);
        setColor(Color.BLUE);
        isSolid = false;
        placedEntity = false;
    }

    public void placeEntity(ArrayList<List<Entity>> entities) {
        entities.get(2).add(new MemoryCharacter((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() + (getHeight() / 8)), (int) (getWidth() / 2), (int) (getHeight() - (getHeight() / 3)), 1, 5, thoughts));
    }
}
