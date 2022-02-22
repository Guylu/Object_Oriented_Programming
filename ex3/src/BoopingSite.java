import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;

/**
 * class to represent a search engine for hotels
 */
public class BoopingSite {
    private Hotel[] hotels;
    private Hotel[] test;
    private double MIN_LATITUDE = -90;
    private double MAX_LATITUDE = 90;
    private double MIN_LONGITUDE = -180;
    private double MAX_LONGITUDE = -180;

    /**
     * This constructor receives as parameter a string, which is
     * the name of the dataset (e.g. "hotels dataset.txt"). This parameter can later be passed to the
     * HotelDataset.getHotels(String fileName) function.
     *
     * @param name: name of file
     */
    public BoopingSite(String name) {
        hotels = HotelDataset.getHotels(name);
    }

    /**
     * This method returns an array of hotels located in the given city, sorted from the highest star-rating to the lowest. Hotels that have the
     * same rating will be organized according to the alphabet order of the hotel’s (property) name. In case
     * there are no hotels in the given city, this method returns an empty array.
     *
     * @param city: city to filter for
     * @return: filtered array of hotels in the city
     */
    // star(high)-> name->
    public Hotel[] getHotelsInCityByRating(String city) {
        // filter by city:
        Hotel[] result = getCity(city, hotels);
        //sort by star and name:
        Arrays.sort(result, new CompareByRatingAndName());
        // return result
        return result;
    }

    /**
     * @param city: city to filter out of array
     * @param h:    array of hotels
     * @return: filtered array
     */
    private Hotel[] getCity(String city, Hotel[] h) {
        Hotel[] result = new Hotel[h.length];
        int j = 0;
        for (int i = 0; i < h.length; i++) {
            if (h[i].getCity().equals(city)) {
                result[j] = h[i];
                j++;
            }
        }
        Hotel[] result2 = new Hotel[j];
        for (int i = 0; i < j; i++)
            result2[i] = result[i];
        return result2;
    }

    /**
     * This method
     * returns an array of hotels, sorted according to their (euclidean) distance from the given geographic
     * location, in ascending order. Hotels that are at the same distance from the given location are organized
     * according to the number of points-of-interest for which they are close to (in a decreasing order). In
     * case of illegal input, this method returns an empty array.
     *
     * @param latitude:  latitude point
     * @param longitude: longitude point
     * @return resulting array
     */
    //distance from point -> #POI
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        // check id coordinates are valid:
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE ||
                longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE)
            return new Hotel[0];
        // clone:
        Hotel[] result = hotels.clone();
        // sort by distance and POI:
        Arrays.sort(result, new CompareByDistanceAndPOI(latitude, longitude));
        return result;
    }

    /**
     * This method returns an array of hotels in the given city, sorted according to their (euclidean) distance
     * from the given geographic location, in ascending order. Hotels that are at the same distance from the
     * given location are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order). In case of illegal input, this method returns an empty array.
     *
     * @param city:      city to filter by
     * @param latitude:  latitude point
     * @param longitude: longitude point
     * @return resulting array of hotels
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        // check id coordinates are valid:
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE ||
                longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE)
            return new Hotel[0];
        // sort by distance and POI
        test = getHotelsByProximity(latitude, longitude);
        // filter by city
        return getCity(city, test);
    }

}
