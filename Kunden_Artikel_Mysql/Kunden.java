package Kunden_Artikel_MySql;

import java.sql.*;


public class Kunden {
	
	public static void createTableKunde(Connection c) {
        try {
            Statement stmt = c.createStatement();
            //String sql = "DROP TABLE IF EXISTS Kunde;";
            //stmt.executeUpdate(sql);
            String sql = "CREATE TABLE IF NOT EXISTS Kunde" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(30)," +
                    "email VARCHAR(25));";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("createKunde erfolgreich");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("createKunde fehlgeschlagen");
        }
    }

    public static void insertIntoKunde(Connection c, String name, String email) {
        try {
            Statement stmt = c.createStatement();
            String sql = "insert into Kunde (name, email) values" +
                    "(\"" + name + "\", \"" + email +"\");";
            stmt.executeUpdate(sql);
            stmt.close();
			System.out.println("Insert erfolgreich");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert fehlgeschlagen");
        }
    }
}
