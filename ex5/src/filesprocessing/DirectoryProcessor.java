package filesprocessing;

import filesprocessing.ProjectExceptions.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * main class to manage the entire project
 */
public class DirectoryProcessor {

    private static final int FILTER_INDEX = 0;
    private static final int ORDER_INDEX = 1;
    private static final int COLS = 2;
    private static final int SECTION_DEFAULT_SIZE = 4;
    private static final String FILTER_LINE_ERROR = "missing filter command line";
    private static final String FILTER_DES_ERROR = "missing filter description";
    private static final String ORDER_LINE_ERROR = "missing order command line";
    private static final String AT_LINE = "at line ";
    private static final String BC = " because of: ";
    private static final String ERROR = "ERROR: ";
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String DEFAULT_ORDER = "abs";
    private static final int VALUES = 2;
    private static final int CONTENT = 0;
    private static final int LINE = 1;

    /**
     * enum for filters
     */
    public enum filters {
        // all possible filters:
        GREATER_THAN("greater_than"),
        BETWEEN("between"),
        SMALLER_THAN("smaller_than"),
        FILE("file"),
        CONTAINS("contains"),
        PREFIX("prefix"),
        SUFFIX("suffix"),
        WRITABLE("writable"),
        EXECUTABLE("executable"),
        HIDDEN("hidden"),
        ALL("all");
        // actual appearance in command
        private final String name;

        /**
         * constructor of filters
         *
         * @param name the name of the command
         */
        filters(String name) {
            this.name = name;
        }

        /**
         * @return the name of the command
         */
        @Override
        public String toString() {
            return name.toLowerCase();
        }

    }

    /**
     * enum of orders
     */
    public enum orders {
        // all possible orders
        ABS("abs"),
        TYPE("type"),
        SIZE("size");
        private final String name;

        /**
         * constructor of orders
         *
         * @param name the name of the command
         */
        orders(String name) {
            this.name = name;
        }

        /**
         * @return the name of the command
         */
        @Override
        public String toString() {
            return name.toLowerCase();
        }
    }

    /**
     * main class to run the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // variables to hold the input to the program
        String dirName = args[0];
        String commandsDir = args[1];
        File sourceDir = new File(dirName);
        ArrayList<String> data;
        String[][][] parsed;
        File[] listFiles;
        String[] tmp;
        ArrayList<Section> mods = new ArrayList<>();

        //IO STUFF:

        // trying to read the data in the command file
        // and trying to initializing the source directory
        try {
            data = fileAccessing(commandsDir);
            listFiles = directoryAccessing(sourceDir);
        } catch (Type2 type2) {
            // ERROR while getting data
            System.err.println(ERROR + type2.description);
            return;
        }

        // temp array
        tmp = new String[data.size()];


        // data processing stuff:

        //trying to get data from command file, parsed into the array
        // and getting the filter and orders from factory
        try {
            parsed = parser(data.toArray(tmp));
            mods = SectionFactory.factory(parsed);
        } catch (Type2 type2) {
            System.err.println(ERROR + AT_LINE + type2.line + BC + type2.description);
            return;
        } catch (Type1 type1) {
            System.err.println(ERROR + BC + type1.description);
        }

        //final method to print the results of the program
        outputResults(listFiles, mods);
    }


    /**
     * method to print the result of the program
     *
     * @param listFiles files to process
     * @param mods      the sections to invoke
     */
    private static void outputResults(File[] listFiles, ArrayList<Section> mods) {
        // getting files to activate the objects from factory
        File[] temp, result;
        // getting all data from the source directory filtered and ordered by the product of the factory:
        for (int i = 0; i < mods.size(); i++) {
            temp = mods.get(i).getFilter().activateFilter(listFiles);// filtered
            result = mods.get(i).getOrder().activateOrder(temp);// organized
            for (File f : result) {
                System.out.println(f.getName());// printing results
            }
        }
    }

