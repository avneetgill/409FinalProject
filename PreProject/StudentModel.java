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
 * StudentModel
 */
public class StudentModel {
    public BinSearchTree tree;

    public StudentModel() {
        tree = new BinSearchTree();
    }
    public Node find(String num){
        Node n = tree.find(tree.root, num);
        return n;
    }
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