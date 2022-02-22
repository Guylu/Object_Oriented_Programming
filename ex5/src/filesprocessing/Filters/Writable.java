package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.InvalidFilterValue;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters files that are hidden
 */
public class Writable extends Filter {
    private final String YES = "YES";
    private final String NO = "NO";

    private Boolean writable;
    boolean isNot;


    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public Writable(String command, boolean isNot) throws Type1 {
        if (command.length() > 0)
            command = command.substring(1);
        this.isNot = isNot;
        if (command.equals(YES))
            writable = true;
        else if (command.equals(NO))
            writable = false;
        else throw new InvalidFilterValue("value should only be YES/NO");
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
            if ((file.canWrite() == writable) != isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }
}
