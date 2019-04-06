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
 * LoginController
 */
public class LoginController extends Controller{
    
    private MyListener listener;
    public Controller c;

    public LoginController(LoginView login, Controller c) {
        super();
        c.setLogin(login);
        this.c = c;
        System.out.println("login set");
        listener = new MyListener();
        // addListeners();
    }
    
    public void startGui(){
        super.startGui();
    }

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
                        // if(username.valid() && password.valid()){
                            // check if user and pass are valid
                        // }
                        c.login.setVisible(false);
                        c.view.setVisible(true);
                    }
                }
            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    @Override
    public void addListeners() {
        c.login.addSubmitListener(listener);
    }
    
}