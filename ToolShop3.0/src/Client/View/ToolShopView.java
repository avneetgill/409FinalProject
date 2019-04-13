package Client.View;


import java.awt.Dimension;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

/**
 * Creates the main GUI for the toolshop -  houses most of the functionality
 * @author Shamin Rahman, Avneet Gill, Kelvin Tran
 * @version 1.0
 */
public class ToolShopView extends JFrame{
    
    JLabel title = new JLabel("Inventory Manager Pro" + "\u2122");

    // public JTextArea textBox = new JTextArea(70, 30);
    public DefaultListModel<String> model = new DefaultListModel<>();
    public JList<String> textBox = new JList<>(model);
    /**
     *  button for search functionality 
     */
    public JButton searchButton = new JButton("Search");
      /**
     *  button for list tools functionality 
     */
    public JButton listToolButton = new JButton("List Tools");
      /**
     *  button for list supplier functionality 
     */
    public JButton listSupButton = new JButton("List Suppliers");
    /**
     *  button for displaying order functionality 
     */
    public JButton orderButton = new JButton("View Orders");
      /**
     *  button for decrease functionality 
     */
    public JButton decreaseButton = new JButton("Decrease Quantity"); //maybe change to 'buy'
      /**
     *  button for delete functionality 
     */
    public JButton deleteButton = new JButton("Delete");
      /**
     *  button for add functionality 
     */
    public JButton addButton = new JButton("Add");

    /**
     * Constructor that assigns the label and buttons to specific places on the gui
     */
    public ToolShopView(){
        JPanel mainPanel = new JPanel ();
        //mainPanel.setBackground(Color.WHITE);
        JPanel topButtons = new JPanel();
        JPanel bottomButtons = new JPanel();
        JPanel topTitle = new JPanel();

        setTitle("Inventory Manager Pro" + "\u2122");
        this.setSize(800, 620);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        topButtons.setBackground(Color.WHITE);
        bottomButtons.setBackground(Color.WHITE);
        

        setLayout(new BorderLayout());

        title.setFont(new Font("Castellar", Font.BOLD, 20));
        topTitle.add(title);

        topButtons.setLayout(new FlowLayout());
        setButtonFontSize(20);
        topButtons.add(searchButton);
        topButtons.add(listToolButton);
        topButtons.add(orderButton);
        topButtons.add(listSupButton);
        topButtons.setBorder(new EmptyBorder(10, 15, 0, 15));

        bottomButtons.setLayout(new FlowLayout());
        bottomButtons.add(decreaseButton);
        bottomButtons.add(deleteButton);
        bottomButtons.add(addButton);
        // decreaseButton.setEnabled(false);                                                        // danger code
        // deleteButton.setEnabled(false);                                                        // danger code
        changeButtonState(false);                               // "danger code"
        bottomButtons.setBorder(new EmptyBorder(0, 15, 10, 15));

        // textBox.setEditable(false);
        textBox.setFont(new Font("Sans", Font.PLAIN, 16));
        textBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollText = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollText, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        add(topTitle);
        ImageIcon image = new ImageIcon("icon.jpg");
        JLabel label = new JLabel(image);
        JPanel north = new JPanel();
        north.setBackground(Color.WHITE);

        north.setLayout(new BorderLayout());
        north.add(label, BorderLayout.NORTH);
        north.add(topButtons, BorderLayout.CENTER);
        add(north, BorderLayout.NORTH);
        //add(topButtons, BorderLayout.NORTH);
        add("Center", mainPanel);
        add("South", bottomButtons);

    }
    /**
     * Assigns font sizes to the buttons on the gui
     * @param fontSize - the font size to assign
     */
    public void setButtonFontSize(int fontSize){
        searchButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        listSupButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        listToolButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        orderButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        addButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        decreaseButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
        deleteButton.setFont(new Font("Sans", Font.PLAIN, fontSize));
    }
    /**
     * Adds a string to model
     * @param value - string being added
     */
    public void addElementTextBox(String value){
        model.addElement(value);
    }

    /**
     * assigns a listener to the selection button
     * @param a - the list selection listener being assigned
     */
    public void addSelectionListener(ListSelectionListener a){
        textBox.addListSelectionListener(a);
    }
    /**
     * changes the state of the button to either enabled or disabled 
     * @param enabled - the state ( true for enable, false for disable)
     */
    public void changeButtonState(boolean enabled){
        decreaseButton.setEnabled(enabled);                                                       
        deleteButton.setEnabled(enabled);  
    }
    /**
     * gets user input on how much to decrease an item quantity by
     * @return - the amount to decrease by
     */
    public String decreaseItemDialog(){
        return JOptionPane.showInputDialog(this, "Enter how much to decrease: ");
    }

    // public void refreshTextBox(){
    //     textBox.setText("");
    // }

    // public void setText(String s){
    //     textBox.setText(s);
    //     textBox.setCaretPosition(0);
    // }
    /**
     * function used to communicate errors
     * @param error - the error to display 
     */
    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }

    //#region   <<<button listeners below>>> 
    /**
     * assigns an action listener to the search button
     * @param a - the action listener beign assigned
     */
    public void addSearchListener(ActionListener a){
        searchButton.addActionListener(a);
    }
    /**
     * assigns an action listener to the list tool button
     * @param a - action listener being assigned
     */
    public void addListToolListener(ActionListener a){
        listToolButton.addActionListener(a);
    }
    /**
     * assigns an action listener to the order button
     * @param a - action listener being assigned
     */
    public void addOrderListener(ActionListener a){
        orderButton.addActionListener(a);
    }
     /**
     * assigns an action listener to the supplier button
     * @param a - action listener being assigned
     */
    public void addListSupListener(ActionListener a){
        listSupButton.addActionListener(a);
    }
     /**
     * assigns an action listener to the decrease button
     * @param a - action listener being assigned
     */
    public void addDecreaseListener(ActionListener a){
        decreaseButton.addActionListener(a);
    }
     /**
     * assigns an action listener to the delete button
     * @param a - action listener being assigned
     */
    public void addDeleteListener(ActionListener a){
        deleteButton.addActionListener(a);
    }
     /**
     * assigns an action listener to the add button
     * @param a - action listener being assigned
     */
    public void addAddListener(ActionListener a){
        addButton.addActionListener(a);
    }
    //#endregion

    public static void main(String[] args) {
        ToolShopView tsv = new ToolShopView();
        tsv.setVisible(true);
    }
}