import java.util.Date;

/**
 * Provides methods and data fields to create and manage an 
 * object of Orderline.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class OrderLine{
    /**
     * The item which the orderline is for.
     */
    private Item item;

    /**
     * The current date. 
     */
    private Date date;

    /**
     * The amount of stock of the item being ordered.
     */
    private int amount;

    /**
     * The randomly generated id number for the order. 
     */
    private int orderID;

    /**
     * Constructs an object of Orderline with the specified values.
     * @param i the item that is making the order.
     * @param d the Date that order was made.
     * @param id the id of the order.
     * @param quantity the amount of stock that needs to be ordered. 
     */
    public OrderLine(Item i, Date d, int id, int quantity){
        date = d;
        item = i;
        orderID = id;
        amount = quantity;
    }

    /**
     * Gets the id number of the order. 
     * @return returns the id of the order. 
     */
    public int getID(){
        return orderID;
    }

    /**
     * Assembles a string containing all the information for an orderline.
     * @return returns a string containing all the information for the orderline. 
     */
    public String toString(){
        return "ORDER ID: " + orderID + "\n" +
               "Date Ordered: " + date + "\n\n" +
                item.toString2(amount);
    }
}