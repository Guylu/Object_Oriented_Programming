package filesprocessing.Filters;

import filesprocessing.ProjectExceptions.Type1;

import java.io.File;
import java.util.ArrayList;

/**
 * class to represent filter that filters by which files name are exactly the string given
 */
public class FileFilter extends Filter {
    private String fileName;
    boolean isNot;

    /**
     * constructor of the filter
     *
     * @param command the command to act on
     * @throws Type1 throws Type1 exception
     */
    public FileFilter(String command, boolean isNot) throws Type1 {
        fileName = command;
        this.isNot = isNot;
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
            if (file.getName().equals(fileName) == !isNot)
                fileList.add(file);
        }
        File[] tmp = new File[fileList.size()];

        return fileList.toArray(tmp);
    }

}
