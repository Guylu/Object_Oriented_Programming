package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.InvalidFilterValue;
import filesprocessing.ProjectExceptions.NegativeFilterValue;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters files which size is smaller than the given number
 */
public class SmallerThan extends Filter {
    private double size;
    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public SmallerThan(String command, boolean isNot) throws Type1 {
        this.isNot = isNot;
        try {
            size = Double.parseDouble(command);
        } catch (NumberFormatException e) {
            throw new InvalidFilterValue("only numerical values are valid");
        }
        if (size < 0)
            throw new NegativeFilterValue("invalid values for smaller_than - negative values are invalid");
    }

    /**
     * activates the filtering
     *
     * @param listFiles the files to filter
     * @return filtered files
     */
    public File[] activateFilter(File[] listFiles) {
        ArrayList<File> fileList = new ArrayList<>();
        for (File file : listFiles) {
            if ((file.length() < size * 1024) == !isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }
}
