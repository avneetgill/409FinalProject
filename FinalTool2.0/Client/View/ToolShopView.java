package Client.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

public class ToolShopView extends JFrame{

    JLabel title = new JLabel("Inventory Manager Pro" + "\u2122");
    // public JList textBox = new JList();
    public JTextArea textBox = new JTextArea(70, 30);

    public JButton searchButton = new JButton("Search");
    public JButton listToolButton = new JButton("List Tools");
    public JButton listSupButton = new JButton("List Suppliers");
    public JButton loadButton = new JButton("quit");
    public JButton decreaseButton = new JButton("Decrease Quantity"); //maybe change to 'buy'
    public JButton deleteButton = new JButton("Delete");
    public JButton addButton = new JButton("Add");

    public ToolShopView(){
        JPanel mainPanel = new JPanel ();
        JPanel topButtons = new JPanel();
        JPanel bottomButtons = new JPanel();
        JPanel topTitle = new JPanel();

        setTitle("Inventory Manager Pro" + "\u2122");
        this.setSize(700, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        title.setFont(new Font("Castellar", Font.BOLD, 20));
        topTitle.add(title);

        topButtons.setLayout(new FlowLayout());
        topButtons.add(searchButton);
        topButtons.add(listToolButton);
        topButtons.add(loadButton);
        topButtons.add(listSupButton);
        topButtons.setBorder(new EmptyBorder(10, 15, 0, 15));

        bottomButtons.setLayout(new FlowLayout());
        bottomButtons.add(decreaseButton);
        bottomButtons.add(deleteButton);
        bottomButtons.add(addButton);
        bottomButtons.setBorder(new EmptyBorder(0, 15, 10, 15));

        textBox.setEditable(false);
        JScrollPane scrollText = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollText, BorderLayout.CENTER);
        mainPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        add(topTitle);
        add("North", topButtons);
        add("Center", mainPanel);
        add("South", bottomButtons);

    }

    public void refreshTextBox(){
        textBox.setText("");
    }

    public void setText(String s){
        textBox.setText(s);
        textBox.setCaretPosition(0);
    }

    public void errorMessage(String error){
        JOptionPane.showMessageDialog(this, error);
    }

    //#region   <<<button listeners below>>> 
    public void addSearchListener(ActionListener a){
        searchButton.addActionListener(a);
    }

    public void addListToolListener(ActionListener a){
        listToolButton.addActionListener(a);
    }
    public void addLoadListener(ActionListener a){
        loadButton.addActionListener(a);
    }
    public void addListSupListener(ActionListener a){
        listSupButton.addActionListener(a);
    }
    public void addDecreaseListener(ActionListener a){
        decreaseButton.addActionListener(a);
    }
    public void addDeleteListener(ActionListener a){
        deleteButton.addActionListener(a);
    }
    public void addAddListener(ActionListener a){
        addButton.addActionListener(a);
    }
    //#endregion
}