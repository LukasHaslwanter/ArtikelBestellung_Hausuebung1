package testbsp_students_classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Students {
	
	public static void createStudent(Connection c) {
			try {
				Statement stmt = c.createStatement();
				String sql = "create table if not exists Students(StudentID int primary key auto_increment, fn varchar(30), ln varchar(30), age int,gender varchar(20));";
				stmt.executeUpdate(sql);
				stmt.close();
				System.out.println("Table succesfully created");
			}
			catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Cant create table: Students");
				System.exit(1);
			}
	}
	
	public static void insertIntoSchueler(Connection c, String fn, String ln, int age, String gender) {
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into Students(fn,ln,age,gender) values(\"" + fn + "\",\"" + ln + "\"," + age + ",\"" + gender + "\" );";

			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("insert was a success");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this Student");
		}
	}

	public static int selectIDfromSchueler(Connection c, String fn, String ln) {
		int s_id = 0;
		try {
			Statement stmt = c.createStatement();
			String sql = "select studentId from students where fn = \"" + fn + "\" and ln = \"" + ln + "\" limit 1;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				s_id = rs.getInt("studentId");
			}
			stmt.close();
			rs.close();
			System.out.println("select id was successfully. First Name: "+ fn +". Last Name: "+ ln +". StudentID: "+ s_id +"");
			return s_id;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not select this from Student");
			return 0;
		}
	}
}
