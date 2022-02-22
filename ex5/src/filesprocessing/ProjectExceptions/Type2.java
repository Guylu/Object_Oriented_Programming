package filesprocessing.ProjectExceptions;

/**
 * exception of type2 as explained in the pdf
 */
public class Type2 extends Exception {
    public int line;
    public String description;

    /**
     * @param line        the line of the error
     * @param description quick description of the problem
     */
    Type2(int line, String description) {
        this.line = line;
        this.description = description;
    }

    /**
     *
     * @param description quick description of the problem
     */
    Type2(String description) {
        this.description = description;
    }
}

