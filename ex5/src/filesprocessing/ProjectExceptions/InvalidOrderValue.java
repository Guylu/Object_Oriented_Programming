package filesprocessing.ProjectExceptions;

/**
 * exception thrown when the line following the line ORDER in not a valid order command
 */
public class InvalidOrderValue extends Type1 {
    /**
     * @param description quick description of the problem
     */
    public InvalidOrderValue(String description) {
        super(description);
    }
}