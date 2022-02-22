import oop.ex2.*;

/**
 *this ship is controlled by the computer.
 * it will try to "bash" into the closest ship it can find.
 */
public class BasherShip extends SpaceShip {
    private final double CRITICAL_DISTANCE = 0.19;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method is overridden because each ship behaves differently.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);// run standard code common to all spaceships
        SpaceShipPhysics closestShip = game.getClosestShipTo(this).spaceShipPhysics;
        double angleToClosestShip = spaceShipPhysics.angleTo(closestShip);
        double distanceToClosestShip = spaceShipPhysics.distanceFrom(closestShip);

        if (distanceToClosestShip < CRITICAL_DISTANCE)
            shieldOn();
        // turning logic:
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = 1;
        else if (angleToClosestShip < 0)
            turn = -1;
        // move the ship:
        spaceShipPhysics.move(true, turn);
        regen();
    }
}
