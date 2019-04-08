package src.Server.Model;

import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
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

    Socket socketIn;
    PrintWriter out;
    BufferedReader in;
    
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
    public Shop(Order o, ArrayList<Supplier> s, Inventory i, Socket socket) throws FileNotFoundException{
        order = o;
        suppliers = s;
        inventory = i;
        socketIn = socket;

        try{
            in = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			out = new PrintWriter((socketIn.getOutputStream()), true);
        }catch(Exception a){
            sendString("error");
            a.printStackTrace();
        }

        addSuppliersText();
        // inventory.addItemsText();
        assignSuppliers();
    }

    public void setSocketIn(Socket socketIn) {
        this.socketIn = socketIn;
        try{
            in = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			out = new PrintWriter((socketIn.getOutputStream()), true);
        }catch(Exception a){
            sendString("error");
            a.printStackTrace();
        }
    }

    public int menu() throws IOException {
        String temp; int choice = 0;
        temp = in.readLine();
        // if(temp.equalsIgnoreCase("quit")){
        //     // input.close();
        //     return 8;
        // }
        choice = Integer.parseInt(temp);
        return choice;
    }

    void sendString(String toSend){
        out.println(toSend);
        out.flush();
    }

    public void menuRunner() throws IOException{
        boolean quit = false;
        while(true){
            switch(menu()){
                case 1:
                    listAllItems();
                    break;
                case 2:
                    listAllSuppliers();
                    break;
                case 3:
                    deleteItem();
                    break;
                case 4:
                    decreaseQuantity();
                    break;
                case 5:
                    search();
                    // checkQuantity();
                    break;
                case 6:
                    // decreaseQuantity();
                    break;
                case 7:
                    addItem();
                    break;
                default:
                    quit = true;                // this was below
                    sendString("Goodbye\1");    //order of these 2 lines were flipped
            }
            if(quit == true){
                in.close();
                out.close();
                break;
            }
        }

    }
    
    public void deleteItem() throws IOException{
        String itemId = in.readLine();
        int id = -1;
        
        try{
            id = Integer.parseInt(itemId);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.deleteItem()");
        }

        Item toBeDeleted = searchID(id);
        inventory.deleteItem(toBeDeleted);

        // System.out.println(inventory.toString());

    }

    public void decreaseQuantity() throws IOException{
        String itemId = in.readLine();
        int id = -1;
        
        try{
            id = Integer.parseInt(itemId);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.decreaseItem()");
        }

        Item p = searchID(id);      // item to be decreased

        String amountTobeDecreased = in.readLine();
        int amount = -1;
        
        try{
            amount = Integer.parseInt(amountTobeDecreased);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.decreaseItem()");
        }

        if(amount > p.getStock()){
            sendString("notEnoughSelling: " + p.getStock());
            amount =  p.getStock();
        } else{
            sendString("success");
        }
        p.setStock(p.getStock() - amount);
        if(p.getStock() < 40){
            orderMore(p);
        }
    }

    public void search() throws IOException{
        String nameOrId = in.readLine();
        int id = -1;
        Item p = null;

        if(nameOrId.equals("id")){
            try{
                id = Integer.parseInt(in.readLine());
            } catch(NumberFormatException a){
                System.out.println("error converting socket input to int in Shop.search()");
            }
            p = searchID(id);
        } else{
            p = searchName(in.readLine());
        }
        if(p == null){
            sendString("fail");
        } else{
            sendString(p.toString());
        }
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
    public void addItem()throws IOException{
        String temp, name = ""; int id = -1; int stock = -1; int supID = -1; double price = -1;
        String error = "";
        
        name = in.readLine();
            if(name.equals("") || name.equals(" ")){
                error += "name ";
            }

        id = Integer.parseInt(in.readLine());
            if(id < 0 || id > 9999 || inventory.itemIDChecker(id)){
                error += "id ";
            }

        stock = Integer.parseInt(in.readLine());
            if(stock < 40){
                error += "stock ";
            }

        supID = Integer.parseInt(in.readLine());
            if(!supplierIDChecker(supID)){
                error += "supplier ";
            }

        price = Double.parseDouble(in.readLine());
        
        if(error.equals("")){
            addItem(name, id, price, stock, supID);
            assignSuppliers();
            sendString("success");
        } else{
            sendString(error);
        }
    }

    /**
     * Prints all items in the inventory with their details.
     */
    public void listAllItems(){
        // sendString("Items in inventory and their details:");    
        // sendString("Items in inventory and their details:");
        sendString(inventory.toString());
    }

    /**
     * Prints all Suppliers that the shop has with their details.
     */
    public void listAllSuppliers(){
        // sendString("Suppliers and their details:");
        // System.out.println("in lisTsupplier");
        String s = "";
        for(Supplier sup: suppliers){
            s += sup.toString() + "\n";
        }
        s = s.substring(0, s.length()-1);
        sendString(s);
    }



    /**
     * Searches for an item in the inventory by prompting the user to enter
     * id number of the item. 
     * @param option determines what the method will do: 1 to print details
     * about an item, or 2 to return the item to be used by another method.
     * @return returns an item if the parameter is 1, returns null if user
     * quits or the parameter is 2. 
     */
    public Item searchID(int itemId)throws IOException{
        return inventory.searchById(itemId);
    }

    public Item searchName(String name) throws IOException{
        return inventory.searchByName(name);
    }
}