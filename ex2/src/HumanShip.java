import oop.ex2.*;

import java.awt.*;

/**
 * this class handles the human controlled spaceship.
 *
 * @author Guy Lutsker
 */
public class HumanShip extends SpaceShip {
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * will check the user input and act accordingly.
     * this method is overridden because each ship behaves differently.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        super.doAction(game);// run standard code common to all spaceships
        GameGUI gui = game.getGUI();// gets the gui to check the users input
        if (gui.isTeleportPressed()) teleport();
        if (gui.isUpPressed() || gui.isRightPressed() || gui.isLeftPressed()) moveShip(gui);
        if (gui.isShieldsPressed()) shieldOn();
        if (gui.isShotPressed()) fire(game);
        regen();
    }

    /**
     * move the ship according to user generated input.
     *
     * @param gui - the gui of the game.
     */
    private void moveShip(GameGUI gui) {
        int turn = getTurning(gui);
        boolean acc = gui.isUpPressed();
        spaceShipPhysics.move(acc, turn);
    }

    /**
     * will check whether the ship needs to turn.
     *
     * @param gui - the gui of the game.
     * @return 1: right
     * 2: left
     * 0: don't turn
     */
    private int getTurning(GameGUI gui) {
        if (gui.isRightPressed() && !gui.isLeftPressed()) return -1;
        if (gui.isLeftPressed() && !gui.isRightPressed()) return 1;
        return 0; //else
    }

    /**
     * is overridden because the human spaceship has a different skin
     *
     * @return the image of the spaceship
     */
    public Image getImage() {
        if (shields)
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        return GameGUI.SPACESHIP_IMAGE;
    }
}
