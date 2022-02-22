package filesprocessing.Orders;

import filesprocessing.DirectoryProcessor;
import filesprocessing.ProjectExceptions.Type1;

import java.io.File;

/**
 * abstract class to represent an abstract order
 */
public abstract class Order {
    DirectoryProcessor.orders myOrder;
    boolean isReverse;
    /**
     * constructor to build order
     *
     * @throws Type1 throws Type1 exception
     */
    Order() throws Type1 {
    }

    /**
     * abstract method to activate the filtration
     *
     * @param listFiles list of files to sort by a certain order
     * @return the files organized by a certain order
     */
    public abstract File[] activateOrder(File[] listFiles);
}
