import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author Guy Lutsker
 */

public class SpaceShip {
    private final int MAX_ENERGY = 210, CURRENT_ENERGY = 190, HEALTH = 22;
    private final int INCREASE_ENERGY = 18;
    private final int DECREASE_ENERGY = 10;
    private final int ENERGY_THRESHOLD_FIRING = 19;
    private final int ENERGY_THRESHOLD_TELEPORTING = 140;
    private final int ENERGY_THRESHOLD_SHIELD = 3;
    private final int COOLING_TIME = 7;

    protected SpaceShipPhysics spaceShipPhysics = new SpaceShipPhysics();
    private int maxEnergy = MAX_ENERGY, currentEnergy = CURRENT_ENERGY;
    protected int health = HEALTH;
    protected boolean shields = false;
    private int coolTime = 0;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this code is called by each spaceship every turn and so only does things that are common across
     * all of the types of spaceships.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        shields = false;
        if (coolTime > 0) coolTime--;

    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {

        if (shields) {
            maxEnergy += INCREASE_ENERGY;
            currentEnergy += INCREASE_ENERGY;
        } else {
            maxEnergy -= DECREASE_ENERGY;
            if (currentEnergy > maxEnergy)
                currentEnergy = maxEnergy;
            health--;
        }

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random spaceShipPhysics.
     */
    public void reset() {
        spaceShipPhysics = new SpaceShipPhysics();
        maxEnergy = MAX_ENERGY;
        currentEnergy = CURRENT_ENERGY;
        health = HEALTH;
        shields = false;
        coolTime = 0;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return spaceShipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!shields) health--;
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (shields)
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (currentEnergy >= ENERGY_THRESHOLD_FIRING && coolTime == 0) {
            currentEnergy -= ENERGY_THRESHOLD_FIRING;
            game.addShot(spaceShipPhysics);
            coolTime = COOLING_TIME;
        }

    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (currentEnergy >= ENERGY_THRESHOLD_SHIELD) {
            currentEnergy -= ENERGY_THRESHOLD_SHIELD;
            shields = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (currentEnergy >= ENERGY_THRESHOLD_TELEPORTING) {
            currentEnergy -= ENERGY_THRESHOLD_TELEPORTING;
            spaceShipPhysics = new SpaceShipPhysics();
        }
    }

    /**
     * function to regen current energy by 1
     */
    protected void regen() {
        if (currentEnergy < maxEnergy) currentEnergy++;
    }

}
