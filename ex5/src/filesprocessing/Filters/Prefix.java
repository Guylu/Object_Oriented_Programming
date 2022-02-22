package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters files which start with the given string
 */
public class Prefix extends Filter {
    private String prefix;
    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public Prefix(String command, boolean isNot) throws Type1 {
        this.isNot = isNot;
        prefix = command;
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
            if ((file.getName().length() >= prefix.length() &&
                    file.getName().substring(0, prefix.length()).equals(prefix)) != isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }

}
