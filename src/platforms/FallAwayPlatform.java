/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BasicEnemy;
import entities.Entity;
import entities.Player;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class FallAwayPlatform extends SolidPlatform {

    //boolean canFall;
    int initXPos, initYPos;
    int fallCounter, fallDelay;
    boolean isFalling;
    String[] sndFlNms = {"audio/sounds/jump6.wav"};
    boolean soundPlayed;
    TimerDriverV2 fallTimer, resetTimer;
    //SoundDriverV2 sounds;

    public FallAwayPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(Color.LIGHT_GRAY);
        //canFall = false;
        initXPos = (int) getXPos();
        initYPos = (int) getYPos();
        fallCounter = 0;
        fallDelay = 200;
        isOnGround = false;
        isFalling = false;
        soundPlayed = false;
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        fallTimer = new TimerDriverV2(1000, false);
        resetTimer = new TimerDriverV2(5000, false);
        //setImage("images/Tile_Fall_1.png");
    }

    public FallAwayPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        //canFall = false;
        fallCounter = 0;
        fallDelay = 200;
        isOnGround = false;
        isFalling = false;
        soundPlayed = false;
        //setSoundNames(sndFlNms);
        sounds = new SoundDriverV2(sndFlNms);
        fallTimer = new TimerDriverV2(1000, false);
        resetTimer = new TimerDriverV2(5000, false);
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();

        if (/*(fallCounter > fallDelay)*/fallTimer.getTimeReached() && (!isOnGround /*|| reactors.size() <= 0*/)) {
            if(!soundPlayed){
                soundIndex = 0;
                playSound = true;
                soundPlayed = true;                        
            }
            //setImage("images/Tile_Fall_2.png");
            verMove += gravity;
            isOnGround = false;
            //canFall = false;
        } else {
            verMove = 0;
        }



        //System.out.println(initXPos + " " + initYPos);

        //System.out.println(getXPos() + " " + getYPos());

        for (Entity e : reactors) {


            if (hitboxes[3].intersects(e.getHitBox())) {
                //newY = (int) e.getYPos() - height + 1;
                if (e.isSolid()) {
                    newY = (int) e.getYPos() - height + 1;
                    isOnGround = true;
                    isFalling = false;
                    fallTimer.setTimeReached(false);
                    fallTimer.stop();
                    //setImage("images/Tile_Fall_1.png");
                    /*if (!resetTimer.isRunning()) {
                        resetTimer.start();
                        //System.out.println("resetTimer start");
                        //newY = 0;
                    }*/
                }
            }
            if (hitboxes[0].intersects(e.getHitBox())) {
                if ((e instanceof Player) /*&& e.getXPos() > getXPos()*/) {

                    if (!fallTimer.isRunning()) {
                        //System.out.println(timer.isRunning() + " " + timer.getTimeReached());
                        initYPos = (int)getYPos() - (int)entities.get(2).get(0).getYPos();
                        isFalling = true;
                        fallTimer.start();

                    }
                }
            }
        }

        /*if (resetTimer.getTimeReached()) {
            resetTimer.setTimeReached(false);
            resetTimer.stop();
            
            newY = initYPos;
            System.out.println("resetTimer complete");
            //setXPos(initXPos);
            setHitboxLocation((int)getXPos(), (int)entities.get(2).get(0).getYPos() + initYPos);
        }*/

        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + width / 4, (int) getYPos(), width / 2, height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, 0, 0);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, 0, 0);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, width / 2, height / 4);
    }

    /*public void setHitboxLocation(int xValue, int yValue) {
        int newX = (int) getXPos() - xValue, newY = (int) getYPos() - yValue;
        initXPos += newX;
        initYPos += newY;
        xPos = xValue;
        yPos = yValue;
        hitbox.setLocation(xPos, yPos);
        setSides();
        setHitBoxes();
    }*/
}
