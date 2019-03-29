
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.sun.prism.paint.Color;

public class StudentView extends JFrame{
	
	private JLabel title = new JLabel ("Student Record Maintainer" + "\u2122");
	
	private JTextArea textBox = new JTextArea(30, 30);
    
    private JButton insertButton = new JButton ("Insert");
    private JButton findButton = new JButton ("Find");
    private JButton browseButton = new JButton ("Browse");
    private JButton createButton = new JButton ("Create Tree from File");
    
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
    public void setTextArea(String text){
        textBox.setText(text);
    }
    //#region button listeners
	public void addInsertListener(ActionListener a){
        insertButton.addActionListener(a);
    }
	public void addBrowseListener(ActionListener a){
        browseButton.addActionListener(a);
    }
	public void addFindListener(ActionListener a){
        findButton.addActionListener(a);
    }
	public void addCreateListener(ActionListener a){
        createButton.addActionListener(a);
    }
    //#endregion
    public String getFilename(){
        return JOptionPane.showInputDialog(this, "Enter the file name: ");
    }
    public String getID(){
        return JOptionPane.showInputDialog(this, "Enter the student's id: ");
    }
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }
    public void displayStudent(Node n){
       JOptionPane.showMessageDialog(this, "Student details: \n" + n.toString());
    }
}
