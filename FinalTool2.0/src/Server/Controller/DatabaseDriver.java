package src.Server.Controller;

import java.sql.*;

public class DatabaseDriver {

    public static void main(String[] args) {
        // to run this main: javac -d classes src/Server/Controller/DatabaseDriver.java
        // java -cp classes;C:\class\ensf409\FinalProject\409FinalProject\FinalTool2.0\classes Client.Controller.Controller
        try{
            // Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toolshop", "root", "799228002");

            Statement myStatement = myConn.createStatement();

            ResultSet myResult = myStatement.executeQuery("select * from items");

            while(myResult.next()){
                System.out.println(myResult.getString("id") + ", " + myResult.getString("name"));
            }



        }catch(Exception a){
            a.printStackTrace();
        }

    }
}