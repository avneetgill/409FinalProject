import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * StudentController class, used to comunicate with the server & client
 */
public class StudentController {
    /**
     * instance of the student view (ie. Client)
     */
    private StudentView view;
    /**
     * instance of the student model (ie. server)
     */
    private StudentModel model;
    /**
     * instance of insert view used for dialogs and pop ups
     */
    private InsertStudentView insertView;

    public StudentController(StudentView v, StudentModel m, InsertStudentView i) {
        view = v;
        model = m;
        insertView = i;
        view.setVisible(true);
        addMainListeners();
        addInsertionListeners();
    }
    /**
     * inner class used to assign action listeners to the main screen
     */
    public void addMainListeners() { 

        view.addInsertListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertView.setVisible(true);
            }
        });
        view.addBrowseListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String list = model.getList();
                // System.out.println(list);
                view.setTextArea(list);
            }
        });
        view.addCreateListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String temp = view.getFilename();
                if (model.readFromFile(temp) == false) {
                    view.errorMessage("Tree was not created, Please try again");
                } else {
                    view.errorMessage("Tree Created!");
                }
            }
        });
        view.addFindListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String temp = view.getID();
                Node n = model.find(temp);
                if (n == null) {
                    view.errorMessage("Student not found");
                } else {
                    view.displayStudent(n);
                }
            }
        });
    }

    /**
     * inner class used to assign action listeners to the insert node dialog box
     */
    public void addInsertionListeners() { 

        insertView.addInsertListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                String id, faculty, major, year;

                id = insertView.getID();
                faculty = insertView.getFaculty();
                major = insertView.getMajor();
                year = insertView.getYear();

                if (id == null || faculty == null || major == null || year == null) {
                    insertView.errorMessage("Please enter all fields");
                } else if (!model.insertNode(id, faculty, major, year)) { // if it already exists
                    insertView.errorMessage("Student already exists");
                } else {
                    insertView.errorMessage("Student added!");
                }
                insertView.clearText();
                insertView.setVisible(false);
                String list = model.getList();
                System.out.println(list);
                view.setTextArea(list);
            }
        });
        insertView.addReturnListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                insertView.clearText();
                insertView.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        StudentView v = new StudentView();
        StudentModel m = new StudentModel();
        InsertStudentView i = new InsertStudentView();
        StudentController c = new StudentController(v, m, i);
        
    }
    
}