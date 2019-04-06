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
 * MainController
 */
public class MainController extends Controller{
    
    private MyListener listener;
    public Controller c;
    
    public MainController(ToolShopView v, Controller c) {
        super();
        c.setView(v);
        this.c = c;
        System.out.println("view set");
        listener = new MyListener();
        // addListeners();
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == c.view.listToolButton){
                    
                    // c.view.errorMessage("list tools pressed");
                    c.view.textBox.clearSelection();
                    itemOrSupplier = "item";
                    c.view.model.clear();
                    c.socketOut.println("1");
                    c.socketOut.flush();
                    int itemAmount = Integer.parseInt(c.socketIn.readLine());
                    String s = "";
                    for(int i = 0; i < itemAmount; i++){
                        // s += c.socketIn.readLine() + "\n";
                        c.view.addElementTextBox(c.socketIn.readLine());
                    }
                    // s = s.substring(0, s.length() - 2);
                    System.out.println("Showing items");
                    c.view.changeButtonState(true);
                    // c.view.setText(s);
                } 
                
                else if(e.getSource() == c.view.listSupButton){
                    // c.view.errorMessage("list suppliers pressed");
                    c.view.changeButtonState(false);
                    c.view.textBox.clearSelection();
                    itemOrSupplier = "supplier";
                    c.view.model.clear();
                    c.socketOut.println("2");
                    c.socketOut.flush();
                    // int itemAmount = Integer.parseInt(c.socketIn.readLine());
                    String m = "";
                    for(int i = 0; i < 20; i++){
                        // m += c.socketIn.readLine() + "\n";
                        c.view.addElementTextBox(c.socketIn.readLine());
                    }
                    // m = m.substring(0, m.length() - 2);
                    System.out.println("Showing suppliers");
                    // c.view.setText(m);
                    // cleanBuffer();
                    // c.view.addButton.setEnabled(true);
                }

                else if(e.getSource() == c.view.deleteButton){
                    if(selected == null){
                        c.view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            c.view.errorMessage("Cannot delete suppliers");
                        } else{
                            c.view.model.removeElement(selected);
                            // code to actually delete item from database
                            c.view.errorMessage("Item removed");
                        }
                        // c.view.errorMessage(selected);
                    }
                }

                else if(e.getSource() == c.view.decreaseButton){
                    if(selected == null){
                        c.view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            c.view.errorMessage("Cannot decrease amount of suppliers");
                        } else{
                            String temp = null; int amount = 0;
                            temp = c.view.decreaseItemDialog();
                            try{
                                amount = Integer.parseInt(temp);
                            }catch(NumberFormatException a){
                                c.view.errorMessage("Please enter an integer value");
                                return;
                            }
                            
                            // code to actually decrease items from database
                            c.view.errorMessage(amount + "");

                        }
                    }
                }

                else if(e.getSource() == c.view.addButton){
                    add.setVisible(true);
                }

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    @Override
    public void addListeners() {
        c.view.addSearchListener(listener);
        c.view.addListToolListener(listener);
        c.view.addLoadListener(listener);
        c.view.addListSupListener(listener);
        c.view.addDecreaseListener(listener);
        c.view.addDeleteListener(listener);
        c.view.addAddListener(listener);

        c.view.addSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    selected = c.view.textBox.getSelectedValue();

                    // if(itemOrSupplier.equals("item")){
                    //     c.view.changeButtonState(true);
                    // }

                    if(selected != null){
                        // c.view.errorMessage(" xxxx " + selected);
                    }
                }
            }
        });
    }
    
}