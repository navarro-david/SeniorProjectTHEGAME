/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BasicEnemy;
import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class SpikePlatform extends SolidPlatform {

    public SpikePlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.DARK_GRAY);
    }

    public SpikePlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
    }
    
    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);

        for (Entity e : reactors) {
            
            if(!e.isSolid()){
                e.kill();
            }       

        }


    }
}
