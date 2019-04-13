package Server.Model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import Server.Controller.*;

/** 
 * The class which acts as the main Shop, allowing the various classes on the server side to interface with the client side classses through the socket. 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class Shop implements Runnable{

    /**
     * The object of Order that manages the orderlines for the shop. 
     */
    private Order order;

    /**
     * Object which allows this class to communicate with the databse table of items. 
     */
    private ItemDatabaseController database;

    /**
     * Object which allows this class to communicate with the databse table of logins. 
     */
    private LoginDatabaseController logindDb;

    /**
     * Object which allows this class to communicate with the databse table of suppliers. 
     */
    private SupplierDatabaseController supplierDb;

    /**
     * The socket with which to communicate with the the client
     */
    Socket socketIn;
    /**
     * writer to write to the socket. 
     */
    PrintWriter out;

    /**
     * Reader to read input from the socket. 
     */
    BufferedReader in;
    
    /**
     * Constructs an object of Shop with the specified objects. Calls functions
     * to intialize the readers and writers for the socket. 
     * @param o the Order object assigned to Shop.
     * @param db the database controller for items table.
     * @param loginDb the database controller for the logins table. 
     * @param supplierDb the database controller for the supplier table. 
     * @param socket the socket to communicate with the client. 
     */
    public Shop(Order o, ItemDatabaseController db, LoginDatabaseController loginDb, SupplierDatabaseController supplierDb, Socket socket){
        order = o;
        socketIn = socket;

        try{
            in = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			out = new PrintWriter((socketIn.getOutputStream()), true);
        }catch(Exception a){
            sendString("error");
            a.printStackTrace();
        }

        database = db;
        this.logindDb = loginDb;
        this.supplierDb = supplierDb;
    }
    /**
     * Reads input from the client through the socket and returns it. 
     * @return integer which the client sent. 
     * @throws IOException thrown if there is an issue with the socket. 
     */
    public int menu() throws IOException {
        String temp; int choice = 0;
        temp = in.readLine();
        choice = Integer.parseInt(temp);
        return choice;
    }

    /**
     * Writes to the socket to send to the client. 
     * @param toSend the String to be sent to the client. 
     */
    void sendString(String toSend){
        out.println(toSend);
        out.flush();
    }

    /**
     * Swich statement which receives an integer from the menu() method, calls the method requested by the client. 
     * @throws IOException thrown if there is an issue with the socket. 
     */
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
                    break;
                case 6:
                    validateLogin();
                    break;
                case 7:
                    addItem();
                    break;
                case 8:
                    displayOrders();
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

    /**
     * sends the client the orders to be displayed
     */
    public void displayOrders(){
        sendString(order.toString());
    }

    /**
     * Takes login input from the client and compares them to the database of logins to make sure it is valid
     * @throws IOException thrown if there is issue. 
     */
    public void validateLogin()throws IOException{
        String user = in.readLine();
        String pass = in.readLine();

        boolean valid = logindDb.validateUser(user, pass);

        if(!valid){
            sendString("fail");
        } else{
            sendString("success");
        }
    }
    
    /**
     * deletes an item from the database based on the clients input
     * @throws IOException thrown if there is issue
     */
    public void deleteItem() throws IOException{
        String itemId = in.readLine();
        int id = -1;
        
        try{
            id = Integer.parseInt(itemId);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.deleteItem()");
        }

        database.deleteItem(id);
    }

    /**
     * decreases the stock of an item based on client input, creates a new order if it is required. 
     * @throws IOException thrown if there is an issue
     */
    public synchronized void decreaseQuantity() throws IOException{
        String itemId = in.readLine();
        int id = -1;
        
        try{
            id = Integer.parseInt(itemId);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.decreaseItem()");
        }
        // Item p = searchID(id);      // item to be decreased
        String amountTobeDecreased = in.readLine();
        int amount = -1;
        
        try{
            amount = Integer.parseInt(amountTobeDecreased);
        } catch(NumberFormatException a){
            System.out.println("error converting socket input to int in Shop.decreaseItem()");
        }

        int stockOfItem = database.getStock(id);

        if(amount > stockOfItem){
            sendString("notEnoughSelling: " + stockOfItem);
            amount =  stockOfItem;
        } else{
            sendString("success");
        }
        database.setStock(id, (stockOfItem - amount));
        stockOfItem = database.getStock(id);
        if(stockOfItem < 40){
            orderMore(id);
        }
    }

    /**
     * searches for an item based on client input, sends the client item info or an error message if there is no such item. 
     * @throws IOException thrown if there is an issue 
     */
    public void search() throws IOException{
        String nameOrId = in.readLine();
        int id = -1;
        String itemToString = null;

        if(nameOrId.equals("id")){
            try{
                id = Integer.parseInt(in.readLine());
            } catch(NumberFormatException a){
                System.out.println("error converting socket input to int in Shop.search()");
            }
            // p = searchID(id);
            itemToString = database.search(id);
        } else{
            // p = searchName(in.readLine());
            itemToString = database.search(in.readLine());
        }
        if(itemToString == null){
            sendString("fail");
        } else{
            // sendString(p.toString());
            sendString(itemToString);
        }
    }

    /**
     * makes a new order for an item and restocks item to 50 if they fall below 40. 
     * @param itemId the id of the item to be ordered more of
     * @throws IOException thrown if there is an issue. 
     */
    public void orderMore(int itemId) throws IOException{
        int itemStock = database.getStock(itemId);
        int amount = 50 - itemStock;
        database.setStock(itemId, (itemStock + amount));
        System.out.println("ordering more");

        int supplierId = database.getSupplierId(itemId);
        String supplierName = supplierDb.getSupplierName(supplierId);

        String itemToString2 = database.toString2(itemId, amount, supplierName);

        order.newOrder(itemId, itemToString2);
    }

    /**
     * Adds an item based on client input, but return an error through the socket if conditions arent met. 
     * @throws IOException thrown if there is an issue. 
     */
    public void addItem()throws IOException{
        String temp, name = ""; int id = -1; int stock = -1; int supID = -1; double price = -1;
        String error = "";
        
        name = in.readLine();
            if(name.equals("") || name.equals(" ")){
                error += "name ";
            }

        id = Integer.parseInt(in.readLine());
            if(id < 0 || id > 9999 || database.itemAlreadyExists(id)){
                error += "id ";
            }

        stock = Integer.parseInt(in.readLine());
            if(stock < 40){
                error += "stock ";
            }

        supID = Integer.parseInt(in.readLine());
            // if(!supplierIDChecker(supID)){
            //     error += "supplier ";
            // }
            if(!supplierDb.supplierExists(supID)){
                error += "supplier ";
            }

        price = Double.parseDouble(in.readLine());
        
        if(error.equals("")){
            // addItem(name, id, price, stock, supID);
            database.addData(id, name, price, supID, stock);
            // assignSuppliers();
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
        // sendString("41");
        sendString(database.getItemCount());
        sendString(database.listAll());
    }

    /**
     * sends string with all the items on the database. 
     */
    public void listAllSuppliers(){
        String list = supplierDb.listAll();
        sendString(list);
    }

    /**
     * The run method which makes this class a runnable, calls the menuRunner() method. 
     */
    @Override
    public void run() {
        try{
            menuRunner();
        }catch(SocketException a){
            System.err.println("    ---A Client disconnected");
            Thread.currentThread().interrupt();
            return;      
        }catch(IOException a){
            System.err.println(" run caught error ");
            a.printStackTrace();
        }catch(Exception b){
            System.err.println(" run caught error 2 ");
            b.printStackTrace();
        }
    }
}