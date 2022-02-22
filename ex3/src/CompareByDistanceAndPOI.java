import oop.ex3.searchengine.Hotel;

import java.lang.Math;
import java.util.Comparator;

/**
 * comparator class to sort by distance from given point and then by # of POI
 */
public class CompareByDistanceAndPOI implements Comparator<Hotel> {
    private double latitude, longitude;

    /**
     * will initialize the point to sort for
     *
     * @param latitude   : point
     * @param longitude: point
     */
    CompareByDistanceAndPOI(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * compare method to do the sorting
     *
     * @param a hotel a to sort against b
     * @param b hotel b to sort against a
     * @return int by a/b relations
     */
    public int compare(Hotel a, Hotel b) {
        // POI comparing
        int POI = b.getNumPOI() - a.getNumPOI();
        // distance from given point compare
        int distanceCompare = distance(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());

        // comparing logic, by distance and then by # of POI
        if (distanceCompare == 0) return ((POI == 0) ? distanceCompare : POI);
        return distanceCompare;
    }

    /**
     * will tell witch point - (ax,ay) or (bx,by) is closer to point (latitude,longitude)
     *
     * @param ax x of point a
     * @param ay y of point a
     * @param bx x of point b
     * @param by y of point b
     * @return
     */
    private int distance(double ax, double ay, double bx, double by) {
        // uclidian formula for distance calculation:
        double distanceA = Math.sqrt(Math.pow((ax - latitude), 2) + Math.pow((ay - longitude), 2));
        double distanceB = Math.sqrt(Math.pow((bx - latitude), 2) + Math.pow((by - longitude), 2));
        // using the sign method too avoid the double type - will convert to int in the right way to
        // use for the sorting mechanism
        return (int) (Math.signum(distanceA - distanceB));
    }
}
