package Server.Model;
/**
 * Provides methods and data fields to create and manage an 
 * object of Supplier.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class Supplier{
    /**
     * The Supplier's id
     */
    private int id;

    /**
     * The Supplier's name
     */
    private String name;

    /**
     * The Supplier's address
     */
    private String address;

    /**
     * The Supplier's contact 
     */
    private String contact;

    /**
     * Constructs an object of Supplier with the specified values.
     * @param ID supplier's id
     * @param n supplier's name
     * @param a supplier's address
     * @param c supplier's contact
     */
    public Supplier(int ID, String n, String a, String c){
        id = ID;
        name = n;
        address = a;
        contact = c;
    }

    /**
     * Constructs a string containing all information about a supplier.
     * @return returns a string with all the information about a supplier. 
     */
    public String toString(){
        return "id: " + id + ", name: " +name+ ", address: " + address + ", contact: " + contact;
    }

    /**
     * Sets the id of the supplier to the specified value.
     * @param i the value to set as the supplier's id. 
     */
    public void setID(int i){
        id = i;
    }
    
    /**
     * Gets the id of the supplier.
     * @return returns the id of the supplier. 
     */
    public int getID(){
        return id;
    }
    /**
     * Gets the name of the supplier. 
     * @return returns the name of the supplier
     */
    public String getName() {
        return name;
    }
}