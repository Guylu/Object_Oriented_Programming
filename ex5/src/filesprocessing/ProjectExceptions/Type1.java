package filesprocessing.ProjectExceptions;

/**
 * exception of type1 as explained in the pdf
 */
public class Type1 extends Exception {
    public String description;

    /**
     * @param description quick description of the problem
     */
    public Type1(String description) {
        this.description = description;
    }

}
