package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * class to represent filter that filters nothing
 */
public class All extends Filter {

    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public All(String command, boolean isNot) throws Type1 {
        if (!command.equals(""))
            throw new Type1( "invalid input after 'all' command");
        this.isNot = isNot;
    }

    /**
     * activates the filtering
     *
     * @param listFiles the files to filter
     * @return filtered files
     */
    public File[] activateFilter(File[] listFiles) {
        if (!isNot)
            return listFiles;
        return new File[0];
    }


}
