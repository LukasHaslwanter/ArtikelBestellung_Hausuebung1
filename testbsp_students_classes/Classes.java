package testbsp_students_classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Classes {
	
	public static void createClass(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists Classes (Class_ID int PRIMARY KEY AUTO_INCREMENT, name varchar(20), room varchar(10), kvnname varchar(30));";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("createClass was successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot create Table Classes");
			System.exit(1);
		}
	}

	public static void insertIntoClass(Connection c, String name, String room, String kvnname) {
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into Classes(name,room,kvnname) values(\"" + name + "\",\"" + room + "\",\"" + kvnname
					+ "\");";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("insertIntoClass was successful");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this Classes");
		}
	}

	public static int selectIDfromClass(Connection c, String name) {
		int k_id = 0;
		try {
			Statement stmt = c.createStatement();
			String sql = "select Class_id from Classes where name = \"" + name + "\" limit 1;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				k_id = rs.getInt("Class_id");
			}
			stmt.close();
			rs.close();
			System.out.println("selectID was successful. Name = " + name +". RoomID = "+ k_id + " ");
			return k_id;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not select this from Klassen");
			return 0;
		}
	}

}
