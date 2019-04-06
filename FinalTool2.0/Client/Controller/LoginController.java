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
public class LoginController{
    
    private MyListener listener;
    private MainController mainController;
    private LoginView login;

    public LoginController(LoginView login, MainController view) {
        this.login = login;
        mainController = view;
        listener = new MyListener();
        addListeners();
    }
    
    public void setSuper(Controller c){
        c.setLogin(login);
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(e.getSource() == login.b){
                    String username = login.getUserName();
                    String pass = login.getPassword();
                    System.out.println("usernam: " + username + ", password: " + pass);
                    if(username.equals("") || pass.equals("")){
                        System.out.println("error");
                        login.errorMessage("Please Enter Username and Password");
                    } else{
                        // if(username.valid() && password.valid()){
                            // check if user and pass are valid
                        // }
                        login.setVisible(false);
                        view.setVisible(true);
                    }
                }
            }catch(Exception a){
                a.printStackTrace();
                System.err.println(" *** problem *** ");
            }
            
        }
    }

    public void addListeners() {
        login.addSubmitListener(listener);
    }
    
}