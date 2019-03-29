package Server.Model;
import Server.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*; 

/**
 * Provides a console based frontend to interact with the shop app.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class FrontEnd{
    
    /**
     * Acts as a menu to be able to use all the functionality of 
     * the shop app, prompting the to enter how they would like to 
     * interact with the app. 
     * @return returns the user's choice.
     */
    public int menu(){
        System.out.println("***************************");
        System.out.println("Choose an Option: \n");
        System.out.println("1. List all Tools\n");
        System.out.println("2. List all Suppliers\n");
        System.out.println("3. Search tool by name\n");
        System.out.println("4. Search tool by ID\n");
        System.out.println("5. Check item quantity\n");
        System.out.println("6. Decrease item Quanity\n");
        System.out.println("7. Add an item manually\n");
        System.out.println("> Or type 'quit' to exit <");
        Scanner input = new Scanner(System.in);
        int choice = -1;
        String temp;
        
        temp = input.nextLine();
        if(temp.equals("quit")){
            input.close();
            return 8;
        }

        while(choice == -1){ 
            try{choice = Integer.parseInt(temp);}catch(Exception a){
                System.err.println("Invalid input. Please Try Again: \n");
                temp = input.nextLine();
            }
        }
        return choice;
    }

    /**
     * Provides a middlepoint between different user inputs, prompting
     * the user to press a key to continue. 
     */
    public void pressEnter(){
        System.out.println("<<< Press enter to continue >>>\n");
        Scanner enter = new Scanner(System.in);
        String s = enter.nextLine();
    }
    /**
     * The main function to act as a frontend to the Shop app. 
     * @param args command line arguments
     * @throws IOException thrown if there is an issue with IO stream. 
     */
    public static void main(String[] args) throws IOException{
        FrontEnd f = new FrontEnd();
        
        ArrayList<OrderLine> line = new ArrayList<OrderLine>();
        Order order = new Order(line);

        ArrayList<Item> items = new ArrayList<Item>();
        Inventory inventory = new Inventory(items);

        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();

        Shop store = new Shop(order, suppliers, inventory);

        boolean quit = false;
        while(true){
            switch(f.menu()){
                case 1:
                    store.listAllItems();
                    f.pressEnter();
                    break;
                case 2:
                    store.listAllSuppliers();
                    f.pressEnter();
                    break;
                case 3:
                    store.searchName(1);
                    f.pressEnter();
                    break;
                case 4:
                    store.searchID(1);
                    f.pressEnter();
                    break;
                case 5:
                    store.checkQuantity();
                    f.pressEnter();
                    break;
                case 6:
                    store.decreaseQuantity();
                    f.pressEnter();
                    break;
                case 7:
                    store.addItem();
                    f.pressEnter();
                    break;
                default:
                    System.out.println("Goodbye");
                    quit = true;
            }
            if(quit == true)
                break;
        }
    }
}