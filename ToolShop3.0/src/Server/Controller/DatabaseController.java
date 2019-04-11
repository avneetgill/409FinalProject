package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseController{
    Connection myConn;
    String query;
    PreparedStatement preStmt;

    public DatabaseController(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
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
     * completely clears database, deletes all rows
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
     * 
     * @param itemId
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

    public String toString(int id, String name, int stock, double price, int supId){
        return "id: " + id + ", item name: " + name +
                ", quantity in stock: " + stock + 
                ", price: $" + price +
                ", supplier id: " + supId;
    }

    public String getItemCount(){
        // System.out.println("   db: counting items");
        int rows = 0;
        try {
            preStmt = myConn.prepareStatement("SELECT COUNT(*) FROM `items`");
            ResultSet rs = preStmt.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("   db: finished counting items");
        return Integer.toString(rows);
    }

    public String toString2(int itemId, int amountOrdered){
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
            "Supplier: " + supId;       // TODO needs to be changed to supplier name

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemToString2;
    }

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
    
    public static void main(String[] args) {
        DatabaseController db = new DatabaseController();

        db.clearDatabase();
        db.populateDatabase();

        System.out.println("items database reset");
    }

}
