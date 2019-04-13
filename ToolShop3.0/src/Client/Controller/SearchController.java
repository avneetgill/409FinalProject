package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/** 
 * Controller for the Search item GUI
 * 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class SearchController{
    
    /**
     * Controller object that connects all the Gui controller classes and lets them work together, and allows this class to access the GUIs. 
     */
    public Client c;

    /**
     * string which indicate the status of the GUI, which option has been selected from the dropdown menu in the search GUI 
     */
    private String choice;

    /**
     * Constructs a MainController object
     * @param c The Client object that this object is linked with
     */
    public SearchController(Client c) {
        this.c = c;
        addListeners();
    }
    /**
     * Assigns the listener object to all the GUI components in login View GUI
     */
    public void addListeners() {
        c.search.addSubmitListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String input = "";
                    input = c.search.getInputText();

                    if(choice.contains("ID")){
                        try{
                            Integer.parseInt(input);
                        }catch(NumberFormatException a){
                            c.search.errorMessage("Please enter a valid ID");
                            return;
                        }
                        c.socketOut.println("5");       // tells menu to search for item
                        c.socketOut.println("id");
                        c.socketOut.println(input);     // sennding ID string to server
                    }

                    else if(choice.contains("Name")){
                        c.socketOut.println("5");       // tells menu to search for item
                        c.socketOut.println("name");
                        c.socketOut.println(input);     // sennding ID string to server
                    }
                    input = c.socketIn.readLine();
                    if(input.equals("fail")){
                        c.search.errorMessage("Item was not found");
                    } else{
                        c.search.errorMessage("Item found! Details:\n" + input);
                    }
                    
                }catch(Exception a){
                    System.err.println("Error in searchController");
                }
            }
        });
        c.search.addDropdownListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    choice = (String)e.getItem();
                    if(!choice.equals("--choose one--")){
                        c.search.buttonState(true);
                    } else{
                        c.search.buttonState(false);
                    }
                }
            }
        });
        c.search.addCloseListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                c.search.refreshOptions();
                c.search.setVisible(false);
            }
        });
    }
    
}