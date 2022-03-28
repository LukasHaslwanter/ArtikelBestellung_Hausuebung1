package Kunden_Artikel_MySql;

import java.sql.*;

public class Artikel {
	
	public static void createTableArtikel(Connection con) {
		try { 			// try wird immer ausgeführt
			Statement stmt = con.createStatement(); 				// benötigen wir für das updaten in der Datenbank
			//String sql = "DROP TABLE IF EXISTS Artikel;";
			//stmt.executeUpdate(sql);
			String sql = "CREATE TABLE IF NOT EXISTS Artikel" + 
					"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+ 
					"bezeichnung VARCHAR(30)," + 
					"preis double," + 
				    "lagerbestand int);";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("createArtikel erfolgreich");
		} catch (SQLException e) { // catch falls Exeption im try
			e.printStackTrace();
			System.out.println("createTable fehlgeschlagen");
		}
			
		}
		

	public static void insertIntoArtikel(Connection con, int id, String bezeichnung, double preis, int lagerbestand) {
		try {
			Statement stmt = con.createStatement();
			String sql = "insert into Artikel (id,bezeichnung, preis, lagerbestand)" + 
					"values ("+id+" \"" + bezeichnung + "\", \"" + preis + "\", \"" + lagerbestand + "\");";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("InsertArtikel erfolgreich");
		} catch (SQLException e) {
			System.out.println("InsertArtikel fehlgeschlagen");
			e.printStackTrace();
		}
	}
	
	public static void Lagerupdate(Connection con, int lagerbestand, int id) {
		try {
			Statement stmt = con.createStatement();
			String sql = "update Artikel set lagerbestand = "+ lagerbestand +", where Artikelid = "+ id +");";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Lagerupdate erfolgreich");
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Lagerupdate fehlgeschlagen");
		}
	}
	
	
}
