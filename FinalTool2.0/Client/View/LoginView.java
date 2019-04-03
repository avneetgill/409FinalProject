package Client.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * LoginView
 */
public class LoginView extends JFrame {
    private JTextField userName = new JTextField(10);
    private JTextField password = new JTextField(10);
    private JLabel input = new JLabel("Employee Code");
    private JLabel passwordLabel = new JLabel("Password");
    private JPanel f = new JPanel();
    public JButton b = new JButton("Login");

    public LoginView() {
        this.setTitle("Login");
        this.setSize(200, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        input.setFont(new Font("Sans", Font.PLAIN, 20));
        passwordLabel.setFont(new Font("Sans", Font.PLAIN, 20));
        userName.setFont(new Font("Sans", Font.PLAIN, 20));
        password.setFont(new Font("Sans", Font.PLAIN, 20));
        b.setFont(new Font("Sans", Font.BOLD, 20));

        // b.setBorder(new EtchedBorder());
        b.setBorder(new EmptyBorder(10, 10, 10, 10));

        f.setLayout(new FlowLayout());
        f.add(input);
        f.add(userName);
        f.add(passwordLabel);
        f.add(password);
        f.add(b);
        f.setBorder(new EmptyBorder(10, 10, 10, 10));

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

    public void addSubmitListener(ActionListener a){
        b.addActionListener(a);
    }

    public String getUserName() { // retrieves a input from the textfield
        return userName.getText();
    }

    public String getPassword(){
        return password.getText();
    }

    public void errorMessage(String text){
        JOptionPane.showMessageDialog(this, text);
    }

    public boolean realEmployee(String check) { // will check a list of employees to see if the code matches an existing
                                                // employee id
        return true;
    }

}