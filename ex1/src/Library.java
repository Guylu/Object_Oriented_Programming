/**
 * this class handles a library witch stores books and can lend them to patrons.
 */
public class Library {
    final int maxBookCapacity;
    final int maxBorrowedBooks;
    final int maxPatronCapacity;
    Book[] lib;
    boolean patrons[][];
    Patron registeredPatrons[];

    Library(int libMaxBookCapacity, int libMaxBorrowedBooks, int libMaxPatronCapacity) {
        maxBookCapacity = libMaxBookCapacity;
        maxBorrowedBooks = libMaxBorrowedBooks;
        maxPatronCapacity = libMaxPatronCapacity;
        lib = new Book[maxBookCapacity];
        registeredPatrons = new Patron[maxPatronCapacity];
        patrons = new boolean[maxBookCapacity][maxPatronCapacity];
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * Parameters:
     * book - The book to add to this library.
     * Returns:
     * a non-negative id number for the book if there was a spot and the book was successfully added, or if
     * the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        int count = 0;
        for (int i = 0; i < lib.length; i++) {
            if (lib[i] == book)
                return i;
            if (lib[i] != null)
                count++;
        }
        if (count >= maxBookCapacity)
            return -1;
        lib[count] = book;
        return count;
    }


    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * Parameters:
     * bookId - The id to check.
     * Returns:
     * true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return (bookId <= maxBookCapacity && bookId >= 0 && lib[bookId] != null);
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * Parameters:
     * book - The book for which to find the id number.
     * Returns:
     * a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < lib.length; i++)
            if (lib[i] == book)
                return i;
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * Parameters:
     * bookId - The id number of the book to check.
     * Returns:
     * true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (!isBookIdValid(bookId))
            return false;
        for (int j = 0; j < patrons[0].length; j++)
            if (patrons[bookId][j])
                return false;
        return true;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * Parameters:
     * patron - The patron to register to this library.
     * Returns:
     * a non-negative id number for the patron if there was a spot and the patron was successfully registered,
     * a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        int count = 0;
        for (int i = 0; i < registeredPatrons.length; i++) {
            if (registeredPatrons[i] == patron)
                return i;
            if (registeredPatrons[i] != null)
                count++;
        }
        if (count >= maxPatronCapacity)
            return -1;
        registeredPatrons[count] = patron;
        return count;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * Parameters:
     * bookId - The id to check.
     * Returns:
     * true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        boolean bounds = patronId >= 0 && patronId <= maxPatronCapacity;
        return (bounds && registeredPatrons[patronId] != null);
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1
     * otherwise.
     * Parameters:
     * patron - The patron for which to find the id number.
     * Returns:
     * a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < registeredPatrons.length; i++)
            if (registeredPatrons[i] == patron)
                return i;
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and
     * if the patron will enjoy this book.
     * Parameters:
     * bookId - The id number of the book to borrow.
     * patronId - The id number of the patron that will borrow the book.
     * Returns:
     * true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (!(isPatronIdValid(patronId) && isBookIdValid(bookId) && isBookAvailable(bookId)))
            return false;
        if (getLibSize() >= maxBorrowedBooks)
            return false;
        if(!registeredPatrons[patronId].willEnjoyBook(lib[bookId]))
            return false;
        lib[bookId].setBorrowerId(patronId);
        patrons[bookId][patronId] = true;
        return true;
    }
    /**
     * return the amount of books actually  held in the library
     */
    int getLibSize() {
        int count = 0;
        for (int i = 0; i < patrons.length; i++)
            for (int j = 0; j < patrons[0].length; j++)
                if (patrons[i][j])
                    count++;
        return count;
    }

    /**
     * Return the given book.
     * Parameters:
     * bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId)) {
            for (int i = 0; i < patrons[0].length; i++)
                if (patrons[bookId][i]) {
                    patrons[bookId][i] = false;
                    lib[bookId].returnBook();
                    return;
                }
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     * Parameters:
     * patronId - The id number of the patron to suggest the book to.
     * Returns:
     * The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        int maxEnjoiment = -1;
        Book bestBook = null;
        int temp;

        Patron p = registeredPatrons[patronId];
        for (int i = 0; i < lib.length; i++) {
            if (!isBookAvailable(getBookId(lib[i])))
                continue;
            temp = p.getBookScore(lib[i]);
            if (p.willEnjoyBook(lib[i]) && temp > maxEnjoiment) {
                maxEnjoiment = temp;
                bestBook = lib[i];
            }
        }
        if (bestBook != null)
            return bestBook;
        return null;
    }


}
