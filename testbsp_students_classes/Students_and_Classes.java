package testbsp_students_classes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Students_and_Classes {
	
	public static void createTable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists S_and_C (StudentID int, Class_ID int, zuordnungsdatum date, primary key(StudentID, Class_ID), foreign key(StudentID) references Students(StudentID), foreign key(Class_ID) references Classes(Class_ID));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Cannot create Table S_and_C");
			System.exit(1);
		}
	}

	public static void insertIntoS_zu_K(Connection c, int s_id, int c_id) {
		LocalDate cDate = LocalDate.now();
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into S_and_C values(" + s_id + "," + c_id + ",\'" + cDate + "\');";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert this S_and_C");
		}
	}

}
