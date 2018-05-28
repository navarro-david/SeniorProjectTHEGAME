/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import platforms.ActionPlatform;
import platforms.NoPlayerPlatform;
import platforms.SolidPlatform;
import platforms.WaterPlatform;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class TalkableCharacter extends Entity {

    TimerDriverV2 jumpTimer, talkTimer;
    Random rnd;
    int minTime = 2, maxTime = 5, timerIndex = 0, actionDistance = 500;
    int frameCounter, frameDelay, frameIndex;
    String[] sndFlNms = {"audio/sounds/talk2.wav", "audio/sounds/jump4.wav", "audio/sounds/pickup4.wav"};
    SoundDriverV2 sounds;
    BufferedImage[][] images = new BufferedImage[2][5];

    public TalkableCharacter(int xP, int yP, int w, int h, int xV, int yV, String[] msgs) {
        super(xP, yP, w, h, xV, yV, new Color(255, 160, 122));
        isSolid = false;
        health = 1;
        isAlive = true;
        frameCounter = 0;
        frameDelay = 50;
        frameIndex = 0;
        setMessages(msgs);
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
        talkTimer = new TimerDriverV2(3000, true);
        isFacingRight = false;
        for (int i = 0; i < images[0].length - 1; i++) {
            images[0][i] = addImage("images/character/Character_Standing_Left_1-" + (i + 1) + ".png");
            images[1][i] = addImage("images/character/Character_Standing_Right_1-" + (i + 1) + ".png");
        }
        images[0][4] = addImage("images/character/Character_Jumping_Left_1-1.png");
        images[1][4] = addImage("images/character/Character_Jumping_Right_1-1.png");

        rnd = new Random();
        jumpTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        jumpTimer.start();

    }

    public TalkableCharacter(int xP, int yP, String imgName, int xV, int yV, String[] msgs) {
        super(xP, yP, imgName, xV, yV);
        isSolid = false;
        health = 1;
        isAlive = true;
        setMessages(msgs);
        frameCounter = 0;
        frameDelay = 50;
        frameIndex = 0;
        sounds = new SoundDriverV2(sndFlNms);
        //setSoundNames(sndFlNms);
        talkTimer = new TimerDriverV2(3000, true);
        rnd = new Random();
        jumpTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        jumpTimer.start();

    }

    public void drawEntity(Graphics2D win) {

        int val = isFacingRight ? 1 : 0;

        frameCounter++;
        if (frameCounter > frameDelay) {
            frameCounter = 0;
            if (frameIndex < images[val].length - 2) {
                frameIndex++;
            } else {
                frameIndex = 0;
            }
            charImage = images[val][frameIndex];
        }
        if (!isOnGround) {
            charImage = images[val][4];
        }


        if (charImage != null) {
            win.drawImage(charImage, (int) getXPos(), (int) getYPos(), null);
        } else {
            win.setColor(color);
            win.fill(hitbox);
            win.setColor(Color.BLACK);
            for (Line2D.Double s : sides) {
                win.draw(s);
            }

        }
        if (playSound) {
            sounds.play(soundIndex);
            playSound = false;
        }

        if (talkTimer.getCount() > messages.length) {
            talkTimer.resetCount();
            talkTimer.stop();
            timerIndex = 0;
        } else if (talkTimer.isRunning()) {
            if (talkTimer.getCount() < messages.length) {

                if (talkTimer.getCount() >= timerIndex) {
                    timerIndex++;
                    soundIndex = 0;
                    playSound = true;
                }

                win.setColor(new Color(255, 255, 255, 128));
                win.setFont(new Font("Arial", Font.ITALIC, 20));
                int strWidth = win.getFontMetrics().stringWidth(messages[talkTimer.getCount()]), strHeight = win.getFontMetrics().getHeight();
                win.fillRect((int) ((getXPos() + width / 2) - (strWidth / 2)), (int) getYPos() - 15, strWidth, strHeight);
                win.setColor(Color.BLACK);
                win.drawString(messages[talkTimer.getCount()], (int) ((getXPos() + width / 2) - (strWidth / 2)), (int) (getYPos() - 20 + strHeight));
            }
        }
    }

    /*public ArrayList<Entity> processEntities(ArrayList<List<Entity>> entities) {
     ArrayList<Entity> reactors = super.processEntities(entities);
     for (int i = 0; i < entities.size(); i++) {
     for (int j = 0; j < entities.get(i).size(); j++) {
     if (entities.get(i).get(j).equals(this)) {
     continue;
     }
     if (Math.abs(entities.get(i).get(j).getXPos() - getXPos()) < actionDistance
     && Math.abs(entities.get(i).get(j).getYPos() - getYPos()) < actionDistance
     && ((entities.get(i).get(j) instanceof Player))) {
     reactors.add(entities.get(i).get(j));

     }
     }
     }
     return reactors;
     }*/
    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();


        if (!isOnGround || reactors.size() <= 0) {
            if (isInWater) {
                verMove = 0;
                verMove += gravity / 2;
            } else {
                verMove += gravity;
            }

            isOnGround = false;

        } else {
            verMove = 0;
        }

        canMoveInRightDir = true;
        canMoveInLeftDir = true;
        isOnGround = false;
        isInWater = false;

        for (Entity e : reactors) {

            if (!e.isSolid() /*&& !(e instanceof NoPlayerPlatform)*/) {

                //<editor-fold defaultstate="collapsed" desc="Enemy Actions">

                if ((e instanceof WaterPlatform) /*&& (e.getYPos() > getYPos())*/) {

                    setIsInWater(true);
                } else if ((e instanceof Player)) {
                    if (e.getXPos() <= getXPos()) {
                        isFacingRight = false;
                    } else {
                        isFacingRight = true;
                    }
                }
                //</editor-fold>


            } else {

                //<editor-fold defaultstate="collapsed" desc="Block Actions">

                if (hitboxes[3].intersects(e.getHitBox())) {

                    isOnGround = true;
                    newY = (int) e.getYPos() - height + 1;

                }
                if (hitboxes[1].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() + (int) e.getWidth();
                    canMoveInRightDir = false;
                    canMoveInLeftDir = true;
                    horMove = 0;


                }
                if (hitboxes[2].intersects(e.getHitBox())) {

                    newX = (int) e.getXPos() - width;
                    canMoveInRightDir = true;
                    canMoveInLeftDir = false;
                    horMove = 0;

                }
                if (hitboxes[0].intersects(e.getHitBox())) {
                    newY = (int) e.getYPos() + (int) e.getHeight();
                    verMove = 0;
                }
                //</editor-fold>
            }
        }

        //Jumping Control
        if (jumpTimer.getTimeReached()) {
            jumpTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
            jumpTimer.start();
            soundIndex = 1;
            playSound = true;
            if (isOnGround) {
                isOnGround = false;
                verMove = -getYVel();
            } else if (isInWater) {
                verMove = -(getYVel() / 2);
            }
            //isJumping = true;
        }

        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }

    public void talk() {
        if (!talkTimer.isRunning()) {
            talkTimer.start();
        }
    }
}
