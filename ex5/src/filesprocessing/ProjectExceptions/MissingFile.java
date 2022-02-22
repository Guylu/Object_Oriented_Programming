package filesprocessing.ProjectExceptions;

/**
 * exception thrown when their is a problem with the given commands file
 */
public class MissingFile extends Type2 {
    /**
     * @param description quick description of the problem
     */
    public MissingFile(String description) {
        super(description);
    }
}
