package src.Server.Model;
import src.Server.Model.*;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.*; 

/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class FrontEnd implements Runnable{
    Shop theShop;

    public FrontEnd(Shop s) {
        theShop = s;
    }

    @Override
    public void run() {
        try{
            theShop.menuRunner();
        }catch(SocketException a){
            System.err.println("    ---A Client disconnected");
            Thread.currentThread().interrupt();
            return;      
        }catch(IOException a){
            System.err.println(" run caught error ");
            a.printStackTrace();
        }catch(Exception b){
            System.err.println(" run caught error 2 ");
            b.printStackTrace();
        }
    }
}