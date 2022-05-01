package JSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class json_read {
	
	static void createTable(Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists json (Produkt varchar(30), preis long, age long, beschreibung varchar(30), primary key(produkt));";
			stmt.executeUpdate(sql);
			System.out.println("Table json wurde erstellt!");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void read(Connection c, String file) {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			String d = "";
			while (s.hasNextLine()) {
				d = s.nextLine();
				Object ob = new JSONParser().parse(d);
				JSONObject js = (JSONObject) ob;
				
				String Produkt = (String)js.get("Produkt");
				long preis = (long)js.get("preis");
				long age = (long)js.get("age");
				String beschreibung = (String)js.get("beschreibung");
				
				String sql = "insert into json (produkt, preis, age, beschreibung) values (?, ?, ?, ?);";
				
				PreparedStatement stmt = c.prepareStatement(sql);
				stmt.setString(1, Produkt);
				stmt.setLong(2, preis);
				stmt.setLong(3, age);
				stmt.setString(4, beschreibung);
				stmt.executeUpdate();
				stmt.close();
				System.out.printf("JSON_Reader:  Vorname: " + Produkt + "   Nachname: " + preis + "   Alter:  " + age +  " Beschreibung: " + beschreibung + "\n");
				System.out.println();
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File wurde nicht gefunden!");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}