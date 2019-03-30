import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Class used to deal with data and methods related to data. 
 */
public class StudentModel {
    /**
     * The binary search tree that holds current enteries of students and their data
     */
    public BinSearchTree tree;

    /**
     * Constructs a StudentModel object with an empty Binary Search Tree
     */
    public StudentModel() {
        tree = new BinSearchTree();
    }
    /**
     * finds a student given the id 
     * @param num - student id number
     * @return the student with that id, or null if such a student does not exist. 
     */
    public Node find(String num){
        Node n = tree.find(tree.root, num);
        return n;
    }
    /**
     * adds students from the file to the binary search tree
     * @param Filename - name of file containing data
     * @return status of read (true = successfuly read and added to tree)
     * (false = IO exception, file not read successfully)
     */
    public boolean readFromFile(String Filename) {
        FileReader f = null;

        try{
            f = new FileReader(new File(Filename));
        }catch(FileNotFoundException a){
            System.err.println("File not found");
            return false;
        }

        BufferedReader s = new BufferedReader(f);
        String[] arr;
        String temp;
        try{
            while((temp = s.readLine()) != null){
                temp = temp.replaceAll("      ", " ");
                temp = temp.replaceAll("     ", " ");
                temp = temp.replaceAll("    ", " ");
                temp = temp.replaceAll("   ", " ");
                temp = temp.replaceAll("  ", " ");
                temp = temp.substring(1, temp.length());
                arr = temp.split(" ");
                tree.insert(arr[0], arr[1], arr[2], arr[3]);
            }
        }catch(IOException a){
            System.err.println("IOException caught");
            return false;
        }
        return true;
    }
    /**
     * adds a new student to the binary search tree
     * @param id - student's id
     * @param faculty - student's faculty
     * @param major - student's major
     * @param year - student's year of study
     * @return - status of insert (true = inserted, false = student already exists, not inserted)
     */
    public boolean insertNode(String id, String faculty, String major, String year){

        Node n = tree.find(tree.root, id);
        if(n != null){      // so if(Node exists){}
            System.err.println("Node already exists");
            return false;
        } else{
            tree.insert(id, faculty, major, year);
        }
        return true;
    }
    /**
     * function used to display all of the current students in the binary search tree
     * @return a string holding the list of students arranged by increasing ID number. 
     */
    public String getList(){
        String temp, s = "";
        FileReader f;
        try{
            PrintWriter out = new PrintWriter("temp.txt", "UTF-8");
            tree.print_tree(tree.root, out);
            out.close();

            f = new FileReader(new File("temp.txt"));
            BufferedReader in = new BufferedReader(f);

            while((temp = in.readLine()) != null){
                s += temp;
                s += "\n";
            }
        }catch(Exception a){
            System.err.println("error in getList");
            return null;
        }
        return s;
    }
    public static void main(String[] args) throws IOException {
        StudentModel m = new StudentModel();
        m.readFromFile("input.txt");
        String temp = m.getList();
        System.out.println(temp);
    }
}