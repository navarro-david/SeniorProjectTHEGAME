/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import platforms.ActionPlatform;
import platforms.LevelChangerPlatform;
import platforms.NoPlayerPlatform;
import platforms.WaterPlatform;
import utilities.PowerUpType;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class Player extends Entity {

    int healthCap, shootCount, shootDelay;
    int frameCount = 0, frameDelay = 15, frameIndex = 0, levelFrameIndex = 0;
    boolean canShoot;
    //Color plyrColor;
    Random rnd;
    TimerDriverV2 hurtTimer, actionTimer, invincibleTimer;
    String[] sndFlNms = {"audio/sounds/jump1.wav", "audio/sounds/hurt5.wav", "audio/sounds/shot5.wav", "audio/sounds/pickup7.wav"};
    SoundDriverV2 sounds;
    BufferedImage[][] images = new BufferedImage[3][18];

    public Player(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV, new Color(255, 229, 204));
        //plyrColor = getColor();
        isSolid = false;
        canShoot = false;
        rnd = new Random();
        setHealth(3);
        sounds = new SoundDriverV2(sndFlNms);
        healthCap = 4;
        shootCount = 0;
        shootDelay = 100;
        hurtTimer = new TimerDriverV2(5000, false);
        actionTimer = new TimerDriverV2(1000, false);
        invincibleTimer = new TimerDriverV2(15000, false);
        //setSoundNames(sndFlNms);

        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < 4; j++) {
                images[i][j] = addImage("images/character/Character_Standing_Left_" + (i + 1) + "-" + (j + 1) + ".png");
                images[i][j + 4] = addImage("images/character/Character_Walking_Left_" + (i + 1) + "-" + (j + 1) + ".png");
                images[i][j + (images[0].length / 2)] = addImage("images/character/Character_Standing_Right_" + (i + 1) + "-" + (j + 1) + ".png");
                images[i][j + (images[0].length / 2) + 4] = addImage("images/character/Character_Walking_Right_" + (i + 1) + "-" + (j + 1) + ".png");
            }
        }

        for (int i = 0; i < images.length; i++) {
            images[i][8] = addImage("images/character/Character_Jumping_Left_" + (i + 1) + "-1.png");
            images[i][17] = addImage("images/character/Character_Jumping_Right_" + (i + 1) + "-1.png");
        }

        /*for (int i = 0; i < 4; i++) {
         images[1][i] = addImage("images/character/Character_Standing_Right_" + (i + 1) + ".png");
         images[1][i + 4] = addImage("images/character/Character_Walking_Right_" + (i + 1) + ".png");
         }
         images[1][8] = addImage("images/character/Character_Jumping_Right.png");*/
    }

    public Player(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        //plyrColor = new Color(255, 229, 204);
        isSolid = false;
        canShoot = false;
        rnd = new Random();
        setHealth(3);
        sounds = new SoundDriverV2(sndFlNms);
        healthCap = 4;
        shootCount = 0;
        shootDelay = 100;
        hurtTimer = new TimerDriverV2(5000, false);
        actionTimer = new TimerDriverV2(1000, false);
        invincibleTimer = new TimerDriverV2(15000, false);
        //setSoundNames(sndFlNms);
    }

    public void drawEntity(Graphics2D win) {

        int val = isFacingRight ? (images[levelFrameIndex].length / 2) : 0;

        if (!isOnGround) {
            charImage = images[levelFrameIndex][(images[levelFrameIndex].length / 2) - 1 + val];
        }

        if (charImage != null) {
            win.drawImage(charImage, (int) getXPos(), (int) getYPos(), null);
        } else {
            if (invincibleTimer.isRunning()) {
                win.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            } else {
                win.setColor(color);
            }
            win.fill(hitbox);
            win.setColor(Color.BLACK);
            for (Line2D.Double s : sides) {
                win.draw(s);
            }

            /*
             * win.setColor(Color.RED); win.fill(hitboxes[0]);
             * win.setColor(Color.BLUE); win.fill(hitboxes[1]);
             * win.setColor(Color.GREEN); win.fill(hitboxes[2]);
             * win.setColor(Color.YELLOW);
             win.fill(hitboxes[3]);
             */
        }
        if (playSound) {
            sounds.play(soundIndex);
            playSound = false;
        }

    }

    public void act(boolean[] keys, boolean[] progress, int[] levelIndex, ArrayList<List<Entity>> entities) {
        if (keys.length < 16) {
            System.out.println("keys[] array is too small!");
            return;
        }
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
            if (verMove > 10) {
                hurt();
            }
            verMove = 0;
        }

        if (keys[1] && !isOnGround && isInWater) {
            verMove = getYVel() / 2;
        }

        canMoveInRightDir = true;
        canMoveInLeftDir = true;
        isOnGround = false;
        isInWater = false;

        for (Entity e : reactors) {

            if (!e.isSolid() && !(e instanceof NoPlayerPlatform)) {

                if (keys[6] && !actionTimer.isRunning() && ((e instanceof ActionPlatform) || (e instanceof TalkableCharacter))) {
                    actionTimer.start();
                    e.talk();
                    if (e instanceof MemoryCharacter) {
                        ((MemoryCharacter) e).setProgress(progress, levelIndex);
                        if (progress[(levelIndex[0] * 2) - 2]) {
                            levelFrameIndex = 1;
                        } if (progress[(levelIndex[0] * 2) - 1]) {
                            levelFrameIndex = 2;
                        }
                    } else if (e instanceof LevelChangerPlatform) {
                        ((LevelChangerPlatform) e).checkLevelComplete(progress, levelIndex);
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="Enemy Actions">
                if ((e instanceof BasicEnemy) /*&& !hitboxes[3].intersects(e.getHitBoxesArray()[0]) &&*/) {

                    if (invincibleTimer.isRunning()) {
                        e.hurt();
                        e.kill();
                    } else if (!hurtTimer.isRunning()) {
                        for (int i = 0; i < hitboxes.length - 1; i++) {
                            if (hitboxes[i].intersects(e.getHitBox())) {
                                hurt();
                                verMove = -5;
                                if (getXPos() < e.getYPos()) {
                                    horMove = 5;
                                } else {
                                    horMove = -5;
                                }
                                canShoot = false;
                                hurtTimer.start();
                                //System.out.println("Ouch! That enemy hurt me! *sadface* ");
                                break;
                            }
                        }
                    }
                }

                if ((e instanceof WaterPlatform) /*&& (e.getYPos() > getYPos())*/) {

                    setIsInWater(true);
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="PowerUp Actions">
                if (e instanceof PowerUp) {
                    powerUpActions(e.getPowerUp());
                    e.kill();
                    soundIndex = 3;
                    playSound = true;
                    //System.out.println("got here...");
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
        if (keys[0]) {
            if (isOnGround) {
                isOnGround = false;
                verMove = -getYVel();
                soundIndex = 0;
                playSound = true;
            } else if (isInWater) {
                verMove = -(getYVel() / 2);
            }
            //isJumping = true;
        }

        //Right and Left Control
        boolean isMoving = false;
        if (keys[3] && canMoveInRightDir) {             //Right
            isFacingRight = true;
            isMoving = true;
            horMove = getXVel();
        } else if (keys[2] && canMoveInLeftDir) {     //Left
            isFacingRight = false;
            isMoving = true;
            horMove = -getXVel();
        } else {                                        //Friction
            if (horMove > 0) {
                horMove -= friction;
            } else if (horMove < 0) {
                horMove += friction;
            } else {
                horMove = 0;
            }
        }

        //Animation
        frameCount++;
        if (frameCount > frameDelay) {
            frameCount = 0;
            if (frameIndex < 3) {
                frameIndex++;
            } else {
                frameIndex = 0;
            }
            int currentFrameIndex = frameIndex;
            if (isMoving) {
                currentFrameIndex += 4;
            }
            if (isFacingRight) {
                currentFrameIndex += (images[levelFrameIndex].length / 2);
            }
            charImage = images[levelFrameIndex][currentFrameIndex];
        }


        //Shooting
        shootCount++;
        if (keys[5] && canShoot && shootCount >= shootDelay) {
            shootCount = 0;
            //soundIndex = rnd.nextInt(2) + 2;
            soundIndex = 2;
            playSound = true;
            if (isFacingRight) {
                entities.get(2).add(new Projectile((int) getXPos(), (int) getYPos(), 25, 25, 5, 4));
            } else {
                entities.get(2).add(new Projectile((int) getXPos(), (int) getYPos(), 25, 25, -5, 4));
            }

        }


        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);
    }

    public boolean isInvincible() {
        return invincibleTimer.isRunning();
    }

    public void powerUpActions(PowerUpType value) {
        if (value == PowerUpType.HEALTH) {
            addHealth(1);
            //System.out.println(health);
        }
        if (value == PowerUpType.PROJECTILE) {
            canShoot = true;
        }
        if (value == PowerUpType.INVINCIBLE) {
            invincibleTimer.start();
        }
    }

    public void hurt() {
        if (!hurtTimer.isRunning()) {
            hurtTimer.start();
            health--;
            soundIndex = 1;
            playSound = true;
        }
        if (health <= 0) {
            kill();
        }
    }

    public void addHealth(int value) {
        if (health + value > healthCap) {
            health = healthCap;
        } else {
            health += value;
        }
    }

    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + width / 3, (int) getYPos(), width / 3, height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 4, (int) getYPos() + height - height / 4, width / 2, height / 4);
    }
}
