package Client.View;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class OrderView extends JFrame{
	/**
     * Main Label for  student record maintainer
     */
	private JLabel title = new JLabel ("Orders");
	/**
     * TextArea that displays all students when browse is pressed. 
     */
	private JTextArea textBox = new JTextArea(10, 30);
    
	public OrderView(){
        JPanel mainPanel = new JPanel ();
        JPanel topTitle = new JPanel();
     
        setTitle("Main");
        this.setBackground(Color.WHITE);
        this.setSize(700, 400);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setLayout(new BorderLayout());	// doesnt do jack
        
        title.setFont(new Font("Sans", Font.BOLD, 18));
        topTitle.add(title);

        textBox.setEditable(false);
        // Border border = BorderFactory.createLineBorder(Color.gray, 10);
        // textBox.setBorder(border);
        textBox.setBorder(new EmptyBorder(0, 15, 10, 15));
        textBox.setFont(new Font("Sans", Font.PLAIN, 16));

        JScrollPane scrollText = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollText, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(0, 15, 10, 15));
        mainPanel.setBackground(Color.WHITE);
        topTitle.setBackground(Color.WHITE);
        
        add("Center", mainPanel);
        add("North", topTitle);
        setVisible(false);
    }
    /**
     * displays String in the TextArea 
     * @param text - text to be displayed
     */
    public void setTextArea(String text){
        textBox.setText(text);
    }
    
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }
    
    public static void main(String[] args) {
        OrderView o = new OrderView();
        o.setVisible(true);
        o.setTextArea("yes\n no\n yes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\nyes\n no\n");

    }
}
