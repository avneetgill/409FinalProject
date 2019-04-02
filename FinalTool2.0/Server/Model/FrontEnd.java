package Server.Model;
// import Server.Model.*;

import java.io.IOException;

/**
 * Provides a console based frontend to interact with the shop app.
 * 
 * @author Shamin Rahman
 * @version 1.0
 * @since February 6, 2019
 */
public class FrontEnd implements Runnable{
    Shop theShop;

    public FrontEnd(Shop s) {
        theShop = s;
    }

    /**
     * The main function to act as a frontend to the Shop app. 
     * @param args command line arguments
     * @throws IOException thrown if there is an issue with IO stream. 
     */
    /* public static void main(String[] args) throws IOException{
        FrontEnd f = new FrontEnd();
        
        ArrayList<OrderLine> line = new ArrayList<OrderLine>();
        Order order = new Order(line);

        ArrayList<Item> items = new ArrayList<Item>();
        Inventory inventory = new Inventory(items);

        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();

        // Shop store = new Shop(order, suppliers, inventory);

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
 */
    
 @Override
    public void run() {
        try{
            theShop.menuRunner();
        }catch(IOException a){
            System.err.println(" run caught error ");
            a.printStackTrace();
        }catch(Exception b){
            System.err.println(" run caught error 2 ");
            b.printStackTrace();
        }
    }
}