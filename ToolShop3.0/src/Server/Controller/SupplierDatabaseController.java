package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

/**
 * class that deals with the supplier table in the database
 * @author kelvin, shamin , avneet
 * @since April 12 2019
 */
public class SupplierDatabaseController{
    /**
     * connection to the data base 
     */
    Connection myConn;
    /**
     * string query used to communicate with the database 
     */
    String query;
    /**
     * prepared statement for the database 
     */
    PreparedStatement preStmt;

    /**
     * constructor for the class, that initializes variables to desired values
     * @param conn the connection to the database 
     */
    SupplierDatabaseController(Connection conn){
        try {
            myConn = conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * populates the database 
     */
    public void populateDatabase(){
        try {
            Scanner read = new Scanner(new File("suppliers.txt"));
            read.useDelimiter(";");
            String id, name, address, contact, nextLine;
            String[] arr = new String[4];
            int ID;
            while(read.hasNext()){
                nextLine = read.nextLine();
                arr = nextLine.split(";");
                id = arr[0];
                name = arr[1];
                address = arr[2];
                contact = arr[3];
                
                ID = Integer.parseInt(id);
                
                addData(ID, name, contact, address);	
			}
			read.close();
        } catch (FileNotFoundException e){
           System.err.println("File not found");
        }
        
    }

    /**
     * adds item to the database 
     * @param id supplier id 
     * @param name supplier name 
     * @param contact supplier contact 
     * @param address supplier address 
     */
    public void addData(int id, String name, String contact, String address){
        try {
            query =  "INSERT INTO `suppliers` (`id`,`name`, `contact`, `address`)"
            + "VALUES(?,?,?,?)";
            preStmt = myConn.prepareStatement(query);

            preStmt.setInt(1, id);
            preStmt.setString(2, name);
            preStmt.setString(3, contact);
            preStmt.setString(4, address);
            preStmt.execute();

        } catch (SQLException e) {
            System.err.println("SQL Error in populateDatabase");
            e.printStackTrace();
        }   
    }

    /**
     * clears the supplier table
     */
    public void clearDatabase(){
        try{
            query = "DELETE FROM `suppliers`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * list all of the suppliers in the table
     * @return the suppliers 
     */
    public String listAll(){
        String query = "SELECT * FROM `suppliers`";
        try{
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String list = "";
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");

                list += toString(id, name, address, contact) + "\n";
            }
            list = list.substring(0, list.length() -1);
            return list;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "something went wrong";
    }

    /**
     * checks if the given supplier exist 
     * @param supId supplier id 
     * @return returns true if it does, or false otherwise
     */
    public boolean supplierExists(int supId){
        try{
            query = "SELECT * FROM `suppliers` WHERE `id` = ?";
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, supId);
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * retursn the following information in a string format 
     * @param id supplier id 
     * @param name supplier name 
     * @param address supplier address 
     * @param contact supplier contact 
     * @return String containing all the information
     */
    public String toString(int id, String name, String address, String contact){
        return "id: " + id + ", name: " +name+ ", address: " + address + ", contact: " + contact;
    }

    /**
     * returns the supplier name from the suppplier id 
     * @param supId the supplier id for which to get name of
     * @return name
     */
    public String getSupplierName(int supId){
        try{
            query = "SELECT `name` FROM `suppliers` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, supId);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            // rs.next();
            return rs.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * the main function of the class, used at beginning to fill suppliers table with supplier from the text file
     */
    public static void main(String[] args) throws SQLException{
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        SupplierDatabaseController db = new SupplierDatabaseController(myConn);
        db.clearDatabase();
        db.populateDatabase();

        System.out.println("Supplier database reset");
    }

}