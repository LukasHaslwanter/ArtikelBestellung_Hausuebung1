package JSON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class json_ausführen {

	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://localhost:3306/json";
			String user = "root";
			String password = "";

			Connection c = getConnection(url, user, password);
			
			dropTable(c,"JSON_Read");
			json_read.createTable(c);
			json_read.read(c,"C://Users//LukasHaslwanter//Documents//Infi//jsonRead.txt");
			json_write.write(c,"C:\\Users\\LukasHaslwanter\\Documents\\Infi\\jsonWrite.txt");
			
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	static void dropTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists " + tableName + ";";
			System.out.println("Table " + tableName + " wurde gelöscht!");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	static Connection getConnection(String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}


