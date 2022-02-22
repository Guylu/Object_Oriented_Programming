import oop.ex2.*;

/**
 * this ship is controlled by the computer.
 * it will find the ship witch is nearest to it, and try to shot at it.
 */
public class AggressiveShip extends SpaceShip {
    private final double CRITICAL_ANGLE = 0.21;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * will check the user input and act accordingly.
     * this method is overridden because each ship behaves differently.
     *
     * @param game the game object to which this ship belongs.
     * @author Guy Lutsker
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);// run standard code common to all spaceships
        SpaceShipPhysics closestShip = game.getClosestShipTo(this).spaceShipPhysics;
        double angleToClosestShip = spaceShipPhysics.angleTo(closestShip);

        if (angleToClosestShip < CRITICAL_ANGLE) fire(game);
        // turning logic:
        int turn = 0;
        if (angleToClosestShip > 0) turn = 1;
        else if (angleToClosestShip < 0) turn = -1;
        //move the ship:
        spaceShipPhysics.move(true, turn);
        regen();
    }
}
