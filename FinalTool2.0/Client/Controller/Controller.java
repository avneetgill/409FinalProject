package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Client.View.*;

public class Controller {

    private Socket palinSocket;
    protected PrintWriter socketOut;
    protected BufferedReader socketIn;
    
    // protected MainController mainController;
    // protected LoginController loginController;
    // protected AddController addController;

    protected ToolShopView view;
    protected LoginView login;
    protected AddView add;

    protected String selected;
    protected String itemOrSupplier;

    public Controller(){}

    public Controller(int port, String name){//, MainController main, LoginController login, AddController add) {
        try {
            palinSocket = new Socket(name, port);
			socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        }catch(IOException a){
            a.printStackTrace();
        }

        // mainController = main;
        // loginController = login;
        // addController = add;
        
    }

    public void setAdd(AddView add) {
        this.add = add;
    }
    
    public void setLogin(LoginView login) {
        this.login = login;
        // System.out.println(login);
    }

    public void setView(ToolShopView view) {
        this.view = view;
    }

    public void startGui(){
        // System.out.println("in startGui");
        login.setVisible(true); 
    }

    protected void addListeners(){}
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/Controller.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.Controller

        ToolShopView view = new ToolShopView();
        LoginView login = new LoginView();
        AddView add = new AddView();

        Controller c = new Controller(8988, "localhost");
        
        AddController addController = new AddController(add, c);
        MainController main = new MainController(view, c);
        LoginController loginController = new LoginController(login, c);
        
        loginController.addListeners();
        main.addListeners();
        addController.addListeners();
        
        // loginController.startGui();
        
        c.startGui();

    }
}
