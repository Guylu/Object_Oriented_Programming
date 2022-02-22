import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * comparator class to sort by star rating and then by name of property
 */
public class CompareByRatingAndName implements Comparator<Hotel> {
    /**
     * empty constructor
     */
    CompareByRatingAndName() {
    }

    /**
     * compare method to do the sorting
     *
     * @param a hotel a to sort against b
     * @param b hotel b to sort against a
     * @return int by a/b relations
     */
    // star(high)-> name->
    public int compare(Hotel a, Hotel b) {
        // name compare - get a numeral expression for their relation alphabetically:
        int nameCompare = a.getPropertyName().compareTo(b.getPropertyName());
        // star comparing:
        int starCompare = (b.getStarRating()) - a.getStarRating();

        // comparing logic  by star and then by name:
        if (starCompare == 0) return ((nameCompare == 0) ? starCompare : nameCompare);
        return starCompare;

    }
}
