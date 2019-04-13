package Server.Controller;
import java.sql.*;

/**
 * class that deals with the Logins table in the database
 * @author kelvin, shamin , avneet
 * @since April 12 2019
 */
public class LoginDatabaseController{
    /**
     * is the data base connection 
     */
    Connection myConn;
    /**
     * string query used to communicate with the database 
     */
    String query;
    /**
     * prepared statement for the data base 
     */
    PreparedStatement preStmt;

    /**
     * class constructor used to intiallize variable to desired values 
     * @param conn conncetion to the database
     */
    public LoginDatabaseController(Connection conn){
        try {
            myConn = conn;
        } catch (Exception e) {
            System.err.println("error connecting to database");
            e.printStackTrace();
        }
    }
    
    /**
     * inserts username and password 
     * @param user the username 
     * @param pass the password 
     */
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
    /**
     * clears the logins table 
     */
    public void clearDatabase(){
        try{
            query = "DELETE FROM `logins`";
            preStmt = myConn.prepareStatement(query);
            preStmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * checks if the given user info is valid 
     * @param user username of the person 
     * @param pass password of the person 
     * @return true if user is valid, false otherwise
     */
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

    /**
     * login main menu 
     * @param args value from command line 
     * @throws SQLException if there is an issue with SQL database or queries 
     */
    public static void main(String[] args) throws SQLException{
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop?user=root","root", "799228002");
        LoginDatabaseController db = new LoginDatabaseController(myConn);

        db.clearDatabase();

        db.addData("JohnSmith", "8002");
        db.addData("Yahoo", "1998");

        System.out.println("Logins reset");
    }

}
