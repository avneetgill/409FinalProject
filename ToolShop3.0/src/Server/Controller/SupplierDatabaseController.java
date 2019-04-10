package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SupplierDatabaseController{
    Connection myConn;
    String query;
    PreparedStatement preStmt;

    SupplierDatabaseController(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?user=root","root", "Rocky@299");
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

    public String toString(int id, String name, String address, String contact){
        return "id: " + id + ", name: " +name+ ", address: " + address + ", contact: " + contact;
    }

}