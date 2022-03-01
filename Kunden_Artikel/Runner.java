package Kunden_Artikel;

import java.sql.*;

public class Runner {
	
	
	//@SuppressWarnings("finally") für eclipse: unterdrückt warnung - dass kein finally ausgeführt wird
    public static Connection createConnection() { 	//wir behandeln die exeption in dieser Klasse
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Connection funktioniert");
            return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\LukasHaslwanter\\Desktop\\Sqlite3\\Kunden_Artikel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1); 	//0-> kein Fehler, exit; - Wenn 1 -> bzw. größer 0: irgndein Fehler
            return null; 
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Datenbank konnte nicht gefunden werden");
            System.exit(1);
            return null; 
        }
        finally {
            System.out.println("Sind im finally");
            System.out.println();
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
			Bestellung.insertIntoBestellung(c, 2, 3, 2,"30.11.2021");  		//int kundenid, int artikelid, int anzahl, String bestelldatum)
			Bestellung.insertIntoBestellung(c, 3, 2, 100,"14.05.1980");
			System.out.println();
			
			System.out.println("BESTELLUNG ANZEIGE:");
			Bestellung.select(c, 1);
			
			Bestellung.löschen(c, 1, 1, "22-23-2020"); // int kundenid, int artikelid, String bestelldatum
			Bestellung.select(c, 1);
			
		//	Bestellung.updaten(c, 2, 3, 2, "30.11.2021", 1, 3, 99); //kundenid, artikelid, anzahl, bestelldatum, neuekundenid,neueartikelid,neueanzahl

		//	Bestellung.select(c, 1);
			/*boolean b = Bestellung.Lagercheck(c,3); //bestellid
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
			Bestellung.selectBestellung(c, 1);*/
			c.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
