package filesprocessing.ProjectExceptions;

/**
 * exception thrown when a filters description that needs to filter an integer is negative
 */
public class NegativeFilterValue extends InvalidFilterValue {
    /**
     * @param description quick description of the problem
     */
    public NegativeFilterValue(String description) {
        super(description);
    }
}
