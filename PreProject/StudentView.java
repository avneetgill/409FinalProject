
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.sun.prism.paint.Color;
/**
 * Class that acts as the main GUI
 */
public class StudentView extends JFrame{
	/**
     * Main Label for  student record maintainer
     */
	private JLabel title = new JLabel ("Student Record Maintainer" + "\u2122");
	/**
     * TextArea that displays all students when browse is pressed. 
     */
	private JTextArea textBox = new JTextArea(30, 30);
    /**
     * buttons for insert, find, browse and create
     */
    private JButton insertButton = new JButton ("Insert");
    private JButton findButton = new JButton ("Find");
    private JButton browseButton = new JButton ("Browse");
    private JButton createButton = new JButton ("Create Tree from File");
    /**
     * Constructs the main GUI with its components. 
     */
	public StudentView(){
        JPanel mainPanel = new JPanel ();
        JPanel bottomButtons = new JPanel();
        JPanel topTitle = new JPanel();

        setTitle("Main");
        this.setSize(400, 400);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());	// doesnt do jack
        
        title.setFont(new Font("Sans", Font.BOLD, 18));
        topTitle.add(title);

        bottomButtons.setLayout(new FlowLayout());
        bottomButtons.add(insertButton);
        bottomButtons.add(findButton);
        bottomButtons.add(browseButton);
        bottomButtons.add(createButton);

        textBox.setEditable(false);
        JScrollPane scrollText = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollText, BorderLayout.CENTER);
        
        add("Center", mainPanel);
        add("North", topTitle);
        add("South", bottomButtons);
    }
    /**
     * displays String in the TextArea 
     * @param text - text to be displayed
     */
    public void setTextArea(String text){
        textBox.setText(text);
    }
    /**
     * Assigns an action listener to the insert button
     * @param a - action listner being assigned
     */
	public void addInsertListener(ActionListener a){
        insertButton.addActionListener(a);
    }
    /**
     * Assigns an action listener to the browse button
     * @param a - action listner being assigned
     */
	public void addBrowseListener(ActionListener a){
        browseButton.addActionListener(a);
    }
    /**
     * Assigns an action listener to the find button
     * @param a - action listner being assigned
     */
	public void addFindListener(ActionListener a){
        findButton.addActionListener(a);
    }
    /**
     * Assigns an action listener to the create button
     * @param a - action listner being assigned
     */
	public void addCreateListener(ActionListener a){
        createButton.addActionListener(a);
    }
    /**
     * prompts the user to enter the file name of the file containing data 
     * @return the file name entered
     */
    public String getFilename(){
        return JOptionPane.showInputDialog(this, "Enter the file name: ");
    }
    /**
     * prompts the user to enter an id 
     * @return the id entered
     */
    public String getID(){
        return JOptionPane.showInputDialog(this, "Enter the student's id: ");
    }
    /**
     * function used to display error messages
     * @param error - the message to display
     */
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }
    /**
     * Displays a student's data (id, faculty, majot, year)
     * @param n - node containing a student's data
     */
    public void displayStudent(Node n){
       JOptionPane.showMessageDialog(this, "Student details: \n" + n.toString());
    }
}
