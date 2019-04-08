package src.Server.Model;

/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class Item{
    /**
     * The item's Supplier. 
     */
    private Supplier supplier;

    /**
     * The item's id number. 
     */
    private int id;

    /**
     * The item's name. 
     */
    private String name;

    /**
     * The item's price. 
     */
    private double price;

    /**
     * The item's quantity in stock. 
     */
    private int stock;

    /**
     * The item's SupplierID
     */
    private int supplierID;

    /**
     * Constructs an object of type Item with the specified values. 
     * @param i id number of the item.
     * @param n name of the item. 
     * @param p price of the item.
     * @param s quantity in stock of the item. 
     * @param supplierId the item's supplier's id
     */
    public Item(int i, String n, double p, int s, int supplierId){
        id = i;
        name = n;
        price = p;
        stock = s;
        supplierID = supplierId;
    }

    /**
     * Assigns the item's Supplier variable the specified object of supplier.
     * @param s supplier object to be assigned
     */
    public void assignSupplier(Supplier s){
        supplier = s;
    }

    /**
     * Gets the item's supplier id.
     * @return returns the supplier id.
     */
    public int getSupplierID(){
        return supplierID;
    }

    /**
     * Gets the item's id
     * @return returns the item's id
     */
    public int getID(){
        return id;
    }

    /**
     * Gets the item's name.
     * @return returns the name of the item.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the price of the item.
     * @return returns the price of the item. 
     */
    public double getPrice(){
        return price;
    }

    /**
     * Gets the quantity in stock for the item.
     * @return returns the amount in stock for the item.
     */
    public int getStock(){
        return stock;
    }

    /**
     * Sets the item's id to the specified value.
     * @param i the value to be set as the item's id
     */
    public void setID(int i){
        id = i;
    }

    /**
     * Sets the name of the item to the specified value.
     * @param n the string to be set as the name of the item. 
     */
    public void setName(String n){
        name = n;
    }

    /**
     * Sets the price of the item to the specified value.
     * @param p the value to set the item's price to.
     */
    public void setPrice(double p){
        price = p;
    }

    /**
     * Sets the amount in stock for the item.
     * @param quantity the amount to be set as stock for the item.
     */
    public void setStock(int quantity){
        stock = quantity;
    }

    /**
     * Assembles a string with all the information about the item.
     * @return returns a String with all the information about the item.
     */
    public String toString(){
        return "id: " + id + ", item name: " + name +
                ", quantity in stock: " + stock + 
                ", price: $" + price +
                ", supplier id: " + supplier.getID();
    }

    /**
     * Assembles a String with the information required to order more of the item.
     * @param amountOrdered the amount of the item that has been ordered.
     * @return returns a string with the required information to make a new order
     */
    public String toString2(int amountOrdered){
        return "Item description: " + name + "\n" +
                "Amount ordered: " + amountOrdered + "\n" +
                "Supplier: " + supplier.getName();
    }
}