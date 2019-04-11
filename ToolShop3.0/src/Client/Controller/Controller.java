package Client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import Client.View.*;

public class Controller {

    private Socket palinSocket;
    protected PrintWriter socketOut;
    protected BufferedReader socketIn;
    
    public ToolShopView view;
    public LoginView login;
    public AddView add;
    public SearchView search;
    public OrderView orders;

    public Controller(int port, String name){//, MainController main, LoginController login, AddController add) {
        try {
            palinSocket = new Socket(name, port);
			socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        }catch(IOException a){
            a.printStackTrace();
        }

        view = new ToolShopView();
        login = new LoginView();
        add = new AddView();
        search = new SearchView();
        orders = new OrderView();
    }

    public void startGui(){
        login.setVisible(true); 
    }

    protected void addListeners(){}
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/Controller.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.Controller

        // 10.12.140.162
        Controller c = new Controller(9091, "10.13.140.162");
        
        AddController addController = new AddController(c);
        MainController main = new MainController(c);
        LoginController loginController = new LoginController(c);
        SearchController searchController = new SearchController(c);
        
        c.startGui();

    }
}
