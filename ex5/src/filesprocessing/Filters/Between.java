package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.InvalidFilterValue;
import filesprocessing.ProjectExceptions.NegativeFilterValue;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters files which size in kb is between to given numbers
 */
public class Between extends Filter {
    private final char HASH = '#';
    private final int KB = 1024;


    private double lower, upper;
    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public Between(String command, boolean isNot) throws Type1 {
        int i = 0;
        String lowerStr = "";
        String upperStr = "";
        this.isNot = isNot;
        //gets lower bound
        while (command.charAt(i) != HASH) {
            lowerStr += command.charAt(i);
            i++;
        }
        //gets upper bound
        for (i++; i < command.length(); i++) {
            upperStr += command.charAt(i);
        }
        // checks validity of bounds
        try {
            lower = Double.parseDouble(lowerStr) * KB;
            upper = Double.parseDouble(upperStr) * KB;
            if (upper < 0 || lower < 0)
                throw new NegativeFilterValue("invalid values for upper and lower - negative values are invalid");
            if (upper < lower)
                throw new InvalidFilterValue("invalid values for upper and lower - probably lower>upper");

        } catch (NumberFormatException e) {
            throw new InvalidFilterValue("one of the bounds(upper of lower) is not a number");
        }


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
            if ((file.length() <= upper && file.length() >= lower) != isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }

}
