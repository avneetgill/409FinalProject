package Client.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/** 
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class AddView extends JFrame{
    JPanel headerPanel = new JPanel();
    JPanel textFields = new JPanel();
    JPanel buttons = new JPanel();
    
    JLabel header = new JLabel ("Add a new tool");
    
    public JButton insertButton = new JButton("Add");
    public JButton returnButton = new JButton("Return to Main Window");

    JTextField field1 =  new JTextField(10);
    JTextField field2 =  new JTextField(10);
    JTextField field3 =  new JTextField(10);
    JTextField field4 =  new JTextField(10);
    JTextField field5 = new JTextField(10);

    public AddView(){
        this.setLayout(new BorderLayout());
        this.setTitle("Add New Item");
        this.setSize(400, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        header.setFont(new Font("Sans", Font.BOLD, 20));
        // headerPanel.add(header);

        buttons.setLayout(new FlowLayout());
        buttons.add(insertButton);
        buttons.add(returnButton);
        insertButton.setFont(new Font("Sans", Font.PLAIN, 20));
        returnButton.setFont(new Font("Sans", Font.PLAIN, 20));

        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        setTextBoxFont(20);
        JLabel l1 = new JLabel("Enter Item Name");
        l1.setFont(new Font("Sans", Font.PLAIN, 20));
        textFields.add(l1);
        textFields.add(field1);
        JLabel l2 = new JLabel("Enter Item Price");
        l2.setFont(new Font("Sans", Font.PLAIN, 20));
        textFields.add(l2);
        textFields.add(field2);
        JLabel l3 = new JLabel("Enter Item ID");
        l3.setFont(new Font("Sans", Font.PLAIN, 20));
        textFields.add(l3);
        textFields.add(field3);
        JLabel l4 = new JLabel("Enter Item Stock");
        l4.setFont(new Font("Sans", Font.PLAIN, 20));
        textFields.add(l4);
        textFields.add(field4);
        JLabel l5 = new JLabel("Enter Item's Supplier ID");
        l5.setFont(new Font("Sans", Font.PLAIN, 20));
        textFields.add(l5);
        textFields.add(field5);
        
        textFields.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.add("Center", textFields);
        // this.add("North", headerPanel);
        this.add("South", buttons);
        
    }

    public void setTextBoxFont(int fontSize){
        field1.setFont(new Font("Sans", Font.PLAIN, fontSize));
        field2.setFont(new Font("Sans", Font.PLAIN, fontSize));
        field3.setFont(new Font("Sans", Font.PLAIN, fontSize));
        field4.setFont(new Font("Sans", Font.PLAIN, fontSize));
        field5.setFont(new Font("Sans", Font.PLAIN, fontSize));
    }

    public void addWindowListener(WindowAdapter a){
        this.addWindowListener(a);
    }

    public String getName(){
        return field1.getText();
    }

    public String getPrice(){
        return field2.getText();
    }

    public String getID(){
        return field3.getText();
    }

    public String getStock(){
        return field4.getText();
    }

    public String getSupID(){
        return field5.getText();
    }

    public void clearText(){
        field1.setText(null);
        field2.setText(null);
        field3.setText(null);
        field4.setText(null);
        field5.setText(null);
    }

    public void addInsertListener(ActionListener a){
        insertButton.addActionListener(a);
    }

    public void addReturnListener(ActionListener a){
        returnButton.addActionListener(a);
    }

    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }

    public static void main(String[] args) {
        // to run this main: javac -d classes Client/View/AddView.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.View.AddView
        AddView av = new AddView();
        av.setVisible(true);
    }
}