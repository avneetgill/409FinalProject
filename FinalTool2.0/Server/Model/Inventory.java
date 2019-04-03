package Server.Model;

import java.util.ArrayList;
import java.util.*; 
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Provides methods and data fields to create and manage 
 * an ArrayList of Item objects.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class Inventory{

    /**
     * The ArrayList of items
     */
    private ArrayList<Item> items;

    /**
     * Constructs the Inventory with the specified ArrayList of Item.
     * @param i the ArrayList of Item to be assigned.
     */
    public Inventory(ArrayList<Item> i){
        items = i;
    }

    /**
     * Gets the size of the ArrayList of items.
     * @return returns the size of ArrayList, the amount of Items in inventory.
     */
    public int getSize(){
        return items.size();
    }

    /**
     * Adds Items to the ArrayList by reading from text file.
     * @throws FileNotFoundException thrown if there are file access issues.
     */
    public void addItemsText() throws FileNotFoundException{
        Scanner read = new Scanner(new File("items.txt"));
        read.useDelimiter(";");
        String id, name, quantity, price, supplierID, nextLine;
        String[] arr = new String[5];
        int ID, amount, supID; double cost;
        while(read.hasNext()){
            nextLine = read.nextLine();
            arr = nextLine.split(";");
            id = arr[0];
            name = arr[1];
            quantity = arr[2];
            price = arr[3];
            supplierID = arr[4];
            
            cost = Double.parseDouble(price);
            ID = Integer.parseInt(id);
            amount = Integer.parseInt(quantity);
            supID = Integer.parseInt(supplierID);
            addItem(new Item(ID, name, cost, amount, supID));
        }
        read.close();
    }
    

    /**
     * Checks if the id number of an item being added is already taken.
     * @return returns true if id is already taken, false if it is not.
     * @param id the number to be checked. 
     */
    public boolean itemIDChecker(int id){
        for(Item i: items){
            if(id == i.getID())
                return true;
        }
        return false;
    }

    /**
     * Adds the specified item to the ArrayList.
     * @param i the Item object to be added. 
     */
    public void addItem(Item i){
        items.add(i);
    }

    /**
     * Removes the specified item from the ArrayList.
     * @param i the Item object to be added. 
     */
    public void deleteItem(Item i){
        items.remove(i);
    }

    /**
     * Gets the Item object at the nth index of the ArrayList.
     * @param n the index to get from.
     * @return returns the nth element of the Item ArrayList.
     */
    public Item getItemAt(int n){
        return items.get(n);
    }

    /**
     * Searches items for the object with the specified name.
     * @param n the name of the item being searched.
     * @return returns the Item being searched if it exists, 
     * else returns null
     */
    public Item searchByName(String n){
        for(Item i: items){
            if(i.getName().equals(n))
                return i;
        }
        return null;
    }

    /**
     * Searches items for the object with the specified id.
     * @param id the id of the item being searched.
     * @return returns the Item being searched if it exists, 
     * else returns null
     */
    public Item searchById(int id){
        for(Item i: items){
            if(i.getID() == id)
                return i;
        }
        return null;
    }

    /**
     * Assembles a String containing the information of all items
     * in the inventory.
     * @return returns the string containing information about all 
     * items in inventory
     */
    public String toString(){
        String s = "";
        for(Item i: items){
            s += i.toString() + "\n";
        }
        return s;
    }
}