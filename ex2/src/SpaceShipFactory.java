import oop.ex2.*;

/**
 * this class creates spaceship objects according to its input.
 * and out put them as an array of spaceships.
 *
 * @author Guy Lutsker
 */
public class SpaceShipFactory {
    private static final char HUMAN = 'h';
    private static final char RUNNER = 'r';
    private static final char BASHER = 'b';
    private static final char AGGRESSIVE = 'a';
    private static final char DRUNKARD = 'd';
    private static final char SPECIAL = 's';

    /**
     * @param args - input from user- witch spaceships to generate into the game.
     * @return array of spaceships the user asked for.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] spaceShips = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case HUMAN:
                    spaceShips[i] = new HumanShip();
                    break;
                case RUNNER:
                    spaceShips[i] = new RunnerShip();
                    break;
                case BASHER:
                    spaceShips[i] = new BasherShip();
                    break;
                case AGGRESSIVE:
                    spaceShips[i] = new AggressiveShip();
                    break;
                case DRUNKARD:
                    spaceShips[i] = new DrunkardShip();
                    break;
                case SPECIAL:
                    spaceShips[i] = new SpecialShip();
                    break;
            }
        }
        return spaceShips;
    }
}
