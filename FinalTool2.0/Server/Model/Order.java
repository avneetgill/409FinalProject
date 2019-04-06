package Server.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class Order{

    /**
     * The ArrayList of OrderLine objects that Order manages.
     */
    private ArrayList<OrderLine> lines;

    /**
     * Constructs an object of Order with the specified value. 
     * @param l the ArrayList of OrderLine that is assigned to Order. 
     */
    public Order(ArrayList<OrderLine> l){
        lines = l;
    }

    /**
     * Creates a new OrderLine and adds it to the lines with the specified values.
     * @param i the item for which the orderline is being created.
     * @param amount the amount of stock to be ordered.
     * @throws IOException thrown if there is an issue with IO stream.
     */
    public void newOrder(Item i, int amount)throws IOException{
        Date date = new Date();
        int id = generateID();
        OrderLine OrderNew = new OrderLine(i, date, id, amount);
        lines.add(OrderNew);
        writeTxt();
    }

    /**
     * Generates a random five digit number to be the order id for
     * an orderline.
     * @return returns the generated number
     */
    private int generateID(){
        Random rand = new Random();
        int id;
        do{
            id = rand.nextInt((99999 - 10000) + 1) + 10000;
        }while(numberExists(id));
        return id;
    }

    /**
     * Checks if the random number generated already exists.
     * @param id the number to be checked.
     * @return returns true if the number already exists in another 
     * OrderLine, false if it doesnt.
     */
    private boolean numberExists(int id){
        for(OrderLine l: lines){
            if(l.getID() == id)
                return true;
        }
        return false;
    }

    /**
     * Assembles a string which holds all the orderlines' information
     * in an aesthetic way. 
     * @return returns a string holding the orderline information.
     */
    public String toString(){
        String s = "";
        for(OrderLine n: lines){
            s += "**********************************************\n";
            s += n.toString() + "\n";
        }
        s += "**********************************************\n";
        return s;
    }

    /**
     * Writes the Orderlines to a text file.
     * @throws IOException thrown if there is an issue with file IO
     */
    public void writeTxt() throws IOException{
        PrintWriter writer = new PrintWriter("orders.txt", "UTF-8");
        writer.println(this.toString());
        writer.close();
    }
}