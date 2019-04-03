package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client.View.*;

public class GUIController {

    private PrintWriter socketOut;
	private Socket palinSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

    private ToolShopView view;
    MyListener listener;

    public GUIController(ToolShopView view, int port, String name) {
        this.view = view;
        view.setVisible(true);
        // addListeners();
        listener = new MyListener();
        try {
            palinSocket = new Socket(name, port);
			// stdIn = new BufferedReader(new InputStreamReader(System.in));
			// stdIn = new BufferedReader(new InputStreamReader(controller.in));
			socketIn = new BufferedReader(new InputStreamReader(palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
        }catch(IOException a){
            a.printStackTrace();
        }
        // cleanBuffer();
    }
    
    public void cleanBuffer(){      // not needed
        String temp = null;
        try {
            while ((socketIn.readLine()) != null) {
                temp = null;
            }
        } catch (IOException e) {
            System.out.println("clear error");
            e.printStackTrace();
        }
    }
    

    class MyListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == view.listToolButton){
                    
                    // view.errorMessage("list tools pressed");
                    socketOut.println("1");
                    socketOut.flush();
                    // int itemAmount = Integer.parseInt(socketIn.readLine());
                    String s = "";
                    for(int i = 0; i < 42; i++){
                        s += socketIn.readLine() + "\n";
                    }
                    // s = s.substring(0, s.length() - 2);
                    System.out.println(s);
                    view.setText(s);
                } 
                
                else if(e.getSource() == view.listSupButton){
                    // view.errorMessage("list suppliers pressed");
                    socketOut.println("2");
                    socketOut.flush();
                    // int itemAmount = Integer.parseInt(socketIn.readLine());
                    String m = "";
                    for(int i = 0; i < 20; i++){
                        m += socketIn.readLine() + "\n";
                    }
                    // m = m.substring(0, m.length() - 2);
                    System.out.println(m);
                    view.setText(m);
                    // cleanBuffer();
                }

                else if(e.getSource() == view.loadButton){
                    socketOut.println("8");
                    socketOut.flush();
                    try{
                        socketIn.close();
                        palinSocket.close();
                    }catch(IOException b){
                        b.printStackTrace();
                    }
                }
                // view.refreshTextBox();
                // cleanBuffer();

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    public void addListeners(){
        view.addSearchListener(listener);
        view.addListToolListener(listener);
        view.addLoadListener(listener);
        view.addListSupListener(listener);
        view.addDecreaseListener(listener);
        view.addDeleteListener(listener);
        view.addAddListener(listener);
    }
    
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/GUIController.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.GUIController
        ToolShopView view = new ToolShopView();
        GUIController c = new GUIController(view, 8988, "localhost");
        c.addListeners();
    }

}