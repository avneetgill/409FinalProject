package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client.View.*;

public class GUIController{
    private ToolShopView view;
    // private Client client;

    public GUIController(ToolShopView view){
        this.view = view;
        view.setVisible(true);
        addListeners();
    }

    public void addListeners(){
        view.addSearchListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("Search pressed");

            }
        });
        view.addListToolListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("list all pressed");
            }
        });
        view.addLoadListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("load pressed");
            }
        });
        view.addListSupListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("list suppliers pressed");
            }
        });
        view.addDecreaseListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("decrease pressed");
            }
        });
        view.addDeleteListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("delete pressed");
            }
        });
        view.addAddListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                view.errorMessage("add pressed");
            }
        });
    }
    public static void main(String[] args) {
        // to run this main: javac -d classes Client/Controller/GUIController.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.GUIController
        ToolShopView view = new ToolShopView();
        GUIController c = new GUIController(view);
    }
}