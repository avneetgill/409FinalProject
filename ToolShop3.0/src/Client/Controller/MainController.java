package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/** 
 * Controller for the Main ToolShopView GUI
 * 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class MainController{
    
    /**
     * The inner Listener class that will be assigned to the gui elements for add items view
     */
    private MyListener listener;

    /**
     * Controller object that connects all the Gui controller classes and lets them work together, and allows this class to access the GUIs. 
     */
    public Client c;

    /**
     * string which indicate the status of the GUI, which item has been selected from the list
     */
    public String selected;
    
    /**
     * string which indicate the status of the GUI, list is being displayed, item or supplier
     */
    public String itemOrSupplier;
    
    /**
     * Constructs a MainController object
     * @param c The Client object that this object is linked with
     */
    public MainController(Client c) {
        this.c = c;
        listener = new MyListener();
        addListeners();
    }

    /**
     * Inner class that is the listener for the Login view gui components. 
     */
    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == c.view.listToolButton){
                    
                    c.view.textBox.clearSelection();
                    itemOrSupplier = "item";
                    c.view.model.clear();
                    c.socketOut.println("1");
                    c.socketOut.flush();
                    int itemAmount = Integer.parseInt(c.socketIn.readLine());
                    for(int i = 0; i < itemAmount; i++){
                        String temp = c.socketIn.readLine();
                        // System.out.println(temp);
                        c.view.addElementTextBox(temp);
                    }
                    System.out.println("Showing items");
                    c.view.changeButtonState(true);
                } 
                
                else if(e.getSource() == c.view.listSupButton){

                    c.view.changeButtonState(false);
                    c.view.textBox.clearSelection();
                    itemOrSupplier = "supplier";
                    c.view.model.clear();
                    c.socketOut.println("2");
                    c.socketOut.flush();
                    for(int i = 0; i < 20; i++){
                        c.view.addElementTextBox(c.socketIn.readLine());
                    }
                    System.out.println("Showing suppliers");
                }

                else if(e.getSource() == c.view.deleteButton){
                    System.out.println("delete item");
                    if(selected == null){
                        c.view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            c.view.errorMessage("Cannot delete suppliers");
                        } else{
                            c.socketOut.println("3");       // tells shop menu we're deleting item

                            String[] arr = selected.split(",");     // to get the id of the item being deleted
                            String temp = arr[0];
                            String itemId = temp.replaceAll("id: ", "");

                            c.socketOut.println(itemId);    // gives Shop.deleteItem() the item id

                            c.view.model.removeElement(selected);
                            c.view.errorMessage("Item removed");
                        }
                    }
                }

                else if(e.getSource() == c.view.decreaseButton){
                    System.out.println("decrease item");
                    if(selected == null){
                        c.view.errorMessage("Please select an item");
                    } else{
                        if(selected.contains(", contact: ")){               // in the rare case that supplier does enable delete button
                            c.view.errorMessage("Cannot decrease amount of suppliers");
                        } else{
                            c.socketOut.println("4");

                            String[] arr = selected.split(",");     // to get the id of the item being decreased
                            String temp = arr[0];
                            String itemId = temp.replaceAll("id: ", "");

                            c.socketOut.println(itemId);    // gives Shop.decreaseItem() the item id

                            String temp2 = null; int amount = 0;         //to get the amount to be decreased
                            temp2 = c.view.decreaseItemDialog();
                            try{
                                amount = Integer.parseInt(temp2);
                            }catch(NumberFormatException a){
                                c.view.errorMessage("Please enter an integer value");
                                return;
                            }

                            c.socketOut.println(temp2);             // gives Shop.decreaseItem() the amount to be deleted
                            
                            temp2 = c.socketIn.readLine();

                            // System.out.println(temp2 + " temp 2 value");

                            if(temp2.contains("notEnoughSelling: ")){       // if not enough to sell
                                arr = temp2.split(" ");
                                temp2 = arr[1];
                                c.view.errorMessage("Did not have enough of this item to sell, instead sold " + temp2);
                            } else{                                 // if yesss enough to sell
                                c.view.errorMessage("Sold " + amount + " of this item");
                            }
                            // c.view.errorMessage("Please press List items to refresh the list");
                            c.view.listToolButton.doClick();
                        }
                    }
                }

                else if(e.getSource() == c.view.addButton){
                    c.add.setVisible(true);
                }

                else if(e.getSource() == c.view.searchButton){
                    c.search.setVisible(true);
                }

                else if(e.getSource() == c.view.orderButton){
                    c.socketOut.println("8");
                    c.orders.setTextArea("");
                    int numberOfEntries = Integer.parseInt(c.socketIn.readLine());
                    numberOfEntries = 4*numberOfEntries +1;     //each entry has 2 lines of [***] and 3 lines of info
                    System.out.println(numberOfEntries);
                    String result = "";
                    for(int i = 0; i < numberOfEntries; i++){
                        String temp = c.socketIn.readLine();
                        result += temp + "\n";
                    }
                    c.orders.setTextArea(result);
                    c.orders.setVisible(true);
                }

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    /**
     * Assigns the listener object to all the GUI components in login View GUI
     */
    public void addListeners() {
        c.view.addSearchListener(listener);
        c.view.addListToolListener(listener);
        c.view.addOrderListener(listener);
        c.view.addListSupListener(listener);
        c.view.addDecreaseListener(listener);
        c.view.addDeleteListener(listener);
        c.view.addAddListener(listener);

        c.view.addSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    selected = c.view.textBox.getSelectedValue();

                    if(selected != null){
                        // c.view.errorMessage(" xxxx " + selected);
                    }
                }
            }
        });
    }
    
}