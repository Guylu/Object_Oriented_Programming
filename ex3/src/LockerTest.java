
import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.*;

public class LockerTest {
    private Locker locker0, locker1, locker2, locker3;
    private Item sporesEngine, helmet1, helmet3, bat, football;
    private String NOT_MATCH = "Results don’t match";

    /**
     * initialize components for tests
     */
    @Before
    public void good_init() {
        locker0 = new Locker(6);
        locker1 = new Locker(10);
        locker2 = new Locker(100);
        locker3 = new Locker(990);
        bat = ItemFactory.createSingleItem("baseball bat");
        helmet1 = ItemFactory.createSingleItem("helmet, size 1");
        helmet3 = ItemFactory.createSingleItem("helmet, size 3");
        sporesEngine = ItemFactory.createSingleItem("spores engine");
        football = ItemFactory.createSingleItem("football");
    }

//    @Test(expected = NumberFormatException.class)
//    public void init() {
//        Locker locker1 = new Locker("g");
//    }

    /**
     * general adding - should pass
     */
    @Test
    public void add1() {
        assertEquals(NOT_MATCH, 0, locker1.addItem(bat, 1));
    }

    /**
     * general adding - shouldnt pass
     */
    @Test
    public void add2() {
        assertEquals(NOT_MATCH, -1, locker1.addItem(sporesEngine, 2));
    }

    /**
     * general adding - shouldnt pass
     */
    @Test
    public void add3() {
        assertEquals(NOT_MATCH, -1, locker1.addItem(sporesEngine, -1));
    }

    /**
     * general adding - first should pass, second should also but will overflow
     */
    @Test
    public void add4() {
        assertEquals(NOT_MATCH, 0, locker1.addItem(helmet3, 1));
        assertEquals(NOT_MATCH, 1, locker1.addItem(helmet3, 1));
    }

    /**
     * general adding - should pass - 0 is legal
     */
    @Test
    public void add5() {
        assertEquals(NOT_MATCH, 0, locker1.addItem(helmet1, 0));
    }

    /**
     * general adding - should pass
     */
    @Test
    public void add6() {
        assertEquals(NOT_MATCH, 0, locker2.addItem(bat, 2));
    }

    /**
     * general adding - shouldnt pass - over the capacity
     */
    @Test
    public void add7() {
        assertEquals(NOT_MATCH, -1, locker1.addItem(bat, 6));
    }

    /**
     * general adding - should pass until it will fill up - over the available capacity
     */
    @Test
    public void add8() {
        // locker is full
        assertEquals(NOT_MATCH, 0, locker0.addItem(bat, 1));
        assertEquals(NOT_MATCH, 0, locker0.addItem(helmet1, 1));
        assertEquals(NOT_MATCH, -1, locker0.addItem(helmet3, 1));
    }

    /**
     * tries adding null object
     */
    @Test
    public void addnull() {
        assertEquals(NOT_MATCH, -1, locker1.addItem(null, 1));
        assertEquals(NOT_MATCH, -1, locker1.addItem(null, 0));
    }

    /**
     * remove checking - shouldnt pass - went to LTS
     */
    @Test
    public void remove1() {
        locker1.addItem(sporesEngine, 1);
        assertEquals(NOT_MATCH, -1, locker1.removeItem(sporesEngine, 1));
    }

    /**
     * remove checking - shouldnt pass - its empty
     */
    @Test
    public void remove2() {
        assertEquals(NOT_MATCH, -1, locker1.removeItem(sporesEngine, 1));
    }

    /**
     * remove checking
     */
    @Test
    public void remove3() {
        locker1.addItem(bat, 1);
        assertEquals(NOT_MATCH, -1, locker1.removeItem(sporesEngine, 2));
        assertEquals(NOT_MATCH, 0, locker1.removeItem(bat, 1));
        assertEquals(NOT_MATCH, -1, locker1.removeItem(bat, 1));
        assertEquals(NOT_MATCH, 0, locker1.removeItem(sporesEngine, 0));
    }

    /**
     * remove checking - shouldn't pass - its empty, and its over the capacity
     */
    @Test
    public void remove4() {
        assertEquals(NOT_MATCH, -1, locker3.removeItem(bat, 990));
    }

    /**
     * remove checking - shouldn't pass - its empty, and its over the capacity
     */
    @Test
    public void remove5() {
        assertEquals(NOT_MATCH, -1, locker3.removeItem(bat, -1));
    }

    /**
     * remove checking - shouldn't pass - its empty, and its over the capacity
     */
    @Test
    public void remove6() {
        assertEquals(NOT_MATCH, -1, locker3.removeItem(null, 1));
    }

    /**
     * cheking item counts
     */
    @Test
    public void getItemCount1() {
        locker1.addItem(bat, 2);
        assertEquals(NOT_MATCH, 2, locker1.getItemCount(bat.getType()));
    }

