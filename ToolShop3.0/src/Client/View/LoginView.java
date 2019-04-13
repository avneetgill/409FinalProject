package Client.View;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class LoginView extends JFrame {
    /**
     * text field that holds the user name
     */
    private JTextField userName = new JTextField(10);
    /**
     * text field that holds the password
     */
    private JTextField password = new JTextField(10);

    private JLabel input = new JLabel("Employee Code");
    private JLabel passwordLabel = new JLabel("Password");
    private JPanel f = new JPanel();
    public JButton b = new JButton("Login");

    /**
     * Constructor that sets up the gui and assigns location to different panels
     */
    public LoginView() {
        this.setTitle("Login");
        this.setSize(260, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        input.setFont(new Font("Sans", Font.PLAIN, 20));
        passwordLabel.setFont(new Font("Sans", Font.PLAIN, 20));
        userName.setFont(new Font("Sans", Font.PLAIN, 20));
        password.setFont(new Font("Sans", Font.PLAIN, 20));
        b.setFont(new Font("Sans", Font.BOLD, 20));

        this.setBackground(Color.WHITE);
        input.setBackground(Color.WHITE);
        f.setBackground(Color.WHITE);

        // b.setBorder(new EtchedBorder());
        b.setBorder(new EmptyBorder(10, 10, 10, 10));

        f.setLayout(new FlowLayout());
        // f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));
        f.setBorder(new EmptyBorder(10, 30, 10, 30));
        f.add(input);
        f.add(userName);
        f.add(passwordLabel);
        f.add(password);
        f.add(b);

        // userName.addActionListener(new ActionListener() {

        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         v.setVisible(true);
        //         String input = "";
        //         do {
        //             input = getUserName(userName);
        //         } while (!realEmployee(input));
        //         System.out.println(input);
        //         v.setTitle("Employee #: " + input);
        //         setVisible(false);
        //     }
        // });

        // b.addActionListener(new ActionListener() {

        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         v.setVisible(true);
        //         String input = "";
        //         do {
        //             input = getUserName(userName);
        //         } while (!realEmployee(input));
        //         System.out.println(input);
        //         v.setTitle("Employee #: " + input);
        //         setVisible(false);
        //     }
        // });

        add(f);
    }
    /**
     * assigns an action listener to the submit button
     * @param a - the action listener being assigned
     */
    public void addSubmitListener(ActionListener a){
        b.addActionListener(a);
    }
    /**
     * returns a string - the user name entered by the user 
     */
    public String getUserName() { // retrieves a input from the textfield
        return userName.getText();
    }
    /**
     * returns a string - the password entered by the user
     */
    public String getPassword(){
        return password.getText();
    }
    /**
     * function used to display error messages
     * @param text - the error message to be displayed
     */
    public void errorMessage(String text){
        JOptionPane.showMessageDialog(this, text);
    }
    /**
     * checks to see if the username and password entered belong to a valid user in the login database
     * @param check - boolean value to see if the user is real or not
     * @return
     */
    public boolean realEmployee(String check) { // will check a list of employees to see if the code matches an existing
                                                // employee id
        return true;
    }

}