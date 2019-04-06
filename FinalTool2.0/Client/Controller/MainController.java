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
public class MainController {
    
    private MyListener listener;
    private ToolShopView view;
    
    public MainController(ToolShopView v, AddController addController) {
        view = v;
        listener = new MyListener();
        addListeners();
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == view.listToolButton){
                    
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

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    @Override
    public void addListeners() {
        view.addSearchListener(listener);
        view.addListToolListener(listener);
        view.addLoadListener(listener);
        view.addListSupListener(listener);
        view.addDecreaseListener(listener);
        view.addDeleteListener(listener);
        view.addAddListener(listener);

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
    
}