package filesprocessing.ProjectExceptions;

/**
 * exception thrown when their is a problem with the given directory
 */
public class MissingDirectory extends Type2 {
    /**
     * @param description quick description of the problem
     */
    public MissingDirectory(String description) {
        super(description);
    }
}
