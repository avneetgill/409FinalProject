package Server.Model;

import java.util.ArrayList;
import java.io.IOException;
import java.util.*; 
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Provides methods and data fields to create and manage a store app. 
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class Shop{

    /**
     * The object of Order that manages the orderlines for the shop. 
     */
    private Order order;

    /**
     * The ArrayList of Suppliers for the shop. 
     */
    private ArrayList<Supplier> suppliers;

    /**
     * The shop's inventory which manages all its items. 
     */
    private Inventory inventory;

    /**
     * Constructs an object of Shop with the specified objects. Calls functions
     * to intialize the suppliers from a text file, to initialize inventory
     * from a text file, and to link the Suppliers with the appropriate Items in
     * the inventory. 
     * @param o the Order object assigned to Shop.
     * @param s the ArrayList of Suppliers to be assigned to Shop.
     * @param i the inventory to be assigned to the Shop. 
     * @throws FileNotFoundException thrown if there is an issue with file access. 
     */
    public Shop(Order o, ArrayList<Supplier> s, Inventory i) throws FileNotFoundException{
        order = o;
        suppliers = s;
        inventory = i;
        addSuppliersText();
        inventory.addItemsText();
        assignSuppliers();
    }

    /**
     * Decreases the amount in stock for an Item by a specified amount, read 
     * from the input stream by prompting the user. 
     * @throws IOException thrown if there is an issue IO stream
     */
    public void decreaseQuantity() throws IOException{
        Item p;
        System.out.println("Please choose how you would like to select an item to decrease quantity ");
        System.out.println("1. Search item by name");
        System.out.println("2. Search item by id");
        Scanner scan = new Scanner(System.in);
        String temp; int choice = -1, amount = -1;
        do{
            temp = scan.nextLine();
            try{choice = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(choice != 1 && choice != 2)
                System.err.println("Invalid input. Please Try Again: \n");
        }while(choice != 1 && choice != 2);

        if(choice == 1)
            p = searchName(2);
        else
            p = searchID(2);

        System.out.println("How much would you like to decrease: ");
        do{
            temp = scan.nextLine();
            try{amount = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(amount < 0)
                System.err.println("Invalid input. Please Try Again: \n");
        } while(amount < 0);
        if(amount > p.getStock()){
            System.out.println("Sorry we dont have that much of " + p.getName() + " in stock");
            System.out.println("Selling " + p.getStock() + " of " + p.getName() + " instead");
            amount =  p.getStock();
        }
        p.setStock(p.getStock() - amount);
        if(p.getStock() < 40)
        orderMore(p);
        System.out.println("Stock decreased");
    }

    /**
     * Generates an orderline for the specified Item.
     * @param item the Item object for which to generate an order.
     * @throws IOException thrown if there is an issue with IO stream.
     */
    public void orderMore(Item item) throws IOException{
        int amount = 50 - item.getStock();
        item.setStock(item.getStock() + amount);
        order.newOrder(item, amount);
    }

    /**
     * Links the Suppliers to the appropriate Items in the inventory. 
     */
    public void assignSuppliers(){
        for(int i = 0; i < inventory.getSize(); i++){
            for(Supplier s: suppliers){
                if(inventory.getItemAt(i).getSupplierID() == s.getID())
                    inventory.getItemAt(i).assignSupplier(s);
            }
        }
    }

    /**
     * Adds Suppliers to the shop by reading from a text file.
     * @throws FileNotFoundException thrown if there is an issue with file access. 
     */
    public void addSuppliersText() throws FileNotFoundException{
        Scanner read = new Scanner(new File("suppliers.txt"));
        read.useDelimiter(";");
        String id, name, address, contact, nextLine;
        String[] arr = new String[4];
        int ID;
        while(read.hasNext()){
            nextLine = read.nextLine();
            arr = nextLine.split(";");
            id = arr[0];
            name = arr[1];
            address = arr[2];
            contact = arr[3];
            
            ID = Integer.parseInt(id);
            suppliers.add(new Supplier(ID, name, address, contact));
        }
        read.close();
    }

    /**
     * Adds an item to the inventory with the specified values.
     * @param name the name of the item.
     * @param id the id number of the item.
     * @param price the price of the item.
     * @param stock the amount in stock for the item.
     * @param supID the supplier id for the item.
     */
    private void addItem(String name, int id, double price, int stock, int supID){
        inventory.addItem(new Item(id, name, price, stock, supID));
    }

    /**
     * Checks if the Supplier specified by the id exists.
     * @param id the id of the supplier to be checked for existance.
     * @return returns true if the Supplier exists, false if it does not.
     */
    private boolean supplierIDChecker(int id){
        for(Supplier s: suppliers){
            if(id == s.getID())
                return true;
        }
        return false;
    }

    /**
     * Adds an item to the inventory, read from the input stream, prompting the
     * user.  
     */
    public void addItem(){
        Scanner scan = new Scanner(System.in);
        String temp, name = ""; int id = -1; int stock = -1; int supID = -1; double price = -1;
        System.out.println("Enter the name of the new Item:");
        while(name.equals("")){
            name = scan.nextLine();
            if(name.equals(""))
                System.err.println("invalid name");
        }
        System.out.println("Enter id of new Item");
        do{
            temp = scan.nextLine();
            try{id = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(id < 0 || id > 9999)
                System.err.println("Invalid input. Please Try Again: \n");
            if(inventory.itemIDChecker(id)){
                System.err.println("That id is already used, please enter a unique id number");
                id = -1;
            }
        }while(id < 0 || id > 9999);
        System.out.println("Enter quantity of new item");
        do{
            temp = scan.nextLine();
            try{stock = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(stock < 40)
                System.err.println("Invalid input. Please Try Again, stock must be >= 40: \n");
        }while(stock < 40);
        System.out.println("Enter supplier id of new Item");
        do{
            temp = scan.nextLine();
            try{supID = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(supID < 0 || supID > 9999)
                System.err.println("Invalid input. Please Try Again: \n");
            if(!supplierIDChecker(supID)){
                System.err.println("That Supplier doesn't exist, please enter valid supplier id");
                supID = -1;
            }
        }while(supID < 0 || supID > 9999);
        System.out.println("Enter price of new Item");
        do{
            temp = scan.nextLine();
            try{price = Double.parseDouble(temp);}catch(Exception a){
                System.err.println("Non Double input. Please Try Again");
            }
            if(price < 0)
                System.err.println("Invalid input. Please Try Again: \n");
        }while(price < 0);
        addItem(name, id, price, stock, supID);
        assignSuppliers();
        System.out.println("Item added!");
    }

    /**
     * Deletes or keeps the specified item from the inventory by prompting
     * the user and reading from the input stream.
     * @param p the Item which is to be deleted.
     */
    public void deleteItem(Item p){
        System.out.println("\nWould you like to delete this item?");
        System.out.println("press 'y' if yes");
        System.out.println("press 'n' if no");
        Scanner scan = new Scanner(System.in);
        String temp = "";
        do{
            temp = scan.nextLine();
            if(!temp.equals("y") && !temp.equals("n")){
                temp = "";
                System.out.println("invalid input, try again: ");
            }
        } while(temp.equals(""));
        
        if(temp.equals("y")){
            inventory.deleteItem(p);
            System.out.println("Item deleted");
            return;
        }
        System.out.println("Ok Item not deleted");
    }

    /**
     * Prints all items in the inventory with their details.
     */
    public void listAllItems(){
        System.out.println("Items in inventory and their details:");
        System.out.println(inventory.toString());
    }

    /**
     * Prints all Suppliers that the shop has with their details.
     */
    public void listAllSuppliers(){
        System.out.println("Suppliers and their details:");
        String s = "";
        for(Supplier sup: suppliers){
            s += sup.toString() + "\n";
        }
        System.out.println(s);
    }

    /**
     * Prints the quantity in stock of an item by prompting the user to select
     * an item. 
     */
    public void checkQuantity(){
        System.out.println("Please choose how you would like to select an item to be checked: ");
        System.out.println("1. Search item by name");
        System.out.println("2. Search item by id");
        Scanner scan = new Scanner(System.in);
        String temp; int choice = -1;
        do{
            temp = scan.nextLine();
            try{choice = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Non Integer input. Please Try Again");
            }
            if(choice != 1 && choice != 2)
                System.err.println("Invalid input. Please Try Again: \n");
        }while(choice != 1 && choice != 2);

        if(choice == 1){
            System.out.println("The quantity of the item is " + searchName(2).getStock());
            
        } else if(choice ==  2)
            System.out.println("The quantity of the item is " + searchID(2).getStock());
        else
            System.err.println("Error");
    }

    /**
     * Searches for an item in the inventory by prompting the user to enter
     * name of the item. 
     * @param option determines what the method will do: 1 to print details
     * about an item, or 2 to return the item to be used by another method.
     * @return returns an item if the parameter is 1, returns null if user
     * quits or the parameter is 2. 
     */
    public Item searchName(int option){
        String name;
        Item p;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the item you are looking for: ");
        name = scan.nextLine();
        p = inventory.searchByName(name);
        while(p == null){
            System.out.println("That item was not found, please try again, or type quit to exit: ");
            name = scan.nextLine();
            if(name.equals("quit"))
                return null;
            p = inventory.searchByName(name);
        }
        if(option == 1){
            System.out.println("The item you were looking for: ");
            System.out.println(p.toString());
            deleteItem(p);
        } else {
            return p;
        }
        return null;
    }

    /**
     * Searches for an item in the inventory by prompting the user to enter
     * id number of the item. 
     * @param option determines what the method will do: 1 to print details
     * about an item, or 2 to return the item to be used by another method.
     * @return returns an item if the parameter is 1, returns null if user
     * quits or the parameter is 2. 
     */
    public Item searchID(int option){
        String id;
        int num;
        Item p;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the id of the item you are looking for: ");
        id = scan.nextLine();
        try{
            num = Integer.parseInt(id);
            p = inventory.searchById(num);
        }catch(Exception a){
            System.err.println("Invalid input");
            p = null;
        }
        while(p == null){
            System.out.println("That item was not found, please try again, or type quit to exit: ");
            id = scan.nextLine();
            if(id.equals("quit"))
                return null;
            try{
                num = Integer.parseInt(id);
                p = inventory.searchById(num);
            }catch(Exception a){
                System.err.println("Invalid input");
                p = null;
            }
        }
        if(option == 1){
            System.out.println("The item you were looking for: ");
            System.out.println(p.toString());
            deleteItem(p);
        } else{
            return p;
        }
        return null;
    }
}