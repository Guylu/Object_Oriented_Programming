package filesprocessing.Orders;

import filesprocessing.DirectoryProcessor;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * class to represent order that orders by the type of file(alphabetical)
 */
public class Type extends Order {
    /**
     * constructor to build the type organizer
     *
     * @param command the command
     * @throws Type1 throws Type1 exception
     */
    public Type(String command, boolean isReverse) throws Type1 {
        myOrder = DirectoryProcessor.orders.TYPE;
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