    /**
     * method that gets path of directory and return the files in that directory
     *
     * @param sourceDir the path of directory
     * @return array of files in the directory
     * @throws Type2 throws type 2 exception
     */
    private static File[] directoryAccessing(File sourceDir) throws Type2 {
        // checking if the path is actually a directory
        if (!sourceDir.isDirectory())
            throw new MissingDirectory("source directory given isn't a directory");
        File[] listFiles, tmp;
        ArrayList<File> temp = new ArrayList<>();
        // pulling files from directory
        try {
            listFiles = sourceDir.listFiles();
        } catch (Exception e) {
            throw new MissingDirectory("invalid directory");
        }
        // taking only files from the path
        for (File file : listFiles)
            if (!file.isDirectory())
                temp.add(file);
        tmp = new File[temp.size()];
        listFiles = temp.toArray(tmp);
        return listFiles;
    }

    /**
     * method that gets path of file and return the content in that file
     *
     * @param commandsFile path of file
     * @return content of file
     * @throws Type2 throws type 2 exception
     */
    private static ArrayList<String> fileAccessing(String commandsFile) throws Type2 {
        ArrayList<String> data = new ArrayList<String>();
        File commands = new File(commandsFile);
        //checking that the path is of a file
        if (!commands.isFile())
            throw new MissingFile("command file given isn't a file ");
        // getting the content of the file into data arraylist
        try (Scanner s = new Scanner(commands)) {
            while (s.hasNext()) {
                data.add(s.nextLine());
            }
        } catch (FileNotFoundException e) { // Type2 ERROR - missing file
            throw new MissingFile("cant access files content");
        }
        return data;
    }

    /**
     * method to parse the data in the commands file
     * will return the data divided into section
     *
     * @param data content of command file
     * @return array of strings of the section in the array by filter and order
     * @throws Type2 throws Type2 exception
     */
    private static String[][][] parser(String[] data) throws Type2 {
        if (data.length == 0)
            throw new MissingCommand(1, FILTER_LINE_ERROR);
        String[][][] parsedData = new String[data.length][COLS][VALUES];
        int j = 0; //var to hold number of sections added
        //runs though all the data in the file
        for (int i = 0; i < data.length; i += SECTION_DEFAULT_SIZE, j++) {
            //cases of ERROR in the file:
            if (!data[i].equals(FILTER))
                throw new MissingCommand(i, FILTER_LINE_ERROR);
            if (i + 1 >= data.length)
                throw new MissingCommand(i + 1, FILTER_DES_ERROR);
            if (i + 2 >= data.length || !data[i + 2].equals(ORDER))
                throw new MissingCommand(i + 2, ORDER_LINE_ERROR);
            parsedData[j][FILTER_INDEX][CONTENT] = data[i + 1];
            parsedData[j][FILTER_INDEX][LINE] = String.valueOf(i + 1);
            //special case of section ending with 3 rows
            if (i + 3 >= data.length) {
                //add default section
                parsedData[j][ORDER_INDEX][CONTENT] = DEFAULT_ORDER;
                parsedData[j][ORDER_INDEX][LINE] = String.valueOf(-1);
                j++;
                break;
            }

            //regular case of section of length 3:
            if (data[i + 3].equals(FILTER)) {
                //add default section
                parsedData[j][ORDER_INDEX][CONTENT] = DEFAULT_ORDER;
                parsedData[j][ORDER_INDEX][LINE] = String.valueOf(-1);
                i--;// raising iteration by only 3(because at the end of loop we raise by 4)
            } else {
                parsedData[j][ORDER_INDEX][CONTENT] = data[i + 3];
                parsedData[j][ORDER_INDEX][LINE] = String.valueOf(i + 3);
            }
        }
        //shrinking the array to not hold 0 values
        String[][][] result = new String[j][COLS][VALUES];
        for (int k = 0; k < result.length; k++) {
            for (int l = 0; l < result[FILTER_INDEX].length; l++) {
                for (int i = 0; i < result[FILTER_INDEX][CONTENT].length; i++) {
                    result[k][l][i] = parsedData[k][l][i];
                }

            }
        }
        return result;
    }
}