package filesprocessing.ProjectExceptions;

/**
 * exception thrown when line FILTER/ORDER doesn't appear where it is supposed to
 */
public class MissingCommand extends Type2 {
    /**
     * @param description quick description of the problem
     */
    public MissingCommand(int line, String description) {
        super(line, description);
    }
}
