import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * StudentController class, uses data and methods from model to supply view with useful information and 
 * uses input from view to modify data. 
 */
public class StudentController {
    /**
     * instance of the student view (ie. main screen GUI)
     */
    private StudentView view;
    /**
     * instance of the student model (ie. data)
     */
    private StudentModel model;
    /**
     * instance of insert view used for insert student dialogs
     */
    private InsertStudentView insertView;

    /**
     * Constructs the controller with two views and a model.
     * @param v the main screen GUI
     * @param m the model for data and methods
     * @param i the insert student dialog GUI
     */
    public StudentController(StudentView v, StudentModel m, InsertStudentView i) {
        view = v;
        model = m;
        insertView = i;
        view.setVisible(true);
        addMainListeners();
        addInsertionListeners();
    }
    /**
     * Assigns listeners to buttons in main view using anonymous inner classes. 
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
     * Assigns listeners to buttons in insert student dialogue view using anonymous inner classes. 
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
        StudentView v = new StudentView();
        StudentModel m = new StudentModel();
        InsertStudentView i = new InsertStudentView();
        StudentController c = new StudentController(v, m, i);
        
    }
    
}