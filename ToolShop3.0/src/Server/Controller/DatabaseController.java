package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseController{
    Connection myConn;
    String query;
    PreparedStatement preStmt;

    DatabaseController(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?user=root","root", "Rocky@299");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateDatabase(){
        try {
            Scanner read = new Scanner(new File("items.txt"));
            read.useDelimiter(";");
			String id, name, quantity, price, supplierID, nextLine;
			String[] arr = new String[5];
			int ID, amount, supID; double cost;
			while(read.hasNext()){
				nextLine = read.nextLine();
				arr = nextLine.split(";");
				id = arr[0];
				name = arr[1];
				quantity = arr[2];
				price = arr[3];
				supplierID = arr[4];
				
				cost = Double.parseDouble(price);
				ID = Integer.parseInt(id);
				amount = Integer.parseInt(quantity);
                supID = Integer.parseInt(supplierID);
                
                addData(ID, name, cost, supID, amount);	
			}
			read.close();
        } catch (FileNotFoundException e){
           System.err.println("File not found");
        }
        
    }

    public void addData(int id, String name, double cost, int supID, int amount){
        try {
            query =  "INSERT INTO `items` (`id`,`name`, `price`, `suppId`, `stock`)"
            + "VALUES(?,?,?,?,?)";
            preStmt = myConn.prepareStatement(query);

            preStmt.setInt(1, id);
            preStmt.setString(2, name);
            preStmt.setDouble(3, cost);
            preStmt.setInt(4, supID);
            preStmt.setInt(5, amount);

            preStmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL Error in populateDatabase");
            e.printStackTrace();
        }   
    }

    public void clearDatabase(){
        try{
            query = "DELETE FROM `items`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
