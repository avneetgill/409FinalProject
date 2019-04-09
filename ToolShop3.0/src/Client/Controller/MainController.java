package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * MainController
 */
public class MainController{
    
    private MyListener listener;
    public Controller c;

    public String selected;
    public String itemOrSupplier;
    
    public MainController(Controller c) {
        this.c = c;
        listener = new MyListener();
        addListeners();
    }

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
                        c.view.addElementTextBox(c.socketIn.readLine());
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
                    String m = "";
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

            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

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