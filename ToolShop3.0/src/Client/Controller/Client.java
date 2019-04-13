package Client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import Client.View.*;

/** 
 * Client in this program, that links all the various controllers and GUIS
 * 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class Client {

    /**
     * The socket assigned to this client, used for communication with the server.
     */
    private Socket palinSocket;

    /**
     * Used to write to the socket
     */
    protected PrintWriter socketOut;

    /**
     * used to read input from the socket.
     */
    protected BufferedReader socketIn;

    /**
     * The various GUIS that make up the client side of this program.
     */
    public ToolShopView view;
    public LoginView login;
    public AddView add;
    public SearchView search;
    public OrderView orders;

    /**
     * Constructs the client with the port name and number of the server. Also initializes various views and readers/writers to be used. 
     * @param port the port number to communicate with the server
     * @param name the server's address
     */
    public Client(int port, String name) {// , MainController main, LoginController login, AddController add) {
        try {
            palinSocket = new Socket(name, port);
            socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
            socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        } catch (IOException a) {
            a.printStackTrace();
        }

        view = new ToolShopView();
        login = new LoginView();
        add = new AddView();
        search = new SearchView();
        orders = new OrderView();
    }

    /**
     * Starts the GUI by making the login screen visible
     */
    public void startGui() {
        login.setVisible(true);
    }

    /**
     * Activates the Client
     * @param args command line arguments are not used
     */
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/Controller.java
        // java -cp
        // classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes
        // Client.Controller.Controller

        // for network connection, put instead of localhost the ip address (ipv4) of the
        // server computer
        Client c = new Client(9091, "localhost");
        
        AddController addController = new AddController(c);
        MainController main = new MainController(c);
        LoginController loginController = new LoginController(c);
        SearchController searchController = new SearchController(c);
        
        c.startGui();

    }
}
