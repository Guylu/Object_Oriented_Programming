package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * abstract class to represent an abstract filter
 */
public abstract class Filter {
    /**
     * constructor of filter class
     *
     * @throws Type1 throws type 1 exception
     */
    public Filter() throws Type1 {

    }

    /**
     * abstract method all instances will create to activate the flirtations
     *
     * @param listFiles files to filter
     * @return filtered files
     */
    public abstract File[] activateFilter(File[] listFiles);
}
