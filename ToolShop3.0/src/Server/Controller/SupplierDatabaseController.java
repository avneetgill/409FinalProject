package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SupplierDatabaseController{
    Connection myConn;
    String query;
    PreparedStatement preStmt;

    SupplierDatabaseController(Connection conn){
        try {
            myConn = conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void clearDatabase(){
        try{
            query = "DELETE FROM `suppliers`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

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

    public String toString(int id, String name, String address, String contact){
        return "id: " + id + ", name: " +name+ ", address: " + address + ", contact: " + contact;
    }

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

    public static void main(String[] args) throws SQLException{
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        SupplierDatabaseController db = new SupplierDatabaseController(myConn);
        db.clearDatabase();
        db.populateDatabase();

        System.out.println("Supplier database reset");
    }

}