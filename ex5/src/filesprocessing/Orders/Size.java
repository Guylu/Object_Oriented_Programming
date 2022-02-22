package filesprocessing.Orders;

import filesprocessing.DirectoryProcessor;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * class to represent oder that orders files by size of file
 */
public class Size extends Order {
    /**
     * constructor to build the size organizer
     * @param command the command
     * @throws Type1 throws Type1 exception
     */
    public Size(String command,boolean isReverse) throws Type1 {
        myOrder = DirectoryProcessor.orders.SIZE;
        this.isReverse = isReverse;
    }

    /**
     * abstract method all instances will create to activate the sorting
     *
     * @param listFiles files to filter
     * @return filtered files
     */
    public File[] activateOrder(File[] listFiles) {
        QuickSortFileArray.sort(listFiles, myOrder,isReverse);
        return listFiles;
    }
}
