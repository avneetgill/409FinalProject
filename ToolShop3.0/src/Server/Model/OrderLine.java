package Server.Model;

import java.util.Date;

/** 
 * One line of order
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class OrderLine{
    /**
     * The item which the orderline is for.
     */
    private int itemId;

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
     * String containing info about the item being ordered
     */
    private String itemToString2;

    /**
     * Constructs an object of Orderline with the specified values.
     * @param i the item that is making the order.
     * @param d the Date that order was made.
     * @param id the id of the order.
     * @param itemToString2 String containing info about the item being ordered
     */
    public OrderLine(int i, Date d, int id, String itemToString2){
        date = d;
        itemId = i;
        orderID = id;
        this.itemToString2 = itemToString2;
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
        // return "ORDER ID: " + orderID + "\n" +
        //        "Date Ordered: " + date + "\n\n" +
        //         item.toString2(amount);
        return itemToString2;
    }
}