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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import utilities.PowerUpType;
import utilities.SoundDriverV2;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class Entity {

    protected Rectangle hitbox;
    protected Rectangle[] hitboxes = new Rectangle[4];
    protected Line2D.Double[] sides = new Line2D.Double[4];
    protected int width, height, xPos, yPos, xVel, yVel, health, srtXPos, srtYPos;
    protected double gravity = 0.075, friction = 0.15, horMove = 0, verMove = 0;
    protected boolean isFacingRight, isOnGround, canMoveInRightDir, canMoveInLeftDir, isAlive, isSolid, isInWater, playSound;
    protected Color color;
    protected BufferedImage charImage;
    //protected TimerDriverV2 talkTimer;
    //protected SoundDriverV2 sounds;
    protected int soundIndex;
    protected String[] messages/*, soundNames*/;
    protected PowerUpType powerUp;

    public Entity(int xP, int yP, int w, int h, int xV, int yV, Color c) {
        charImage = null;
        width = w;
        height = h;
        xPos = xP;
        yPos = yP;
        xVel = xV;
        yVel = yV;
        srtXPos = xP;
        srtYPos = yP;
        health = 1;
        hitbox = new Rectangle(xPos, yPos, width, height);
        setSides();
        setHitBoxes();
        isFacingRight = true;
        isOnGround = false;
        canMoveInRightDir = true;
        canMoveInLeftDir = true;
        isAlive = true;
        isSolid = true;
        isInWater = false;
        color = c;
        //talkTimer = new TimerDriverV2(4000, true);
        //soundNames = new String[1];
        //soundNames[0] = "audio/sounds/hurt6.wav";
        //sounds = new SoundDriverV2(soundNames);
        soundIndex = 0;
        messages = new String[3];
        messages[0] = "Hello World! I am " + this;
        messages[1] = "I Think cats are cool!";
        messages[2] = "S..J..S..U! San Jose State!";
        powerUp = PowerUpType.NONE;
    }

    public Entity(int xP, int yP, String imgName, int xV, int yV) {

        try {
            charImage = ImageIO.read(new File(imgName));
            width = charImage.getWidth();
            height = charImage.getHeight();
            xPos = xP;
            yPos = yP;
            xVel = xV;
            yVel = yV;
            srtXPos = xP;
            srtYPos = yP;
            health = 1;
            hitbox = new Rectangle(xPos, yPos, width, height);
            setSides();
            setHitBoxes();
            isFacingRight = true;
            isOnGround = false;
            canMoveInRightDir = true;
            canMoveInLeftDir = true;
            isAlive = true;
            isSolid = true;
            isInWater = false;
            color = Color.BLUE;
            //talkTimer = new TimerDriverV2(4000, true);
            //soundNames = new String[1];
            //soundNames[0] = "audio/sounds/hurt6.wav";
            //sounds = new SoundDriverV2(soundNames);
            soundIndex = 0;
            messages = new String[3];
            messages[0] = "Hello World! I am " + this;
            messages[1] = "I Think cats are cool!";
            messages[2] = "S..J..S..U! San Jose State!";
            powerUp = PowerUpType.NONE;
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void drawEntity(Graphics2D win) {
        if (charImage != null) {
            win.drawImage(charImage, (int) getXPos(), (int) getYPos(), null);
        } else {
            win.setColor(color);
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
        /*if (playSound) {
         sounds.play(soundIndex);
         playSound = false;
         }*/

        /*if (talkTimer.getCount() > messages.length) {
         talkTimer.resetCount();
         talkTimer.stop();
         } else if (talkTimer.isRunning()) {
         if (talkTimer.getCount() < messages.length) {
                
         for (String s : messages) { System.out.println(s);
         }
                 
         win.setColor(new Color(255, 255, 255, 128));
         int strWidth = win.getFontMetrics().stringWidth(messages[talkTimer.getCount()]), strHeight = win.getFontMetrics().getHeight();
         win.fillRect((int) ((getXPos() + width / 2) - (strWidth / 2)), (int) getYPos() - 20, strWidth, strHeight);
         win.setColor(Color.BLACK);
         win.drawString(messages[talkTimer.getCount()], (int) ((getXPos() + width / 2) - (strWidth / 2)), (int) (getYPos() - 22 + strHeight));
         }

         }*/
    }

    public void moveEntity(int xDir, int yDir) {
        hitbox.translate(xDir, yDir);
        setSides();
        setHitBoxes();
    }

    public ArrayList<Entity> processEntities(ArrayList<List<Entity>> entities) {
        ArrayList<Entity> reactors = new ArrayList<Entity>();
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(i).size(); j++) {
                if (entities.get(i).get(j).equals(this)) {
                    continue;
                }
                if (hitbox.intersects(entities.get(i).get(j).getHitBox())) {
                    reactors.add(entities.get(i).get(j));
                }
            }
        }
        return reactors;
    }

    public void act(ArrayList<List<Entity>> entities) {
        ArrayList<Entity> reactors = processEntities(entities);
        int newX = (int) getXPos(), newY = (int) getYPos();

        if (!isOnGround || reactors.size() <= 0) {
            verMove += gravity;
            isOnGround = false;
        } else {
            verMove = 0;
        }

        for (Entity e : reactors) {
            if (e.isSolid()) {
                if (hitboxes[3].intersects(e.getHitBox())) {
                    isOnGround = true;
                    //isJumping = false;
                    newY = (int) e.getYPos() - height + 1;
                }
                if (hitboxes[1].intersects(e.getHitBox())) {
                    newX = (int) e.getXPos() + (int) e.getWidth();
                }
                if (hitboxes[2].intersects(e.getHitBox())) {
                    newX = (int) e.getXPos() - width;
                }
                if (hitboxes[0].intersects(e.getHitBox())) {
                    newY = (int) e.getYPos() + (int) e.getHeight();
                    verMove = 0;
                }
            }
        }

        setHitboxLocation(newX, newY);
        moveEntity((int) horMove, (int) verMove);


    }

    //<editor-fold defaultstate="collapsed" desc=""Character" Methods">
    public void hurt() {
        health--;
        //soundIndex = 0;
        //sounds.play(soundIndex);
        if (health <= 0) {

            kill();
        }
    }

    public void kill() {
        setHealth(0);
        isAlive = false;
        color = Color.BLACK;
        //System.out.println("Ouch! I " + toString() + " am now dead! *breaks down in tears* ");
    }

    public void talk() {
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] value) {
        messages = null;
        messages = value;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int value) {
        health = value;
    }

    public void addHealth(int value) {
        health += value;
    }

    public void setIsInWater(boolean value) {
        isInWater = value;
    }

    public boolean isInWater() {
        return isInWater;
    }

    public void setImage(String name) {

        charImage = null;

        try {
            charImage = ImageIO.read(new File(name));

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public BufferedImage addImage(String name) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(name));

        } catch (IOException e) {
            System.out.println(e);
        }
        
        return image;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    public final Rectangle getHitBox() {
        return hitbox;
    }

    public final Line2D.Double[] getSidesArray() {
        return sides;
    }

    public final Rectangle[] getHitBoxesArray() {
        return hitboxes;
    }

    public final double getHeight() {
        return hitbox.getHeight();
    }

    public final double getWidth() {
        return hitbox.getWidth();
    }

    public final double getXPos() {
        return hitbox.getX();
    }

    public final double getYPos() {
        return hitbox.getY();
    }

    public final int getXVel() {
        return xVel;
    }

    public final int getYVel() {
        return yVel;
    }

    public final int getSrtXPos() {
        return srtXPos;
    }

    public final int getSrtYPos() {
        return srtYPos;
    }

    public final double getHorMove() {
        return horMove;
    }

    public final double getVerMove() {
        return verMove;
    }

    public final boolean isFacingRight() {
        return isFacingRight;
    }

    public final boolean isOnGround() {
        return isOnGround;
    }

    public final boolean canMoveInLeftDir() {
        return canMoveInLeftDir;
    }

    public final boolean canMoveInRightDir() {
        return canMoveInRightDir;
    }

    public final boolean isSolid() {
        return isSolid;
    }

    public final Color getColor() {
        return color;
    }

    public final PowerUpType getPowerUp() {
        return powerUp;
    }

    /*public final String[] getSoundNames() {
     return soundNames;
     }*/
    //</editor-fold>    //standard get methods that access value of fields
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    public void setHitBoxes() {
        hitboxes[0] = new Rectangle((int) getXPos() + width / 3, (int) getYPos(), width / 3, height / 4);
        hitboxes[1] = new Rectangle((int) getXPos(), (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[2] = new Rectangle((int) getXPos() + width / 2, (int) getYPos() + height / 4, width / 2, height / 2);
        hitboxes[3] = new Rectangle((int) getXPos() + width / 3, (int) getYPos() + height - height / 4, width / 3, height / 4);
    }

    public final void setSides() {
        sides[0] = (new Line2D.Double(getXPos(), getYPos() + 1, getXPos(), getYPos() + height - 2));
        sides[1] = (new Line2D.Double(getXPos() + width - 1, getYPos() + 1, getXPos() + width - 1, getYPos() + height - 2));
        sides[2] = (new Line2D.Double(getXPos() + 1, getYPos(), getXPos() + width - 2, getYPos()));
        sides[3] = (new Line2D.Double(getXPos() + 1, getYPos() + height - 1, getXPos() + width - 2, getYPos() + height - 1));

    }

    public final void setHeight(int value) {
        height = value;
        hitbox.setSize(width, height);
        setSides();
        setHitBoxes();
    }

    public final void setWidth(int value) {
        width = value;
        hitbox.setSize(width, height);
        setSides();
        setHitBoxes();
    }

    public final void setHitboxSize(int wValue, int hValue) {
        width = wValue;
        height = hValue;
        hitbox.setSize(width, height);
        setSides();
        setHitBoxes();
    }

    public final void setXPos(int value) {
        xPos = value;
        hitbox.setLocation(xPos, yPos);
        setSides();
        setHitBoxes();
    }

    public final void setYPos(int value) {
        yPos = value;
        hitbox.setLocation(xPos, yPos);
        setSides();
        setHitBoxes();
    }

    public void setHitboxLocation(int xValue, int yValue) {
        xPos = xValue;
        yPos = yValue;
        hitbox.setLocation(xPos, yPos);
        setSides();
        setHitBoxes();
    }

    public final void setXVel(int value) {
        xVel = value;
    }

    public final void setYVel(int value) {
        yVel = value;
    }

    public final void setHorMove(double value) {
        horMove = value;
    }

    public final void setVerMove(double value) {
        verMove = value;
    }

    public final void setIsFacingRight(boolean value) {
        isFacingRight = value;
    }

    public final void setIsOnGround(boolean value) {
        isOnGround = value;
    }

    public final void setCanMoveInRightDir(boolean value) {
        canMoveInRightDir = value;
    }

    public final void setCanMoveInLeftDir(boolean value) {
        canMoveInLeftDir = value;
    }

    public final void setIsSolid(boolean value) {
        isSolid = value;
    }

    public final void setColor(Color value) {
        color = value;
    }

    public final void setPowerUp(PowerUpType value) {
        powerUp = value;
    }

    /*public final void setSoundNames(String[] value) {
     soundNames = value;
     sounds = new SoundDriverV2(soundNames);
     soundIndex = 0;
     }*/
    //</editor-fold>    //standard set methods that modify value of fields
}
