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
    /**
     * Panels for main frame, text fields and buttons
     */
    JPanel headerPanel = new JPanel();
    JPanel textFields = new JPanel();
    JPanel buttons = new JPanel();
    /**
     * Label for inserting a new node
     */
    JLabel header = new JLabel ("Insert a New Node");
    /**
     * buttons for insert and return (cancel)
     */
    JButton insertButton = new JButton("insert");
    JButton returnButton = new JButton("Return to Main Window");
    /**
     * textfields for student id, faculty, major and year
     */
    JTextField field1 =  new JTextField(10);
    JTextField field2 =  new JTextField(10);
    JTextField field3 =  new JTextField(10);
    JTextField field4 =  new JTextField(10);
    /**
     * inner class used to set up the GUI with in a border layout
     */
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
    /**
     * adds a window listener to panel used to call function
     * @param a - window adapter used to monitor events
     */
    public void addWindowListener(WindowAdapter a){
        this.addWindowListener(a);
    }
    /**
     * getter for ID from text field
     */
    public String getID(){
        return field1.getText();
    }
    /**
     * getter for faculty from text field
     */
    public String getFaculty(){
        return field2.getText();
    }
    /**
     * getter for major from text field
     */
    public String getMajor(){
        return field3.getText();
    }
    /**
     * getter for year from text field
     */
    public String getYear(){
        return field4.getText();
    }
    /**
     * removes all current entries
     */
    public void clearText(){
        field1.setText(null);
        field2.setText(null);
        field3.setText(null);
        field4.setText(null);
    }

    /**
     * Assigns an action lister to the insert button to monitor events
     * @param a - the action listener being assigned to insert button
     */
    public void addInsertListener(ActionListener a){
        insertButton.addActionListener(a);
    }
    /**
     * Assigns an action listener to the return button to monitor events
     * @param a - the action listener being assigned to the return button
     */
    public void addReturnListener(ActionListener a){
        returnButton.addActionListener(a);
    }
    /**
     * Function used to display error messages to the user
     * @param error - the error message to display
     */
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }
}