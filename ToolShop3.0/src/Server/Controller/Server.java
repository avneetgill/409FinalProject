package Server.Controller;

import Server.Model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/** 
 * Class which is the server for the program. 
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

    /**
     * Objects which deal with the new order that are created
     */
    private ArrayList<OrderLine> line;
    private Order order;

    /**
     * The connection to the database
     */
    private Connection myConn;

    /**
     * The classes that deal with the different tables on the database. 
     */
    private DatabaseController database;
    private LoginDatabaseController loginDatabase;
    private SupplierDatabaseController supplierDatabase;

    /** 
     * Constructs a Server and initializes the ServerSocket. 
     * @param portNumber the port that the ServerSocket will be initialized to
     */
    public Server(int portNumber){
        try{
            myServer = new ServerSocket(portNumber);
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        } catch(IOException a){
            System.err.println("Error creating new ServerSocket");
            a.printStackTrace();
        } catch(SQLException a){
            System.err.println("Error connecting to database");
            a.printStackTrace();
        }
        System.out.println("<< Server is Running >>");
    }

    /**
     * Allows communication between the Server and the clients, runs the game of tic tac toe once enough clients have joined. 
     * @throws IOException thrown if there is an issue with I/O. 
     */
    public void communicateClient()throws IOException{
        database = new DatabaseController(myConn);
        loginDatabase = new LoginDatabaseController(myConn);
        supplierDatabase = new SupplierDatabaseController(myConn);

        line = new ArrayList<OrderLine>();
        order = new Order(line);

        try{
            while(true){
                System.out.println(" At loopTop ");
                pool = Executors.newCachedThreadPool();
                aSocket = myServer.accept();

                Shop store = new Shop(order, database, loginDatabase, supplierDatabase, aSocket);

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
     * runs the server with port number 9091
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // to run this main: javac -d classes Server/Controller/Server.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Server.Controller.Server

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