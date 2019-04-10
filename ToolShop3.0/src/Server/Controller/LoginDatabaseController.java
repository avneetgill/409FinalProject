package Server.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class LoginDatabaseController{
    Connection myConn;
    String query;
    PreparedStatement preStmt;

    public LoginDatabaseController(){
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        } catch (Exception e) {
            System.err.println("error connecting to database");
            e.printStackTrace();
        }
    }

    public void addData(String user, String pass){
        try {
            query =  "INSERT INTO `logins` (`username`,`password`)"
            + "VALUES(?,?)";
            preStmt = myConn.prepareStatement(query);

            preStmt.setString(1, user);
            preStmt.setString(2, pass);

            preStmt.execute();
        } catch (SQLException e) {
            System.err.println("SQL Error in populateDatabase");
            e.printStackTrace();
        }   
    }

    public void clearDatabase(){
        try{
            query = "DELETE FROM `logins`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean validateUser(String user, String pass){
        try{
            query = "SELECT * FROM `logins` WHERE `username` = ? AND `password` = ?";
            preStmt = myConn.prepareStatement(query);
            preStmt.setString(1, user);
            preStmt.setString(2, pass);
            // preStmt.execute();
            ResultSet rs = preStmt.executeQuery();
            if(!rs.next()){
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

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

    public static void main(String[] args) {
        LoginDatabaseController db = new LoginDatabaseController();

        // db.addData("TerryLund", "8002");
        // db.addData("PendiFud", "1998");

        // boolean yuh = db.validateUser("PendiFud", "1998");
        // System.out.println(yuh);
    }

}
