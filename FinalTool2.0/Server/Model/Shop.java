package Server.Model;

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
    
    public int menu2(){      // deprecated
        sendString("***************************");
        sendString("Choose an Option: \n");
        sendString("1. List all Tools\n");
        sendString("2. List all Suppliers\n");
        sendString("3. Search tool by name\n");
        sendString("4. Search tool by ID\n");
        sendString("5. Check item quantity\n");
        sendString("6. Decrease item Quanity\n");
        sendString("7. Add an item manually\n");
        sendString("> Or type 'quit' to exit <");
        Scanner input = new Scanner(System.in);
        int choice = -1;
        String temp;
        
        temp = input.nextLine();
        if(temp.equals("quit")){
            input.close();
            return 8;
        }

        while(choice == -1){ 
            try{choice = Integer.parseInt(temp);}catch(Exception a){
                sendString("Invalid input. Please Try Again: \n");
                temp = input.nextLine();
            }
        }
        return choice;
    }

    public int menu3(){      // testing
        sendString("***************************");
        sendString("Choose an Option: \n");
        sendString("1. List all Tools\n");
        sendString("2. List all Suppliers\n");
        sendString("3. Search tool by name\n");
        sendString("4. Search tool by ID\n");
        sendString("5. Check item quantity\n");
        sendString("6. Decrease item Quanity\n");
        sendString("7. Add an item manually\n");
        sendString("> Or type 'quit' to exit <\0");
        // Scanner input = new Scanner(System.in);
        int choice = -1;
        String temp;
        
        try{
            temp = in.readLine();
            if(temp.equalsIgnoreCase("quit")){
                // input.close();
                return 8;
            }

            while(choice == -1){ 
                try{choice = Integer.parseInt(temp);}catch(Exception a){
                    sendString("Invalid input. Please Try Again: \n\0");
                    temp = in.readLine();
                }
            }
        }catch(IOException a){
            sendString(" error reading from socket menu ");
            a.printStackTrace();
        }
        return choice;
    }

    public int menu() throws IOException {
        // sendString("Welcome\0");
        String temp; int choice = 0;
        // try{
            temp = in.readLine();
            if(temp.equalsIgnoreCase("quit")){
                // input.close();
                return 8;
            }
            choice = Integer.parseInt(temp);

        // } catch(SocketException b){
        //     System.err.println("Socket error, must restart server");
        //     socketIn.close();
        //     in.close();
        //     out.close();
        //     // b.printStackTrace();
        // } catch (IOException a){
        //     System.err.println("IOexcpetion in shop menu");
        //     return 8;
        // }   
        return choice;
    }

    void sendString(String toSend){
        out.println(toSend);
        out.flush();
    }

    public void pressEnter() throws IOException{
        sendString("<<< Press enter to continue >>>\n\0");
        // Scanner enter = new Scanner(System.in);
        // String s = enter.nextLine();
        String s = in.readLine();
    }

    public void menuRunner() throws IOException{
        boolean quit = false;
        while(true){
            switch(menu()){
                case 1:
                    listAllItems();
                    break;
                    // pressEnter();
                case 2:
                    listAllSuppliers();
                    break;
                    // pressEnter();
                case 3:
                    searchName(1);
                    break;
                    // pressEnter();
                case 4:
                    searchID(1);
                    // pressEnter();
                    break;
                case 5:
                    checkQuantity();
                    // pressEnter();
                    break;
                case 6:
                    decreaseQuantity();
                    // pressEnter();
                    break;
                case 7:
                    addItem();
                    // pressEnter();
                    break;
                default:
                    // sendString("Goodbye");
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
    
    
    /**
     * Decreases the amount in stock for an Item by a specified amount, read 
     * from the input stream by prompting the user. 
     * @throws IOException thrown if there is an issue IO stream
     */
    public void decreaseQuantity() throws IOException{
        Item p;
        sendString("Please choose how you would like to select an item to decrease quantity ");
        sendString("1. Search item by name");
        sendString("2. Search item by id\0");
        // Scanner scan = new Scanner(System.in);
        String temp; 
        int choice = -1, amount = -1;
        do{
            temp = in.readLine();
            try{
                choice = Integer.parseInt(temp);
            }catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(choice != 1 && choice != 2)
                sendString("Invalid input. Please Try Again: \n\0");
        }while(choice != 1 && choice != 2);

        if(choice == 1)
            p = searchName(2);
        else
            p = searchID(2);

        sendString("How much would you like to decrease: \0");
        do{
            temp = in.readLine();
            try{
                amount = Integer.parseInt(temp);
            }catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(amount < 0)
                sendString("Invalid input. Please Try Again: \n\0");
        } while(amount < 0);

        if(amount > p.getStock()){
            sendString("Sorry we dont have that much of " + p.getName() + " in stock");
            sendString("Selling " + p.getStock() + " of " + p.getName() + " instead");
            amount =  p.getStock();
        }
        p.setStock(p.getStock() - amount);
        if(p.getStock() < 40)
        orderMore(p);
        sendString("Stock decreased");
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
        // Scanner scan = new Scanner(System.in);
        String temp, name = ""; int id = -1; int stock = -1; int supID = -1; double price = -1;
        sendString("Enter the name of the new Item:\0");
        while(name.equals("")){
            name = in.readLine();
            if(name.equals(""))
                sendString("invalid name\0");
        }
        sendString("Enter id of new Item\0");
        do{
            temp = in.readLine();
            try{id = Integer.parseInt(temp);}catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(id < 0 || id > 9999)
                sendString("Invalid input. Please Try Again: \n\0");
            if(inventory.itemIDChecker(id)){
                sendString("That id is already used, please enter a unique id number\0");
                id = -1;
            }
        }while(id < 0 || id > 9999);

        sendString("Enter quantity of new item\0");
        do{
            temp = in.readLine();
            try{stock = Integer.parseInt(temp);}catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(stock < 40)
                sendString("Invalid input. Please Try Again, stock must be >= 40: \n\0");
        }while(stock < 40);

        sendString("Enter supplier id of new Item\0");
        do{
            temp = in.readLine();
            try{supID = Integer.parseInt(temp);}catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(supID < 0 || supID > 9999)
                sendString("Invalid input. Please Try Again: \n\0");
            if(!supplierIDChecker(supID)){
                sendString("That Supplier doesn't exist, please enter valid supplier id\0");
                supID = -1;
            }
        }while(supID < 0 || supID > 9999);

        sendString("Enter price of new Item\0");
        do{
            temp = in.readLine();
            try{price = Double.parseDouble(temp);}catch(Exception a){
                sendString("Non Double input. Please Try Again\0");
            }
            if(price < 0)
                sendString("Invalid input. Please Try Again: \n\0");
        }while(price < 0);

        addItem(name, id, price, stock, supID);
        assignSuppliers();
        sendString("Item added!");
    }

    /**
     * Deletes or keeps the specified item from the inventory by prompting
     * the user and reading from the input stream.
     * @param p the Item which is to be deleted.
     */
    public void deleteItem(Item p)throws IOException{
        sendString("\nWould you like to delete this item?");
        sendString("press 'y' if yes");
        sendString("press 'n' if no\0");
        // Scanner scan = new Scanner(System.in);
        String temp = "";
        do{
            temp = in.readLine();
            if(!temp.equals("y") && !temp.equals("n")){
                temp = "";
                sendString("invalid input, try again: \0");
            }
        } while(temp.equals(""));
        
        if(temp.equals("y")){
            inventory.deleteItem(p);
            sendString("Item deleted");
            return;
        }
        sendString("Ok Item not deleted");
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
        s = s.substring(0, s.length()-2);
        sendString(s);
    }

    /**
     * Prints the quantity in stock of an item by prompting the user to select
     * an item. 
     */
    public void checkQuantity()throws IOException{
        sendString("Please choose how you would like to select an item to be checked: ");
        sendString("1. Search item by name");
        sendString("2. Search item by id\0");
        // Scanner scan = new Scanner(System.in);
        String temp; int choice = -1;
        do{
            temp = in.readLine();
            try{choice = Integer.parseInt(temp);}catch(Exception a){
                sendString("Non Integer input. Please Try Again\0");
            }
            if(choice != 1 && choice != 2)
                sendString("Invalid input. Please Try Again: \n\0");
        }while(choice != 1 && choice != 2);

        if(choice == 1){
            sendString("The quantity of the item is " + searchName(2).getStock());
            
        } else if(choice ==  2)
            sendString("The quantity of the item is " + searchID(2).getStock());
        else
            sendString("Error");
            // sendString("Error");
    }

    /**
     * Searches for an item in the inventory by prompting the user to enter
     * name of the item. 
     * @param option determines what the method will do: 1 to print details
     * about an item, or 2 to return the item to be used by another method.
     * @return returns an item if the parameter is 1, returns null if user
     * quits or the parameter is 2. 
     */
    public Item searchName(int option)throws IOException{
        String name;
        Item p;
        // Scanner scan = new Scanner(System.in);
        sendString("Please enter the name of the item you are looking for: \0");
        name = in.readLine();
        p = inventory.searchByName(name);
        while(p == null){
            sendString("That item was not found, please try again, or type quit to exit: \0");
            name = in.readLine();
            if(name.equals("quit"))
                return null;
            p = inventory.searchByName(name);
        }
        if(option == 1){
            sendString("The item you were looking for: ");
            sendString(p.toString());
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
    public Item searchID(int option)throws IOException{
        String id;
        int num;
        Item p;
        // Scanner scan = new Scanner(System.in);
        sendString("Please enter the id of the item you are looking for: \0");
        id = in.readLine();
        try{
            num = Integer.parseInt(id);
            p = inventory.searchById(num);
        }catch(Exception a){
            sendString("Invalid input");
            p = null;
        }
        while(p == null){
            sendString("That item was not found, please try again, or type quit to exit: \0");
            id = in.readLine();
            if(id.equals("quit"))
                return null;
            try{
                num = Integer.parseInt(id);
                p = inventory.searchById(num);
            }catch(Exception a){
                sendString("Invalid input");
                p = null;
            }
        }
        if(option == 1){
            sendString("The item you were looking for: ");
            sendString(p.toString());
            deleteItem(p);
        } else{
            return p;
        }
        return null;
    }
}