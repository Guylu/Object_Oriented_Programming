package filesprocessing;

import filesprocessing.Filters.*;
import filesprocessing.Orders.*;
import filesprocessing.ProjectExceptions.*;

import java.util.ArrayList;

/**
 * factory to make the sections of filters and orders by the data which was created from the commands file
 */
class SectionFactory {
    private static final int FILTER_INDEX = 0;
    private static final int ORDER_INDEX = 1;
    private static final int CONTENT = 0;
    private static final int LINE = 1;
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    private static final String WARNING = "Warning in line ";
    private static final String NOT = "#NOT";
    private static final String REVERSE = "#REVERSE";

    private static final int GREATER_THAN_LEN = 13;
    private static final int BETWEEN_LEN = 8;
    private static final int SMALLER_THAN_LEN = 13;
    private static final int FILE_LEN = 5;
    private static final int CONTAINS_LEN = 9;
    private static final int PREFIX_LEN = 7;
    private static final int SUFFIX_LEN = 7;
    private static final int WRITABLE_LEN = 8;
    private static final int EXECUTABLE_LEN = 10;
    private static final int HIDDEN_LEN = 6;
    private static final int ALL_LEN = 3;

    /**
     * this method act as a factory to make an arraylist if sections containing filters and orders from the
     * commands file.
     *
     * @param data data from the commands file to make sections out of
     * @return arraylist of sections containing filters and orders
     * @throws Type1 throws Type1 exceptions
     */
    static ArrayList<Section> factory(String[][][] data) throws Type1 {
        ArrayList<Section> sections = new ArrayList<Section>();
        ArrayList<String> warnings = new ArrayList<String>();
        Filter filter;
        Order order;
        for (int i = 0; i < data.length; i++) {
            // the command to check:
            String filterCommand = data[i][FILTER_INDEX][CONTENT];
            //if the not condition applies
            boolean isNot = filterCommand.contains(NOT);
            //         length of the command             smart calculation to know how to redact the command
            int len = filterCommand.length(), addition = (isNot ? TRUE : FALSE) * NOT.length();
            try {
                switch (checkFilter(filterCommand)) {
                    case GREATER_THAN:        // len - addition--> the command without the "NOT"(is appears)
                        filter = new GreaterThan(filterCommand.substring(GREATER_THAN_LEN, len - addition), isNot);
                        break;
                    case BETWEEN:
                        filter = new Between(filterCommand.substring(BETWEEN_LEN, len - addition), isNot);
                        break;
                    case SMALLER_THAN:
                        filter = new SmallerThan(filterCommand.substring(SMALLER_THAN_LEN, len - addition), isNot);
                        break;
                    case FILE:
                        filter = new FileFilter(filterCommand.substring(FILE_LEN, len - addition), isNot);
                        break;
                    case CONTAINS:
                        filter = new Contains(filterCommand.substring(CONTAINS_LEN, len - addition), isNot);
                        break;
                    case PREFIX:
                        filter = new Prefix(filterCommand.substring(PREFIX_LEN, len - addition), isNot);
                        break;
                    case SUFFIX:
                        filter = new Suffix(filterCommand.substring(SUFFIX_LEN, len - addition), isNot);
                        break;
                    case WRITABLE:
                        filter = new Writable(filterCommand.substring(WRITABLE_LEN, len - addition), isNot);
                        break;
                    case EXECUTABLE:
                        filter = new Executable(filterCommand.substring(EXECUTABLE_LEN, len - addition), isNot);
                        break;
                    case HIDDEN:
                        filter = new Hidden(filterCommand.substring(HIDDEN_LEN, len - addition), isNot);
                        break;
                    case ALL:
                        filter = new All(filterCommand.substring(ALL_LEN, len - addition), isNot);
                        break;
                    default:
                        throw new InvalidFilterValue("no valid filter description value");
                }
            } catch (Type1 type1) {
                // is got here there was a type1 exception - warning and continue
                warnings.add(WARNING + (Integer.parseInt(data[i][FILTER_INDEX][LINE]) + 1));
                // default filter:
                filter = new All("", false);
            }

            //same deal :)
            String orderCommand = data[i][ORDER_INDEX][CONTENT];
            boolean isReverse = orderCommand.contains(REVERSE);
            try {
                switch (checkOrder(orderCommand)) {
                    case ABS:
                        order = new Abs(orderCommand, isReverse);
                        break;
                    case SIZE:
                        order = new Size(orderCommand, isReverse);
                        break;
                    case TYPE:
                        order = new Type(orderCommand, isReverse);
                        break;
                    default:
                        throw new InvalidOrderValue("no valid order description value");
                }
            } catch (Type1 type1) {
                // is got here there was a type1 exception - warning and continue
                warnings.add(WARNING + (Integer.parseInt(data[i][ORDER_INDEX][LINE]) + 1));
                // default order:
                order = new Abs(orderCommand, false);
            }
            // in the end of iteration add the filter and order as a section to the arraylist
            sections.add(new Section(filter, order));
        }
        // print all warnings that have been gathered
        for (String warning : warnings)
            System.err.println(warning);
        //return product of the factory !!
        return sections;
    }

    /**
     * receives a possible command and tries to translate it into a filter
     *
     * @param data possible filter description
     * @return filter
     * @throws Type1 throws type1 exception if didn't correspond to filter
     */
    private static DirectoryProcessor.filters checkFilter(String data) throws Type1 {
        // check all possible filters
        for (DirectoryProcessor.filters filter : DirectoryProcessor.filters.values())
            // should first of all contains the text, then check that it appears first in the command
            if (data.startsWith(filter.toString()))
                // great! found it!
                return filter;
        // could not find a filter that correspond to the data
        throw new InvalidFilterValue("no valid filter description value");
    }

    /**
     * receives a possible command and tries to translate it into am order
     *
     * @param data possible order description
     * @return order
     * @throws Type1 throws type1 exception if didn't correspond to order
     */
    private static DirectoryProcessor.orders checkOrder(String data) throws Type1 {
        // check all possible orders
        for (DirectoryProcessor.orders order : DirectoryProcessor.orders.values())
            // should first of all contains the text, then check that it appears first in the command
            if (data.startsWith(order.toString()))
                return order;                // great! found it!
        // could not find an order that correspond to the data
        throw new InvalidOrderValue("no valid order description value");


    }
}
