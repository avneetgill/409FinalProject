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
/**
 * AddController
 */
public class AddController extends Controller{
    
    private MyListener listener;
    public Controller c;

    public AddController(AddView add, Controller c) {
        super();
        c.setAdd(add);
        this.c = c;
        System.out.println("add set");
        listener = new MyListener();
        // addListeners();
    }
    
    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == add.returnButton){
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

    @Override
    public void addListeners() {
        c.add.addReturnListener(listener);
        c.add.addInsertListener(listener);
    }
    
}