/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package platforms;

import entities.BasicEnemy;
import entities.Entity;
import entities.Player;
import entities.PowerUp;
import entities.PowerUpHealth;
import entities.PowerUpProjectile;
import entities.Projectile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utilities.TimerDriverV2;

/**
 *
 * @author David
 */
public class SpawnerPowerUpPlatform extends SpawnerPlatform {

    Random rnd;
    TimerDriverV2 spawnTimer;
    int minTime = 3, maxTime = 7;
    int spawnDistance;

    public SpawnerPowerUpPlatform(int xP, int yP, int w, int h, int xV, int yV) {
        super(xP, yP, w, h, xV, yV);
        setColor(new Color(153, 50, 204));
        rnd = new Random();
        spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        spawnDistance = 500;
    }

    public SpawnerPowerUpPlatform(int xP, int yP, String imgName, int xV, int yV) {
        super(xP, yP, imgName, xV, yV);
        rnd = new Random();
        spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
        spawnDistance = 500;
    }

    public void act(ArrayList<List<Entity>> entities) {

        ArrayList<Entity> reactors = processEntities(entities);

        if (spawnTimer.getTimeReached()) {
            spawnTimer.setTimeReached(false);
            spawnTimer.stop();
            spawnTimer = new TimerDriverV2((rnd.nextInt(maxTime) + minTime) * 1000, false);
            spawnEntities(entities);
        }


        for (Entity e : reactors) {

            if (e instanceof Player) {
                if (!spawnTimer.isRunning()) {
                    spawnTimer.start();
                }
            }
        }
    }

    public void spawnEntities(ArrayList<List<Entity>> entities) {
        Entity powerUp = new PowerUp((int) getXPos() + 50, (int) getYPos() - 50, 50, 50, 1, 1);
        switch (rnd.nextInt(2)) {
            case 0:
                powerUp = new PowerUpHealth((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() - (getHeight() / 2)), (int) (getWidth() / 2), (int) (getHeight() / 2), 1, 1);
                break;
            case 1:
                powerUp = new PowerUpProjectile((int) (getXPos() + (getWidth() / 4)), (int) (getYPos() - (getHeight() / 2)), (int) (getWidth() / 2), (int) (getHeight() / 2), 1, 1);
        }
        powerUp.setVerMove(-5);
        entities.get(2).add(powerUp);
        //entities.get(1).get(entities.get(0).size() - 1).setVerMove(-5);
    }
}