    /**
     * cheking item counts - complex test
     */
    @Test
    public void getItemCount2() {
        locker2.addItem(sporesEngine, 1);
        assertEquals(NOT_MATCH, 1, locker2.getItemCount(sporesEngine.getType()));
        locker2.removeItem(sporesEngine, 1);
        assertEquals(NOT_MATCH, 0, locker2.getItemCount(sporesEngine.getType()));
        assertEquals(NOT_MATCH, 0, locker2.getItemCount(bat.getType()));
        add6();
        assertEquals(NOT_MATCH, 2, locker2.getItemCount(bat.getType()));
        locker2.removeItem(bat, 1);
        assertEquals(NOT_MATCH, 1, locker2.getItemCount(bat.getType()));
        locker2.removeItem(bat, 1);
        assertEquals(NOT_MATCH, 0, locker2.getItemCount(bat.getType()));
    }

    /**
     * trues to add and remove during counting  - seeing everything works properly
     */
    @Test
    public void getItemCount3() {
        locker3.addItem(bat, 9);
        locker3.addItem(helmet3, 18);
        assertEquals(NOT_MATCH, 9, locker3.getItemCount(bat.getType()));
        locker3.removeItem(bat, 7);
        assertEquals(NOT_MATCH, 2, locker3.getItemCount(bat.getType()));
        assertEquals(NOT_MATCH, 18, locker3.getItemCount(helmet3.getType()));
        locker3.removeItem(helmet3, 17);
        assertEquals(NOT_MATCH, 1, locker3.getItemCount(helmet3.getType()));
        locker3.removeItem(helmet3, 17);
        assertEquals(NOT_MATCH, 1, locker3.getItemCount(helmet3.getType()));

    }

    /**
     * checking the returning map - against string
     */
    @Test
    public void get_map1() {
        locker3.addItem(sporesEngine, 1);
        locker3.addItem(bat, 2);
        locker3.addItem(helmet1, 3);
        locker3.addItem(helmet3, 4);
        locker3.addItem(sporesEngine, 5);
        assertEquals(NOT_MATCH,
                "{helmet, size 1=3, baseball bat=2, helmet, size 3=4, spores engine=6}",
                locker3.getInventory().toString());
    }

    /**
     * checking the returning map - against string
     */
    @Test
    public void get_map2() {
        add1();
        add2();
        add3();
        add5();
        assertEquals(NOT_MATCH, "{baseball bat=1}",
                locker1.getInventory().toString());
    }

    /**
     * empty map
     */
    @Test
    public void get_map3() {
        assertTrue(NOT_MATCH, locker1.getInventory().isEmpty());
    }

    /**
     * checking the capacity
     */
    @Test
    public void get_capacity() {
        assertEquals(NOT_MATCH, 10, locker1.getCapacity());
        assertEquals(NOT_MATCH, 100, locker2.getCapacity());
        assertEquals(NOT_MATCH, 990, locker3.getCapacity());

    }

    /**
     * checking the available capacity
     */
    @Test
    public void getAvailableCapacity1() {
        add1();
        assertEquals(NOT_MATCH, 8, locker1.getAvailableCapacity());
    }

    /**
     * checking the available capacity - complex test
     */
    @Test
    public void getAvailableCapacity2() {
        get_map1();
        assertEquals(NOT_MATCH, 897, locker3.getAvailableCapacity());
        assertEquals(NOT_MATCH, 0, locker3.addItem(bat, 10));
        assertEquals(NOT_MATCH, 877, locker3.getAvailableCapacity());
        locker3.removeItem(sporesEngine, 5);
        assertEquals(NOT_MATCH, 927, locker3.getAvailableCapacity());
    }

    /**
     * checking the football/bat logic
     */
    @Test
    public void football_and_sporesEngine1() {
        assertEquals(NOT_MATCH, 0, locker3.addItem(football, 1));
        assertEquals(NOT_MATCH, -2, locker3.addItem(bat, 1));
    }

    /**
     * checking the football/bat logic
     */
    @Test
    public void football_and_sporesEngine2() {
        assertEquals(NOT_MATCH, 0, locker3.addItem(bat, 1));
        assertEquals(NOT_MATCH, -2, locker3.addItem(football, 1));
    }

    /**
     * checking the football/bat logic with 0 bats
     */
    @Test
    public void football_and_sporesEngine3() {
        locker2.addItem(football, 1);
        assertEquals(NOT_MATCH, -2, locker2.addItem(bat, 0));
    }

    /**
     * checking the football/bat logic with 0 balls
     */
    @Test
    public void football_and_sporesEngine4() {
        locker2.addItem(bat, 1);
        assertEquals(NOT_MATCH, -2, locker2.addItem(football, 0));
    }
}
