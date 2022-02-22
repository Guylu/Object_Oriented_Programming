import java.util.Random;

/**
 * this ship is controlled by the (drunk) computer.
 * its driver is hitting all the buttons an the dashboard and is completely unpredictable.
 *
 * @author Guy Lutsker
 */
public class DrunkardShip extends SpaceShip {
    private final int TURNING_FACTOR = 6;
    private final int ACCELERATION_FACTOR = 1;
    private final int SPECIAL_EFFECTS_FACTOR = 80;
    private final int RIGHT_OR_LEFT_FACTOR = 2;
    private final int TELEPORT_FACTOR = 2;
    private final int SHIELD_FACTOR = 15;
    private final int SHOOTING_FACTOR = 69;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method is overridden because each ship behaves differently.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);// run standard code common to all spaceships
        Random r = new Random();
        // what follows here is a random piece of code, witch number were decided by me trying to simulate
        // a drunken driver. they are not following any logic, and their only purpose is make the ship behave
        // in a weird unpredictable way.

        int rnd1 = r.nextInt(TURNING_FACTOR);
        int rnd2 = r.nextInt(ACCELERATION_FACTOR);
        int rnd3 = r.nextInt(SPECIAL_EFFECTS_FACTOR);
        int turn = 0;
        if (rnd1 > RIGHT_OR_LEFT_FACTOR) turn = 1;
        else if (rnd1 < RIGHT_OR_LEFT_FACTOR) turn = -1;
        boolean acc = rnd1 > rnd2;
        spaceShipPhysics.move(acc, turn);

        if (rnd3 % TELEPORT_FACTOR == 0) teleport();
        if (rnd3 % SHIELD_FACTOR == 0) shieldOn();
        if (rnd3 % SHOOTING_FACTOR == 0) fire(game);
        regen();

    }
}
