package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    
    private MyListener listener;
    public Controller c;

    public LoginController(Controller c) {
        // c.setLogin(login);
        this.c = c;
        listener = new MyListener();
        addListeners();
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

    public void addListeners() {
        c.login.addSubmitListener(listener);
    }
    
}