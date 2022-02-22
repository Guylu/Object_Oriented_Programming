package filesprocessing.ProjectExceptions;

/**
 * exception thrown when the line following the line FILTER in not a valid filter
 */
public class InvalidFilterValue extends Type1 {
    /**
     * @param description quick description of the problem
     */
    public InvalidFilterValue(String description) {
        super(description);
    }
}
