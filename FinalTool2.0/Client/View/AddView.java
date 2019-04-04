package Client.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;

public class AddView extends JFrame{
    JPanel headerPanel = new JPanel();
    JPanel textFields = new JPanel();
    JPanel buttons = new JPanel();
    
    JLabel header = new JLabel ("Add a new tool");
    
    JButton insertButton = new JButton("Add");
    JButton returnButton = new JButton("Return to Main Window");

    JTextField field1 =  new JTextField(10);
    JTextField field2 =  new JTextField(10);
    JTextField field3 =  new JTextField(10);
    JTextField field4 =  new JTextField(10);
    JTextField field5 = new JTextField(10);

    public AddView(){
        this.setLayout(new BorderLayout());
        this.setTitle("Add New Item");
        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        header.setFont(new Font("Sans", Font.BOLD, 18));
        headerPanel.add(header);

        buttons.setLayout(new FlowLayout());
        buttons.add(insertButton);
        buttons.add(returnButton);

        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        textFields.add(new JLabel("Enter Item Name"));
        textFields.add(field1);
        textFields.add(new JLabel("Enter Item Price"));
        textFields.add(field2);
        textFields.add(new JLabel("Enter Item ID"));
        textFields.add(field3);
        textFields.add(new JLabel("Enter Item Stock"));
        textFields.add(field4);
        textFields.add(new JLabel("Enter Item's Supplier ID"));
        textFields.add(field5);

        this.add("Center", textFields);
        this.add("North", headerPanel);
        this.add("South", buttons);
        
    }

    public void addWindowListener(WindowAdapter a){
        this.addWindowListener(a);
    }

    public String getName(){
        return field1.getText();
    }

    public int getPrice(){
        return Integer.parseInt(field2.getText());
    }

    public int getID(){
        return Integer.parseInt(field3.getText());
    }

    public int getStock(){
        return Integer.parseInt(field4.getText());
    }

    public int getSupID(){
        return Integer.parseInt(field5.getText());
    }

    public void clearText(){
        field1.setText(null);
        field2.setText(null);
        field3.setText(null);
        field4.setText(null);
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
        AddView av = new AddView();
        av.setVisible(true);
    }
}