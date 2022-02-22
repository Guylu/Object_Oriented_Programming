
import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * this class represents the LTS
 */
public class LongTermStorage extends Storage {

    /**
     * This constructor initializes a Long-Term Storage object.
     */
    public LongTermStorage() {
        this.capacity = CAPACITY;
        storageMap = new HashMap<String, Integer>();
        itemVolume = new HashMap<String, Integer>();
    }

    /**
     * This method adds n Items of the given type to the longterm storage unit. If the action is successful, this method should return 0. If n Items cannot be added
     * to the locker at this time, no Items should be added, the method should return -1, and the following
     * message should be printed to System.out.println: ”Error: Your request cannot be completed at this
     * time. Problem: no room for n Items of type type ”.
     *
     * @param item: item to add to locker
     * @param n:    how many to add
     * @return 0: all good
     * -1: no room  for n items
     */
    public int addItem(Item item, int n) {
        if (item == null)
            return errors(GENERAL_ERROR);
        if (item.getVolume() * n > getAvailableCapacity())
            return errors(ERROR11 + n + TYPE_ERROR + item.getType());
        if (n < 0)
            return errors(NEGATIVE_ERROR);
        else if (n == 0)
            return RETURN_ALL_GOOD;
        int current = getValue(storageMap.get(item.getType()));
        storageMap.put(item.getType(), n + current);
        itemVolume.put(item.getType(), item.getVolume());
        return RETURN_ALL_GOOD;
    }

    /**
     * This method resets the long-term storage’s inventory (i.e. after  it is invoked the inventory does not contain any Item).
     */
    public void resetInventory() {
        storageMap.clear();
        itemVolume.clear();
    }

    /**
     * @param msg: msg to show the user
     * @return -1
     */
    private int errors(String msg) {
        System.out.println(msg);
        return RETURN_ERROR_GENERAL;
    }

}