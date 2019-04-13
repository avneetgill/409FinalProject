package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * Controller for the Login GUI
 * 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class LoginController {
    
    /**
     * The inner Listener class that will be assigned to the gui elements for add items view
     */
    private MyListener listener;

    /**
     * Controller object that connects all the Gui controller classes and lets them work together, and allows this class to access the GUIs. 
     */
    public Client c;

    /**
     * Constructs a LoginController object
     * @param c The client object that this object is linked with
     */
    public LoginController(Client c) {
        // c.setLogin(login);
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
                if(e.getSource() == c.login.b){
                    String username = c.login.getUserName();
                    String pass = c.login.getPassword();
                    System.out.println("usernam: " + username + ", password: " + pass);
                    if(username.equals("") || pass.equals("")){
                        System.out.println("error");
                        c.login.errorMessage("Please Enter Username and Password");
                    } else{
                        c.socketOut.println("6");
                        c.socketOut.println(username);
                        c.socketOut.println(pass);

                        String result = c.socketIn.readLine();

                        if(result.contains("fail")){
                            c.login.errorMessage("EmployeeId or password invalid");
                        } else{
                            c.login.setVisible(false);
                            c.view.setVisible(true);
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
     * Assigns the listener object to all the GUI components in login View GUI
     */
    public void addListeners() {
        c.login.addSubmitListener(listener);
    }
    
}