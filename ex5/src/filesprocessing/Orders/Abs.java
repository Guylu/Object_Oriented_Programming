package filesprocessing.Orders;

import filesprocessing.DirectoryProcessor;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * class to represent an orders which orders files by absolute path
 */
public class Abs extends Order {
    /**
     * constructor to build the abs order - order by absolute path alphabetically
     *
     * @param command the command
     * @throws Type1 throws Type1 exception
     */
    public Abs(String command, boolean isReverse) throws Type1 {
        myOrder = DirectoryProcessor.orders.ABS;
        this.isReverse = isReverse;
    }

    /**
     * abstract method all instances will create to activate the sorting
     *
     * @param listFiles files to filter
     * @return filtered files
     */
    public File[] activateOrder(File[] listFiles) {
        QuickSortFileArray.sort(listFiles, myOrder, isReverse);
        return listFiles;
    }
}
