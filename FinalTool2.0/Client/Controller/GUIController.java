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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Client.View.*;

/**
 * Controls the GUI views and also acts as the client
 */
public class GUIController {

    private PrintWriter socketOut;
	private Socket palinSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

    private ToolShopView view;
    private LoginView login;
    private AddView add;
    MyListener listener;

    public String selected;
    public String itemOrSupplier;

    public GUIController(ToolShopView view, LoginView login, AddView add, int port, String name) {
        this.view = view;
        this.login = login;
        this.add = add;

        view.setVisible(false);     // starts with main is not visble
        login.setVisible(true);     // but login is
        
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
                if(e.getSource() == login.b){
                    String username = login.getUserName();
                    String pass = login.getPassword();
                    System.out.println("usernam: " + username + ", password: " + pass);
                    if(username.equals("") || pass.equals("")){
                        System.out.println("error");
                        login.errorMessage("Please Enter Username and Password");
                    } else{
                        // if(username.valid() && password.valid()){
                            // check if user and pass are valid
                        // }
                        login.setVisible(false);
                        view.setVisible(true);
                    }
                }

                else if(e.getSource() == view.listToolButton){
                    
                    // view.errorMessage("list tools pressed");
                    view.textBox.clearSelection();
                    itemOrSupplier = "item";
                    view.model.clear();
                    socketOut.println("1");
                    socketOut.flush();
                    int itemAmount = Integer.parseInt(socketIn.readLine());
                    String s = "";
                    for(int i = 0; i < itemAmount; i++){
                        // s += socketIn.readLine() + "\n";
                        view.addElementTextBox(socketIn.readLine());
                    }
                    // s = s.substring(0, s.length() - 2);
                    System.out.println("Showing items");
                    view.changeButtonState(true);
                    // view.setText(s);
                } 
                
                else if(e.getSource() == view.listSupButton){
                    // view.errorMessage("list suppliers pressed");
                    view.changeButtonState(false);
                    view.textBox.clearSelection();
                    itemOrSupplier = "supplier";
                    view.model.clear();
                    socketOut.println("2");
                    socketOut.flush();
                    // int itemAmount = Integer.parseInt(socketIn.readLine());
                    String m = "";
                    for(int i = 0; i < 20; i++){
                        // m += socketIn.readLine() + "\n";
                        view.addElementTextBox(socketIn.readLine());
                    }
                    // m = m.substring(0, m.length() - 2);
                    System.out.println("Showing suppliers");
                    // view.setText(m);
                    // cleanBuffer();
                    // view.addButton.setEnabled(true);
                }

                else if(e.getSource() == view.deleteButton){
                    if(selected == null){
                        view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            view.errorMessage("Cannot delete suppliers");
                        } else{
                            view.model.removeElement(selected);
                            // code to actually delete item from database
                            view.errorMessage("Item removed");
                        }
                        // view.errorMessage(selected);
                    }
                }

                else if(e.getSource() == view.decreaseButton){
                    if(selected == null){
                        view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            view.errorMessage("Cannot decrease amount of suppliers");
                        } else{
                            String temp = null; int amount = 0;
                            temp = view.decreaseItemDialog();
                            try{
                                amount = Integer.parseInt(temp);
                            }catch(NumberFormatException a){
                                view.errorMessage("Please enter an integer value");
                                return;
                            }
                            
                            // code to actually decrease items from database
                            view.errorMessage(amount + "");

                        }
                    }
                }

                else if(e.getSource() == view.addButton){
                    add.setVisible(true);
                }

                else if(e.getSource() == add.returnButton){
                    add.setVisible(false);
                    add.clearText();

                }

                else if(e.getSource() == add.insertButton){
                    String id, name, price, supplierID, stock;
                    int idi, supplierIDi, stocki; double pricei;

                    name = add.getName();
                    id = add.getID();
                    price = add.getPrice();
                    supplierID = add.getSupID();
                    stock = add.getSupID();

                    if(name.equals("") || id.equals("") || price.equals("") || supplierID.equals("") || stock.equals("")){
                        add.errorMessage("Enter all fields");
                    } else{
                        try{
                            idi = Integer.parseInt(id);
                            supplierIDi = Integer.parseInt(supplierID);
                            stocki = Integer.parseInt(stock);
                            pricei = Double.parseDouble(price);
                        }catch(NumberFormatException a){
                            add.errorMessage("Enter valid numbers");
                            return;
                        }

                        
                    }
                }

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

        login.addSubmitListener(listener);

        add.addReturnListener(listener);
        add.addInsertListener(listener);
        
        view.addSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    selected = view.textBox.getSelectedValue();

                    // if(itemOrSupplier.equals("item")){
                    //     view.changeButtonState(true);
                    // }

                    if(selected != null){
                        // view.errorMessage(" xxxx " + selected);
                    }
                }
            }
        });
    }
    
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/GUIController.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.GUIController
        ToolShopView view = new ToolShopView();
        LoginView login = new LoginView();
        AddView add = new AddView();
        GUIController c = new GUIController(view, login, add, 8988, "localhost");
        c.addListeners();
    }

}