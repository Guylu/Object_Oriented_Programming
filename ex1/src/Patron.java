/**
 * this calss handles a patron with certain enjoyment threshold and can lend books
 */
public class Patron {
    final String FirstName;
    final String LastName;
    final int comicTendency;
    final int dramaticTendency;
    final int educationalTendency;
    final int EnjoymentThreshold;

    Patron(String patronFirstName, String patronLastName, int patroncomicTendency, int patrondramaticTendency,
           int patroneducationalTendency, int patronEnjoymentThreshold) {
        FirstName = patronFirstName;
        LastName = patronLastName;
        comicTendency = patroncomicTendency;
        dramaticTendency = patrondramaticTendency;
        educationalTendency = patroneducationalTendency;
        EnjoymentThreshold = patronEnjoymentThreshold;


    }

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name,
     * separated by a single white space. For example, if the patron's first name is "Ricky" and his last name
     * is "Bobby", this method will return the String "Ricky Bobby".
     * Returns:
     * the String representation of this patron.
     */

    String stringRepresentation() {
        return FirstName + " " + LastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * Parameters:
     * book - The book to asses.
     * Returns:
     * the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        int g = book.getComicValue() * comicTendency + book.getDramaticValue() * dramaticTendency +
                book.getEducationalValue() * educationalTendency;
        return g;
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * Parameters:
     * book - The book to asses.
     * Returns:
     * true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) >= EnjoymentThreshold;
    }


}
