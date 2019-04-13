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

    private DatabaseController database;
    private LoginDatabaseController logindDb;
    private SupplierDatabaseController supplierDb;


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
    public Shop(Order o, DatabaseController db, LoginDatabaseController loginDb, SupplierDatabaseController supplierDb, Socket socket) throws FileNotFoundException{
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

    public void displayOrders(){
        sendString(order.toString());
    }

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

    public void listAllSuppliers(){
        String list = supplierDb.listAll();
        sendString(list);
    }

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