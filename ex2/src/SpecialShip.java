import oop.ex2.*;

/**
 * i call this "THE GREEZLY BREAR!"
 * this ship is controlled by a scary greezly bear, witch does not want to be disturbed.
 * this bear will not actively try to attack you, unless you provoke it - enter its danger radius.
 * IF you provoke it, he will chase you out of its danger radius, by shooting you.
 * if however the bear is damaged enough, it will try to teleport out of any battle.
 *
 * @author Guy Lutsker
 */
public class SpecialShip extends SpaceShip {
    private final double DANGER_DISTANCE = 0.25;
    private final double SHIELD_FACTOR = 6;
    private final int CRITICAL_HEALTH = 6;


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

        // is it provoked?
        if (distanceToClosestShip > DANGER_DISTANCE) {
            regen();
            return;
        }
        //bear is hurt?
        if (health < CRITICAL_HEALTH && distanceToClosestShip < DANGER_DISTANCE) {
            teleport(); // it is :(   -  run away!
            regen();
            return;
        }
        // bear is provoked and ready for battle!
        // turning logic:
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = 1;
        else if (angleToClosestShip < 0)
            turn = -1;
        if (distanceToClosestShip < DANGER_DISTANCE / SHIELD_FACTOR)
            shieldOn();
        fire(game);
        // move the ship:
        spaceShipPhysics.move(true, turn);
        regen();
    }


}

