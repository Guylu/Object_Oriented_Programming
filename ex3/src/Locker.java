
import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * class to represent a locker in the spaceship
 */
public class Locker extends Storage{

    /**
     * This constructor initializes a Locker object with the given capacity.
     */
    public Locker(int capacity) {
        this.capacity = capacity;
        storageMap = new HashMap<String, Integer>();
        itemVolume = new HashMap<String, Integer>();
        longTermStorage = new LongTermStorage();// long term storage - LTS
        // boolean vars to hold if the locker contains bats, or balls.
        containsBat = false;
        containsBall = false;
    }

    /**
     * This method adds n Items of the given type to the
     * locker1. If the action is successful, this method should return 0. If n Items
     * cannot be added to the locker1
     * at this time, no Items should be added, the method should return -1, and the
     * following message should
     * be printed to System.out.println: ”Error: Your request cannot be completed at
     * this time. Problem:
     * no room for n items of type type ”. If this action causes Items to be moved to
     * long-term storage,
     * the method should return 1, and the following message should be printed to
     * System.out.println:
     * ”Warning: Action successful, but has caused items to be moved to storage”.
     * In case the long-term
     * storage is full, no Items should be added, the method should return -1, and
     * the following message
     * should be printed to System.out.println: ”Error: Your request cannot be
     * completed at this time.
     * Problem: no room for n Items of type type in the long-term storage. 1
     *
     * @param item: item to add to locker
     * @param n:    how many to add
     * @return 0: all good
     * 1: added, but moved some to LTS
     * -1: no room  for n items
     */


    public int addItem(Item item, int n) {
        // impossible scenario:
        if (item == null)
            return errors(GENERAL_ERROR, RETURN_ERROR_GENERAL);
        //football/baseball bat check:
        if ((item.getType().equals(FOOTBALL) && containsBat) ||
                (item.getType().equals(BAT) && containsBall))
            return errors(ERROR_CONTAINING_CONTRADICTING1 + item.getType()
                    + ERROR_CONTAINING_CONTRADICTING2, RETURN_ERROR_CONTRADICTING);
        if (n == 0)
            return RETURN_ALL_GOOD;
        else if (n < 0)
            return errors(NEGATIVE_ERROR, RETURN_ERROR_GENERAL);
        // check if there's room in the locker:
        if (item.getVolume() * n > getAvailableCapacity())
            return errors(ERROR11 + n + TYPE_ERROR + item.getType(),
                    RETURN_ERROR_GENERAL);
        //initialize vars to add vals to locker:
        int current = getValue(storageMap.get(item.getType())) * item.getVolume();
        int additionToShort = n * item.getVolume();
        int futureSize = current + additionToShort;
        //check if there's "overflow", and we need to move items to LTS
        if (futureSize > capacity / HALF) {
            return over_flow(item, n);
        }
        // regular adding to locker:
        int item_current = getValue(storageMap.get(item.getType()));
        storageMap.put(item.getType(), n + item_current);
        itemVolume.put(item.getType(), item.getVolume());
        // check if we added football/baseball bat:
        limitations(item);
        return RETURN_ALL_GOOD;
    }

    /**
     * football/baseball bat check
     *
     * @param item: item we added to check - football/baseball bat
     */
    private void limitations(Item item) {
        if (item.getType().equals(FOOTBALL)) containsBall = true;
        else if (item.getType().equals(BAT)) containsBat = true;
    }

    /**
     * @param item: item to add
     * @param n:    number of items to add
     * @return 0: all good
     * 1: added, but moved some to LTS
     * -1: no room  for n items
     */
    private int over_flow(Item item, int n) {
        // over flow logic: try to add to LTS in if statement, of doesn't work issue an error
        int keep = (capacity / PERCENTAGE) / item.getVolume();
        int send = getItemCount(item.getType()) + n - keep;
        if (longTermStorage.addItem(item, send) == RETURN_ERROR_GENERAL)
            return RETURN_ERROR_GENERAL;
        // reorganize items in locker:
        if (keep != 0) {
            storageMap.put(item.getType(), keep);
            itemVolume.put(item.getType(), item.getVolume());
            limitations(item);
        }
        System.out.println(WARNING);
        return RETURN_WARNING;
    }


    /**
     * @param msg:       msg to show the user
     * @param returning: return statement for the class to return
     * @return returning!
     */
    private int errors(String msg, int returning) {
        System.out.println(msg);
        return returning;
    }

    /**
     * This method removes n Items of the type type from
     * the locker1. If the action is successful, this method should return 0. In case there are
     * less than n Items
     * of this type in the locker1, no Items should be removed, the method should return -1, and
     * the following
     * message should be printed to System.out.println: ”Error: Your request cannot be completed at
     * this time. Problem: the locker1 does not contain n items of type type ”. In case n is
     * negative, no
     * Items should be removed, the method should return -1, and the following message should
     * be printed
     * to System.out.println: ”Error: Your request cannot be completed at this time.
     * Problem: cannot remove a negative number of items of type type ”.
     *
     * @param item: item to remove
     * @param n:    how many to remove
     * @return 0: successful
     * -1: unable to remove
     */
    public int removeItem(Item item, int n) {
        //impossible:
        if (item == null)
            return errors(GENERAL_ERROR, RETURN_ERROR_GENERAL);
        if (n < 0) return errors(NEGATIVE_ERROR + item.getType(),
                RETURN_ERROR_GENERAL);
        else if (n == 0)
            return RETURN_ALL_GOOD;
            // not enough items in locker:
        else if (getItemCount(item.getType()) < n)
            return errors(NOT_CONTAIN + n + TYPE_ERROR + item.getType(), RETURN_ERROR_GENERAL);
        int current = getValue(storageMap.get(item.getType()));
        //remove all from locker(remove from both maps):
        if (current == n) {
            storageMap.remove(item.getType());
            itemVolume.remove(item.getType());
        } else
            storageMap.put(item.getType(), current - n);
        return RETURN_ALL_GOOD;
    }
}
