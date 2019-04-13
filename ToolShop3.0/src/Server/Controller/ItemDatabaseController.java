package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


/**
 * controller responsible for communicating with the items table of the database 
 * @author Shamin Rahman, Kelvin Tran , avneet
 * @since April 12 2019
 */
public class ItemDatabaseController{
    /**
     * the database connections
     */
    Connection myConn;
    /**
     *  string query that communicates with the data base 
     */
    String query;
    /**
     * prepared statement for the data base
     */
    PreparedStatement preStmt;

    /**
     * constructor for DatabaseController that initializes the connection to the desired value
     * @param conn the databse connection
     */
    public ItemDatabaseController(Connection conn){
        try {
            myConn = conn;
        } catch (Exception e) {
            System.err.println("error connecting to database");
            e.printStackTrace();
        }
    }

    /**
     * only to be used at the beginning once to fill database with items from text file
     */
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

    /**
     * used to add an item to the items table
     * @param id is the id value of the item
     * @param name name of the item
     * @param cost cost of the item
     * @param supID supplier of the item
     * @param amount quantity of the item
     */
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

    /**
     * completely clears items table, deletes all rows
     */
    public void clearDatabase(){
        try{
            query = "DELETE FROM `items`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * used to list all of the items that are currently in database 
     * @return returns a string of all the items
     */
    public String listAll(){
        String query = "SELECT * FROM `items`";
        try{
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String list = "";
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                int supId = rs.getInt("suppId");
                int stock = rs.getInt("stock");
                list += toString(id, name, stock, price, supId) + "\n";
            }
            list = list.substring(0, list.length() -1);
            return list;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "something went wrong";
    }
    /**
     * deletes a desired item from the items table
     * @param itemName the name of the item that should be removed from the database 
     */
    public void deleteItem(String itemName){
        try{
            // query = "DELETE FROM `items` WHERE name like" + "`%" + itemName + "%`";
            query = "DELETE FROM `items` WHERE `name` = ?";
            preStmt = myConn.prepareStatement(query);
            preStmt.setString(1, itemName);
            preStmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * deletes an item from the items table
     * @param itemId the id of the item that should be removed from the databse
     */
    public void deleteItem(int itemId){
        try{
            query = "DELETE FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
            preStmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * searches for an item on the data base 
     * @param itemId is the id of the item 
     * @return returns information about the item with the given id 
     */
    public String search(int itemId){
        String itemToString = null;
        try{
            query = "SELECT * FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int supId = rs.getInt("suppId");
            int stock = rs.getInt("stock");
            itemToString = toString(id, name, stock, price, supId);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return itemToString;
    }
    /**
     * searches for an item on the data base 
     * @param itemName is the name of the item 
     * @return returns information about the item with the given name 
     */
    public String search(String itemName){
        String itemToString = null;
        try{
            query = "SELECT * FROM `items` WHERE `name` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setString(1, itemName);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int supId = rs.getInt("suppId");
            int stock = rs.getInt("stock");
            itemToString = toString(id, name, stock, price, supId);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return itemToString;
    }

    /**
     * gets the stock of items specified by id
     * @param itemId id of item
     * @return stock of item, or -1 if no such item
     */
    public int getStock(int itemId){
        try{
            query = "SELECT `stock` FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return -1;
            }
            // rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * obtains the supplier of the item 
     * @param itemId the id of the item that we want to obtain the supplier of 
     * @return the supplier id of the item
     */
    public int getSupplierId(int itemId){
        try{
            query = "SELECT `suppId` FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return -1;
            }
            // rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * adjust the number in stock to the desired value
     * @param itemId the id of the item that should be adjusted 
     * @param newStock the new stock value of the item 
     */
    public void setStock(int itemId, int newStock){
        try{
            query = "UPDATE `items` SET `stock` = ? WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, newStock);
            preStmt.setInt(2, itemId);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
            System.err.println("error setting stock");
        }
    }

    /**
     * decreases an item quantity
     * @param itemId the item whose quantity shall be adjusted
     * @param amount the amount that the item shall decrease by 
     */
    @Deprecated
    public void decreaseQuantity(int itemId, int amount){
        int currentStock = 0;
        String query = "SELECT * FROM `items`";
        try{
            Statement stmt = myConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                int supId = rs.getInt("suppId");
                int stock = rs.getInt("stock");
                if(id == itemId){
                    currentStock = stock;
                    break;
                }
            }
            if(currentStock > amount){
                int newStock = currentStock - amount;
                query = "UPDATE `items` SET `stock` = " + newStock + " WHERE `id` = " + itemId;
                preStmt = myConn.prepareStatement(query);
                preStmt.execute();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * writes the item information to a string 
     * @param id item id 
     * @param name item name
     * @param stock item quantity 
     * @param price item price 
     * @param supId item supplier id 
     * @return the String containing all the item's information
     */
    public String toString(int id, String name, int stock, double price, int supId){
        return "id: " + id + ", item name: " + name +
                ", quantity in stock: " + stock + 
                ", price: $" + price +
                ", supplier id: " + supId;
    }
    /**
     * gets the number of items 
     * @return the number of items 
     */
    public String getItemCount(){
        int rows = 0;
        try {
            preStmt = myConn.prepareStatement("SELECT COUNT(*) FROM `items`");
            ResultSet rs = preStmt.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.toString(rows);
    }

    /**
     * writes the following information to a string, used for ordering 
     * @param itemId item id 
     * @param amountOrdered amound needed to order 
     * @param supplierName supplier name 
     * @return String containing all this info
     */
    public String toString2(int itemId, int amountOrdered, String supplierName){
        String itemToString2 = null;
        try {
            query = "SELECT * FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return null;
            }
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int supId = rs.getInt("suppId");
            int stock = rs.getInt("stock");

            itemToString2 = "Item description: " + name + "\n" +
            "Amount ordered: " + amountOrdered + "\n" +
            "Supplier: " + supplierName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemToString2;
    }

    /**
     * check if item alreayt exist 
     * @param itemId the item to be checked 
     * @return true if item already exists, false otherwise
     */
    public boolean itemAlreadyExists(int itemId){
        try{
            query = "SELECT * FROM `items` WHERE `id` = ?";// + itemId;
            preStmt = myConn.prepareStatement(query);
            preStmt.setInt(1, itemId);
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
     * main method that runs clears the data base and populates it at the start of the program 
     * @param args arguments from the command line 
     * @throws SQLException if there is an issue with the SQL databse or queries
     */
    public static void main(String[] args) throws SQLException{
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        ItemDatabaseController db = new ItemDatabaseController(myConn);
        // to reset the database of items to items found in text file
        db.clearDatabase();
        db.populateDatabase();

        System.out.println("items database reset");
    }

}
