import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import javax.swing.*;
/**
 * Class that is used to make the dialog box for when Insert Student is pressed
 */
public class InsertStudentView extends JDialog{

    // JDialog dialog = new JDialog();
    JPanel headerPanel = new JPanel();
    JPanel textFields = new JPanel();
    JPanel buttons = new JPanel();

    JLabel header = new JLabel ("Insert a New Node");

    JButton insertButton = new JButton("insert");
    JButton returnButton = new JButton("Return to Main Window");

    JTextField field1 =  new JTextField(10);
    JTextField field2 =  new JTextField(10);
    JTextField field3 =  new JTextField(10);
    JTextField field4 =  new JTextField(10);

    public InsertStudentView(){
        
        this.setLayout(new BorderLayout());
        this.setTitle("Add New Student");
        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        header.setFont(new Font("Sans", Font.BOLD, 18));
        headerPanel.add(header);

        buttons.setLayout(new FlowLayout());
        buttons.add(insertButton);
        buttons.add(returnButton);

        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        textFields.add(new JLabel("Enter the Student ID"));
        textFields.add(field1);
        textFields.add(new JLabel("Enter faculty"));
        textFields.add(field2);
        textFields.add(new JLabel("Enter Major"));
        textFields.add(field3);
        textFields.add(new JLabel("Enter Year"));
        textFields.add(field4);

        this.add("Center", textFields);
        this.add("North", headerPanel);
        this.add("South", buttons);

    }
    public void addWindowListener(WindowAdapter a){
        this.addWindowListener(a);
    }
    //code for textfields
    public String getID(){
        return field1.getText();
    }
    public String getFaculty(){
        return field2.getText();
    }
    public String getMajor(){
        return field3.getText();
    }
    public String getYear(){
        return field4.getText();
    }
    public void clearText(){
        field1.setText(null);
        field2.setText(null);
        field3.setText(null);
        field4.setText(null);
    }

    //code for action listeners
    public void addInsertListener(ActionListener a){
        insertButton.addActionListener(a);
    }
    public void addReturnListener(ActionListener a){
        returnButton.addActionListener(a);
    }
    // for errors
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }
}