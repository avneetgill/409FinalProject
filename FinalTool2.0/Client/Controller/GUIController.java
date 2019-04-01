import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GUIController{
    private ToolShopView view;

    public GUIController(ToolShopView view){
        this.view = view;
    }

    public void addListeners(){
        view.addSearchListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addListToolListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addLoadListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addListSupListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addDecreaseListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addDeleteListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
        view.addAddListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("yuh");
            }
        });
    }
    public static void main(String[] args) {
        ToolShopView view = new ToolShopView();
        GUIController c = new GUIController(view);
    }
}