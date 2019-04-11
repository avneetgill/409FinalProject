package Server.Controller;

import Server.Model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class Server {
    /**
     * Writer to write to the Player's Socket
     */
    private PrintWriter out;
    /**
     * Socket to communicate to the client with. 
     */
    private Socket aSocket;
    /**
     * Socket to function as the server, accepts many clients
     */
    private ServerSocket myServer;
    /**
     * Pool for threads, each game is ran as a seperate thread
     */
    private ExecutorService pool;

    
    
    ArrayList<OrderLine> line;
    Order order;

    ArrayList<Item> items;
    Inventory inventory;

    ArrayList<Supplier> suppliers;

    DatabaseController database;
    LoginDatabaseController loginDatabase;
    SupplierDatabaseController supplierDatabase;

    /** 
     * Constructs a Server and initializes the ServerSocket. 
     * @param portNumber the port that the ServerSocket will be initialized to
     */
    public Server(int portNumber){
        try{
            myServer = new ServerSocket(portNumber);
        } catch(IOException a){
            System.err.println("Error creating new ServerSocket");
            a.printStackTrace();
        }
        System.out.println("<< Server is Running >>");
    }

    void constructObjects()throws Exception{

        line = new ArrayList<OrderLine>();
        order = new Order(line);

        items = new ArrayList<Item>();
        inventory = new Inventory(items);
        inventory.addItemsText();

        suppliers = new ArrayList<Supplier>();

        // store = new Shop(order, suppliers, inventory);//, aSocket);

        // f = new FrontEnd(store);
    }
    /**
     * Allows communication between the Server and the clients, runs the game of tic tac toe once enough clients have joined. 
     * @throws IOException thrown if there is an issue with I/O. 
     */
    public void communicateClient()throws IOException{
        items = new ArrayList<Item>();
        inventory = new Inventory(items);
        // inventory.addItemsText();

        database = new DatabaseController();
        loginDatabase = new LoginDatabaseController();
        supplierDatabase = new SupplierDatabaseController();

        try{
            while(true){
                System.out.println(" At loopTop ");
                pool = Executors.newCachedThreadPool();
                aSocket = myServer.accept();


                line = new ArrayList<OrderLine>();
                order = new Order(line);

                // items = new ArrayList<Item>();
                // inventory = new Inventory(items);
                // inventory.addItemsText();

                suppliers = new ArrayList<Supplier>();


                Shop store = new Shop(order, database, loginDatabase, supplierDatabase, suppliers, inventory, aSocket);
                // store.setSocketIn(aSocket);

                // FrontEnd f = new FrontEnd(store);        // no longer used

                System.out.println("<< Shop app started >>");
                pool.execute(store);
                pool.shutdown();
            }
        } catch(Exception a){
            System.err.println("-- Exception caught in server loop --");
            a.printStackTrace();
            pool.shutdown();
        } finally{
            out.close();
        }
    }
    /**
     * runs the server with port number 8988
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // to run this main: javac -d classes Server/Controller/Server.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Server.Controller.Server

        // my local ip address (ipv4): 10.0.0.94(Preferred)
        //10.13.139.95
        //10.13.133.25
        Server s = new Server(9091);
        try {
            // s.constructObjects();
            s.communicateClient();
        } catch (IOException e) {
            System.err.println("Server main issue");
            e.printStackTrace();
        } catch(Exception a){
            System.err.println("Server issue main 2");
            a.printStackTrace();
        }
    }
}