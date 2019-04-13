package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** 
 * Controller for the Add items GUI
 * 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class AddController{
    
    /**
     * The inner Listener class that will be assigned to the gui elements for add items view
     */
    private MyListener listener;

    /**
     * Controller object that connects all the Gui controller classes and lets them work together, and allows this class to access the GUIs. 
     */
    public Client c;

    /**
     * Constructs an AddController object
     * @param c The client object that this object is linked with
     */
    public AddController(Client c) {
        // super();
        // c.setAdd(add);
        this.c = c;
        listener = new MyListener();
        addListeners();
    }
    
    /**
     * Shows the user the meaning of the error received in the socket when incorrectly trying to add an item. 
     * @param error the error message sent by the Server to describe issues with the item trying to be added. 
     */
    public void errorMeaning(String error){
        String toShow = "";

        if(error.contains("name")){
            toShow += "Item Name must be proper words\n";
        }
        if(error.contains("id")){
            toShow += "Item id must be unique, and 0 < id < 9999\n";
        }
        if(error.contains("stock")){
            toShow += "Item initial stock must be >= 40\n";
        }
        if(error.contains("supplier")){
            toShow += "Item's supplier must exist, choose from Supplier List\n";
        }
        
        c.add.errorMessage(toShow);
    }

    /**
     * Inner class that is the listener for the Add view gui components. 
     */
    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == c.add.insertButton){
                    String id, name, price, supplierID, stock;
                    // int idi, supplierIDi, stocki; double pricei;

                    name = c.add.getName();
                    id = c.add.getID();
                    price = c.add.getPrice();
                    supplierID = c.add.getSupID();
                    stock = c.add.getStock();

                    if(name.equals("") || id.equals("") || price.equals("") || supplierID.equals("") || stock.equals("")){
                        c.add.errorMessage("Enter all fields");
                    } else{
                        try{
                            Integer.parseInt(id);
                            Integer.parseInt(supplierID);
                            Integer.parseInt(stock);
                            Double.parseDouble(price);
                        }catch(NumberFormatException a){
                            c.add.errorMessage("Enter valid numbers");
                            return;
                        }

                        c.socketOut.println("7");       // telling menu to add item

                        c.socketOut.println(name);      // passes paramters to Shop.addItem(): void
                        c.socketOut.println(id);
                        c.socketOut.println(stock);
                        c.socketOut.println(supplierID);
                        c.socketOut.println(price);

                        String temp = c.socketIn.readLine();
                        if(temp.equals("success")){
                            c.add.errorMessage(name + " added!");
                            c.view.listToolButton.doClick();
                            c.add.setVisible(false);
                            c.add.clearText();
                        } else{
                            errorMeaning(temp);
                        }

                    }
                }

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
        }
    }

    /**
     * Assigns the listener object to all the GUI components in Add View GUI
     */
    public void addListeners() {
        // c.add.addReturnListener(listener);
        c.add.addInsertListener(listener);
        c.add.addCloseListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                c.add.clearText();
                c.add.setVisible(false);
            }
        });
    }
    
}