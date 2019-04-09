package jdbcDemo;
import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?user=root","root", "Rocky@299");
			
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from items");
			while(myRs.next()) {
				System.out.println(myRs.getString("name"));
			}
			System.out.println("uuh");
		}catch(Exception exc) {
			exc.printStackTrace();
		}

	}

}
