package testbsp_students_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class runner {
	
	public static void main(String[] args){
		Connection c = getConnection();
		setAutoCommit(c, true);
		Students.createStudent(c);
		Students.insertIntoSchueler(c,"Mike", "Anderson", 21, "male");
		Students.insertIntoSchueler(c, "Julia", "Meier", 19, "female");
		Students.selectIDfromSchueler(c, "Mike", "Anderson");
		
		Classes.createClass(c);
		Classes.insertIntoClass(c, "Musikraum", "A304", "Ghünter");
		Classes.insertIntoClass(c, "Labor", "I200", "Grief");
		Classes.selectIDfromClass(c, "Musikraum");
		
		Students_and_Classes.createTable(c);
		
		
		
		
	}
		
		
	
	
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/testbsp_students_classes";	//jdbc:mysql://C:\\xampp\\htdocs\\Kunden_Artikel - 
	    String user = "root";		//benutzer für mysql anlegen
	    String pass = "";

	    try {
	    Connection con = DriverManager.getConnection(url, user, pass);
	    System.out.println("Verbindung erfolgreich hergestellt");
	    return con;

	    } catch (SQLException e) {
	    System.out.println(e.getMessage());
	    return null;
	    }
	    
		}
	
	
	public static void setAutoCommit(Connection c, boolean ToF) {
		try {
			c.setAutoCommit(ToF);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Set Auto Commit didnt work!");
		}

	}
}



