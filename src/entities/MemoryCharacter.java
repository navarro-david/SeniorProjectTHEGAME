/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author bhsjava
 */
public class MemoryCharacter extends TalkableCharacter {

    boolean gotMemory;

    public MemoryCharacter(int xP, int yP, int w, int h, int xV, int yV, String[] msgs) {
        super(xP, yP, w, h, xV, yV, msgs);
        isSolid = false;
        health = 1;
        isAlive = true;
        gotMemory = false;
    }

    public MemoryCharacter(int xP, int yP, String imgName, int xV, int yV, String[] msgs) {
        super(xP, yP, imgName, xV, yV, msgs);
        isSolid = false;
        health = 1;
        isAlive = true;
        gotMemory = false;

    }

    public void setProgress(boolean[] progress, int levelIndex[]) {
        if (gotMemory || progress.length <= 0) {
            return;
        }
        gotMemory = true;

        if (!progress[(levelIndex[0] * 2) - 2]) {
            progress[(levelIndex[0] * 2) - 2] = true;
            soundIndex = 2;
            playSound = true;
        } else if (!progress[(levelIndex[0] * 2) - 1]) {
            progress[(levelIndex[0] * 2) - 1] = true;
            soundIndex = 2;
            playSound = true;
        }

    }
}
