package Kunden_Artikel_MySql;

import java.sql.*;


public class Bestellung {
	
	public static void createTableBestellung(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS Bestellung;";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Bestellung" +
            		"(kundenid INTEGER," +		//keine Bestellid will der prof, denn es ist eine unn�tige spalte f�r das programm 
                    "artikelid INTEGER," +
                    "anzahl INTEGER," +
                    "bestelldatum VARCHAR(30)," +
                    "primary key(bestelldatum)," +
                    "FOREIGN KEY(kundenid) REFERENCES Kunde(id) ," +		//default - on update restrict
                    "FOREIGN KEY(artikelid) REFERENCES Artikel(id) );";	
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("createBestellung erfoglreich");
        } catch (SQLException e) {
        	System.out.println("createBestellung fehlgeschlagen");
            e.printStackTrace();
        }
    }
	
	public static void insertIntoBestellung(Connection c, int kundenid, int artikelid, int anzahl, String bestelldatum) {
        try {
            Statement stmt = c.createStatement();
            String sql = "insert into Bestellung (kundenid, artikelid, anzahl, bestelldatum) values" +
                    "("+ kundenid + ", "  + artikelid + ", " + anzahl + ", \"" + bestelldatum + "\");";
            stmt.executeUpdate(sql);
        	stmt.close();
        	System.out.println("insertBestellung erfolgreich");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("insertBestellung fehlgeschlagen");
        }
    }
	
	public static boolean Lagercheck(Connection c, int bestellid) {
		int Artikelid = 0;
		int anzahl = 0;
		int lagerbestand = 0;
		try{
			Statement stmt = c.createStatement();
			System.out.println("Lagercheck:");
			ResultSet rs =stmt.executeQuery("select Artikelid from Bestellung where Bestellid = "+ bestellid +";");	
			Artikelid = rs.getInt("Artikelid");
			rs.close();
			
			ResultSet rs2 =stmt.executeQuery("select anzahl from Bestellung where Bestellid = "+ bestellid +";");
			anzahl = rs2.getInt("anzahl");
			rs2.close();
			
			ResultSet rs3 =stmt.executeQuery("select lagerbestand from Artikel where id = "+ Artikelid +";");
			lagerbestand = rs3.getInt("lagerbestand");
			rs3.close();
			stmt.close();
			
		}catch(SQLException e) {
			System.out.println("Lagercheck fehlgeschlagen");
			e.printStackTrace();
		}
		
		if(lagerbestand >= anzahl) {	//unter catch da keine SQL exeption im if passieren wird
			System.out.println("Lagerbestand reicht");
			return true;
			
		}else {
			Bestellungl�schen(c, bestellid);
			return false;
		}		
	}
	
	
	public static void selectBestellung(Connection c, int kundenid) {
		try {
			Statement stmt = c.createStatement();
			System.out.printf("Bestellung mit kundenid: " + kundenid + ":");
			System.out.println();
			String sql = "SELECT k.name, a.bezeichnung, b.anzahl, " +
					"(SELECT a.preis * b.anzahl FROM Artikel a INNER JOIN Bestellung b ON a.id = b.artikelID WHERE b.kundenid == " + kundenid + ")AS gesamtPreis " + 
					"FROM Kunde k INNER JOIN Bestellung b ON k.id == b.kundenid " +
					"INNER JOIN Artikel a ON a.id == b.artikelid " +
					"WHERE b.kundenid == " + kundenid + ";";
			ResultSet rs = stmt.executeQuery(sql);
			
			while ( rs.next() ) {
		         String  name = rs.getString("name");
		         String  bezeichnung = rs.getString("bezeichnung");
		         int anzahl = rs.getInt("anzahl");
		         double gesamtPreis = rs.getDouble("gesamtPreis");
		         
		         System.out.println("NAME = " + name );
		         System.out.println("BEZEICHNUNG = " + bezeichnung );
		         System.out.println("ANZAHL = " + anzahl );
		         System.out.printf("GESAMTPREIS = %.2f�", gesamtPreis);
		         System.out.println();
		      }
			
			
		    rs.close();
		    stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Bestellungl�schen(Connection c, int bestellid) {
		Statement stmt;
		try {
			stmt = c.createStatement();
			String sql = "delete from Bestellung where bestellid ="+ bestellid +";";
			stmt.executeUpdate(sql);	//"Erfolgsausgabe" nach dem executen, weil wenn exeption dann kein Erfolg
			System.out.println();
			stmt.close();
			System.out.println("Bestellung mit der Bestellid " + bestellid + " wurde gel�scht");
		}catch(SQLException e) { 
			e.printStackTrace();
		}
		
	}
	
	public static void Bestellungupdaten(Connection c, int bestellid, int anzahl, int neuekundenid, int neueartikelid, int neueanzahl) {
		try {
			Statement stmt = c.createStatement();
			String sql = "update Bestellung set kundenid = "+ neuekundenid +", artikelid = "+ neueartikelid +", "
					+ "anzahl ="+ neueanzahl +" where bestellid ="+ bestellid +" and anzahl ="+ anzahl +";";
			stmt.executeUpdate(sql);
			System.out.printf("Bestellung mit der bestellid "+ bestellid +" wurde ge�ndert. Neue kundenid = "+ neuekundenid +"");
			System.out.println();
			stmt.close();
	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
