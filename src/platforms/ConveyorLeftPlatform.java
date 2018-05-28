/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class ConveyorLeftPlatform extends SolidPlatform {
    
    int conveyorSpeed;
    boolean effectEntity;

    public ConveyorLeftPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(119,136,153));
        conveyorSpeed = -1;
        effectEntity = false;
        //setImage("images/Tile_ConveyorLeft.png");
    }

    public ConveyorLeftPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        conveyorSpeed = -1;
        effectEntity = false;
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);


        for (Entity e : reactors) {

//if(key is pressed){
            if (hitboxes[3].intersects(e.getHitBox())) {
            }
            if (hitboxes[0].intersects(e.getHitBox())) {
                if (!e.isSolid()) {
                    effectEntity = !effectEntity;
                    if(effectEntity){
                       e.moveEntity(conveyorSpeed, 0);  
                    }
                   
                    //e.setHorMove(-1);
                }
            }
        }
    }
    //}else(){
    //e.moveEntity(-1,0)
//}

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + (width / 16), (int) getYPos(), width - (width / 8), height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, 0, 0);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, 0, 0);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, 0, 0);
    }
}
