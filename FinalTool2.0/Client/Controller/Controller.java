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
    
    protected LoginController loginController;

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

        
    }

    public static void main(String[] args) {

    }
}
