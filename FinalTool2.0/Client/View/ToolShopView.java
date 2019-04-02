package Client.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.sun.prism.paint.Color;

public class ToolShopView extends JFrame{
    private JLabel title = new JLabel("Inventory Manager Pro" + "\u2122");
    private JList textBox = new JList();

    private JButton searchButton = new JButton("Search");
    private JButton listToolButton = new JButton("List Tools");
    private JButton listSupButton = new JButton("List Suppliers");
    private JButton loadButton = new JButton("Load from database");
    private JButton decreaseButton = new JButton("Decrease Quantity"); //maybe change to 'buy'
    private JButton deleteButton = new JButton("Delete");
    private JButton addButton = new JButton("Add");

    public ToolShopView(){
        JPanel mainPanel = new JPanel ();
        JPanel topButtons = new JPanel();
        JPanel bottomButtons = new JPanel();
        JPanel topTitle = new JPanel();

        setTitle("Main");
        this.setSize(500, 350);
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        title.setFont(new Font("Castellar", Font.BOLD, 20));
        topTitle.add(title);

        topButtons.setLayout(new FlowLayout());
        topButtons.add(searchButton);
        topButtons.add(listToolButton);
        topButtons.add(loadButton);
        topButtons.add(listSupButton);

        bottomButtons.setLayout(new FlowLayout());
        bottomButtons.add(decreaseButton);
        bottomButtons.add(deleteButton);
        bottomButtons.add(addButton);

        //textBox.setEditable(false);
        JScrollPane scrollText = new JScrollPane(textBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollText, BorderLayout.CENTER);
        
        add(topTitle);
        add(topButtons);
        add(mainPanel);
        add(bottomButtons);

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