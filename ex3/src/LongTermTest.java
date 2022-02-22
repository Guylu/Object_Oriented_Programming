
import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LongTermTest {
    private LongTermStorage longTermStorage;
    private Item sporesEngine, helmet1, helmet3, bat, football;
    private String NOT_MATCH = "Results don’t match";

    /**
     * initialize components for tests
     */
    @Before
    public void init() {
        longTermStorage = new LongTermStorage();
        bat = ItemFactory.createSingleItem("baseball bat");
        helmet1 = ItemFactory.createSingleItem("helmet, size 1");
        helmet3 = ItemFactory.createSingleItem("helmet, size 3");
        sporesEngine = ItemFactory.createSingleItem("spores engine");
        football = ItemFactory.createSingleItem("football");
    }

    /**
     * try adding - should pass
     */
    @Test
    public void add1() {
        assertEquals(NOT_MATCH, 0, longTermStorage.addItem(sporesEngine, 1));
    }

    /**
     * try adding - should pass
     */
    @Test
    public void add2() {
        assertEquals(NOT_MATCH, 0, longTermStorage.addItem(helmet3, 100));
    }

    /**
     * try adding - shouldnt pass - negative
     */
    @Test
    public void add3() {
        assertEquals(NOT_MATCH, -1, longTermStorage.addItem(sporesEngine, -10));
    }

    /**
     * try adding - should pass - zero is valid
     */
    @Test
    public void add4() {
        assertEquals(NOT_MATCH, 0, longTermStorage.addItem(bat, 0));
    }

    /**
     * try adding over the capacity
     */
    @Test
    public void add5() {
        assertEquals(NOT_MATCH, -1, longTermStorage.addItem(sporesEngine, 200));
    }


    /**
     * tries adding null object
     */
    @Test
    public void addnull() {
        assertEquals(NOT_MATCH, -1, longTermStorage.addItem(null, 1));
        assertEquals(NOT_MATCH, -1, longTermStorage.addItem(null, 0));
    }

    /**
     * try adding - should pass until it will fill up
     */
    @Test
    public void several_additions1() {
        assertEquals(NOT_MATCH, 0, longTermStorage.addItem(sporesEngine, 80));
        assertEquals(NOT_MATCH, 0, longTermStorage.addItem(helmet3, 20));
        assertEquals(NOT_MATCH, -1, longTermStorage.addItem(sporesEngine, 50));
    }

    @Test
    public void several_additions2() {
        longTermStorage.addItem(sporesEngine, 100);
        assertEquals("test failed", -1, longTermStorage.addItem(helmet3, 4));
    }

    /**
     * try reset - should pass
     */
    @Test
    public void reset1() {
        longTermStorage.addItem(sporesEngine, 10);
        longTermStorage.resetInventory();
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
    }

    /**
     * try reseting several times.
     */
    @Test
    public void reset2() {
        longTermStorage.addItem(sporesEngine, 1);
        assertEquals(NOT_MATCH, 1, longTermStorage.getItemCount(sporesEngine.getType()));
        longTermStorage.resetInventory();
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
        longTermStorage.addItem(sporesEngine, 5);
        assertEquals(NOT_MATCH, 5, longTermStorage.getItemCount(sporesEngine.getType()));
        longTermStorage.resetInventory();
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
    }

    /**
     * try see if reseting empty LTS will work
     */
    @Test
    public void empty_reset1() {
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
        longTermStorage.resetInventory();
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
    }

    /**
     * try adding - and check if the item count changed accordingly
     */
    @Test
    public void getItemCount1() {
        add1();
        assertEquals(NOT_MATCH, 1, longTermStorage.getItemCount(sporesEngine.getType()));
    }

    /**
     * try adding(empty) - and check if the item count changed accordingly
     */
    @Test
    public void getItemCount2() {
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(sporesEngine.getType()));
    }

    /**
     * trues to add and remove during counting  - seeing everything works properly
     */
    @Test
    public void getItemCount3() {
        longTermStorage.addItem(bat, 9);
        longTermStorage.addItem(helmet3, 18);
        assertEquals(NOT_MATCH, 9, longTermStorage.getItemCount(bat.getType()));
        longTermStorage.resetInventory();
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(bat.getType()));
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(helmet3.getType()));
        assertEquals(NOT_MATCH, 0, longTermStorage.getItemCount(helmet1.getType()));
        longTermStorage.addItem(bat, 9);
        assertEquals(NOT_MATCH, 9, longTermStorage.getItemCount(bat.getType()));
    }

    /**
     * try adding a lot and see if the map is correct
     */
    @Test
    public void get_map1() {
        additions();
        assertEquals(NOT_MATCH,
                "{helmet, size 1=3, baseball bat=2, helmet, size 3=4, football=10, spores engine=6}",
                longTermStorage.getInventory().toString());
    }

    /**
     * empty map
     */
    @Test
    public void get_map2() {
        assertTrue(NOT_MATCH, longTermStorage.getInventory().isEmpty());
    }
    /**
     * empty map
     */
    @Test
    public void get_map3() {
        additions();
        assertEquals(NOT_MATCH,
                "{helmet, size 1=3, baseball bat=2, helmet, size 3=4, football=10, spores engine=6}",
                longTermStorage.getInventory().toString());
        longTermStorage.resetInventory();
        assertTrue(NOT_MATCH, longTermStorage.getInventory().isEmpty());
    }
    private void additions(){
        longTermStorage.addItem(sporesEngine, 1);
        longTermStorage.addItem(bat, 2);
        longTermStorage.addItem(helmet1, 3);
        longTermStorage.addItem(helmet3, 4);
        longTermStorage.addItem(football, 10);
        longTermStorage.addItem(sporesEngine, 5);
    }
    /**
     * check the capacity
     */
    @Test
    public void get_capacity() {
        assertEquals(NOT_MATCH, 1000, longTermStorage.getCapacity());

    }

    /**
     * check the available capacity after adding
     */
    @Test
    public void getAvailableCapacity1() {
        longTermStorage.addItem(sporesEngine, 1);
        assertEquals(NOT_MATCH, 990, longTermStorage.getAvailableCapacity());
    }
    /**
     * check the available capacity after adding
     */
    @Test
    public void getAvailableCapacity2() {
        additions();
        assertEquals(NOT_MATCH, 867, longTermStorage.getAvailableCapacity());
    }
    /**
     * check the available capacity after adding to max capacity
     */
    @Test
    public void getAvailableCapacity3() {
        longTermStorage.addItem(sporesEngine, 100);
        assertEquals(NOT_MATCH, 0, longTermStorage.getAvailableCapacity());
    }
    /**
     * check the available capacity after adding 0
     */
    @Test
    public void getAvailableCapacity4() {
        longTermStorage.addItem(sporesEngine, 0);
        assertEquals(NOT_MATCH, 1000, longTermStorage.getAvailableCapacity());
        longTermStorage.addItem(bat, 0);
        assertEquals(NOT_MATCH, 1000, longTermStorage.getAvailableCapacity());
        longTermStorage.addItem(helmet3, 0);
        assertEquals(NOT_MATCH, 1000, longTermStorage.getAvailableCapacity());
    }
}
