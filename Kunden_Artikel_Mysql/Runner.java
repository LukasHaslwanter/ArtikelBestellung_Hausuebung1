package Kunden_Artikel_MySql;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Runner {
	
	

		public static void main(String[] args){

		    String url = "jdbc:mysql://localhost:3306/Kunden_Artikel_mysql";	//jdbc:mysql://C:\\xampp\\htdocs\\Kunden_Artikel - 
		    String user = "root";		//benutzer für mysql anlegen
		    String pass = "";

		    try {
		    Connection con = DriverManager.getConnection(url, user, pass);
		    System.out.println("Verbindung erfolgreich hergestellt");

		    } catch (SQLException e) {
		    System.out.println(e.getMessage());
		    }
		}
		
	
	
	public static void main(String[] args) {
		try {
			Connection c = createConnection();
			c.setAutoCommit(true);
			
			Statement stmt = c.createStatement();
			String sql = ("Pragma foreign_keys = ON;");
			stmt.executeUpdate(sql);
			stmt.close();
			
			Kunden.createTableKunde(c);
			Artikel.createTableArtikel(c);
			Bestellung.createTableBestellung(c);
			System.out.println("Tabellen erstellt");
			System.out.println();
		
			System.out.println("KUNDEN:");
			Kunden.insertIntoKunde(c, "Gustav Gans", "Gustavglück@gmx.com");
			Kunden.insertIntoKunde(c, "Max Draxer", "DraxerM@tsn.at");
			Kunden.insertIntoKunde(c, "Karl Karlos", "Karl.Karl@gmail.at");
			System.out.println();
			
			System.out.println("ARTIKEL:");
			Artikel.insertIntoArtikel(c, "Überraschung.", 27.60, 30);  //bezeichnung, preis, lagerbestand
			Artikel.insertIntoArtikel(c, "Ak-47", 500.00, 20);
			Artikel.insertIntoArtikel(c, "Rtx", 6.99, 25);
			System.out.println();
			
			System.out.println("BESTELLUNG");
			//kundenid,artikelid,anzahl,bestelldatum
			Bestellung.insertIntoBestellung(c, 1, 1, 14,"22-23-2020"); 			//Gustav kauft 14 Überraschungen
			Bestellung.insertIntoBestellung(c, 2, 3, 2,"30.11.2021");  
			Bestellung.insertIntoBestellung(c, 3, 2, 100,"14.05.1980");
			System.out.println();
			
			boolean b = Bestellung.Lagercheck(c,3); //bestellid
			/*if(b = true) {
				System.out.println("Lagerbestand reicht");
			}
			else {
				Bestellung.Bestellunglöschen(c, 2);
			}
			
			System.out.println();

			System.out.println("BESTELLUNG ANZEIGE:");
			//Bestellung.selectBestellung(c, 1);
			System.out.println();
			
			Bestellung.Bestellunglöschen(c, 1);
			Bestellung.selectBestellung(c, 1);
			System.out.println();
			
			System.out.println("BESTELLUNG UPDATEN:");
			Bestellung.Bestellungupdaten(c, 3, 100, 1, 1, 13); 	//zuerst alt - dann neu //bestellid,anzahl,neuekundenid,neueartikel,neueanzahl
			Bestellung.selectBestellung(c, 1);
			c.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}*/
}
