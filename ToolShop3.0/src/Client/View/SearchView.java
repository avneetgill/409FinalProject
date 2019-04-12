package Client.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * SearchView
 */
public class SearchView extends JFrame {

    private JLabel title = new JLabel("Search Item");
    private String[] options = { "--choose one--", "Search by ID", "Search by Name" };
    public JComboBox<String> dropdown = new JComboBox<String>(options);

    public JButton searchSubmitButton = new JButton("Search");

    private JTextField inputField = new JTextField(10);

    private JPanel mainPanel = new JPanel();

    // private JOptionPane errorMessage = new JOptionPane();
    private JLabel errorLabel = new JLabel();

    public SearchView() {
        this.setTitle("Search Item");
        this.setSize(260, 240);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.WHITE);

        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        dropdown.setBackground(Color.WHITE);
        inputField.setFont(new Font("Sans", Font.PLAIN, 20));
        title.setFont(new Font("Sans", Font.PLAIN, 20));
        dropdown.setFont(new Font("Sans", Font.PLAIN, 20));
        searchSubmitButton.setFont(new Font("Sans", Font.PLAIN, 20));

        searchSubmitButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        // inputField.setBorder(new EmptyBorder(10, 10, 10, 10));
        dropdown.setBorder(new EmptyBorder(10, 10, 10, 10));
        // dropdown.setSize(70, 20);

        mainPanel.add(title);
        mainPanel.add(dropdown);
        mainPanel.add(inputField);
        mainPanel.add(searchSubmitButton);

        buttonState(false);

        add(mainPanel);

    }

    public void refreshOptions(){
        inputField.setText("");
        dropdown.setSelectedItem("--choose one--");
    }

    public void addCloseListener(WindowAdapter a){
        this.addWindowListener(a);
    }

    public void errorMessage(String error){
        // errorLabel.setText(error);
        // errorLabel.setFont(new Font("Sans", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(this, error);
    }

    public void addSubmitListener(ActionListener a) {
        searchSubmitButton.addActionListener(a);
    }

    public void addDropdownListener(ItemListener a) {
        dropdown.addItemListener(a);
    }

    public String getInputText(){
        return inputField.getText();
    }

    public void buttonState(boolean f){
        searchSubmitButton.setEnabled(f);
    }

    public static void main(String[] args) {
        // to run this main: javac -d classes Client/View/SearchView.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.View.SearchView
        
        SearchView s = new SearchView();
        s.setVisible(true);
    }
}