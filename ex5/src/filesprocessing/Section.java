package filesprocessing;

import filesprocessing.Filters.Filter;
import filesprocessing.Orders.Order;

/**
 * class to represent a section in the command file
 * a section consists of a filter and an order
 */
class Section {
    private Filter filter;
    private Order order;

    /**
     * constructor to initialize filter and order
     *
     * @param filter filter to hold
     * @param order  order to hold
     */
    Section(Filter filter, Order order) {
        this.filter = filter;
        this.order = order;
    }

    /**
     * @return filter
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * @return order
     */
    public Order getOrder() {
        return order;
    }
}
