package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * AddController
 */
public class AddController{
    
    private MyListener listener;
    public Controller c;

    public AddController(Controller c) {
        // super();
        // c.setAdd(add);
        this.c = c;
        listener = new MyListener();
        addListeners();
    }
    
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