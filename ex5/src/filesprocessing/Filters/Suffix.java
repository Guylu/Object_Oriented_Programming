package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters files which end with the given string
 */
public class Suffix extends Filter {
    private String suffix;
    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public Suffix(String command, boolean isNot) throws Type1 {
        this.isNot = isNot;
        suffix = command;//fileName.lastIndexOf('.');
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
            if ((file.getName().length() >= suffix.length() &&
                    file.getName().substring(file.getName().length() - suffix.length()).equals(suffix)) != isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }
}
