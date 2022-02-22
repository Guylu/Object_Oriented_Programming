
import oop.ex3.searchengine.Hotel;
import org.junit.*;

import java.lang.Math;

import static org.junit.Assert.*;

public class BoopingSiteTest {
    private BoopingSite boop;

    @Test
    /**
     * test to check if getHotelsInCityByRating filters city correctly
     */
    public void getHotelsInCityByRating1() {
        boop = new BoopingSite("hotels_dataset.txt");
        Hotel[] test = boop.getHotelsInCityByRating("goa");
        for (int i = 0; i < test.length; i++) {
            assertEquals("Results don’t match at " + i, "goa", test[i].getCity());
        }
    }

    @Test
    /**
     * test of empty array
     */
    public void testingHotelsByRatingEmpty() {
        boop = new BoopingSite("hotels_tst1.txt");
        Hotel[] test = boop.getHotelsInCityByRating("");
        assertEquals("Results don’t match", 0, test.length);
    }

    @Test
    /**
     * test to check if getHotelsInCityByRating sorts correctly both conditions
     */
    // star(high)-> name->
    public void getHotelsInCityByRating2() {
        boop = new BoopingSite("hotels_dataset.txt");
        Hotel[] test = boop.getHotelsInCityByRating("goa");
        for (int i = 0; i < test.length - 1; i++) {
            assertEquals("Results don’t match at " + i, true, test[i].getStarRating() >= test[i + 1].getStarRating());
            if (test[i].getStarRating() >= test[i + 1].getStarRating())
                assertEquals("Results don’t match at " + i, true, test[i].getPropertyName().compareTo(test[i].getPropertyName()) >= 0);

        }
    }

    @Test
    /**
     * test to check if getHotelsInCityByRating sorts correctly both conditions
     */
    // star(high)-> name->
    public void getHotelsInCityByRating3() {
        boop = new BoopingSite("hotels_tst2.txt");
        Hotel[] test = boop.getHotelsInCityByRating("goa");
        for (int i = 0; i < test.length - 1; i++) {
            assertEquals("Results don’t match at " + i, true, test[i].getStarRating() >= test[i + 1].getStarRating());
            if (test[i].getStarRating() >= test[i + 1].getStarRating())
                assertEquals("Results don’t match at " + i, true, test[i].getPropertyName().compareTo(test[i].getPropertyName()) >= 0);

        }
    }

    //distance from point -> #POI
    @Test
    /**
     * test to check if getHotelsByProximity sorts correctly both conditions on ALOT of coordinates
     */
    public void getHotelsByProximity1() {
        double[][] testCoordinates = {{0, 0},
                {78, 90},
                {62, 1},
                {12, -70},
                {8, 9},
                {-80, -60},
                {1, -100},
                {-100, 1},
                {90, 180}};
        boop = new BoopingSite("hotels_dataset.txt");
        for (double[] points : testCoordinates) {
            double latitude = points[0];
            double longitude = points[1];
            Hotel[] test = boop.getHotelsByProximity(latitude, longitude);
            for (int i = 0; i < test.length - 1; i++) {
                Hotel h1 = test[i];
                Hotel h2 = test[i + 1];
                int d = distance(h1.getLatitude(), h1.getLongitude(), h2.getLatitude(), h2.getLongitude(), latitude, longitude);
                assertTrue("Results don’t match at " + i, 0 <= d);
                if (d == 0)
                    assertTrue("Results don’t match at " + i, h1.getNumPOI() >= h2.getNumPOI());
            }
        }
    }

