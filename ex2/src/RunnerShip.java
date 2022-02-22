import oop.ex2.*;

/**
 * this ship is controlled by the (coward) computer.
 * it always attempts to run away from close ships, and will even try to teleport if your getting too close.
 * it will never attempt to fire at you.
 *
 * @author Guy Lutsker
 */
public class RunnerShip extends SpaceShip {
    private final double CRITICAL_ANGLE = 0.23;
    private final double CRITICAL_DISTANCE = 0.25;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method is overridden because each ship behaves differently.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);// run standard code common to all spaceships
        SpaceShipPhysics closestShip = game.getClosestShipTo(this).spaceShipPhysics;
        double angleToClosestShip = spaceShipPhysics.angleTo(closestShip);
        double distanceToClosestShip = spaceShipPhysics.distanceFrom(closestShip);
        // if there is a a ship that is too close to the runner ship.
        if (angleToClosestShip < CRITICAL_ANGLE && distanceToClosestShip < CRITICAL_DISTANCE)
            teleport();
        // turning logic:
        int turn = 0;
        if (angleToClosestShip > 0) turn = -1;
        else if (angleToClosestShip < 0) turn = 1;
        //move the ship
        spaceShipPhysics.move(true, turn);
        regen();
    }
}