    @Test
    /**
     * test to check if getHotelsByProximity sorts correctly both conditions on ALOT of illegal coordinates
     */
    public void getHotelsByProximity2() {
        double[][] testCoordinates = {{91, 1},
                {-91, 1},
                {181, 1},
                {-181, 1},
                {181, 91},
                {-181, 91},
                {-181, -91},
                {181, -91},
                {-400, -500},
                {400, 500},
                {-1000, 1000}};
        boop = new BoopingSite("hotels_dataset.txt");
        for (double[] points : testCoordinates) {
            double latitude = points[0];
            double longitude = points[1];
            Hotel[] test = boop.getHotelsByProximity(latitude, longitude);
            for (int i = 0; i < test.length - 1; i++) {
                Hotel h1 = test[i];
                Hotel h2 = test[i + 1];
                int d = distance(h1.getLatitude(), h1.getLongitude(), h2.getLatitude(), h2.getLongitude(), latitude, longitude);
                assertEquals("Results don’t match at " + i, 0, d);
            }
        }
    }

    @Test
    /**
     * test to check if getHotelsInCityByProximity sorts correctly both conditions on ALOT of coordinates
     */
    public void getHotelsInCityByProximity1() {
        double[][] testCoordinates = {{0, 0},
                {78, 90},
                {62, 1},
                {12, -70},
                {8, 9},
                {-80, -60},
                {1, -100},
                {-100, 1},
                {90, 180}};

        boop = new BoopingSite("hotels_dataset.txt");
        for (double[] points : testCoordinates) {
            double latitude = points[0];
            double longitude = points[1];
            Hotel[] test = boop.getHotelsInCityByProximity("goa", latitude, longitude);
            for (int i = 0; i < test.length - 1; i++) {
                assertEquals("Results don’t match at " + i, "goa", test[i].getCity());
                assertEquals("Results don’t match at " + i, "goa", test[i + 1].getCity());
                Hotel h1 = test[i];
                Hotel h2 = test[i + 1];
                int d = distance(h1.getLatitude(), h1.getLongitude(), h2.getLatitude(), h2.getLongitude(), latitude, longitude);
                assertTrue("Results don’t match at " + i, 0 <= d);
                if (d == 0)
                    assertTrue("Results don’t match at " + i, h1.getNumPOI() >= h2.getNumPOI());
            }
        }
    }

    @Test
    /**
     * test to check if getHotelsInCityByProximity sorts correctly both conditions on ALOT of coordinates
     * on the other file.
     */
    public void getHotelsInCityByProximity2() {
        double[][] testCoordinates = {{0, 0},
                {78, 90},
                {62, 1},
                {12, -70},
                {8, 9},
                {-80, -60},
                {1, -100},
                {-100, 1},
                {90, 180}};
        boop = new BoopingSite("hotels_tst2.txt");
        for (double[] points : testCoordinates) {
            double latitude = points[0];
            double longitude = points[1];
            Hotel[] test = boop.getHotelsInCityByProximity("goa", latitude, longitude);
            for (int i = 0; i < test.length - 1; i++) {
                assertEquals("Results don’t match at " + i, "goa", test[i].getCity());
                assertEquals("Results don’t match at " + i, "goa", test[i + 1].getCity());
                Hotel h1 = test[i];
                Hotel h2 = test[i + 1];
                int d = distance(h1.getLatitude(), h1.getLongitude(), h2.getLatitude(), h2.getLongitude(), latitude, longitude);
                assertTrue("Results don’t match at " + i, 0 <= d);
                if (d == 0)
                    assertTrue("Results don’t match at " + i, h1.getNumPOI() >= h2.getNumPOI());
            }
        }
    }


    /**
     * @param ax        x of point a
     * @param ay        y of point a
     * @param bx        x of point b
     * @param by        y of point b
     * @param latitude  - point
     * @param longitude - point
     * @return 1: a closer to point
     * -1: b closer to point
     * 0: same
     */
    private int distance(double ax, double ay, double bx, double by, double latitude, double longitude) {
        double distanceA = Math.sqrt(Math.pow((ax - latitude), 2) + Math.pow((ay - longitude), 2));
        double distanceB = Math.sqrt(Math.pow((bx - latitude), 2) + Math.pow((by - longitude), 2));
        if (distanceA < distanceB)
            return 1;
        else if (distanceA > distanceB)
            return -1;
        return 0;
    }
}

